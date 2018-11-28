package com.xware.peoplefinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.xware.peoplefinder.entities.Place;

import common.DBHelper;
import common.USStreetValidator;

import static com.xware.peoplefinder.R.id.address;
import static com.xware.peoplefinder.R.id.firstname;
import static com.xware.peoplefinder.R.id.lastname;
import static com.xware.peoplefinder.R.id.phone;

/**
 * Created by paul on 1/5/17.
 */

public class addPlaceActivity 
        extends MainMenu {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mydb == null)
            mydb = new DBHelper(this);
        setContentView(R.layout.activity_add_place);
        EditText edtAddress = (EditText) findViewById(R.id.address);
  //      if (savedInstanceState != null && savedInstanceState.containsKey("current_address"))
    //        edtAddress.setText(savedInstanceState.getString("current_address"));
        String s =getIntent().getStringExtra("current_address");
        if (s !=null)
            edtAddress.setText(s);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    /*    Button homeButton = (Button) findViewById(R.id.bHome);

        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();
                Log.i(" paths", "base context path " + getBaseContext() + "");

                Intent intent = new Intent(context, HomeActivity.class);


                startActivity(intent);



            }
        });

*/
        Button addPlaceButton = (Button) findViewById(R.id.subButton);

        addPlaceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText fd1 = (EditText) findViewById(R.id.placename);
                EditText fd2 = (EditText) findViewById(R.id.description);
                EditText fd3 = (EditText) findViewById(address);
                EditText fd4 = (EditText) findViewById(R.id.email);
                EditText fd5 = (EditText) findViewById(phone);
                CheckBox cb = (CheckBox)findViewById(R.id.cbPlace);
                String f1 = fd1.getText().toString();
                String f2 = fd2.getText().toString();
                String f3 = fd3.getText().toString();
                String f4 = fd4.getText().toString();
                String f5 = fd5.getText().toString();
           //     boolean f6 = cb.isChecked();

                EditText vadd= (EditText) findViewById(address);
                String  add=   vadd.getText().toString();

                String response="good" ;
                // temp diabled smarty streets
          /*      if (add.length()>3 )
                    response=validateAdress(add);
            */
                if (response.equals("good")) {

                    Place place = new Place(0L, f1, f2, f3, f4, f5);
                    boolean upd = Update(place);
                    if (upd  ) {
                 /*       Bundle b = new Bundle();
                        b.putString("name", fd1.getText().toString());
                        b.putString("description", fd2.getText().toString());
                        b.putString("address", fd3.getText().toString());
                        b.putString("phone", fd4.getText().toString());
                        b.putString("email", fd5.getText().toString());
                //        b.putBoolean("checked",f6);
                  */      Context context = v.getContext();
                        Log.i(" paths", "base context path " + getBaseContext() + "");

                        Intent intent = new Intent(context, PlaceListActivity.class);
  //                      intent.putExtras(b);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //   Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    //  setSupportActionBar(toolbar);

//            setContentView(R.layout.activity_display_message);

   /*         Intent intent = getIntent();
            String message = intent.getStringExtra(LocateMeActivity.EXTRA_MESSAGE);
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText(message);

            ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
            layout.addView(textView);

*/


       /*

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
         /*   if (savedInstanceState == null) {
                // Create the detail fragment and add it to the activity
                // using a fragment transaction.
                Bundle arguments = new Bundle();
                arguments.putString(PlaceDetailFragment.ARG_ITEM_ID,
                        getIntent().getStringExtra(PlaceDetailFragment.ARG_ITEM_ID));
                PlaceDetailFragment fragment = new PlaceDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.person_detail_container, fragment)
                        .commit();
            }
            */

    private  String validateAdress(String add) {

        USStreetValidator v =  new USStreetValidator();
        String[] a = add.split(",");
        if (a.length < 4)
            return "false" ;
        String street = a[0];
        String city=a[1];
        String region=a[2];
        String zip=a[3];
        String valid=USStreetValidator.Run(street,  city,  region,zip);
        return valid;

    }
    public boolean Update(Place p ) {
        Bundle extras = getIntent().getExtras();

        long Value = p.id ; //("id");
        if(Value>0){
            if(mydb.updatePlace(p.id,p.name,p.description,
                    p.email,
                    p.address,p.phone )==0){
                return true;
                //       Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                //     Intent intent = new Intent(getApplicationContext(),LocateMeActivity.class);
                //    startActivity(intent);
            }
            else{
                return false;
                // Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
            }
        } else{
          /*   long id =mydb.insertContact(p.name,p.description,
                    p.email,
                    p.address,p.phone );
            */
            long id =mydb.insertPlace (p.name,p.description,
                    p.email,
                    p.address,p.phone);
            if(id > -1){
                p.id=id;
                return true;
                            /*;{
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                            */
            }
            else{
                return false;
                //    Toast.makeText(getApplicationContext(), "not done",
                //         Toast.LENGTH_SHORT).show();
            }
            //   Intent intent = new Intent(getApplicationContext(),LocateMeActivity.class);
            //  startActivity(intent);
        }
    }
}



