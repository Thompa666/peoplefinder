package com.xware.peoplefinder;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import common.DBHelper;

public class AddPictureActivity extends MainMenu {
    static final int  REQUEST_IMAGE_CAPTURE = 1;
    DBHelper mydb;
    ImageView mImageView ;
    String mCurrentPhotoPath;
    Integer contactId ;
    String sendType ;
    String firstname ;
    String lastname ; //= b.getString("lastname");
    String address ; //= b.getString("address");
    String email ; //= b.getString("email");
    String phone ; //= b.getString("phone");
    Integer intId; // =b.getInt("intId");
    String pname ;
    String description ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

     //   setContentView(R.layout.content_add_picture);
      //  intent.putExtra("id", place.name);
        if (mydb == null)
            mydb = new DBHelper(this);
        TextView tv1= (TextView) findViewById(R.id.tvId);
        TextView tv3= (TextView) findViewById(R.id.tvDescription);
        TextView tv2= (TextView)findViewById(R.id.tvName);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            Long id=b.getLong("id");
            contactId =b.getInt("contactId");
            sendType=b.getString("sendType");
            address = b.getString("address");
            email = b.getString("email");
            phone = b.getString("phone");
            intId =b.getInt("intId");
            if (sendType.equals("person")){

                     firstname = b.getString("firstname");
                     lastname = b.getString("lastname");
                     address = b.getString("address");
                     email = b.getString("email");
                     phone = b.getString("phone");
                     intId =b.getInt("intId");
                tv2.setText(firstname);
                tv3.setText(lastname);
            }


            else if (sendType.equals("place")) {
                pname = b.getString("name");
                  description=b.getString("description");
                tv1.setText(id + "");
                tv2.setText(pname);
                tv3.setText(description);
            }
            else {

                tv2.setText("nothing passed");
                tv3.setText("nothing passed");

            }
        }
        else{
            Toast.makeText(this," NO BUNDLE PASSED" , Toast.LENGTH_LONG);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mImageView= (ImageView) findViewById(R.id.image1);
        setSupportActionBar(toolbar);

        Button btnPic = (Button) findViewById(R.id.btnPic);
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
               // Snackbar.make(view, "Click to launch camera ." +'\n'+"You will return here when done.", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    if (contactId != null && contactId > 0 && mCurrentPhotoPath != null &&  mCurrentPhotoPath.length()> 5)
                mydb.insertContactPicture(contactId,mCurrentPhotoPath);
                else
                    Log.e(" insert picture ", " contact id"+" "+contactId +" photo path" +mCurrentPhotoPath );

                Bundle b = new Bundle();
                Context context = view.getContext();
                b.putLong("id", contactId);
                b.putString("address", address);
                b.putString("phone", phone);
                b.putString("email", email);
                b.putInt("intId",intId);
                if (sendType.equals("person")) {
                    Intent intent =new Intent(context,PersonDetailActivity.class);
                    b.putString("firstname",firstname);
                    b.putString("lastname", lastname);
                    try {
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(AddPictureActivity.this, "There is a problem with taking a picture", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (sendType.equals("place")) {
                    b.putString("name",pname);
                    b.putString("description",description);
                    Intent intent = new Intent(context, PlaceDetailActivity.class);
                    try {
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(AddPictureActivity.this, "There is a problem with taking a picture", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dispatchTakePictureIntent();
                Snackbar.make(view, "Click to launch camera ." +'\n'+"You will return here when done.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


}



    private void dispatchTakePictureIntent1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
               Bundle extras = data.getExtras();
         //   if (mCurrentPhotoPath.length()>5 )
           //      mydb.updateContactPicture(contactId,mCurrentPhotoPath);
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println(ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }



}

