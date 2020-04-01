package com.whitesun.aidoface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import aido.properties.HttpProperties;

public class AutomateBroadcastReceiver extends BroadcastReceiver {
    public AutomateBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");

        String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);

        Log.i("AUTOMATE","Got AUTOMATE  message !!!" + message);
        Log.i("AUTOMATE","sending ROS  message !!!" + message);

        Intent intent2 = new Intent();
        intent2.setAction("com.whitesun.httpdownload");
        intent2.putExtra("message", message);
        intent2.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent2);

    }
}
