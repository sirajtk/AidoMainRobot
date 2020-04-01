package com.whitesun.aidoface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.whitesuntech.aidohomerobot.R;

public class ShowPressure extends AppCompatActivity {
TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pressure);
        t1= findViewById(R.id.textView11);
        t2= findViewById(R.id.textView12);

        Intent intent = getIntent();

        String id = intent.getStringExtra("pressure");
        System.out.println("Show reminder "+id);
        String[] sp=null;
        sp= id.split("_");
        if (sp.length == 1) {
            String[] spp1=sp[0].split(",");
            if(!spp1[0].isEmpty()){
                t1.setText(spp1[0]);
            }
            if(!spp1[1].isEmpty()){
                t2.setText(spp1[1]);
            }

        }
    }
}
