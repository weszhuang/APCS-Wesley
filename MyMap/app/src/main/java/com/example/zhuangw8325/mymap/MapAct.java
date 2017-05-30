package com.example.zhuangw8325.mymap;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapAct extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    boolean type = true;
    private LocationManager locationManager;
    private boolean isGPSenabled = false;
    private boolean isNetwrokEnabled = false;
    private boolean canGetLocation = false;
    private static final long MIN_TIME_BW_UPDATES = 1000*15;
    private static final float MIN_DISTANCE_CHANGE_UPDATES = 5.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng birth = new LatLng(38, -77);
        mMap.addMarker(new MarkerOptions().position(birth).title("Born here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(birth));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("MyMapApp", "Failed permission check 1");
            Log.d("MyMapApp", Integer.toString(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)));
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("MyMapApp", "Failed permission check 2");
            Log.d("MyMapApp", Integer.toString(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)));
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }
        mMap.setMyLocationEnabled(true);

    }

    public void toggle(View view) {
        if (mMap.getMapType() == 1) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public void getLocation(){
        try{
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            isGPSenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSenabled) Log.d("MyMapApp", "getLocation: GPS is enabled");

            isNetwrokEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isNetwrokEnabled) Log.d("MyMapApp", "getLocation: Network is enabled");

            if (!isGPSenabled && !isNetwrokEnabled){
                Log.d("MyMapApp", "getLocation: No provider is enabled");
            }else {
                canGetLocation = true;
                if (isGPSenabled){
                    Log.d("MyMapApp", "getLocation: GPS enabled - Requesting location updates");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_UPDATES, locationListenerGps);
                    Log.d("MyMapApp", "getLocation: Network GPS update request is sussessful");
                    Toast.makeText(this, "Using GPS", Toast.LENGTH_SHORT);
                }
                if (isNetwrokEnabled){
                    Log.d("MyMapApp", "getLocation: Network enabled - Requesting location updates");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_UPDATES, locationListenerGps);
                    Log.d("MyMapApp", "getLocation: Network GPS update request is sussessful");
                    Toast.makeText(this, "Using Network", Toast.LENGTH_SHORT);
                }
            }
        }catch (Exception e){
            Log.d("MyMapApp", "Caught an exception is getLocation");
            e.printStackTrace();
        }
    }
}