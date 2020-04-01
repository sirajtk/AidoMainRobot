package com.whitesun.aidoface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whitesuntech.aidohomerobot.R;

import java.util.ArrayList;
import java.util.List;

public class FirstToRun extends Activity {
    //FirebaseDatabase _firedbhandle = FirebaseDatabase.getInstance();
   // DatabaseReference _firedbreference = _firedbhandle.getReference();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference tripsRef = rootRef.child("AidoRobot1").child("Network");
    //public static String fire_aidoid = "Network";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_to_run);

        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        rootRef.child("Android IP").setValue(ip);
        //databaseReference.child("Android Ip").setValue(ip);
        System.out.println("Android IP:" +ip);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            List<String> list = new ArrayList<>();
                            String time = null;
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String arrival = ds.child("connected").getValue(String.class);
                                String departure = ds.child("ext_ip").getValue(String.class);
                                time = ds.child("int_ip").getValue(String.class);
                                Log.d("TAG", arrival + " / " + departure + " / " + time);
                                list.add(time);
                            }
                            Log.d("TAG", time);
                            System.out.println("insdie firebase" + time);
                            // String ip1 = (String) dataSnapshot2.getValue();
                            // System.out.println("FIREBASE:" + ip1);
                            // SharedPreferences sharedPreferences = getSharedPreferences("your_preferences", Activity.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = sharedPreferences.edit();
                            //editor.putString("your_integer_key", ip1);
                            //editor.apply();
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(FirstToRun.this, AidoFace.class);
                            bundle.putString("Valu", time);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            bundle.clear();
                            finish();
                        } else {
                            Toast.makeText(FirstToRun.this, "no Ip assign", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                tripsRef.addListenerForSingleValueEvent(valueEventListener);

            }
        }, 100);



    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}
