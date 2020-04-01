package com.whitesun.aidoface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import aido.TextToSpeech.BroadcastTTS;

/**
 * Created by rathore on 27/10/17.
 */

public class SceneReceiver extends BroadcastReceiver {
    BroadcastTTS _ttsaido1;
    @Override
    public void onReceive(Context context, Intent intent) {
        _ttsaido1 = new BroadcastTTS(context);
        Log.i("FaceRecognitionReceiver","called");
        if(intent!=null){
            if(intent.getAction().equals("com.example.ajeet.scene")){
                Log.i("sceneReceiver","called");
                if(intent.getStringExtra("data")!=null) {
                    Log.i("scene broadcastreciver",intent.getStringExtra("data"));
                    _ttsaido1.speak(intent.getStringExtra("data"));

                }
            }
        }
}}
