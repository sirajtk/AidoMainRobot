package com.whitesun.aidoface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import aido.TextToSpeech.BroadcastTTS;

/**
 * Created by rathore on 07/11/17.
 */

public class EmotionFaceBroadCastReceiver extends BroadcastReceiver {
    BroadcastTTS _ttsaido1;
    @Override
    public void onReceive(Context context, Intent intent) {
       // Log.i("emotionbroadcastRece","called");
        _ttsaido1 = new BroadcastTTS(context);

        if(intent!=null){
            if(intent.getAction().equals("com.example.ajeet.emotionfaces")){
                Log.i("emotionbroadcastRece","called");
                String s = intent.getStringExtra("data");
                _ttsaido1.speak(s);
            }
        }

    }
}
