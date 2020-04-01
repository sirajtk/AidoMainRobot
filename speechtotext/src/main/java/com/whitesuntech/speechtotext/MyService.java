package com.whitesuntech.speechtotext;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;



@SuppressLint("Registered")
public class MyService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("RESTART_SERVICE");
        sendBroadcast(intent);
    }
}
