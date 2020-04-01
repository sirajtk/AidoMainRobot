package com.whitesun.aidoface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whitesuntech.aidohomerobot.R;

public class Scene extends Activity {

    private static final String TAG = "ActivityAddReminder";
    private TextView demoValue;
    private TextView demoValue2;
    private Button fetch;
    private Button fetch2;
    private DatabaseReference rootRef;
    private DatabaseReference demoRef;
    private ImageView imgView;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String captImagePath;

    public byte[] inputData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        demoValue = findViewById(R.id.tvValue);
        imgView = findViewById(R.id.imageView1);


        rootRef = FirebaseDatabase.getInstance().getReference();
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
}
