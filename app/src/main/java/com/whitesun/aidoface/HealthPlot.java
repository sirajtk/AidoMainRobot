package com.whitesun.aidoface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whitesuntech.aidohomerobot.R;

import aido.TextToSpeech.BroadcastTTS;

public class HealthPlot extends AppCompatActivity {
    BroadcastTTS _ttsaido;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_plot);
        _ttsaido = new BroadcastTTS(HealthPlot.this);
        _ttsaido.speak("here is the health plot");

    }
}
