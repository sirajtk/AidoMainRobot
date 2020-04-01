package com.whitesun.aidoface.settings.social;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whitesuntech.aidohomerobot.R;

import aido.common.CommonlyUsed;
import aido.setdelay.SetDelay;
import aido.skype.SkypeAPIs;

public class MakeSkypeCallToCompanion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_skype_call_to_companion);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String aidoskypename = settings.getString("partnerskypename", "");

        if(CommonlyUsed.stringIsNullOrEmpty(aidoskypename))
        {
            CommonlyUsed.showMsg(this,"Companion skype is not paired with Aido");
            finish();
        }

        SkypeAPIs.callCompanionVideo(this,aidoskypename);

        SetDelay sd = new SetDelay(7000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                finish();
            }
        });

    }
}
