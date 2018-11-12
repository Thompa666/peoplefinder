package com.xware.peoplefinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
// import com.xware.peoplefinder.entities.PersonContent;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

*/



        Button showPersonListButton = (Button) findViewById(R.id.bShowPersonList);

        Button showPlaceListButton = (Button) findViewById(R.id.bShowPlaceList);
        Button addPersonButton = (Button) findViewById(R.id.bAddPerson);
        Button addPlaceButton = (Button) findViewById(R.id.bAddPlace);
        Button findMeButton = (Button) findViewById(R.id.bFindMe);


        showPersonListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();


                Intent intent = new Intent(context, PersonListActivity.class);
                Bundle arguments = new Bundle();
         //       arguments.putLong(ARG_ITEM_ID,
           //             getIntent().getLongExtra(ARG_ITEM_ID));

                startActivity(intent);



            }
        });
        showPlaceListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();


                Intent intent = new Intent(context, PlaceListActivity.class);
                Bundle arguments = new Bundle();
            //    arguments.putString(ARG_ITEM_IDp,
              //          getIntent().getStringExtra(ARG_ITEM_IDp));

                startActivity(intent);



            }
        });
        addPersonButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();


                Intent intent = new Intent(context, addPersonActivity.class);
                Bundle arguments = new Bundle();
            //    arguments.putLong(ARG_ITEM_ID,
              //          getIntent().getLongExtra(ARG_ITEM_ID));

                startActivity(intent);



            }
        });
        addPlaceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();


                Intent intent = new Intent(context, addPlaceActivity.class);
                Bundle arguments = new Bundle();
              //  arguments.putString(ARG_ITEM_IDp,
            //            getIntent().getStringExtra(ARG_ITEM_IDp));

                startActivity(intent);



            }
        });
        findMeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();


                Intent intent = new Intent(context, LocateMeActivity.class);
                Bundle arguments = new Bundle();
          //      arguments.putString(ARG_ITEM_ID,
            //            getIntent().getStringExtra(ARG_ITEM_ID));

                startActivity(intent);



            }
        });
    }


    private void addCurrentLocation(String ltype){
        Context context = HomeActivity.this;


        Intent intent = new Intent(context, LocateMeActivity.class);
        Bundle arguments = new Bundle();
     /*   if (ltype.equals("place")) {
            arguments.putString(ARG_ITEM_IDp,
                    getIntent().getStringExtra(ARG_ITEM_IDp));
        }
        else {
            arguments.putString(ARG_ITEM_ID,
                    getIntent().getStringExtra(ARG_ITEM_ID));
        }
        */
        startActivity(intent);
    }
    private void addPerson(){
        Context context =  HomeActivity.this;


        Intent intent = new Intent(context, addPersonActivity.class);
        Bundle arguments = new Bundle();
 //       arguments.putString(ARG_ITEM_ID,
   //             getIntent().getStringExtra(ARG_ITEM_ID));

        startActivity(intent);


    }
    private void addPlace(){

        Context context =  HomeActivity.this;


        Intent intent = new Intent(context, PlaceListActivity.class);
        Bundle arguments = new Bundle();
   //     arguments.putString(ARG_ITEM_IDp,
     //           getIntent().getStringExtra(ARG_ITEM_IDp));

        startActivity(intent);
    }
    private void personList(){
        Context context = HomeActivity.this;


        Intent intent = new Intent(context, PersonListActivity.class);
        Bundle arguments = new Bundle();
 //       arguments.putString(ARG_ITEM_ID,
   //             getIntent().getStringExtra(ARG_ITEM_ID));

        startActivity(intent);

    }
    private void placeList(){
        Context context =  HomeActivity.this;


        Intent intent = new Intent(context, PlaceListActivity.class);
        Bundle arguments = new Bundle();
     //   arguments.putString(ARG_ITEM_IDp,
       //         getIntent().getStringExtra(ARG_ITEM_IDp));

        startActivity(intent);

    }
    private void home(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu mainmenu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, mainmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.list_people:
                personList();
                return true;
            case R.id.list_places:
                placeList();
                return true;
            case R.id.new_person:
                addPerson();
                return true;
            case R.id.new_place:
                addPlace();
                return true;
            case R.id.add_this_person:
                addCurrentLocation("person");
                return true;
            case R.id.add_this_place:
                addCurrentLocation("place");
                return true;


            case R.id.help:
           //     showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
