package com.whitesuntech.aidohomerobot;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import aido.TextToSpeech.BroadcastTTS;
import aido.skype.SkypeAPIs;

/**
 * Created by rathore on 04/08/17.
 */

public class Pairing extends PreferenceActivity {
    Button nextbutton;
    public void nextButtonClick(View view){
        Log.i("nextbutton","clicked");

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wifisetup1);
        nextbutton = findViewById(R.id.nextbutton);

        addPreferencesFromResource(R.xml.pairing);




//
    }
}
