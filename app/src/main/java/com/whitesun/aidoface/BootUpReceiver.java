package com.whitesun.aidoface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sumeendranath on 10/03/17.
 */
public class BootUpReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AidoFace.class);  //MyActivity can be anything which you want to start on bootup...
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
