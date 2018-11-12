package com.xware.peoplefinder.entities;

/**
 * Created by paul on 1/5/17.
 */



import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.DBHelper;

import static com.xware.peoplefinder.dummy.DummyContent.ITEMS;
import static com.xware.peoplefinder.dummy.DummyContent.ITEM_MAP;

/**
 * Created by paul on 12/10/16.
 */


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceContentNOTUSED {

    /**
     * An array of sample (dummy) items.
     */
    private Context context;

    public  List<Place> ITEMS;
    //  public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
   // public static Map<Long, Place> ITEM_MAP = new HashMap<Long, Place>();

    private static  int COUNT = 1;
public PlaceContentNOTUSED(){
    ITEMS =new ArrayList<Place>();;

}
    public  void initPlaceContent(Context c) {
     //   this.ITEMS = new ArrayList<Place>();

//        DBHelper h =  new DBHelper(c);

        this.ITEMS.clear();
        DBHelper h =  new DBHelper(c);
        List<Place> places= h.getAllPlaces("place");
        for (Place p:places ) {
            this.ITEMS.add(p);
    //        ITEM_MAP.put(p.id, p);

        }

    }
    public  void addItem(Place item,Context c) {
        if (item.id ==null || item.id== 0){
            item.id = new Long(this.ITEMS.size()+1);
        }
        this.ITEMS.add(item);
     //   ITEM_MAP.put(item.id, item);
    }
    private  void addStaticItem(Place item) {
        this.ITEMS.add(item);
     //   ITEM_MAP.put(item.id, item);
    }
    private void addContent(Context c){
        // Add some sample items.
        for (long i = 1; i <= COUNT; i++) {
            addItem(new Place(i ,"place"+i,"some shit"+i),c);
        }

    }
 /*   static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
           // addItem(new Place("" ,"",""));
        }
    }



    private static com.xware.peoplefinder.dummy.DummyContent.DummyItem createDummyItem(int position) {
        return new com.xware.peoplefinder.dummy.DummyContent.DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
*/

    /**
     * A dummy item representing a piece of content.
     */

}