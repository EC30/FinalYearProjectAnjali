package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    static ImageView profileImage;
    private Button btn_addPhoto, btn_changepassword;
    private TextView nameTV;
    final int CODE_GALLERY_REQUEST=999;
    String encodedImage ;
    Bitmap bitmap;
    String rpasswd, crpasswd,oldpassw;
    String phonelogged2;
    String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage=findViewById(R.id.profileImage);
        btn_addPhoto=findViewById(R.id.addPhoto);
        nameTV=findViewById(R.id.nameTV);
        btn_changepassword=findViewById(R.id.btn_changepassword);
        phonelogged2= HomeActivity.phone_logged_home;
        fullname=HomeActivity.fullname_logged_home;

        nameTV.setText(fullname);

        UrlClass my_url= new UrlClass();
        String load_url=my_url.getUrl()+"upload/"+HomeActivity.phone_logged_home.substring(1)+".jpg";

        Picasso.with(ProfileActivity.this).load(load_url).placeholder(R.drawable.user_img).into(profileImage,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //do smth when picture is loaded successfully
                        btn_addPhoto.setText("Change Photo");
                    }

                    @Override
                    public void onError() {
                        //do smth when there is picture loading error

                    }
                });


        btn_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePsw();
            }
        });

        btn_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ProfileActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE },
                        CODE_GALLERY_REQUEST
                );

               // UploadPhoto(ProfileActivity.this,phonelogged2,profileImage.);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CODE_GALLERY_REQUEST){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"),CODE_GALLERY_REQUEST);
            }else{
                Toast.makeText(this, "You don't have permission to access gallery", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==CODE_GALLERY_REQUEST && resultCode==RESULT_OK && data !=null){
            Uri filePath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filePath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                profileImage.setImageBitmap(bitmap);
                HomeActivity.imageView.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
                UploadPhoto();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes=outputStream.toByteArray();

        //String encodededImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        encodedImage= android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //return  encodedImage;
    }

    public void ChangePsw(){

        AlertDialog.Builder builder=new AlertDialog.Builder(ProfileActivity.this);
        View dialogView= LayoutInflater.from(ProfileActivity.this).inflate(R.layout.changepasswordlayout,null);

        EditText input_oldpassword, input_resetPassword, input_confirmResetPassword;
        Button cancelText, changeText;

        input_oldpassword=dialogView.findViewById(R.id.input_old_password);
        input_resetPassword=dialogView.findViewById(R.id.input_reset_password);
        input_confirmResetPassword=dialogView.findViewById(R.id.input_confirmResetPassword);




        builder.setView(dialogView);
         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
         builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        oldpassw=input_oldpassword.getText().toString();
//                        rpasswd = input_resetPassword.getText().toString();
//                        crpasswd = input_confirmResetPassword.getText().toString();
//                        if(oldpassw.equals("") || rpasswd.equals("") || crpasswd.equals("")){
//                            Toast.makeText(ProfileActivity.this, "Please fill the respective fields", Toast.LENGTH_SHORT).show();
//                        }else if(rpasswd.length()<8){
//                            Toast.makeText(ProfileActivity.this, "Password must be 8 charcters long", Toast.LENGTH_SHORT).show();
//                        }
//                        else if (!rpasswd.equals(crpasswd)) {
//                            Toast.makeText(ProfileActivity.this, "Sorry! Password and confirm password does not match", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            String newps = input_resetPassword.getText().toString();
//                            UrlClass myurl = new UrlClass();
//                            String url = myurl.getUrl();
//                            String cpUrl = url + "changepassword.php";
//
//                            RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
//                            StringRequest stringRequest = new StringRequest(Request.Method.POST, cpUrl, new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    if (response.contains("Password Updated Successfully.")) {
//                                        Toast.makeText(ProfileActivity.this, "Password Changed successfully.", Toast.LENGTH_SHORT).show();
//                                    } else if (response.contains("Cannot change password.")) {
//                                        Toast.makeText(ProfileActivity.this, "Cannot change password", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(ProfileActivity.this, "* Internal Error. Please try again later.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }) {
//                                @Override
//                                protected Map<String, String> getParams() {
//                                    Map<String, String> params = new HashMap<String, String>();
//                                    params.put("phone_login", phonelogged2);
//                                    params.put("passwd_old", oldpassw);
//                                    params.put("passwd_new", newps);
//                                    return params;
//                                }
//
//                                @Override
//                                public Map<String, String> getHeaders() throws AuthFailureError {
//                                    Map<String, String> params = new HashMap<String, String>();
//                                    params.put("Content-Type", "application/x-www-form-urlencoded");
//                                    return params;
//                                }
//                            };
//                            requestQueue.add(stringRequest);
//                    }
                    }

                });
         AlertDialog alertDialog=builder.create();
         alertDialog.show();
         alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 oldpassw=input_oldpassword.getText().toString();
                 rpasswd = input_resetPassword.getText().toString();
                 crpasswd = input_confirmResetPassword.getText().toString();
                 if(oldpassw.equals("") || rpasswd.equals("") || crpasswd.equals("")){
                     Toast.makeText(ProfileActivity.this, "Please fill the respective fields", Toast.LENGTH_SHORT).show();
                 }else if(rpasswd.length()<8){
                     Toast.makeText(ProfileActivity.this, "Password must be 8 charcters long", Toast.LENGTH_SHORT).show();
                 }
                 else if (!rpasswd.equals(crpasswd)) {
                     Toast.makeText(ProfileActivity.this, "Sorry! Password and confirm password does not match", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     String newps = input_resetPassword.getText().toString();
                     UrlClass myurl = new UrlClass();
                     String url = myurl.getUrl();
                     String cpUrl = url + "changepassword.php";

                     RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
                     StringRequest stringRequest = new StringRequest(Request.Method.POST, cpUrl, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             if (response.contains("Password Updated Successfully.")) {
                                 Toast.makeText(ProfileActivity.this, "Password Changed successfully.", Toast.LENGTH_SHORT).show();
                             } else if (response.contains("Cannot change password.")) {
                                 Toast.makeText(ProfileActivity.this, "Cannot change password", Toast.LENGTH_SHORT).show();
                             } else if (response.contains("Old Password Incorrect.")) {
                                 Toast.makeText(ProfileActivity.this, "Your old password is incorrect.", Toast.LENGTH_SHORT).show();
                             } else {
                                 Toast.makeText(ProfileActivity.this, "* Internal Error. Please try again later.", Toast.LENGTH_SHORT).show();
                             }
                         }
                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                         }
                     }) {
                         @Override
                         protected Map<String, String> getParams() {
                             Map<String, String> params = new HashMap<String, String>();
                             params.put("phone_login", phonelogged2);
                             params.put("passwd_old", oldpassw);
                             params.put("passwd_new", newps);
                             return params;
                         }

                         @Override
                         public Map<String, String> getHeaders() throws AuthFailureError {
                             Map<String, String> params = new HashMap<String, String>();
                             params.put("Content-Type", "application/x-www-form-urlencoded");
                             return params;
                         }
                     };
                     requestQueue.add(stringRequest);
                     alertDialog.dismiss();
                 }
             }
         });
       // builder.show();


    }

    private void UploadPhoto(){

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();
        String uploadUrl = url + "fileupload.php";


        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("File Uploaded Successfully")) {
                    Toast.makeText(ProfileActivity.this, "Uploaded.", Toast.LENGTH_SHORT).show();
                    btn_addPhoto.setText("Change Photo");
                } else if (response.contains("Could not upload File")) {
                    Toast.makeText(ProfileActivity.this, "Could not upload File", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "* Internal Error. Please try again later.", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> maps = new HashMap<String, String>();
                maps.put("action","insert");
                maps.put("uploaded_image",encodedImage);
                maps.put("phone_logged",phonelogged2);
                return maps;
            }
        };

        requestQueue.add(stringRequest);
    }
}