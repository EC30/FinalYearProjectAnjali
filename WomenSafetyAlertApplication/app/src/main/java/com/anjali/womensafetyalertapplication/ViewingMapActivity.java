package com.anjali.womensafetyalertapplication;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewingMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;

    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // 5 Meters

    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(false);

        run_count_down();

    }

    public void run_count_down(){
        new CountDownTimer(60000, 1000) {
            public void onFinish() {
                run_count_down();
            }

            public void onTick(long millisUntilFinished) {
                //btn_resend.setText("RESEND CODE ("+String.valueOf(millisUntilFinished/1000)+")");
                //String my_loc=Common.LastLocation;


                if(!Common.LastLocation.contains("UnKnown")) {
                    String[] splitted = Common.LastLocation.split("/");
                    int height = 70;
                    int width = 70;
                    BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.user_img);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    //latLng=new LatLng(Common.LastLocation);
                    latLng = new LatLng(Double.valueOf(splitted[0]), Double.valueOf(splitted[1]));
                    mMap.clear();
                   // Marker marker= mMap.addMarker(new MarkerOptions()
                     //       .position(latLng)
                       //     .title("My position . Lat:"+ splitted[0]+",Lon:"+splitted[1])
                         //   .snippet("Snippet").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My position . Lat:"+ splitted[0]+",Lon:"+splitted[1]));
                    mMap.resetMinMaxZoomPreference();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng( latLng));
                }
            }

        }.start();
    }


    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}