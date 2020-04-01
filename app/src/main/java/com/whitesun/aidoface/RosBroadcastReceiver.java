package com.whitesun.aidoface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import aido.properties.HttpProperties;

public class RosBroadcastReceiver extends BroadcastReceiver {
    public RosBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);

        Log.i("AUTOMATE","Got ROS  message !!!" + message);

    }


}
