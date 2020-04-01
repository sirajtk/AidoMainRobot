package com.whitesuntech.aidohomerobot;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by rathore on 04/08/17.
 */

public class FaceRecognition extends PreferenceActivity{
    public void previousButtonClick(View view){

        Intent intent=new Intent(getApplicationContext(),UserInfo.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifisetup1);
        Button next = findViewById(R.id.nextbutton  );
        next.setVisibility(View.GONE);

        addPreferencesFromResource(R.xml.facerecognition);

    }
}
