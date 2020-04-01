package com.whitesun.aidoface;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import aido.properties.HttpProperties;

/**
 * Created by rathore on 19/10/17.
 */

public class BeckgroundServicee extends Service {
    Handler handler;
    Thread t;
    SpeechBroadcastReceiver speechBroadcastReceiver ;
    //DatabaseHandler databaseHandler;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                startJob();
            }
        });
        t.start();

        return START_NOT_STICKY;
    }

    public void startJob(){
        Toast.makeText(getApplicationContext(),"Aido Service run",Toast.LENGTH_SHORT).show();
        android.os.Handler handler1 = new android.os.Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//
                speechBroadcastReceiver=new SpeechBroadcastReceiver(){
                          @Override
                          public void onReceive(Context context, Intent intent) {
                              super.onReceive(context, intent);
                              if(intent.getAction().equalsIgnoreCase(HttpProperties.PROP_STT_RECEIVER_INTENTID)) {
//


                                  final String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);
                                  Log.e("service anil", "onReceive: " );
                                  Log.i("messageinservice", message);
                                 if(message.contains("open")){
                                     Log.i("messageinservice", message);
                                     Intent intent1 = new Intent();
                                intent1.setAction("com.example.archi.health.start");
                              intent.setComponent(new ComponentName("com.example.archi.health", "com.example.archi.health..Activity.ActivityDrugInformation"));
//                                //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(intent1);
//                                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.example.archi.health.Activity");
//                                startActivity(LaunchIntent);
                               }
                              }
                          }
//

                };
            }
            };
            handler1.post(runnable);
        try {
            //Thread.sleep(5000);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.i("ThreadSleep","Sleeping");
        startJob();





    }
}













