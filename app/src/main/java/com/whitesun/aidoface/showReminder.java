package com.whitesun.aidoface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.whitesuntech.aidohomerobot.R;

public class showReminder extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    //FirebaseDatabase _firedbhandle = FirebaseDatabase.getInstance();
    //DatabaseReference _firedbreference = _firedbhandle.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminder);
        t1= findViewById(R.id.textView11);
        t2= findViewById(R.id.textView12);
        t3= findViewById(R.id.textView13);
        t4= findViewById(R.id.textView14);
        t5= findViewById(R.id.textView15);
        t6= findViewById(R.id.textView16);
        t7= findViewById(R.id.textView17);
        t8= findViewById(R.id.textView18);
        t9= findViewById(R.id.textView19);
        t10= findViewById(R.id.textView20);
        t11= findViewById(R.id.textView21);
        t12= findViewById(R.id.textView22);

        Intent intent = getIntent();

        String id = intent.getStringExtra("return_text_this");
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
            if(!spp1[1].isEmpty()){
                t3.setText(spp1[2]);
            }
        }

        if (sp.length == 2) {
            String[] spp1=sp[0].split(",");
            if(!spp1[0].isEmpty()){
                t1.setText(spp1[0]);
            }
            if(!spp1[1].isEmpty()){
                t2.setText(spp1[1]);
            }
            if(!spp1[1].isEmpty()){
                t3.setText(spp1[2]);
            }
            String[] spp2=sp[1].split(",");
            if(!spp2[0].isEmpty()){
                t4.setText(spp2[0]);
            }
            if(!spp2[1].isEmpty()){
                t5.setText(spp2[1]);
            }
            if(!spp2[2].isEmpty()){
                t6.setText(spp2[2]);
            }

        }

        if (sp.length == 3) {
            String[] spp1=sp[0].split(",");
            if(!spp1[0].isEmpty()){
                t1.setText(spp1[0]);
            }
            if(!spp1[1].isEmpty()){
                t2.setText(spp1[1]);
            }
            if(!spp1[1].isEmpty()){
                t3.setText(spp1[2]);
            }
            String[] spp2=sp[1].split(",");
            if(!spp2[0].isEmpty()){
                t4.setText(spp2[0]);
            }
            if(!spp2[1].isEmpty()){
                t5.setText(spp2[1]);
            }
            if(!spp2[2].isEmpty()){
                t6.setText(spp2[2]);
            }
            String[] spp3=sp[2].split(",");
            if(!spp3[0].isEmpty()){
                t7.setText(spp3[0]);
            }
            if(!spp3[1].isEmpty()){
                t8.setText(spp3[1]);
            }
            if(!spp3[1].isEmpty()){
                t9.setText(spp3[2]);
            }
        }

        if (sp.length == 4) {

            String[] spp1=sp[0].split(",");
            if(!spp1[0].isEmpty()){
                t1.setText(spp1[0]);
            }
            if(!spp1[1].isEmpty()){
                t2.setText(spp1[1]);
            }
            if(!spp1[1].isEmpty()){
                t3.setText(spp1[2]);
            }
            String[] spp2=sp[1].split(",");
            if(!spp2[0].isEmpty()){
                t4.setText(spp2[0]);
            }
            if(!spp2[1].isEmpty()){
                t5.setText(spp2[1]);
            }
            if(!spp2[2].isEmpty()){
                t6.setText(spp2[2]);
            }
            String[] spp3=sp[2].split(",");
            if(!spp3[0].isEmpty()){
                t7.setText(spp3[0]);
            }
            if(!spp3[1].isEmpty()){
                t8.setText(spp3[1]);
            }
            if(!spp3[1].isEmpty()){
                t9.setText(spp3[2]);
            }
            String[] spp4=sp[3].split(",");
            if(!spp4[0].isEmpty()){
                t10.setText(spp4[0]);
            }
            if(!spp4[1].isEmpty()){
                t11.setText(spp4[1]);
            }
            if(!spp4[1].isEmpty()){
                t12.setText(spp4[2]);
            }

            }


        }


    }

