package com.xware.peoplefinder ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;


/**
 * Created by pnisbe on 1/11/2017.
 */

public class MainMenu extends AppCompatActivity {
     MainMenu(){

     }


    private void addCurrentLocation(){
        Context context = MainMenu.this;


        Intent intent = new Intent(context, LocateMeActivityOld.class);
        Bundle arguments = new Bundle();
       // arguments.putString(ARG_ITEM_IDp,
         //       getIntent().getStringExtra(ARG_ITEM_IDp));

        startActivity(intent);
    }
    private void addPerson(){
        Context context =  MainMenu.this;


        Intent intent = new Intent(context, addPersonActivity.class);
        Bundle arguments = new Bundle();
      //  arguments.putString(ARG_ITEM_ID,
        //        getIntent().getStringExtra(ARG_ITEM_ID));

        startActivity(intent);


    }
    private void addPlace(){

        Context context =  MainMenu.this;


        Intent intent = new Intent(context, addPlaceActivity.class);
        Bundle arguments = new Bundle();
   //     arguments.putString(ARG_ITEM_IDp,
     //           getIntent().getStringExtra(ARG_ITEM_IDp));

        startActivity(intent);
    }
    private void personList(){
        Context context = MainMenu.this;


        Intent intent = new Intent(context, PersonListActivity.class);
        Bundle arguments = new Bundle();
        // arguments.putString(ARG_ITEM_ID,
         //       getIntent().getStringExtra(ARG_ITEM_ID));

        startActivity(intent);

    }
    private void placeList(){
        Context context =  MainMenu.this;


        Intent intent = new Intent(context, PlaceListActivity.class);
        Bundle arguments = new Bundle();
     //   arguments.putString(ARG_ITEM_IDp,
       //         getIntent().getStringExtra(ARG_ITEM_IDp));

        startActivity(intent);

    }
    private void home(){
        Context context =  MainMenu.this;


        Intent intent = new Intent(context, HomeActivity.class);
        Bundle arguments = new Bundle();
        //   arguments.putString(ARG_ITEM_IDp,
        //         getIntent().getStringExtra(ARG_ITEM_IDp));

        startActivity(intent);

    }




    @Override
    public boolean onCreateOptionsMenu(android.view.Menu mainMenu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, mainMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                home();
                return true;
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
                addCurrentLocation();
                return true;
            case R.id.add_this_place:
                addCurrentLocation();
                return true;


            case R.id.help:
                //     showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
