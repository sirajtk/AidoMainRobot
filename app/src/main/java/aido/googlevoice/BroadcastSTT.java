package aido.googlevoice;

import android.content.Context;
import android.content.Intent;

import aido.properties.HttpProperties;
import aido.properties.VoiceProperties;

/**
 * Created by sumeendranath on 27/07/16.
 */
public class BroadcastSTT {
    Context _maincontext;
    public BroadcastSTT(Context context) {
        _maincontext = context;
    }

    public  void sendCommand(String message)
    {
        Intent intent = new Intent();
        intent.setAction(HttpProperties.PROP_STT_BROADCAST_INTENTID);
        intent.putExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD, message);
        System.out.println("06/02/19:message" +message);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        _maincontext.sendBroadcast(intent);

    }
}
