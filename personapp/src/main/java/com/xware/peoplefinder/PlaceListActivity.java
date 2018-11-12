package com.xware.peoplefinder;

/**
 * Created by paul on 1/5/17.
 */

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
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.xware.peoplefinder.entities.Place;


import java.util.ArrayList;
import java.util.List;

import common.DBHelper;

import static android.media.CamcorderProfile.get;


//import android.widget.TextView.OnClickListener ;





/**
 * An activity representing a list of Places. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PlaceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PlaceListActivity extends MainMenu {
DBHelper db;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public  List<Place> ITEMS;
 //   private  ArrayList<Long> removelist;
    private  ArrayList<Place> removelist;

    // public PlaceContent pc;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    public PlaceListActivity(){
        ITEMS = new ArrayList<Place>();
        if (removelist == null)
         //removelist = new ArrayList<Long>();
            removelist = new ArrayList<Place>();
        db = new DBHelper(this);

    }
    public  void initPlaceContent(Context c) {
        //   this.ITEMS = new ArrayList<Place>();

//        DBHelper h =  new DBHelper(c);

        this.ITEMS.clear();

        removelist.clear();
        DBHelper h =  new DBHelper(c);
        List<Place> places= h.getAllPlaces("place");
        for (Place p:places ) {
            this.ITEMS.add(p);

            //        ITEM_MAP.put(p.id, p);

        }

    }
   public  ArrayList<Place> getPlaceListFromDB(){
       return db.getAllPlaces("place");
        // return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
   //     initPlaceContent(this);
        setContentView(R.layout.activity_place_list);
        //PlaceContent placeContent = new PlaceContent();
        //  setDummyContent();
        // get place data
        initPlaceContent(this);
       final Place place;
        Bundle b = getIntent().getExtras();
    /*    if (b != null) {
            String name = b.getString("name");
            String description = b.getString("description");
            String address = b.getString("address");
            String email = b.getString("email");
            String phone = b.getString("phone");
            boolean vchecked=b.getBoolean("checked");


    //        place = new Place(0L, name, description, address, email, phone);
        //    place.checked=vchecked;
         //   placeContent.addItem(place,getApplicationContext()); //addItem(Place item);
        }
        else
            place = null; */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    //    toolbar.setTitle(getTitle());
        toolbar.setTitle("Places");
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
      /*  Button homeButton = (Button) findViewById(R.id.bHome);

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
        Button addPlaceButton = (Button) findViewById(R.id.bAddPlace);

        addPlaceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Log.i(" paths", "base context path " + getBaseContext() + "");

                Context context = v.getContext();
                Log.i(" paths", "base context path " + getBaseContext() + "");

                Intent intent = new Intent(context, addPlaceActivity.class);


                startActivity(intent);



            }
        });
        Button deletePlaceButton = (Button) findViewById(R.id.bPlace_delete);

        deletePlaceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeFromList(removelist);


            }
        });

      /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "pointless  widget", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        View recyclerView = findViewById(R.id.place_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

  /*      if (findViewById(R.id.place_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        */

    }
    private void removeFromList(ArrayList<Place> ai) {
        View recyclerView = findViewById(R.id.place_list);


        assert recyclerView != null;

        // setupRecyclerView((RecyclerView) recyclerView);
        // SimpleItemRecyclerViewAdapter ad = (SimpleItemRecyclerViewAdapter)((RecyclerView)recyclerView).getAdapter();
        Log.i(" delete ", "REMOVE FROM LIST CALLED ARRAY LIST SIZE = " +ai.size() );
        //  ArrayList<Integer> ai = new ArrayList<Integer>();
        if( ai.size() > 0)
            for (int i = ai.size()-1; i > -1 ; i--) {
                Place p = ai.get(i);
                Long ii = p.id;
                db.deleteContact(ii);
                ITEMS.remove(ITEMS.indexOf(p));
                ((RecyclerView)recyclerView).getAdapter().notifyItemRemoved(i);
                ((RecyclerView)recyclerView).getAdapter().notifyDataSetChanged();

                Log.i(" delete ", "DELETED TARGET "  + ii);

            }
        initPlaceContent(this);
    }
    private void removeFromListlast(ArrayList<Long> ai) {
        View recyclerView = findViewById(R.id.place_list);


        assert recyclerView != null;

       // setupRecyclerView((RecyclerView) recyclerView);
       // SimpleItemRecyclerViewAdapter ad = (SimpleItemRecyclerViewAdapter)((RecyclerView)recyclerView).getAdapter();
        Log.i(" delete ", "REMOVE FROM LIST CALLED ARRAY LIST SIZE = " +ai.size() );
      //  ArrayList<Integer> ai = new ArrayList<Integer>();
       if( ai.size() > 0)
        for (int i = ai.size()-1; i > -1 ; i--) {
            Long ii = ai.get(i);
            db.deleteContact(ii);
            /* what the fuck does the below callco
            Apaprentlyidoes fuckignnothign
                    as the next item becomes checked!!! instead afte this deleted item is removed
                    */
            ((RecyclerView)recyclerView).getAdapter().notifyItemRemoved(i);
            ((RecyclerView)recyclerView).getAdapter().notifyDataSetChanged();

         //   notifyDataSetChanged();
         //  (SimpleItemRecyclerViewAdapter)((RecyclerView)recyclerView).getAdapter().removeItem(i); //--.getAdapter().notifyItemRemoved(i);

            Log.i(" delete ", "DELETED TARGET "  + ii);
        //    break;

        }
     //   ai.clear();
        //reset list
     //  initPlaceContent(this);
    }

    /*  private void setDummyContent() {

          DummyContent.ITEMS.removeAll(DummyContent.ITEMS) ;
          DummyContent.DummyItem i1 = new DummyContent.DummyItem(1," dude " ," dude detials");

          DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
      }
  */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ITEMS));
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<Place> mValues;
        RecyclerView mRecyclerView;
        //new
   /*     private SparseBooleanArray selectedItems;

        public void toggleSelection(int pos) {
            if (selectedItems.get(pos, false)) {
                selectedItems.delete(pos);
            }
            else {
                selectedItems.put(pos, true);
            }
            notifyItemChanged(pos);
        }

        public void clearSelections() {
            selectedItems.clear();
            notifyDataSetChanged();
        }

        public int getSelectedItemCount() {
            return selectedItems.size();
        }
//end new
        */
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            mRecyclerView = recyclerView;
        }

        public SimpleItemRecyclerViewAdapter(List<Place> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.place_list_content, parent, false);
            return new ViewHolder(view);
        }
      //  @Override
        public Place getItem(int position) {
            return mValues.get(position);
        }
       // @Override
        public void removeItem(int position) {
            mValues.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
          /*  holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).name + " " + mValues.get(position).description);*/
            final Bundle arguments1 = new Bundle();


                holder.mItem = mValues.get(position);
                holder.mIdView.setText(mValues.get(position).id + "");
                // holder.mContentView.setText(mValues.get(position).content);
                holder.mContentView.setText(mValues.get(position).name ); // + " " + mValues.get(position).description+" "  +holder.mItem.address+" "+ holder.mItem.email+" " + holder.mItem.phone) ;
            holder.mCheckBox.setChecked(false);

                     //   holder.mCheckBox.onKeyUp();
            holder.mCheckBox.setOnCheckedChangeListener(
                    new android.widget.CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                //    if (isChecked && removelist.indexOf(holder.mItem.id) < 0)
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
         //   holder.mCheckBox
            arguments1.putLong("id", holder.mItem.id);
            arguments1.putString("description", holder.mItem.description);
            arguments1.putString("name", holder.mItem.name);
            arguments1.putString("address", holder.mItem.address);
            arguments1.putString("phone", holder.mItem.phone);
            arguments1.putString("email", holder.mItem.email);
       //   final boolean b = holder.mCheckBox.isChecked();


                holder.mView.setOnClickListener(
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                  //      boolean b =holder.mItem.checked;
                        boolean b = holder.mCheckBox.isChecked();
                        Log.i(" checkbox looked at","item marked is "+b);
                        if (!b) {
                            if (mTwoPane)

                            {

                                PlaceDetailFragment fragment = new PlaceDetailFragment();
                                fragment.setArguments(arguments1);
                       //         getSupportFragmentManager().beginTransaction()
                         //               .replace(R.id.activity_place_detail, fragment)
                           //             .commit();
                            } else

                            {
                                Context context = v.getContext();
                                Intent intent = new Intent(context, PlaceDetailActivity.class);
                                //  intent.putExtra(PlaceDetailFragment.ARG_ITEM_IDp, holder.mItem.id);
                                intent.putExtras(arguments1);

                                context.startActivity(intent);
                            }
                       }
                     /*   else{
                            if (removelist.indexOf(holder.mItem.id)<0)
                                 removelist.add(holder.mItem.id);
                            Toast.makeText(v.getContext(),"item marked is"+holder.mItem.id,Toast.LENGTH_SHORT);
                            Log.i(" checkbox looked at","item not switched !!! is  "+holder.mItem.id);
                            Log.i(" deleted list ","number of items is   "+ removelist.size());

                        }
                        */

                    }
                });
            //if delete button must uncheck box at index
            // when previosuly checked item is removed vi a delete the next item becomes checked
            //another fucked upthing about android lists.
          //  if (holder.mCheckBox.isChecked() && removelist.indexOf(holder.mItem) < 0)
            //    holder.mCheckBox.setChecked(false);

        }






        private void placeList(){
            Context context =  PlaceListActivity.this;


            Intent intent = new Intent(context, PlaceListActivity.class);
            Bundle arguments = new Bundle();
        //    arguments.putLong(ARG_ITEM_ID,
          //          getIntent().getStringExtra(ARG_ITEM_ID));

            startActivity(intent);

        }
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        //        implements View.OnClickListener ,View.OnLongClickListener
        {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Place mItem;
           public CheckBox mCheckBox;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);

                mCheckBox = (CheckBox) itemView.findViewById(R.id.cbPlace);
      /*         if( mCheckBox.isChecked() )
                   Log.i("checkbox status = "," Checkbox is NOT cheked");
                else {
                   Log.i("checkbox status = ", " Checkbox is cheked ! ");
                  // removelist.add(mItem.id);
               }
               */

            }


           // mContentView.
             //mContentView.OnClickListener(this);
            /*
            imgViewRemoveIcon.setOnClickListener(this);
            v.setOnClickListener(this);
            mContentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getPosition());
                    }
                    return false;
                }
            });


           // mContentView.OnClickListener(this);
          //  mContentView.setOnClickListener(new View.OnClickListener() {
                OnClickListener  {
                if (listener != null) {
                   String i=  listener.onItemClicked(getPosition());
                }
                @Override
                public void onClick(View v) {
                    //Log.d("View: ", v.toString());
                    //Toast.makeText(v.getContext(), mTextViewTitle.getText() + " position = " + getPosition(), Toast.LENGTH_SHORT).show();
                    if (v.equals(imgViewRemoveIcon)) {
                        removeAt(getAdapterPosition());
                    } else if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                }
                }
         //   })
            mCheckBox.OnLongClickListener(new View.OnLongClickListener()

            {

                if (listener != null) {
                    return listener.onItemLongClicked(getPosition());
                }


                return false;
            }











            @Override
            public String toString() {
                return super.toString() + " " + mCheckBox.isChecked() + " '" + mContentView.getText() + "'";
            }
            public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
                this.mItemClickListener = mItemClickListener;
            }
            public void remove(int i, ViewHolder vh) {
                mValues.remove(i);
                notifyItemRemoved(i);
            }
            */
        }
    }
}
