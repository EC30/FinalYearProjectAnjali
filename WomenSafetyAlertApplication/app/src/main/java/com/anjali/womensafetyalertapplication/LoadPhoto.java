package com.anjali.womensafetyalertapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadPhoto extends AsyncTask<String,Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... urls) {

        try{
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            Bitmap myBitmap= BitmapFactory.decodeStream(in);
            return myBitmap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
