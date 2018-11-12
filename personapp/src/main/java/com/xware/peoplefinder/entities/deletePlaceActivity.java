package com.xware.peoplefinder.entities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import common.DBHelper;

/**
 * Created by pnisbe on 4/17/2017.
 */



public class deletePlaceActivity
        extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mydb == null)
            mydb = new DBHelper(this);
   /*     setContentView(R.layout.place_list);


        EditText fd1 = (EditText) findViewById(R.id.placename);
       /* android.support.v7.widget.RecyclerView rv =(android.support.v7.widget.RecyclerView)findViewById(R.id.place_list) ;
        android.support.v7.widget.RecyclerView.Adapter a=rv.getAdapter();
         a.getItemCount();
        */

       // Button addPlaceButton = (Button) findViewById(R.id.subButton);

        //addPlaceButton.setOnClickListener(new View.OnClickListener() {
/*
            @Override
            public void onClick(View v) {



                String response="good" ;

                if (response.equals("good")) {

                    Place person = new Place("0", f1, f2, f3, f4, f5);
                    boolean upd = Update(person);
                    if (upd) {
                        Bundle b = new Bundle();

                        Context context = v.getContext();
                        Log.i(" paths", "base context path " + getBaseContext() + "");
                       mydb.deleteContact();
                        Intent intent = new Intent(context, PlaceListActivity.class);
                        intent.putExtras(b);

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
    */
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
/*
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
    */
    public boolean Delete(Place p ) {
        Bundle extras = getIntent().getExtras();

        long value = p.id ; //("id");
        if(value>0) {
            if (mydb.deleteContact(value) > -1) {
                return true;
                //       Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                //     Intent intent = new Intent(getApplicationContext(),LocateMeActivity.class);
                //    startActivity(intent);
            }

        }
        return false;
    }
    /*

        public boolean Update(Place p ) {
        Bundle extras = getIntent().getExtras();

        int Value = Integer.valueOf(p.id) ; //("id");
        if(Value>0){
            if(mydb.updateContact(id_To_Update,p.name,p.description,
                    p.email,
                    p.address,p.phone )){
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
            if(mydb.insertContact(p.name,p.description,
                    p.email,
                    p.address,p.phone )){
                return true;

} else{
        return false;
        //    Toast.makeText(getApplicationContext(), "not done",
        //         Toast.LENGTH_SHORT).show();
        }
        //   Intent intent = new Intent(getApplicationContext(),LocateMeActivity.class);
        //  startActivity(intent);
        }
        }
     */
}



