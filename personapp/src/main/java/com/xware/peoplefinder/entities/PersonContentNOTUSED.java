package com.xware.peoplefinder.entities;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.DBHelper;

/**
 * Created by paul on 12/10/16.
 */


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PersonContentNOTUSED {

    /**
     * An array of sample (dummy) items.
     */
    private Context context;

    public   List<Person> ITEMS ; //= new ArrayList<Person>();
    //  public  final List<DummyItem> this = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) this, by ID.
     */
   // public   Map<Long, Person> ITEM_MAP = new HashMap<Long, Person>();

    private   int COUNT = 1;

    public PersonContentNOTUSED(){
        ITEMS = new ArrayList<Person>();
    }

    public  void initPersonContent(Context c) {

      //  ITEM_MAP.clear();
        COUNT=0;
        this.ITEMS.clear();
        DBHelper h =  new DBHelper(c);
        List<Person> people= h.getAllContacts("person");
        for (Person p:people ) {
            this.ITEMS.add(p);
            COUNT++;
        }

    }
    public  void addItem(Person item,Context c) {
        if (item.id ==null || item.id== 0){
            item.id = new Long(this.ITEMS.size()+1);
        }
        this.ITEMS.add(item);
      //  COUNT++;
   //     ITEM_MAP.put(item.id, item);
    }
    private  void addItem(Person item) {
        this.ITEMS.add(item);
       // COUNT++;
     //   ITEM_MAP.put(item.id, item);
    }
    private void addContent(Context c){
        // Add some sample this.ITEMS.
        for (long i = 1; i <= COUNT; i++) {
             addItem(new Person(i ,"person"+i,"some shit"+i),c);
        }

    }
 /*    {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
           // addItem(new Person("" ,"",""));
        }
    }



    private  com.xware.peoplefinder.dummy.DummyContent.DummyItem createDummyItem(int position) {
        return new com.xware.peoplefinder.dummy.DummyContent.DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private  String makeDetails(int position) {
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