package com.whitesun.aidoface;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FaceRecoService extends Service {
    public FaceRecoService() {
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference _firebase = database.getReference();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String value = intent.getStringExtra("value");
        switch (value) {
            case "1":
                _firebase.child("fiveaction").child("facerecoganition").child("CommandAido").setValue("2");
                stopSelf();
                break;
            case "2":
                _firebase.child("fiveaction").child("facerecoganition").child("CommandAido").setValue("4");
                stopSelf();
                break;
            case "3":
                _firebase.child("fiveaction").child("facerecoganition").child("CommandAido").setValue("3");
                stopSelf();
                break;
            default:
                System.out.println("error");
                break;
        }

        return START_NOT_STICKY;


    }
}
