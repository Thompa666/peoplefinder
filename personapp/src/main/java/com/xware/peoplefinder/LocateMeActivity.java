package com.xware.peoplefinder;

import common.Constants;
import common.FetchAddressIntentService;

/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// package com.google.android.gms.location.sample.locationaddress;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

//import com.google.android.gms.location.LocationServices.  .FusedLocationProviderApi;


import static com.xware.peoplefinder.R.id.rbPerson;

/**
 * Getting the Location Address.
 *
 * Demonstrates how to use the {@link android.location.Geocoder} API and reverse geocoding to
 * display a device's location as an address. Uses an IntentService to fetch the location address,
 * and a ResultReceiver to process results sent by the IntentService.
 *
 * Android has two location request settings:
 * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
 * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
 * the AndroidManifest.xml.
 *
 * For a starter example that displays the last known location of a device using a longitude and latitude,
 * see https://github.com/googlesamples/android-play-location/tree/master/BasicLocation.
 *
 * For an example that shows location updates using the Fused Location Provider API, see
 * https://github.com/googlesamples/android-play-location/tree/master/LocationUpdates.
 *
 * This sample uses Google Play services (GoogleApiClient) but does not need to authenticate a user.
 * For an example that uses authentication, see
 * https://github.com/googlesamples/android-google-accounts/tree/master/QuickStart.
 */
// THIS IS NOT THE PROJECT MAIN ACTiVITY - IT IS GOOGLE BULLSHIT
public class LocateMeActivity extends MainMenu implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {


    protected static final String TAG = "main-activity";

    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";


    private String addresstype = "person";
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    /**
     * Tracks whether the user has requested an address. Becomes true when the user requests an
     * address and false when the address (or an error message) is delivered.
     * The user requests an address by pressing the Fetch Address button. This may happen
     * before GoogleApiClient connects. This activity uses this boolean to keep track of the
     * user's intent. If the value is true, the activity tries to fetch the address as soon as
     * GoogleApiClient connects.
     */
    protected boolean mAddressRequested;

    /**
     * The formatted location address.
     */
    protected String mAddressOutput;


    /**
     * Displays the location address.
     */
    protected TextView mLocationAddressTextView ;

    /**
     * Visible while the address is being fetched.
     */
    ProgressBar mProgressBar ;

    /**
     * Kicks off the request to fetch an address when pressed.
     */

    Button addAddressButton;
    RadioGroup rgroup ;
    Button mFetchAddressButton ;
    Button homeButton  ;
    FloatingActionButton fab;
    TextView addressString;

    /* This defines a request code to send to Google Play services, which is returned in Activity.onActivityResult():
 */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver = new AddressResultReceiver(new Handler());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_me);
      //  mLocationAddressTextView = (TextView) findViewById(R.id.location_address_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //    toolbar.setTitle(getTitle());
        toolbar.setTitle("Locate me");
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mFetchAddressButton = (Button) findViewById(R.id.fetch_address_button);

      //  homeButton = (Button) findViewById(R.id.btnHome);
        rgroup= (RadioGroup)findViewById(R.id.rg);
        // Set defaults, then update using values stored in the Bundle.
        mAddressRequested = false;
        mAddressOutput = "";
        addAddressButton = (Button) findViewById(R.id.btnAddAddress);
      //  fab = (FloatingActionButton) findViewById(R.id.fab);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        addressString= (TextView)findViewById(R.id.txtAddress);
        updateValuesFromBundle(savedInstanceState);
        updateUIWidgets();

        buildGoogleApiClient();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        // get address from geo location
       // fetchAddress();
        addAddressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent;
                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();

                if (addresstype.equals("person")) {
                    intent = new Intent(context, addPersonActivity.class);
                } else {
                    intent = new Intent(context, addPlaceActivity.class);
                }


                Bundle arguments = new Bundle();
                if (!mAddressOutput.equals("")) {
                  //  arguments.putString("current_address",mAddressOutput);
                 //   intent.putExtras(arguments);
                   intent.putExtra("current_address",mAddressOutput);
                         //   getIntent().getStringExtra(mAddressOutput));
                }
                startActivity(intent);


            }
        });

  /*      homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, HomeActivity.class);

            }

        });
        */
// update address from geo location
     //   fetchAddress();
