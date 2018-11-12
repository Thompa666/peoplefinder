package com.xware.peoplefinder;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xware.peoplefinder.entities.Person;
// import com.xware.peoplefinder.entities.PersonContent;

import static android.R.attr.fragment;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
// import static com.xware.peoplefinder.PersonDetailFragment.ARG_ITEM_ID;
import static com.xware.peoplefinder.R.id.fab;
import static com.xware.peoplefinder.R.id.firstname;
import static java.lang.Long.getLong;

/**
 * An activity representing a single Person detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PersonListActivity}.
 */
public class PersonDetailActivity extends MainMenu {
private Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.activity_person_detail);
        setContentView(R.layout.activity_person_detail);
       TextView fd1 = (TextView) findViewById(R.id.firstname);
        TextView fd2 = (TextView) findViewById(R.id.lastname);
        TextView fd3 = (TextView) findViewById(R.id.address);
        TextView fd4 = (TextView) findViewById(R.id.phone);
       TextView fd5 = (TextView) findViewById(R.id.email);

            Bundle b = getIntent().getExtras();
            if (b != null) {
                Long id=b.getLong("id");
                String firstname = b.getString("firstname");
                String lastname = b.getString("lastname");
                String address = b.getString("address");
                String email = b.getString("email");
                String phone = b.getString("phone");
                fd1.setText(firstname);
                fd2.setText(lastname);
                fd3.setText(address);
               fd4.setText(phone);
               fd5.setText(email);
             person= new Person(id,firstname,lastname,address,email,phone);




            }


        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
/* FloatingActionButton fab = (FloatingActionButton) findViewById(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        // Show the Up button in the action bar.
     ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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


            PersonDetailFragment fragment = new PersonDetailFragment();
            fragment.setArguments(arguments);
         //   getSupportFragmentManager().beginTransaction()
                  //  .add(R.id.person_detail_container, fragment)
           //         .add(R.id.person_detail, fragment)
         //           .commit();
        }
  /*      Button homeButton = (Button) findViewById(R.id.bHome);

        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);

            }
        });
*/
        // -------
        Button showMapButton = (Button) findViewById(R.id.bShowMap);
//    Button showAnswerButton= (Button) findViewById(R.id.bShowAnswer);
        showMapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                Context context = v.getContext();
                Log.i(" paths", "base context path " + getBaseContext() + "");

                Intent intent = new Intent(context, MapsActivity.class);
                Bundle arguments = new Bundle();
           //     arguments.putLong(ARG_ITEM_ID,
             //           getIntent().getBundleExtra(ARG_ITEM_ID));
           //     Bundle arguments = new Bundle();
         //       arguments.putString(ARG_ITEM_ID,
            //            getIntent().getStringExtra(ARG_ITEM_ID));
            //    Person person = PersonContent.ITEM_MAP.get(Long.parseLong(getIntent().getStringExtra(ARG_ITEM_ID)));

              //   Person person  = PersonContent.ITEM_MAP.get(getIntent().getExtras().getLong("id"));


             //   Person person = PersonContent.ITEM_MAP.get(getIntent().getLongExtra(ARG_ITEM_ID));
                //        mItem = PersonContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
                if (person != null && person.address != null && !person.address.equals("")) {
                    intent.putExtra("id",person.id);
                    intent.putExtra("address", person.address);
                    intent.putExtra("firstname", person.firstname );
                    intent.putExtra("lastname",person.lastname);
                    intent.putExtra("phone",person.phone);
                    intent.putExtra("email",person.email);
                    startActivity(intent);
                } else {
                    Toast.makeText(PersonDetailActivity.this, "There is no address to show.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button phoneButton = (Button) findViewById(R.id.bPhone);
       final int MY_PERMISSIONS_REQUEST_FINE_LOCATION=0;
        // add button listener
        phoneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

              //  Intent callIntent = new Intent(Intent.ACTION_CALL);
                if (person != null && person.phone != null) {
                //      callIntent.setData(Uri.parse(person.phone));
                    String phone =  "tel:"+person.phone;
                    Toast.makeText(PersonDetailActivity.this, "phone variable is : "+phone, Toast.LENGTH_SHORT).show();

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

        Button sendEmailButton = (Button) findViewById(R.id.bEmail);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                Context context = v.getContext();
                Log.i(" paths", "base context path " + getBaseContext() + "");

              //  Intent intent = new Intent(context, MapsActivity.class);
                Bundle arguments = new Bundle();
            //    arguments.putString(ARG_ITEM_ID,
                //        getIntent().getStringExtra(ARG_ITEM_ID));
             //   Person person = PersonContent.ITEM_MAP.get(getIntent().getExtras().getLong("id"));

                //        mItem = PersonContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
                if (person != null) {
               /*     intent.putExtra("id", person.id);
                    intent.putExtra("address", person.address);
                    intent.putExtra("firstname", person.firstname);
                    intent.putExtra("lastname", person.lastname);
                    intent.putExtra("phone", person.phone);
                    intent.putExtra("email", person.email);
*/
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{person.email});
                    i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                    i.putExtra(Intent.EXTRA_TEXT, "body of email");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(PersonDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }


                   // startActivity(intent);
                }
                else
                    Toast.makeText(PersonDetailActivity.this, "There is no person defined", Toast.LENGTH_SHORT).show();



            }
        });

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
            NavUtils.navigateUpTo(this, new Intent(this, PersonListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
