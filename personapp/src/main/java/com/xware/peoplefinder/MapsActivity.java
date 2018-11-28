package com.xware.peoplefinder;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public class MapsActivity extends MainMenu implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String address;
    private String mname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b  = getIntent().getExtras();
        if (b!=null && b.get("address")!= null)
                this.address= (String)b.get("address") ;
        System.out.println("ADDRESS IS " + this.address) ;
        this.mname = (String)b.get("fullname") ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    //    toolbar.setTitle(getTitle());
        setContentView(R.layout.activity_maps);
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
        try {


            getLocationFromAddress(googleMap);
        } catch (java.io.IOException e) {

            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(19));
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // Show rationale and request permission.
            }

        }
    }

    public void getLocationFromAddress(GoogleMap googleMap) throws java.io.IOException{

        Geocoder geocoder = new Geocoder(this);
       // Address address = geocoder.getFromLocationName("731 Market St, San Francisco, CA 94103", 1).get(0);
        //  assertTrue(address.hasLatitude());
        //   assertTrue(address.hasLongitude());
        //  GoogleMap googleMap;
        mMap = googleMap;
        if (this.address == null || this.address.equals("") ) {
            Toast.makeText(this, "No address to show !", Toast.LENGTH_SHORT).show();
            System.out.println("ADDRESS IS null IN GetLocationFromAddress" ) ;

        }
        else{
            try {
                Address address = geocoder.getFromLocationName(this.address, 1).get(0);
                // Add a marker in Sydney and move the camera
                LatLng market = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(market).title(mname));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(market));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            }
            catch(Exception e){
                Toast.makeText(this,"There is something wrong with this address "+this.address ,Toast.LENGTH_LONG) ;
            }

        }

    }
}