/*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "1)Fetch address to get current location.2)Pick address type. 3)click add address", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    /**
     * Updates fields based on data stored in the bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Check savedInstanceState to see if the address was previously requested.
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            // Check savedInstanceState to see if the location address string was previously found
            // and stored in the Bundle. If it was found, display the address string in the UI.
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                displayAddressOutput();
            }
        }
    }

    /**
     * Builds a GoogleApiClient. Uses {@code #addApi} to request the LocationServices API.
     */
   protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    /**
     * Runs when user clicks the Fetch Address button. Starts the service to fetch the address if
     * GoogleApiClient is connected.
     */
    public void fetchAddressButtonHandler(View view) {
        // We only start the service to fetch the address if GoogleApiClient is connected.

        if (mLastLocation == null) {

        }
        fetchAddress();

    }
    private void fetchAddress(){
        mAddressRequested = true;
        updateUIWidgets();
        if (mGoogleApiClient.isConnected()==false )
             mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected() ) {
            startIntentService();
            String s =mAddressOutput;
        } else {
            Log.i(" GOOGLEAPI", "GOOGLEAPI  not fuckign connected !!!!!");
        }
        mAddressRequested = false;
        updateUIWidgets();
        // If GoogleApiClient isn't connected, we process the user's request by setting
        // mAddressRequested to true. Later, when GoogleApiClient connects, we launch the service to
        // fetch the address. As far as the user is concerned, pressing the Fetch Address button
        // immediately kicks off the process of getting the address.

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private LocationRequest mLocationRequest;
    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);

        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);

        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            //      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        } else {
            handleNewLocation(mLastLocation);
        }




        /*
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            //      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            handleNewLocation(location);
        }
        */
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == MY_PERMISSION_ACCESS_FINE_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(this,"fuck you ! fuck you! fuck you!",Toast.LENGTH_LONG);
                    return;
                }
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (location == null) {
                    //      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                } else {
                    handleNewLocation(location);

                }
                // We can now safely use the API we requested access to
                //    Location myLocation =
                //          LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            } else {
                // Permission was denied or request was cancelled
            }
        }
        else{

        }
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_FINE_LOCATION : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (location == null) {
                        //      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                    } else {
                        handleNewLocation(location);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
*/

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    // @Override
    public void onConnectedfucked(Bundle connectionHint) {
        // Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            //   ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
            //         LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                return;
            }

            // It is possible that the user presses the button to get the address before the
            // GoogleApiClient object successfully connects. In such a case, mAddressRequested
            // is set to true, but no attempt is made to fetch the address (see
            // fetchAddressButtonHandler()) . Instead, we start the intent service here if the
            // user has requested an address, since we now have a connection to GoogleApiClient.
            if (mAddressRequested) {
                startIntentService();
            }
        }
        else{
            /*
            if (location == null) {
                // Blank for a moment...
            }
            else {
                */
            handleNewLocation(mLastLocation);
        };
        /*    LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(120000);
            locationRequest.setFastestInterval(30000);
         //   locationRequest.setInterval(LOCATION_INTERVAL);
          //  locationRequest.setFastestInterval(LOCATION_INTERVAL);
            mLastLocation = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,  locationRequest,);
*/


    }
    private void handleNewLocation(Location location) {

        showToast("Location is "+location.toString());
        addressString.setText(location.getLatitude() +"   "+ location.getLongitude());

        Log.d(TAG, location.toString());
    }
    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);


    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * Updates the address in the UI.
     */
    protected void displayAddressOutput() {
        Intent i = new Intent();
       // i.putExtra(Intent.EXTRA_TEXT, "body of email");
        addresstype="person";
        try {
            //    Context context = this.getApplication().getContext();
            if (addresstype.equals("person")){
                i = new Intent(this, PersonDetailActivity.class);
                i.putExtra("firstname", " ");
                i.putExtra("lastname", "");
       //     i.putExtra("address", "");
                i.putExtra("email", "");
                i.putExtra("phone", " ");
            }
            else {
                i = new Intent(this, PlaceDetailActivity.class);
                i.putExtra("name"," ");
                i.putExtra("description","");
          //      i.putExtra("address","");
                i.putExtra("email","");
                i.putExtra("phone"," ");

            }

            i.putExtra("address",mAddressOutput);
            startActivity(i);
          //  startActivity(new Intent(PersonDetailActivity.class));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LocateMeActivity.this, "display address screw up.", Toast.LENGTH_SHORT).show();
        }



    }

    /**
     * Toggles the visibility of the progress bar. Enables or disables the Fetch Address button.
     */
    private void updateUIWidgets() {
        if (mAddressRequested) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
          //  mFetchAddressButton.setEnabled(false);
        } else {
            mProgressBar.setVisibility(ProgressBar.GONE);
          //  mFetchAddressButton.setEnabled(true);
        }
        addressString.setText("Closest Address : "+'\n' +mAddressOutput);
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save whether the address has been requested.
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);

        // Save the address string.
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in LocateMeActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

          //  displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                showToast(getString(R.string.address_found));
                addressString.setText((String)resultData.get( Constants.RESULT_DATA_KEY)+" from onReceiveResult ");
                Log.i(TAG, resultData.toString());
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;
           updateUIWidgets();
        }
    }

    public void onRadioButtonClicked(View view) {
        addresstype ="person";
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        int i=rgroup.getCheckedRadioButtonId();
        if (i==rbPerson){
            addresstype ="person";
        }
        else{
            addresstype ="place";

        }
        // one of the radio buttons is checked


        // Check which radio button was clicked
 /*      switch(view.getId()) {
            case rbPerson:
                if (checked)
                    addresstype ="person";
                Log.i(TAG, "person branch");
                break;
            case R.id.rbPlace:
                if (checked)
                    addresstype ="place";
                Log.i(TAG, " place branch");
                break;
        }
        */
    }

}




