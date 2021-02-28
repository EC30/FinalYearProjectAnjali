package com.anjali.womensafetyalertapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "EC_FRIENDS_WSAA";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "WSAA_DATA_EC_FRIENDS";
    private static final String COLUMN_DATA_OF = "DATA_OF";
    private static final String COLUMN_DATA = "DATA";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String query = "CREATE TABLE " + DB_TABLE + "(" + COLUMN_DATA_OF + " TEXT ," + COLUMN_DATA + " TEXT);";

        DB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(DB);
    }

    public void delete_wsaa() {
        SQLiteDatabase DB=this.getWritableDatabase();
        DB.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        String query = "CREATE TABLE " + DB_TABLE + "(" + COLUMN_DATA_OF + " TEXT ," + COLUMN_DATA + " TEXT);";
        DB.execSQL(query);
    }

    public void add_wsaa(String data_of,String data){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_DATA_OF,data_of);
        cv.put(COLUMN_DATA, data);
        long result=DB.insert(DB_TABLE,null,cv);
        if(result == -1){
            Toast.makeText(context, "Application failed", Toast.LENGTH_SHORT).show();
        } else{
            //Toast.makeText(context, "Inserted succesfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor read_wsaa(){
        String query="SELECT * FROM " + DB_TABLE;
        SQLiteDatabase DB=this.getReadableDatabase();

        Cursor cursor=null;
        if (DB != null ){
            cursor = DB.rawQuery(query,null);
        }
        return cursor;
    }

    public void update_wsaa(String data_of , String data){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_DATA, data);

        long result=DB.update(DB_TABLE,cv," DATA_OF=?",new String[]{data_of});
        if(result == -1){
            Toast.makeText(context, "Application failed to update", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Process Succesful.", Toast.LENGTH_SHORT).show();
        }
    }

}
