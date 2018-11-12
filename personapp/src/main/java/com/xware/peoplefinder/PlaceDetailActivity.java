package com.xware.peoplefinder;

/**
 * Created by paul on 1/5/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.xware.peoplefinder.entities.Person;
// import com.xware.peoplefinder.entities.PersonContent;
import com.xware.peoplefinder.entities.Place;
//import com.xware.peoplefinder.entities.PlaceContent;

// import static android.R.attr.name;
//import static com.xware.peoplefinder.PlaceDetailFragment.ARG_ITEM_IDp;
import static com.xware.peoplefinder.R.id.firstname;
import static com.xware.peoplefinder.R.id.lastname;
import static com.xware.peoplefinder.R.id.phone;

import android.net.Uri;
/**
 * An activity representing a single Place detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PlaceListActivity}.
 * <p>
 * <p>
 * PAUL !!!! CONTENT IS DIPLYED IN PRSONDETAIL FRAGMENT - LINE 85
 */
public class PlaceDetailActivity extends MainMenu {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Place place;
        long pid = 0L;
        setContentView(R.layout.activity_place_detail);
     //   setContentView(R.layout.place_detail);
        TextView fd1 = (TextView) findViewById(R.id.tvPlace);
        TextView fd2 = (TextView) findViewById(R.id.tvDescription);
        TextView fd3 = (TextView) findViewById(R.id.tvAddress);
        TextView fd4 = (TextView) findViewById(R.id.tvEmail);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            Long id = b.getLong("id");
            String name = b.getString("name");
            String description = b.getString("description");
            String address = b.getString("address");
            String email = b.getString("email");
            String phone = b.getString("phone");
            fd1.setText(name);
            fd2.setText(description);
            fd3.setText(address);
            fd4.setText(email);
            place = new Place(id, name, description, address, email, phone);
            pid = place.id;
        //    PlaceContent.addItem(place, getApplicationContext());

        } else
            place = null;

       // setContentView(R.layout.place_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     /   fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Place detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */



        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            //    arguments.putString(ARG_ITEM_IDp,
            //          getIntent().getStringExtra(ARG_ITEM_IDp));
            PlaceDetailFragment fragment = new PlaceDetailFragment();

         //   arguments.putBundle("address", b);
            fragment.setArguments(arguments);
        /*    getSupportFragmentManager().beginTransaction()
                    .add(R.id.place_detail_container, fragment)
                    .commit();
        }
*/

        Button showMapButton = (Button) findViewById(R.id.bShowMap);

        showMapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();
                Intent intent = new Intent(context, MapsActivity.class);


                if (place != null) {
                    intent.putExtra("address", place.address);
                    intent.putExtra("placename", place.name);
                    intent.putExtra("phone", place.phone);
                    intent.putExtra("description", place.description);
                    intent.putExtra("email", place.email);

                    startActivity(intent);
                } else {
                    Toast.makeText(PlaceDetailActivity.this, "There is no address to show.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    //    Button homeButton = (Button) findViewById(R.id.bHome);

            /*
        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);

            }
        });
            */
        Button sendEmailButton = (Button) findViewById(R.id.bEmail);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                Context context = v.getContext();
                Log.i(" paths", "base context path " + getBaseContext() + "");

              /*  Intent intent = new Intent(context, MapsActivity.class);
                if (place != null) {
                    intent.putExtra("address", place.address);
                    intent.putExtra("placename", place.name);
                    intent.putExtra("phone", place.phone);
                    intent.putExtra("description", place.description);
                    intent.putExtra("email", place.email);
                }
*/
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{place.email});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(PlaceDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

             //   startActivity(intent);

            }
        });

            Button phoneButton = (Button) findViewById(R.id.bPhone);
            final int MY_PERMISSIONS_REQUEST_FINE_LOCATION=0;
            // add button listener
            phoneButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //  Intent callIntent = new Intent(Intent.ACTION_CALL);
                    if (place != null && place.phone != null) {
                        //      callIntent.setData(Uri.parse(person.phone));
                        String phone =  "tel:"+place.phone;
                        Toast.makeText(PlaceDetailActivity.this, "phone variable is : "+phone, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                    else {
                        // callIntent.setData(Uri.parse("tel:0377778888"));
                        String phone = "tel:+34666777888";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
            /*    if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                   startActivity(callIntent);
                } else {
                    // Show rationale and request permission.

                    ActivityCompat.requestPermissions(PersonDetailActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                }

*/
                }

            });

    }



}

/*
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, PlaceListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}

