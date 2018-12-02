package com.xware.peoplefinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import com.xware.peoplefinder.entities.Person;
import com.xware.peoplefinder.entities.Place;
//import com.xware.peoplefinder.entities.PersonContent;

import java.util.ArrayList;
import java.util.List;

import common.DBHelper;

import static android.R.attr.fragment;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


// import com.google.android.gms.appindexing.AppIndex;

/**
 * An activity representing a list of People. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PersonDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PersonListActivity extends MainMenu {
    DBHelper db;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    String searchTerm;
    private boolean mTwoPane;
    public   List<Person> ITEMS ;

    //= new ArrayList<Person>();
    //  public  final List<DummyItem> this = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) this, by ID.
     */
    // public   Map<Long, Person> ITEM_MAP = new HashMap<Long, Person>();

  //  private   int COUNT = 1;
    private  ArrayList<Person> removelist;
  // private  ArrayList<Integer> removelist;

    // public PlaceContent pc;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;


    public PersonListActivity(){
        ITEMS = new ArrayList<Person>();
        if (removelist == null)
            removelist = new ArrayList<Person>();
        db = new DBHelper(this);
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public  void initPersonContent(Context c) {

        //  ITEM_MAP.clear();
       // COUNT=0;
        this.ITEMS.clear();
        removelist.clear();
        DBHelper h =  new DBHelper(c);
        List<Person> people= h.getAllContacts("person");
        for (Person p:people ) {
            this.ITEMS.add(p);
           // COUNT++;
        }

    }
    public  void initPersonContent(Context c,String searchTerm) {


        this.ITEMS.clear();
        removelist.clear();
        DBHelper h =  new DBHelper(c);
        List<Person> people= h.getFilterContacts("person",searchTerm);
        for (Person p:people ) {
            this.ITEMS.add(p);
            // COUNT++;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            searchTerm = b.getString("searchTerm");
            initPersonContent(this, searchTerm);
        }
        else

        initPersonContent(this);

        setContentView(R.layout.activity_person_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
      //  toolbar.setTitle("People");

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button searchPersonButton = (Button) findViewById(R.id.bSearch);

        searchPersonButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText tvSearch =(EditText)findViewById(R.id.tvSearch) ;
                String psearchTerm =tvSearch.getText().toString();
                Log.i(" paths", "base context path " + getBaseContext() + "");
                Context context = v.getContext();
                Intent intent = new Intent(context, PersonListActivity.class);
                intent.putExtra("searchTerm",psearchTerm);

                startActivity(intent);



            }
        });
        Button addPersonButton = (Button) findViewById(R.id.bAddPerson);

        addPersonButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();
                Log.i(" paths", "base context path " + getBaseContext() + "");

                Intent intent = new Intent(context, addPersonActivity.class);


                startActivity(intent);



            }
        });
        Button deletePersonButton = (Button) findViewById(R.id.bPerson_delete);

        deletePersonButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeFromList(removelist);


            }
        });


    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "pointless  widget", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        View recyclerView = findViewById(R.id.person_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.person_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    public ArrayList<Person> getPersonListFromDB(){
        return db.getAllContacts("person");
        // return null;
    }


    private void removeFromList(ArrayList<Person> ai) {
        View recyclerView = findViewById(R.id.person_list);


        assert recyclerView != null;

        // setupRecyclerView((RecyclerView) recyclerView);
        // SimpleItemRecyclerViewAdapter ad = (SimpleItemRecyclerViewAdapter)((RecyclerView)recyclerView).getAdapter();
        Log.i(" delete ", "REMOVE FROM LIST CALLED ARRAY LIST SIZE = " +ai.size() );
        //  ArrayList<Integer> ai = new ArrayList<Integer>();
        if( ai.size() > 0)
            for (int i = ai.size()-1; i > -1 ; i--) {
                Person p = ai.get(i);
                Long ii = p.id;
                db.deleteContact(ii);
try {
    ITEMS.remove(ITEMS.indexOf(p));
}
catch(java.lang.ArrayIndexOutOfBoundsException e){
    Log.i(" delete Error ", "ITEMS.indexOf(p) = "  + ITEMS.indexOf(p));
    for(Person pp:ITEMS){

        Log.i(" delete Error ", pp.id+"");
    }
}
                ((RecyclerView)recyclerView).getAdapter().notifyItemRemoved(i);
                ((RecyclerView)recyclerView).getAdapter().notifyDataSetChanged();

                Log.i(" delete ", "DELETED TARGET "  + ii);

            }
        initPersonContent(this);
    }
    private void removeFromListlast(ArrayList<Long> ai) {
        View recyclerView = findViewById(R.id.person_list);


        assert recyclerView != null;

        // setupRecyclerView((RecyclerView) recyclerView);
        // SimpleItemRecyclerViewAdapter ad = (SimpleItemRecyclerViewAdapter)((RecyclerView)recyclerView).getAdapter();
        Log.i(" delete ", "REMOVE FROM PERSON LIST CALLED ARRAY LIST SIZE = " +ai.size() );
        //  ArrayList<Integer> ai = new ArrayList<Integer>();
        if( ai.size() > 0)
            for (int i = ai.size()-1; i > -1 ; i--) {
                Long ii = ai.get(i);
                db.deleteContact(ii);
                ((RecyclerView)recyclerView).getAdapter().notifyItemRemoved(i);
                ((RecyclerView)recyclerView).getAdapter().notifyDataSetChanged();

                //     notifyDataSetChanged();;
                //  (SimpleItemRecyclerViewAdapter)((RecyclerView)recyclerView).getAdapter().removeItem(i); //--.getAdapter().notifyItemRemoved(i);

                Log.i(" delete ", "DELETED TARGET "  + ii);
                //    break;

            }
        ai.clear();
        //reset list
        //   initPlaceContent(this);
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ITEMS));
    }

    
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private  List<Person> mValues;

        public SimpleItemRecyclerViewAdapter(List<Person> items) {
            mValues = items;
            //mAdapter.notifyItemInserted(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.person_list_content, parent, false);
            return new ViewHolder(view);
        }
       private void checkNull(String s){
           if (s ==null){
               s="null";
           }
       }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
           final Bundle arguments1 = new Bundle();
          /*  holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).name + " " + mValues.get(position).description);*/


            holder.mItem = mValues.get(position);
            holder.mIdView.setText(position +  "" ); // rec= " +mValues.get(position).id + "");
            holder.mCheckBox.setChecked(false);
            // holder.mContentView.setText(mValues.get(position).content);
               //   holder.mCheckBox.onTouchEvent()
            //   holder.mCheckBox.onKeyUp();
            holder.mCheckBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    if (isChecked && removelist.indexOf(holder.mItem) < 0)
                    {      //  removelist.add(holder.mItem.id);
                        ;
                        removelist.add(holder.mItem);
                        //   Toast.makeText(v.getContext(),"item marked is"+holder.mItem.id,Toast.LENGTH_SHORT);
                        Log.i(" checkbox looked at", "item added is  " + holder.mItem.id);
                        Log.i(" deleted list ", "number of items is   " + removelist.size());
                    }
                    else {
                        if (removelist.indexOf(holder.mItem)> -1){
                            removelist.remove(holder.mItem) ;
                            Log.i(" checkbox looked at", "item added is  " + holder.mItem.id);
                            Log.i(" deleted list ", "number of items is   " + removelist.size());
                        }
                    }



                }

            });



                // holder.mContentView.setText(mValues.get(position).content);
          //      holder.mContentView.setText(mValues.get(position).firstname + " " + mValues.get(position).lastname);
                //    PersonContent.ITEM_MAP.put(ARG_ITEM_ID,holder.mItem);




                holder.mContentView.setText(holder.mItem.firstname+" " +holder.mItem.lastname); //+"</br> "  +holder.mItem.address+"</br> "+ holder.mItem.email+"</br> " + holder.mItem.phone) ;

            checkNull(holder.mItem.firstname);
                    checkNull(holder.mItem.lastname);
                    checkNull(holder.mItem.address);
                    checkNull(holder.mItem.email);
                    checkNull(holder.mItem.phone);
                if(holder.mItem.firstname.equals("null"))
                    holder.mItem.firstname="first name is null!";

            if(holder.mItem.lastname.equals("null"))
                holder.mItem.lastname="last name is null!";
            if(holder.mItem.email.equals("null"))
                holder.mItem.email="email is null!";
            if(holder.mItem.address.equals("null"))
                holder.mItem.address="address is null!";
            if(holder.mItem.phone.equals("null"))
                holder.mItem.phone="phone is null!";
            arguments1.putLong("id", holder.mItem.id);
            arguments1.putString("firstname", holder.mItem.firstname);
            arguments1.putString("lastname", holder.mItem.lastname);
            arguments1.putString("address", holder.mItem.address);
            arguments1.putString("phone", holder.mItem.phone);
            arguments1.putString("email", holder.mItem.email);
            arguments1.putInt("intId", holder.mItem.intId);
            boolean b = holder.mCheckBox.isChecked();
            Log.i(" checkbox looked at","item marked is "+b);

                holder.mView.setOnClickListener(new OnClickListener() {
                    boolean b = holder.mCheckBox.isChecked();
                    @Override
                    public void onClick(View v) {
                        if (!b) {
                            if (mTwoPane)

                            {


                                PersonDetailFragment fragment = new PersonDetailFragment();
                                fragment.setArguments(arguments1);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.person_detail_container, fragment)
                                        .commit();
                            } else

                            {

                                Context context = v.getContext();
                                Intent intent = new Intent(context, PersonDetailActivity.class);
                                //   intent.putExtra(PersonDetailFragment.ARG_ITEM_ID, holder.mItem.id+"");

                                intent.putExtras(arguments1);

                                context.startActivity(intent);
                            }
                        }

                    }
                });


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Person mItem;
            public CheckBox mCheckBox;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.cbPerson);
                mCheckBox.setChecked(false);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
