package com.xware.peoplefinder;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xware.peoplefinder.entities.Person;
// import com.xware.peoplefinder.entities.PersonContent;

import static com.xware.peoplefinder.R.id.address;
import static com.xware.peoplefinder.R.id.firstname;
import static com.xware.peoplefinder.R.id.lastname;
import static com.xware.peoplefinder.R.id.phone;

/**
 * A fragment representing a single Person detail screen.
 * This fragment is either contained in a {@link PersonListActivity}
 * in two-pane mode (on tablets) or a {@link PersonDetailActivity}
 * on handsets.
 */
public class PersonDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
 //   public static final String ARG_ITEM_ID = "personid";
  // public static final Long ARG_ITEM_ID = 0L;
    /**
     * The dummy content this fragment is presenting.
     */
  //  private DummyContent.DummyItem mItem;
    Person mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.activity_person_detail);
        TextView fd1 = (TextView) findViewById(R.id.firstname);
        TextView fd2 = (TextView) findViewById(R.id.lastname);
        TextView fd3 = (TextView) findViewById(R.id.address);
        TextView fd4 = (TextView) findViewById(R.id.phone);
        TextView fd5 = (TextView) findViewById(R.id.email);
        */
        if (getArguments() != null && getArguments().containsKey("id")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
    //        mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));


            try {
               mItem= new Person();
                mItem.id = getArguments().getLong("id");
               mItem.firstname = getArguments().getString("firstname");
                mItem.lastname = getArguments().getString("lastname");
                mItem.address = getArguments().getString("address");
                mItem.phone = getArguments().getString("phone");
                mItem.email = getArguments().getString("email");
            }catch(Exception e){
                 System.out.println(  "Argument id fucked up "+e.getMessage());
            }
              //  Log.i("ARG_ITEM_ID",ARG_ITEM_ID );
       /*     Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.lastname);
            }
            */
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.person_detail_fragment, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
           // ((TextView) rootView.findViewById(R.id.person_detail_container)).setText(mItem.id +" " + mItem.firstname +" " +mItem.lastname);


            ((TextView) rootView.findViewById(R.id.firstname)).setText(mItem.firstname );
            ((TextView) rootView.findViewById(R.id.lastname)).setText(mItem.lastname );
            ((TextView) rootView.findViewById(R.id.email)).setText(mItem.email);
            ((TextView) rootView.findViewById(R.id.phone)).setText(mItem.phone);
            ((TextView) rootView.findViewById(R.id.address)).setText(mItem.address );


        }

        return rootView;
    }
}
