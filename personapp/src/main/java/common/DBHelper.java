package common;

/**
 * Created by paul on 1/2/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.xware.peoplefinder.entities.Person;
import com.xware.peoplefinder.entities.Place;

import static com.xware.peoplefinder.R.id.firstname;
import static com.xware.peoplefinder.R.id.id;
import static com.xware.peoplefinder.R.id.lastname;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "firstname";
    public static final String CONTACTS_COLUMN_LASTNAME = "lastname";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "address";
    public static final String CONTACTS_COLUMN_CITY = "city";
    public static final String CONTACTS_COLUMN_REGION = "region";
    public static final String CONTACTS_COLUMN_COUNTRY= "country";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }



 //   SQLiteDatabase mDatabase = openOrCreateDatabase("exampleDb.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);

   // Cursor c = null;


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
      //  db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL(
                "create table if not exists contacts " +
                "(id INTEGER primary key autoincrement, firstname text,lastname text,email text, address text,phone text,ctype text,intId int)"
        );
        db.execSQL(
                "create table if not exists contactPictures " +
                        "(id INTEGER primary key autoincrement, contactId int, pictureUrl text)"
        );
      //  db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
     //   don't fucking over write the goddamn db!!!!
    //   db.execSQL("DROP TABLE IF EXISTS contacts");
     //   onCreate(db);
    }

    public Long insertContact (String firstname, String lastname, String email, String address,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

    /*    db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL(
                //  "create table if not exists contacts " +
                "create table contacts " +
                        "(id INTEGER primary key autoincrement, firstname text,lastname text,email text, address text,phone text,ctype text)"
        );
        */

        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("phone", phone);
        contentValues.put("ctype","person" );
       long id= db.insert("contacts", null, contentValues);
        if (id < 0)
            return -1L ;
   //        return  getLastInserted();

        return id;
    }
    public Long insertPlace (String firstname,String lastname, String email, String address,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", "");
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("phone", phone);
        contentValues.put("ctype","place" );
        long id= db.insert("contacts", null, contentValues);
        if (id < 0)
            return -1L ;

        return id;
    }
  /*  public long getLastInserted() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ROWID from CONTACTS order by ROWID DESC limit 1";
      //  Cursor res =  db.rawQuery(
        long lastId=-1;
        Cursor c = db.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }
*/
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public int updateContact (Long id, String firstname, String lastname,String email, String address,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("phone", phone);
     int ret = db.update("contacts", contentValues, "id = ? ", new String[] { 0+"" } );
        return ret;
    }
    public int updatePlace(Long id, String name, String description,String email, String address,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", name);
        contentValues.put("lastname", description);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("phone", phone);
        int ret = db.update("contacts", contentValues, "id = ? ", new String[] { 0+"" } );
        return ret;
    }

    public Integer deleteContact (Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] {id+"" });
    }
public ArrayList<Person> getAllContacts(){
    return getAllContacts("all");
}

    public ArrayList<Person> getAllContacts(String ctype) {
        ArrayList<Person> array_list = new ArrayList<Person>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String qstring;
        if (ctype.equals("person"))
          qstring=      "select * from contacts where ctype='person'";
        else if (ctype.equals("place"))
          qstring= "select * from contacts where ctype = 'place'";
        else
           qstring= "select * from contacts";
        Cursor res =  db.rawQuery( qstring, null );
        res.moveToFirst();
try {
    while (res.isAfterLast() == false) {

        res.getColumnIndex(CONTACTS_COLUMN_ID);
        long id = res.getLong(res.getColumnIndex("id"));
        int intId=res.getInt(res.getColumnIndex("id"));
        String firstname = res.getString(res.getColumnIndex("firstname"));
        String lastname = res.getString(res.getColumnIndex("lastname"));
        String address = res.getString(res.getColumnIndex("address"));
        String phone = res.getString(res.getColumnIndex("phone"));
        String email = res.getString(res.getColumnIndex("email"));
        Person p = new Person(id, firstname, lastname, address, email, phone,intId);
        array_list.add(p);
        //array_list.add(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_ID)  )+"  "+res.getString(   res.getColumnIndex(CONTACTS_COLUMN_NAME)));
        res.moveToNext();
    }
}catch(Exception e){
            System.out.println("ERROR " +e.getMessage());
        }
        return array_list;
    }

    public ArrayList<Place> getAllPlaces(String ctype) {
        ArrayList<Place> array_list = new ArrayList<Place>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String qstring;

            qstring= "select * from contacts where ctype = 'place'";

        Cursor res =  db.rawQuery( qstring, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            res.getColumnIndex(CONTACTS_COLUMN_ID);
            long id=res.getInt(res.getColumnIndex("id"));
            int intId=res.getInt(res.getColumnIndex("id"));
            String firstname=res.getString(res.getColumnIndex("firstname"));
            String lastname=res.getString(res.getColumnIndex("lastname"));
            String address=res.getString(res.getColumnIndex("address"));
            String phone =res.getString(res.getColumnIndex("phone"));
            String email =res.getString(res.getColumnIndex("email"));

            Place p = new Place(id,firstname,lastname,address,email,phone,intId) ;
            array_list.add(p);
            //array_list.add(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_ID)  )+"  "+res.getString(   res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public Long insertContactPicture( Integer contactId ,String pictureUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contactId",contactId);
        contentValues.put("pictureUrl",pictureUrl);

        long id= db.insert("contactPictures", null, contentValues);
        if (id < 0) {
            Log.i("contactPictures FAILED","contactPictures failed !!!!! picturesid ="+id);
            return -1L;
        }
         Log.i("contactPictures","contact picturesid ="+id);
        return id;
    }
    public String getContactImageUrl(int pcontactId){
        SQLiteDatabase db = this.getWritableDatabase();
        String qstring=      "select pictureUrl from contactPictures where contactId="+pcontactId;
        Cursor res =  db.rawQuery( qstring, null );
        res.moveToFirst();
        if (res.getCount()>0) {
            String s = res.getString(0);
            if (s.length() > 5)
                return s;
        }
        else{
            Log.e("pic query is 0", qstring);
        }
        return "";
    }
    /*
   // ex: to store a image in to db

    public void insertImg(int id , Bitmap img ) {


        byte[] data = getBitmapAsByteArray(img); // this is a function

        insertStatement_logo.bindLong(1, id);
        insertStatement_logo.bindBlob(2, data);

        insertStatement_logo.executeInsert();
        insertStatement_logo.clearBindings() ;

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
   // to retrieve a image from db

    public Bitmap getImage(int i){

        String qu = "select img  from table where feedid=" + i ;
        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }
*/
}