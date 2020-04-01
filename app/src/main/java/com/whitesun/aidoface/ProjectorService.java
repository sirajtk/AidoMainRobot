package com.whitesun.aidoface;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class ProjectorService extends Service {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference _ref = firebaseDatabase.getReference("projector");
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _ref.child("child").setValue("on");
        return super.onStartCommand(intent, flags, startId);

    }
}
