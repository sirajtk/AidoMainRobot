package com.whitesun.aidoface;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProjectorOn extends Service {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference _ref = firebaseDatabase.getReference("Projector");
    public ProjectorOn() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _ref.child("projector").setValue("on");
        return super.onStartCommand(intent, flags, startId);
    }
}
