package com.whitesun.aidoface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import aido.TextToSpeech.BroadcastTTS;

/**
 * Created by rathore on 25/10/17.
 */

public class FaceRecognitionReceiver extends BroadcastReceiver
{
    BroadcastTTS _ttsaido1;
    @Override
    public void onReceive(Context context, Intent intent) {
        _ttsaido1 = new BroadcastTTS(context);
        Log.i("FaceRecognitionReceiver","called");
        if(intent!=null){
            if(intent.getAction().equals("com.example.ajeet.facerecognition1")){
                Log.i("FaceRecognitionReceiver","called");
                //Log.i("data11",intent.getStringExtra("data1"));
                if(intent.getStringExtra("data")!=null) {
                    if(intent.getStringExtra("data").toLowerCase().equals("happy"))
                    {

                    _ttsaido1.speak("happy");
               }
                if(intent.getStringExtra("data").toLowerCase().equals("angry")){

                    _ttsaido1.speak("angry");
            }
            if(intent.getStringExtra("data").toLowerCase().equals("surprise")){

                _ttsaido1.speak("surprise");
        }
                    if(intent.getStringExtra("data").toLowerCase().equals("neutral")){

                        _ttsaido1.speak("neutral");
                    }
            }
        }
    }
}}
