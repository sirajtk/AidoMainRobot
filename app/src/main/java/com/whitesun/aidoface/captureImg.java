package com.whitesun.aidoface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whitesuntech.aidohomerobot.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class captureImg extends Activity {

    private Camera camera; // camera object
    private TextView textTimeLeft; // time left field
    private ImageView image;
    private TextView demoValue;
    private DatabaseReference rootRef;
    private DatabaseReference demoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_img);

        textTimeLeft = findViewById(R.id.textView); // make time left object
        image = findViewById(R.id.imgv1);
        demoValue = findViewById(R.id.tvValue);


        camera = Camera.open();
        SurfaceView view = new SurfaceView(this);

        try {
            camera.setPreviewDisplay(view.getHolder()); // feed dummy surface to surface
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        camera.startPreview();




        startTimer();


        rootRef = FirebaseDatabase.getInstance().getReference("aidotest1");
        //database reference pointing to demo node
        demoRef = rootRef.child("emotion");


        String value1 = "true";
        //push creates a unique id in database
        demoRef.child("sflag").setValue(value1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        demoRef.child("scene").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                demoValue.setText(value);
                Log.i("scene", value);
                Intent intentperticularactivity = new Intent();
                intentperticularactivity.setAction("com.whitesun.aidoface");
                intentperticularactivity.putExtra("data", value);
                sendBroadcast(intentperticularactivity);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        String value2 = "false";
        //push creates a unique id in database
        demoRef.child("sflag").setValue(value2);



    }




    Camera.PictureCallback jpegCallBack=new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            // set file destination and file name
            // Bitmap resizedbitmap = (Bitmap) data.getExtras().get("data");

            File destination=new File(Environment.getExternalStorageDirectory(),"myPicture.jpg");

           /* Bitmap rotatedBitmap = Bitmap.createBitmap(resizedbitmap, 0, 0, resizedbitmap.getWidth(),
                    resizedbitmap.getHeight(), matrix, true);*/
            try {
                Bitmap userImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                // set file out stream
                FileOutputStream out = new FileOutputStream(destination);
                // set compress format quality and stream
                userImage.compress(Bitmap.CompressFormat.JPEG, 70, out);


                ImageView image = findViewById(R.id.imgv1);

                image.setRotation(0);
                image.setImageBitmap(userImage);

                /*   //image.setImageBitmap*//*(Bitmap.createScaledBitmap(userImage, image.getWidth(),
                        image.getHeight(), false));*/
//                Bitmap imageCapture = (Bitmap) data.getExtras().get("data");
//                imgView1.setImageBitmap(imageCapture);

                //Bundle extra = data.getExtras();
                //  imgView1.setImageBitmap(userImage);





            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    };


    public void startTimer(){

        // 5000ms=5s at intervals of 1000ms=1s so that means it lasts 5 seconds
        new CountDownTimer(0000,1000){

            @Override
            public void onFinish() {
                // count finished
                textTimeLeft.setText("Picture Taken");
                camera.takePicture(null, null, null, jpegCallBack);

            }

            @Override
            public void onTick(long millisUntilFinished) {
                // every time 1 second passes
                textTimeLeft.setText("Seconds Left: "+millisUntilFinished/1000);
                camera.release();
            }

        }.start();
    }
}
