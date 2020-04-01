package com.whitesun.aidoface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import aido.TextToSpeech.BroadcastTTS;

/**
 * Created by rathore on 26/10/17.
 */

public class SensorReceiver extends BroadcastReceiver {
    BroadcastTTS _ttsaido1;
    String s;

    @Override
    public void onReceive(Context context, Intent intent) {

        _ttsaido1 = new BroadcastTTS(context);
        Log.i("sensorReceiver","called");


        if(intent!=null){

            if(intent.getAction().equals("com.example.ajeet.sensor")){
                Log.i("sensorReceiver","called");
                if(intent.getStringExtra("data")!=null) {
                    Log.i("sensorReceiverdata", intent.getStringExtra("data"));

                    s = intent.getStringExtra("data");
                    if (intent.getStringExtra("adata") != null && intent.getStringExtra("wdata") != null && intent.getStringExtra("hdata") != null) {
                        //ambience
                        Log.i("sensorReceiver", "called");
                        if (intent.getStringExtra("data").toLowerCase().equals("ambience")) {
                            if (intent.getStringExtra("adata").toLowerCase().equals("dark")) {
                                _ttsaido1.speak("ambiencedark");
                            }
                            if (intent.getStringExtra("adata").toLowerCase().equals("day light")) {
                                _ttsaido1.speak("ambienceprrretydim");
                            }
                            if (intent.getStringExtra("adata").toLowerCase().equals("sun light")) {
                                _ttsaido1.speak("ambiencebright");
                            }
                            if (intent.getStringExtra("adata").toLowerCase().equals("very bright")) {
                                _ttsaido1.speak("ambienceextremlybright");
                            }
                        }
                        //cold
                        if (intent.getStringExtra("data").toLowerCase().equals("cold")) {
                            if (intent.getStringExtra("wdata").toLowerCase().equals("it seems to be very hot")) {
                                _ttsaido1.speak("coldhot1");
                            }
                            if (intent.getStringExtra("wdata").toLowerCase().equals("it's a nice warm day")) {
                                _ttsaido1.speak("coldnicewarmday");
                            }
                            if (intent.getStringExtra("wdata").toLowerCase().equals("it's a bit cold")) {
                                _ttsaido1.speak("coldbitcold");
                            }
                            if (intent.getStringExtra("wdata").toLowerCase().equals("its freezing")) {
                                _ttsaido1.speak("coldfreezing");
                            }
                        }
                        //humid
                        if (intent.getStringExtra("data").toLowerCase().equals("humid")) {
                            if (intent.getStringExtra("hdata").toLowerCase().equals("it's very humid and i feel sweaty")) {
                                _ttsaido1.speak("feelsweaty");
                            }
                            if (intent.getStringExtra("hdata").toLowerCase().equals("it's a bit humid")) {
                                _ttsaido1.speak("bithumid");
                            }
                            if (intent.getStringExtra("hdata").toLowerCase().equals("it's not so humid")) {
                                _ttsaido1.speak("verydry");
                            }
                        }

                    }
                }







    }

    }
}
}
