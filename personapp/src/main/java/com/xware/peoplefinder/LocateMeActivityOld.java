package com.xware.peoplefinder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationListener;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationListener;

import android.support.v7.app.AlertDialog;


public class LocateMeActivityOld extends AppCompatActivity {

    LocationManager locationManager;
    double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;
    TextView longitudeValueBest, latitudeValueBest;
    TextView longitudeValueGPS, latitudeValueGPS;
    TextView longitudeValueNetwork, latitudeValueNetwork;
    TextView results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        longitudeValueBest = (TextView) findViewById(R.id.longitudeValueBest);
        latitudeValueBest = (TextView) findViewById(R.id.latitudeValueBest);
        longitudeValueGPS = (TextView) findViewById(R.id.longitudeValueGPS);
        latitudeValueGPS = (TextView) findViewById(R.id.latitudeValueGPS);
        longitudeValueNetwork = (TextView) findViewById(R.id.longitudeValueNetwork);
        latitudeValueNetwork = (TextView) findViewById(R.id.latitudeValueNetwork);
      //  results= (TextView)findViewById(android.support.design.R.results);
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
  //      locationManager.isProviderEnabled(LocationManager.
        boolean b = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!b){
            showAlert();
        }
        return b;

    }

    public void toggleGPSUpdates(View view) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

          //  ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
           //         LocationServices.MY_PERMISSION_ACCESS_FINE_LOCATION);

        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerGPS);
            button.setText(R.string.resume);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);
            button.setText(R.string.pause);
        }
    }
    }

    public void toggleBestUpdates(View view) {
        if(!checkLocation())
            return;
        Button button = (Button) view;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
  //                  LocationServices.MY_PERMISSION_ACCESS_FINE_LOCATION);
            if (button.getText().equals(getResources().getString(R.string.pause))) {
                locationManager.removeUpdates(locationListenerBest);
                button.setText(R.string.resume);
            } else {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                String provider = locationManager.getBestProvider(criteria, true);
                if (provider != null) {
                    locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerBest);
                    button.setText(R.string.pause);
                    Toast.makeText(this, "Best Provider is " + provider, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

   /* public void toggleNetworkUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause)) ) {
            locationManager.removeUpdates(locationListenerNetwork);
            button.setText(R.string.resume);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerNetwork);
            Toast.makeText(this, "Network provider started running", Toast.LENGTH_LONG).show();
            button.setText(R.string.pause);
        }

    }
    */

    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueBest.setText(longitudeBest + "");
                    latitudeValueBest.setText(latitudeBest + "");
                    Toast.makeText(LocateMeActivityOld.this, "Best Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            showAlert();

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueNetwork.setText(longitudeNetwork + "");
                    latitudeValueNetwork.setText(latitudeNetwork + "");
                    Toast.makeText(LocateMeActivityOld.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            showAlert();

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private final LocationListener locationListenerGPS = new LocationListener(){
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueGPS.setText(longitudeGPS + "");
                    latitudeValueGPS.setText(latitudeGPS + "");
                    Toast.makeText(LocateMeActivityOld.this, "GPS Provider update", Toast.LENGTH_SHORT).show();


                  if ( Geocoder.isPresent()) {
                      Geocoder gc = new Geocoder(LocateMeActivityOld.this);
              //        try {
                      //   Location gc.getFromLocation(latitudeGPS, longitudeGPS, 1);
              //        }
                 //     catch (IOException e){
//
                 //     }


                  }



                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

}
