package com.whitesun.aidoface;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.whitesuntech.aidohomerobot.R;

import java.util.HashMap;
import java.util.Map;

import aido.TextToSpeech.BroadcastTTS;

public class ReceiverToStartPerticularActivity1 extends BroadcastReceiver {
    private static ActivityManager activityManager;
    ActivityManager activityManager1;
    BroadcastTTS tts;
    public  String speechToText;
    Map<Integer,Integer> begYourPardonmap ;
    @Override
    public void onReceive(Context context, Intent intent) {
        tts = new BroadcastTTS(context);
//activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        begYourPardonmap = new HashMap<>();
        begYourPardonmap.put(1, R.raw.iamnotsure);
        begYourPardonmap.put(2,R.raw.iamsorrycanyourepeatthat);
        begYourPardonmap.put(3,R.raw.iamunabletounderstandyou);
        begYourPardonmap.put(4,R.raw.myhearingisabitweak);
        begYourPardonmap.put(5,R.raw.pardonme);
        begYourPardonmap.put(6,R.raw.sorrylostyouthere);
        begYourPardonmap.put(7,R.raw.icannothearyouclearly);
        begYourPardonmap.put(0,R.raw.icannotunderstand);
        if(intent.getAction().equals("perticularActivity")){
            if(intent.getStringExtra("data")!=null) {
                if((intent.getStringExtra("data").toLowerCase()).contains("i am suffering from cold")||(intent.getStringExtra("data").toLowerCase()).contains("suffering from cold")||((intent.getStringExtra("data").toLowerCase()).contains("from cold")||(intent.getStringExtra("data").toLowerCase()).contains("cold"))){
                    tts.speak("searching medicine for u");
                    //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ohyouwantmetosearchformedicines);
                    //mediaPlayer.start();
                    Log.i("result",intent.getStringExtra("data"));
                    Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityDrugInformation");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                    context.startActivity(intent1);

                }
                else  if((intent.getStringExtra("data").toLowerCase()).contains("how is the traffic") || (intent.getStringExtra("data").toLowerCase()).contains("is the traffic") || (intent.getStringExtra("data").toLowerCase()).contains("traffic")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                    tts.speak("its look clear");
                    Log.i("result",intent.getStringExtra("data"));
                    Intent intent1 = new Intent("com.rathore.newsweathertraffic.MapsActivity");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                    context.startActivity(intent1);
                }
                else  if((intent.getStringExtra("data").toLowerCase()).contains("show me wheather update") || (intent.getStringExtra("data").toLowerCase()).contains("me wheather update") || (intent.getStringExtra("data").toLowerCase()).contains("weather update")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                    tts.speak("based on my search");
                    Log.i("result",intent.getStringExtra("data"));
                    Intent intent1 = new Intent("com.rathore.newsweathertraffic.WeatherForcast");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                    context.startActivity(intent1);
                }
                else  if((intent.getStringExtra("data").toLowerCase()).contains("search recipe") || (intent.getStringExtra("data").toLowerCase()).contains("recipe")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                    tts.speak("searching");
                    Log.i("result",intent.getStringExtra("data"));
                    Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesList");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                    context.startActivity(intent1);
                }
//                else  if((intent.getStringExtra("data").toLowerCase()).contains("show nearby hospitals") ){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
////                   mediaPlayer.start();
//                    tts.speak("i found nearby hospitals in your locality");
//                    Log.i("result",intent.getStringExtra("data"));
//                    Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHospitalFind");
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                    context.startActivity(intent1);
//                }
            }

        }
    }
}
