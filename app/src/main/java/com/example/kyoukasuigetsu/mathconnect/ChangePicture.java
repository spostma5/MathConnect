package com.example.kyoukasuigetsu.mathconnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class ChangePicture extends ActionBarActivity {

    //Image loading result to pass to startActivityForResult method
    public static final int LOAD_IMAGE_RESULTS = 1;
    public static final String PICTURE = "PICTURE";

    //GUI components
    private Button button; //the button
    private ImageView image; //the imageview
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_picture);

        Bundle data = getIntent().getExtras();
        user = (User) data.getParcelable(LoginActivity.USER);

        //Find references to the GUI objects
        button = (Button)findViewById(R.id.pictureButton);
        image = (ImageView)findViewById(R.id.picView);

        //Set button's onClick listener object
        button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                //create the intent for ImageGallery
                Intent i = new Intent(Intent.ACTION_PICK);

                i.setType("image/*");

                //Start new activity with the LOAD_IMAGE_RESULT to handle back the result when the image is picked from the Image Gallery
                startActivityForResult(i, LOAD_IMAGE_RESULTS);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        InputStream ImageStream = null;

        /*Checking if the activity that was triggered was the ImageGallery
          If so then requestCode will match the LOAD_IMAGE_RESULTS value
          If the resultCode is RESULT_OK &
          There is some data that we know that image was picked*/
        if(requestCode==LOAD_IMAGE_RESULTS && resultCode== Activity.RESULT_OK && data!=null)
        {
            try
            {
                //Let's read the picked image -its URI
                final Uri pickedImage = data.getData();


                //Let's read the image path using content resolver
                ImageStream = getContentResolver().openInputStream(pickedImage);

                //Now let's set the GUI ImageView data with data read from the picked file
                final Bitmap selectedImage = BitmapFactory.decodeStream(ImageStream);
                image.setImageBitmap(selectedImage);

                button.setText("Confirm");
                button.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View view) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();

                        String picture = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        user.setProfilePic(picture);

                        new EndpointsPictureTask().execute(new Pair<Context, String>(ChangePicture.this, user.getEmail() + ";=;" + user.getProfilePic()));
                    }
                });
            }

            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }

            finally
            {
                if(ImageStream != null)
                {
                    try
                    {
                        ImageStream.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }

            }


        }

    }

    public void gotoProfile() {

    }

    public void cancelClick(View view) {
        this.onBackPressed();
    }
}
