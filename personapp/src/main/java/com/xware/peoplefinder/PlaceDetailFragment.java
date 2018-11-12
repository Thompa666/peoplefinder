package com.xware.peoplefinder;

/**
 * Created by paul on 1/5/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xware.peoplefinder.entities.Place;
// import com.xware.peoplefinder.entities.PlaceContent;



/**
 * A fragment representing a single Place detail screen.
 * This fragment is either contained in a {@link PlaceListActivity}
 * in two-pane mode (on tablets) or a {@link PlaceDetailActivity}
 * on handsets.
 */
public class PlaceDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
  //  public static final String ARG_ITEM_IDp = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    //  private DummyContent.DummyItem mItem;
    Place mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("id")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //        mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            try {

                mItem.id = getArguments().getLong("id");
                mItem.name = getArguments().getString("name");
                mItem.description = getArguments().getString("description");
                mItem.address = getArguments().getString("address");
                mItem.phone = getArguments().getString("phone");
                mItem.email = getArguments().getString("email");
            }catch(Exception e){
                System.out.println(  "Argument id missing "+e.getMessage());
            }

         //   mItem = PlaceContent.ITEM_MAP.get(Long.parseLong(getArguments().getString(ARG_ITEM_IDp)));
/*
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if ( mItem != null){
                if ( mItem.description != null &&appBarLayout != null) {
                    appBarLayout.setTitle(mItem.description);
                }
            }
            else{
                System.out.println(" mItem is NULL !!!!!");
            }
            */
        }
        /*
        else{
            Bundle b = getArguments().getBundle("address");
           // b.get("Address");
            if (b != null) {

                String name = b.getString("name");
                String description = b.getString("description");
                String address = b.getString("address");
                String email = b.getString("email");
                String phone = b.getString("phone");
                mItem = new Place(0L, name, description, address, email, phone);


            }

        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_detail_fragment, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            CheckBox cb = new CheckBox(this.getContext());
            cb.setId(mItem.id.intValue());

            ((TextView) rootView.findViewById(R.id.tvPhone)).setText(mItem.phone );
            ((TextView) rootView.findViewById(R.id.tvEmail)).setText(mItem.email);
            ((TextView) rootView.findViewById(R.id.tvDescription)).setText(mItem.description );

            ((TextView) rootView.findViewById(R.id.tvPlace)).setText(mItem.name );
        }

        return rootView;
    }
}
