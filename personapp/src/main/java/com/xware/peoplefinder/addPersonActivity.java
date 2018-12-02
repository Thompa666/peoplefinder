package com.xware.peoplefinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xware.peoplefinder.entities.Person;
// import com.xware.peoplefinder.entities.PersonContent;

import common.DBHelper;
import common.USStreetValidator;

public class addPersonActivity
    extends MainMenu {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    int id_To_Update = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


                if (mydb == null)
                    mydb = new DBHelper(this);
            setContentView(R.layout.activity_add_person);

            EditText edtAddress = (EditText) findViewById(R.id.address);
            String s =getIntent().getStringExtra("current_address");
            if (s !=null)
                edtAddress.setText(s);
          //  if (savedInstanceState != null && savedInstanceState.containsKey("current_address"))
           //     edtAddress.setText(savedInstanceState.getString("current_address"));
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());
            // Show the Up button in the action bar.
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
    /*        Button homeButton = (Button) findViewById(R.id.bHome);

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

                Button addPersonButton = (Button) findViewById(R.id.subButton);

                addPersonButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        EditText fd1 = (EditText) findViewById(R.id.firstname);
                        EditText fd2 = (EditText) findViewById(R.id.lastname);
                        EditText fd3 = (EditText) findViewById(R.id.address);
                        EditText fd4 = (EditText) findViewById(R.id.email);
                        EditText fd5 = (EditText) findViewById(R.id.phone);

                        String f1 = fd1.getText().toString();
                        String f2 = fd2.getText().toString();
                        String f3 = fd3.getText().toString();
                        String f4 = fd4.getText().toString();
                        String f5 = fd5.getText().toString();
                        EditText vadd= (EditText) findViewById(R.id.address);
                        String  add=   vadd.getText().toString();
                        String response="good" ;
                        if (add.length()>0 )
                            response=validateAdress(add);

                        if (response.equals("good")) {

                            Person person = new Person(0L, f1, f2, f3, f4, f5);
                            boolean upd = Update(person);
                       //     person.id=upd;
                            if (upd) {
                                Bundle b = new Bundle();
                                b.putLong("id",person.id);
                                b.putString("firstname", person.firstname);
                                b.putString("lastname", person.lastname);
                                b.putString("address", person.address);
                                b.putString("phone", person.phone);
                                b.putString("email", person.email);
                                Context context = v.getContext();
                                Log.i(" paths", "base context path " + getBaseContext() + "");
                        //        pc.addItem(person,getApplicationContext());
                                Intent intent = new Intent(context, PersonListActivity.class);
                              //  intent.putExtras(b);

                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Person not added", Toast.LENGTH_LONG).show();
                            }
                        }
                            else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            }
                    }
                });
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  dispatchTakePictureIntent();
                    Snackbar.make(view, "Address will be validated only if you are in the US." +'\n'+"Any Problems - Try 'Add This Location in Menu", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            }





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
                arguments.putString(PersonDetailFragment.ARG_ITEM_ID,
                        getIntent().getStringExtra(PersonDetailFragment.ARG_ITEM_ID));
                PersonDetailFragment fragment = new PersonDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.person_detail_container, fragment)
                        .commit();
            }
            */

 private  String validateAdress(String add) {

     USStreetValidator v = new USStreetValidator();
     String[] a = add.split(",");
     if (a.length < 4)
         return "Address Not a Valid US Format";
     String street = a[0];
     String city = a[1];
     String region = a[2];
     String zip = a[3];
     String valid ;
     Context context = this.getApplicationContext();
     String locale = context.getResources().getConfiguration().locale.getCountry();
     Log.e("CURRENT COUNTRY ", "CURRENT COUNTRY IS " + locale);
     if (locale.equals("US")){
         valid = USStreetValidator.Run(street, city, region, zip);
     //if (valid)
     return valid;
     }
     return "Address not valid";

 }
    public boolean Update(Person p ) {
        Bundle extras = getIntent().getExtras();

            long Value = p.id ; //("id");
            if(Value>0){
                if(mydb.updateContact(p.id,p.firstname,p.lastname,
                         p.email,
                        p.address,p.phone )>-1){
                    return true;

                }
                else{
                    return false;
                }
            } else{
                long id =mydb.insertContact(p.firstname,p.lastname,
                        p.email,
                        p.address,p.phone );
                if(id > -1){
                    p.id=id;
                    return true;

                } else{
                    return false;

                }

            }
        }
    }
/*
    --------------



    TextView name ;
    TextView phone;
    TextView email;
    TextView street;
    TextView place;


    @Override
    protected void onCreate1(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextStreet);
        street = (TextView) findViewById(R.id.editTextEmail);
        place = (TextView) findViewById(R.id.editTextCity);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
                String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence)emai);
                email.setFocusable(false);
                email.setClickable(false);

                street.setText((CharSequence)stree);
                street.setFocusable(false);
                street.setClickable(false);

                place.setText((CharSequence)plac);
                place.setFocusable(false);
                place.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            }
            else{
                getMenuInflater().inflate(R.menu.main_menu ,menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                street.setEnabled(true);
                street.setFocusableInTouchMode(true);
                street.setClickable(true);

                place.setEnabled(true);
                place.setFocusableInTouchMode(true);
                place.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LocateMeActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,name.getText().toString(),
                        phone.getText().toString(), email.getText().toString(),
                        street.getText().toString(), place.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LocateMeActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertContact(name.getText().toString(), phone.getText().toString(),
                        email.getText().toString(), street.getText().toString(),
                        place.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),LocateMeActivity.class);
                startActivity(intent);
            }
        }
    }
    */


