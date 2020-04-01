package com.whitesun.aidoface;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.whitesun.aidoface.settings.SettingsActivity;
import com.whitesun.aidoface.settings.appliances.HomeApplianceSetting;
import com.whitesun.aidoface.settings.appliances.PhoneSeting;
import com.whitesun.aidoface.settings.appliances.TabletSetting;
import com.whitesun.aidoface.settings.appliances.TvSetting;
import com.whitesun.aidoface.settings.appliances.UniversalRemoteSetting;
import com.whitesun.aidoface.settings.wifi.WifiCredentials;
import com.whitesuntech.aidohomerobot.FaceRecognition;
import com.whitesuntech.aidohomerobot.R;
import com.whitesuntech.aidohomerobot.UserInfo;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import aido.TextToSpeech.BroadcastTTS;


/**
 * Created by rathore on 19/10/17.
 */

public class ReceiverToStartPerticularActivity extends BroadcastReceiver{
    private static ActivityManager activityManager;
    ActivityManager activityManager1;
    BroadcastTTS tts;
    public  String speechToText;
    Map<Integer,Integer> begYourPardonmap ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        tts = new BroadcastTTS(context);
     //activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        begYourPardonmap = new HashMap<>();
        begYourPardonmap.put(1,R.raw.iamnotsure);
        begYourPardonmap.put(2,R.raw.iamsorrycanyourepeatthat);
        begYourPardonmap.put(3,R.raw.iamunabletounderstandyou);
        begYourPardonmap.put(4,R.raw.myhearingisabitweak);
        begYourPardonmap.put(5,R.raw.pardonme);
        begYourPardonmap.put(6,R.raw.sorrylostyouthere);
        begYourPardonmap.put(7,R.raw.icannothearyouclearly);
        begYourPardonmap.put(0,R.raw.icannotunderstand);
        if(intent.getAction().equals("perticularActivity")){
           if(intent.getStringExtra("data")!=null){
            //getForegroundProcess(context,intent.getStringExtra("data"));
            Log.i("perticularActivity","called");
            Log.i("result",intent.getStringExtra("data"));
            // health App
               if((intent.getStringExtra("data").toLowerCase()).contains("how is the traffic") || (intent.getStringExtra("data").toLowerCase()).contains("is the traffic") || (intent.getStringExtra("data").toLowerCase()).contains("traffic")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("its look clear");
                   Log.i("result",intent.getStringExtra("data"));
                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.MapsActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                   context.startActivity(intent1);
               }
//               else  if((intent.getStringExtra("data").toLowerCase()).contains("temperature details")||(intent.getStringExtra("data").toLowerCase()).contains("temperature") ){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
////                   mediaPlayer.start();
//                   tts.speak("okay..Here is the temperature details");
//                   Log.i("result",intent.getStringExtra("data"));
//                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.WeatherForcast");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                   context.startActivity(intent1);
//               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("how is your health") || (intent.getStringExtra("data").toLowerCase()).contains("your health") || (intent.getStringExtra("data").toLowerCase()).contains("is your health")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("i am good");

               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("weather update")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("here is the weather update");
                   Log.i("result",intent.getStringExtra("data"));
                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.WeatherForcast");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("search recipe") || (intent.getStringExtra("data").toLowerCase()).contains("recipe")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("search your recipe here");
                   Log.i("result",intent.getStringExtra("data"));
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesList");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("nearby hospitals")){ /*|| (intent.getStringExtra("data").toLowerCase()).contains("nearby hospitals") || (intent.getStringExtra("data").toLowerCase()).contains("near hospital")*/
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("i found nearby hospitals in your locality");
                   Log.i("result",intent.getStringExtra("data"));
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHospitalFind");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("humid") ){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("its humid.Turn ON your AC.");


               }
               else  if( (intent.getStringExtra("data").toLowerCase()).contains("enough juice") ){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("Yeah i am 80% full");


               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("all notes") || (intent.getStringExtra("data").toLowerCase()).contains("show all notes") || (intent.getStringExtra("data").toLowerCase()).contains("note")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("see all your notes here ");
                   Intent intent1 = new Intent("com.rathore.evernoteapi.AllNotes");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("set timer")|| (intent.getStringExtra("data").toLowerCase()).contains("timer")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem27);
//                   mediaPlayer.start();
                   tts.speak("ok! ready to go");
                   Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                   i.putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm");
                   i.putExtra(AlarmClock.EXTRA_HOUR, 10);
                   i.putExtra(AlarmClock.EXTRA_MINUTES, 30);
                   context.startActivity(i);

               }
//               &&(intent.getStringExtra("data").toLowerCase()).contains("medicine")
               else   if(((intent.getStringExtra("data").toLowerCase()).contains("what are the side effects")) || (intent.getStringExtra("data").toLowerCase()).contains("side effects"))
               {
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem26);
//                   mediaPlayer.start();
                   //tts.speak("These side effects can range from mild to serious and include headache, nervousness, sleep problems, vomiting, diarrhea, irritability, irregular heartbeat, tremor, heartburn, dizziness, ringing in the ears, convulsions, and confusion");
                   tts.speak("search medicine to see side effects");
                   //tts.speak("devmp3");
                   Log.i("result",intent.getStringExtra("data"));
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityDrugInformation");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                   context.startActivity(intent1);
               }
               else   if(((intent.getStringExtra("data").toLowerCase()).contains("change song") || (intent.getStringExtra("data").toLowerCase()).contains("chain song")))
               {
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem26);
//                   mediaPlayer.start();
                   //tts.speak("These side effects can range from mild to serious and include headache, nervousness, sleep problems, vomiting, diarrhea, irritability, irregular heartbeat, tremor, heartburn, dizziness, ringing in the ears, convulsions, and confusion");
                   tts.speak("nextsongplayed");
                   //tts.speak("devmp3");
                   Log.i("result",intent.getStringExtra("data"));
                   Intent intent1 = new Intent("android.intent.action.MAIN");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

                   context.startActivity(intent1);
               }





//            if(intent.getStringExtra("data").toLowerCase().contains("close it")){
//                Log.i("resultdrug",intent.getStringExtra("data"));
//                Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityDrugInformation");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent1.putExtra("data","close it");
//                context.startActivity(intent1);
//
//            }
//close
               else  if((intent.getStringExtra("data").toLowerCase()).contains("close health")){
                Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_health);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.CloseActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("data","close it");
                context.startActivity(intent1);

            }
//               else  if((intent.getStringExtra("data").toLowerCase()).contains("move north") || (intent.getStringExtra("data").toLowerCase()).contains("north")){
//                   Log.i("resultdrug",intent.getStringExtra("data"));
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_health);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent(context,MobilityService.class);
//                   //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   //intent1.putExtra("data","close it");
//                   context.startService(intent1);
//
//
//               }




//               else  if((intent.getStringExtra("data").toLowerCase()).contains("move north") || (intent.getStringExtra("data").toLowerCase()).contains(" north")){
//                   Log.i("resultdrug",intent.getStringExtra("data"));
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_health);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent(context,ProjectorOn.class);
//                   //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   //intent1.putExtra("data","close it");
//                   context.startService(intent1);
//
//
//               }


               else  if((intent.getStringExtra("data").toLowerCase()).contains("close kitchen")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_kitchen);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("close radio")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_kitchen);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.yusufcakmak.exoplayersample.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }


               else if((intent.getStringExtra("data").toLowerCase()).contains("close personal assistance")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_personal_assistance);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.evernoteapi.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("close alert system")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_alert_system);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.aidoalertsystem.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("close video player")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_alert_system);
//                   mediaPlayer.start();
                   tts.speak("closing video player");
                   Intent intent1 = new Intent("com.example.anil.videoplayer.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("close news")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_news);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("close traffic updates")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_traffic_updates);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("close weather report")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_weather_report);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("close social telecast")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_social_telecast);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.socialtelecast.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }
//               else  if((intent.getStringExtra("data").toLowerCase()).contains("close sensor")){
//                   Log.i("resultdrug",intent.getStringExtra("data"));
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_sensor);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.ajeet.sensor.CloseActivity");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   intent1.putExtra("data","close it");
//                   context.startActivity(intent1);
//
//               }
//               else  if((intent.getStringExtra("data").toLowerCase()).contains("close universal remote")){
//                   Log.i("resultdrug",intent.getStringExtra("data"));
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_universal_remote);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.madan.universalremote.CloseActivity");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   intent1.putExtra("data","close it");
//                   context.startActivity(intent1);
//
//               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("close home maintenance")){
                   Log.i("resultdrug",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.close_home_maintenance);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.CloseActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent1.putExtra("data","close it");
                   context.startActivity(intent1);

               }
               //close done



//               if(intent.getStringExtra("data").toLowerCase().contains(("close hospital"))){
//                   Log.i("resulthospitalsearch",intent.getStringExtra("data"));
//
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHospitalFind");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   intent1.putExtra("data","close it");
//                   intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
//                   context.startActivity(intent1);
//
//               }

//               else   if((intent.getStringExtra("data").toLowerCase()).contains("see health profiles")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.see_health_profiles);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHealthRecord");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
//
            else if ((intent.getStringExtra("data").toLowerCase()).contains("add health record")){
//                   Log.e("health ", " record");

//                   if((intent.getStringExtra("data").toLowerCase()).contains("add health profile")) {
//                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.i_amaddingyourhealthprofile2);
//                       mediaPlayer.start();
                       tts.speak("you can add new health record here");
                       Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHealthRecord");
                       intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       context.startActivity(intent1);

                   }

//                   else if((intent.getStringExtra("data").toLowerCase()).contains("update health profile")){
////                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.you_want_to_update_your_health_profile);
////                       mediaPlayer.start();
//                       tts.speak(" ");
//                   }
//                   else {
////                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.healthprofile);
////                       mediaPlayer.start();
//                       tts.speak(" ");

//                   }
//                   if((intent.getStringExtra("data").toLowerCase()).contains("treatment")){
////                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.treatment);
////                       mediaPlayer.start();
//                       tts.speak(" ");
//                   }
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHealthRecord");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
           // }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("treatment")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file3);
//                   mediaPlayer.start();
                   //tts.speak("devmp3");
                   tts.speak("treatment");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.Profile.ActivityProfileReportSomethingTab");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);

               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("symptoms") || (intent.getStringExtra("data").toLowerCase()).contains("check my symptoms")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem29);
//                   mediaPlayer.start();
                   tts.speak("Here is your symptoms");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.Profile.ActivityProfileReportSomethingTab");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }



//               else  if((intent.getStringExtra("data").toLowerCase()).contains("doctor")&&((intent.getStringExtra("data").toLowerCase()).contains("visit"))){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.doctorvisit);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHealthRecord");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
//               else if(((intent.getStringExtra("data").toLowerCase()).contains("near")||(intent.getStringExtra("data").toLowerCase()).contains("near me"))&&((intent.getStringExtra("data").toLowerCase()).contains("hospital"))){
//                   Log.i("resultttt1",intent.getStringExtra("data"));
//                   // MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.near_hospital);
//                   //mediaPlayer.start();
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHospitalFind");
//
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
//            else if ((intent.getStringExtra("data").toLowerCase()).contains("nearby hospitals")||(intent.getStringExtra("data").toLowerCase()).contains("search hospitals with address")||(intent.getStringExtra("data").toLowerCase()).contains("search hospitals") || (intent.getStringExtra("data").toLowerCase()).contains("near hospitals")){
//                   Log.i("resultttt2",intent.getStringExtra("data"));
//                   //if((intent.getStringExtra("data").toLowerCase()).contains("nearby hospitals")) {
////                      MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file7);
////                      mediaPlayer.start();
//                      tts.speak("nearby hospitals");
//                  //}
////                   else {
////                      MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.these_are_the_hospitals_with_addresses);
////                      mediaPlayer.start();
////                  }
//
//                Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHospitalFind");
//
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }

//               else  if((intent.getStringExtra("data").toLowerCase()).contains("find hospital")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.go_hospital);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityHospitalFind");
//
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("add health investigation")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.this_is_where_you_can_add_an_investigation);
//                   mediaPlayer.start();
                   tts.speak("you can add your health investigation here");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddInvestigation");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("see health investigation")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.please_find_all_the_investigations_here);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityTabMedicineReminder");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("update health investigation")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.i_can_help_you_with_that_here_is_the_investigation_update_screen);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityTabMedicineReminder");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }


//            else if ((intent.getStringExtra("data").toLowerCase()).contains("add medicine")){
//                Log.i("resultttt",intent.getStringExtra("data"));
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.whichmedication_would_you_like_to_add);
////                   mediaPlayer.start();
//                   tts.speak("you can add your medicine");
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddMedications");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }
               else if ((intent.getStringExtra("data").toLowerCase()).contains("open drug information")){
                   Log.i("resultttt",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.whichmedication_would_you_like_to_add);
//                   mediaPlayer.start();
                   tts.speak("search your drug here");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityDrugInformation");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if (((intent.getStringExtra("data").toLowerCase()).contains("add medicine"))){
                   Log.i("resultttt",intent.getStringExtra("data"));
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem30);
//                   mediaPlayer.start();
                   tts.speak("set the medicine timer");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddMedications");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("add health reminder")){
//                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem24);
//                       mediaPlayer.start();
                       tts.speak("add health reminder here");
                       Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddReminder");
                       intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       context.startActivity(intent1);
                   }
//                   if((intent.getStringExtra("data").toLowerCase()).contains("update health reminder")) {
////                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.i_can_definitely_update_your_health_reminder);
////                       mediaPlayer.start();
//                       tts.speak(" ");
//                   }
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityTabMedicineReminder");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("prescribed medicine")||(intent.getStringExtra("data").toLowerCase()).contains("show me my prescribed medicine")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem25);
//                   mediaPlayer.start();
                   tts.speak("see your prescribed medication");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityTabMedicineReminder");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else if ((intent.getStringExtra("data").toLowerCase()).contains("update medication") || (intent.getStringExtra("data").toLowerCase()).contains("medication")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem25);
//                   mediaPlayer.start();
                   tts.speak("see your medication");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityTabMedicineReminder");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

            else if ((intent.getStringExtra("data").toLowerCase()).contains("add vitals")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.iamopening_the_screen_for_your_vitals);
//                   mediaPlayer.start();
                   tts.speak("you can add your vitals here");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("alcohol")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alcohol);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("intake calories")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.intakecalories);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("weight")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.weight);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("pulse")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.pulse);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("height")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.height);
//                   mediaPlayer.start();
//                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("blood pressure")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.bloodpressure);
//                   mediaPlayer.start();
                  //tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
//               else   if((intent.getStringExtra("data").toLowerCase()).contains("body temperature")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.bodytemperature);
////                   mediaPlayer.start();
//                   //tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("burn calories")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.burncalories);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("caffeine")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.caffeine);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityAddVital");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("update vitals")||(intent.getStringExtra("data").toLowerCase()).contains("vital") ||(intent.getStringExtra("data").toLowerCase()).contains("vitals")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.i_will_update_your_vitals);
//                   mediaPlayer.start();

                  // if((intent.getStringExtra("data").toLowerCase()).contains("see vitals")){
//                       MediaPlayer mediaPlayer1 = MediaPlayer.create(context, R.raw.see_all_the_vitals_i_have_stored);
//                       mediaPlayer.start();
                       tts.speak(" ");
                 //  }
                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityTabMedicineReminder");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

            //kitchen App

//               else  if((intent.getStringExtra("data").toLowerCase()).contains("diet")){
//                  //Log.i("weatherrecipeconflict","called");
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.diet);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.AddDietAct");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("see all diet plans")){
                   tts.speak(" ");
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.see_all_diet_plans);
//                   mediaPlayer.start();
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.DietwhizAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("diet")&&(intent.getStringExtra("data").toLowerCase()).contains("add diet")){
                   tts.speak(" ");
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.a_health_mind_in_a_healthy_body_letmeaddthatdiet);
//                   mediaPlayer.start();
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.AddDietAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("delete")&&(intent.getStringExtra("data").toLowerCase()).contains("diet")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.me_help_you_delete_the_diet);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.DietwhizAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((((intent.getStringExtra("data").toLowerCase()).contains("view")||(intent.getStringExtra("data").toLowerCase()).contains("check")||(intent.getStringExtra("data").toLowerCase()).contains("see"))&&(intent.getStringExtra("data").toLowerCase()).contains("diet"))||(intent.getStringExtra("data").toLowerCase()).contains("plans")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.see_all_diet_plans);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.DietwhizAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

               else if((intent.getStringExtra("data").toLowerCase()).contains("make")&&(intent.getStringExtra("data").toLowerCase()).contains("recipe")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file8);
//                   mediaPlayer.start();
                   tts.speak("howtomake");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesList");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("bookmark recipe")||((intent.getStringExtra("data").toLowerCase()).contains("bookmark")||((intent.getStringExtra("data").toLowerCase()).contains("bookmarked")))&&((intent.getStringExtra("data").toLowerCase()).contains("recipes"))){
                   Log.i("weatherrecipeconflict1","called");
// MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.recipe);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.BookmarkRecipesList");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("search recipe")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file6);
//                   mediaPlayer.start();
                   tts.speak("searchrecipe");

                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesList");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

               else if ((intent.getStringExtra("data").toLowerCase()).contains("i want to make pizza") || (intent.getStringExtra("data").toLowerCase()).contains("want to make pizza") || (intent.getStringExtra("data").toLowerCase()).contains("make pizza")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file6);
//                   mediaPlayer.start();
                   tts.speak("Yummy! have a look");

                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesList");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("recipe")||(intent.getStringExtra("data").toLowerCase()).contains("recipes")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.recipe);
//                   mediaPlayer.start();
                   Log.i("weather recipeconflict2","called");
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("add recipe")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.lookslikeyou_like_the_recipe_let_me_save_it_for_later_use);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Log.i("weatherrecipeconflict3","called");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("ingredients")||(intent.getStringExtra("data").toLowerCase()).contains("ingredient")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ingredients);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Log.i("weatherrecipeconflict4","called");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("cook")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.cook);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Log.i("weatherrecipeconflict5","called");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("update recipe")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.i_can_help_you_update_a_recipe);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Log.i("weatherrecipeconflict6","called");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

               else if((intent.getStringExtra("data").toLowerCase()).contains(" eat ")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.eat);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Log.i("weatherrecipeconflict7","called");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("have breakfast")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.have_breakfast);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("have dinner")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.have_dinner);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("have brunch")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.have_brunch);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("have lunch")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.have_lunch);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else    if((intent.getStringExtra("data").toLowerCase()).contains("have snacks")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.have_snacks);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("eat brunch")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.eat_brunch);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("eat breakfast")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.eatbreakfast);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("eat dinner")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.eatdinner);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("eat lunch")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.eatlunch);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("eat now")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.eatnow);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("eat snacks")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.eat_brunch);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.RecipesItemAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }



            else if ((intent.getStringExtra("data").toLowerCase()).contains("add grocery store name")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.grocery_store);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.GroceryAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }


               else  if((intent.getStringExtra("data").toLowerCase()).contains("grocery store")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.grocery_store);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.GroceryAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("grocery")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.grocery);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.GroceryAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("see item in particular store")) {
//                  // MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.grocery_store);
                   //mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.GroceryShopAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
//               if((intent.getStringExtra("data").toLowerCase()).contains("drinks")||(intent.getStringExtra("data").toLowerCase()).contains("drink")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.drinks);
//                   mediaPlayer.start();
//                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.BarTanderVitualWineAct");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
            else if (((intent.getStringExtra("data").toLowerCase()).contains("add")&&(intent.getStringExtra("data").toLowerCase()).contains("drink"))||((intent.getStringExtra("data").toLowerCase()).contains("delete")&&(intent.getStringExtra("data").toLowerCase()).contains("drink"))){
                   if((intent.getStringExtra("data").toLowerCase()).contains("add")&&(intent.getStringExtra("data").toLowerCase()).contains("drink")) {
//                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file37);
//                       mediaPlayer.start();
                       tts.speak("drink");
                   }
                   else {
//                       MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.delete_a_drink_from_my_database);
//                       mediaPlayer.start();
                   }
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.BarTanderVitualWineAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("make")&&((intent.getStringExtra("data").toLowerCase()).contains("drinks")||(intent.getStringExtra("data").toLowerCase()).contains("drink"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem21);
//                   mediaPlayer.start();
                   tts.speak("list of drinks");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.BarTanderVitualWineAct");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("add wine to cart")||((intent.getStringExtra("data").toLowerCase()).contains("wine")&&(intent.getStringExtra("data").toLowerCase()).contains("cart"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.fancy_wine_you_have_let_me_add_it_your_cart);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.VirtualWineManager");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else if((intent.getStringExtra("data").toLowerCase()).contains("update virtual wine")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.can_help_you_with_that);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.VirtualWineManager");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("bottles list")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.a_collection_you_have_here_is_your_personal_wine_collection);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.VirtualWineManager");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("add wine")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.a_fine_wine_you_have_let_me_add_that);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.VirtualWineManager");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("wine")||(intent.getStringExtra("data").toLowerCase()).contains("wines")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem36);
//                   mediaPlayer.start();
                   tts.speak("wine");
                   Intent intent1 = new Intent("com.archirayan.kitchen.Activity.VirtualWineManager");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            // home app

//            else if ((intent.getStringExtra("data").toLowerCase()).contains("see")&&(intent.getStringExtra("data").toLowerCase()).contains("home")&&(intent.getStringExtra("data").toLowerCase()).contains("reminders")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem30);
////                   mediaPlayer.start();
//                   tts.speak("homereminder");
//                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }
            else if ((((intent.getStringExtra("data").toLowerCase()).contains("i want to see home reminder")||(intent.getStringExtra("data").toLowerCase()).contains("see home reminders"))||(intent.getStringExtra("data").toLowerCase()).contains("home reminder"))){
// ||((intent.getStringExtra("data").toLowerCase()).contains("home")&&(intent.getStringExtra("data").toLowerCase()).contains("reminder"))
// MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.are_the_reminders_i_have);
//                   mediaPlayer.start();
                   tts.speak("have a look or set new reminder");
                Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("add vendor directory")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.me_add_the_new_vendor_for_you);
//                   mediaPlayer.start();
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if (((intent.getStringExtra("data").toLowerCase()).contains(" vendor to directory")||(intent.getStringExtra("data").toLowerCase()).contains("add vendor directory"))||(intent.getStringExtra("data").toLowerCase()).contains("vendor directory")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem32);
//                   mediaPlayer.start();
                   tts.speak("yeah i got your vendor directory");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.ActivityAddVenderManually");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else if((intent.getStringExtra("data").toLowerCase()).contains("technical issue")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.technical_issue);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("mechanical issue")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.mechanical_issue);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
            else if (((intent.getStringExtra("data").toLowerCase()).contains("view")||(intent.getStringExtra("data").toLowerCase()).contains("see"))&&(intent.getStringExtra("data").toLowerCase()).contains("appliances")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file2);
//                   mediaPlayer.start();e
                   tts.speak("see appliances");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("add")&&(intent.getStringExtra("data").toLowerCase()).contains("appliances")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.add_appliances);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.ActivityAddAppliance");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

            else if (((intent.getStringExtra("data").toLowerCase()).contains("see")||(intent.getStringExtra("data").toLowerCase()).contains("view"))&&((intent.getStringExtra("data").toLowerCase()).contains("receipts")||(intent.getStringExtra("data").toLowerCase()).contains("receipt"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem31);
//                   mediaPlayer.start();
//                   tts.speak("homereceipts");
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else if ((intent.getStringExtra("data").toLowerCase()).contains("receipt")||((intent.getStringExtra("data").toLowerCase()).contains("add receipt"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.add_a_recipt);
//                   mediaPlayer.start();

                   tts.speak("homereceipts");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.ActivityAddReceipts");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if (intent.getStringExtra("data").toLowerCase().contains("bill")||(intent.getStringExtra("data").toLowerCase()).contains("bills")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem22);
//                   mediaPlayer.start();
                   tts.speak("bills");
                   Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

            //Alert App
            else if ((intent.getStringExtra("data").toLowerCase()).contains("see")&&((intent.getStringExtra("data").toLowerCase()).contains("alert")||(intent.getStringExtra("data").toLowerCase()).contains("alerts"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.are_all_the_alerts_you_have_from_me);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.aidoalertsystem.ViewAlerts");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if (((intent.getStringExtra("data").toLowerCase()).contains("set")||(intent.getStringExtra("data").toLowerCase()).contains("add"))&&((intent.getStringExtra("data").toLowerCase()).contains("alert")||(intent.getStringExtra("data").toLowerCase()).contains("alerts"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.can_set_an_alert_here);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.aidoalertsystem.MainActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("see")&&((intent.getStringExtra("data").toLowerCase()).contains("triggered alert")||(intent.getStringExtra("data").toLowerCase()).contains("triggered alerts"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.see_all_triggered_alerts);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.aidoalertsystem.Dashboard");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

            //news weather and traffic


            else if ((intent.getStringExtra("data").toLowerCase()).contains("traffic")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file4);
//                   mediaPlayer.start();
                  // tts.speak("Traffic news - Traffic information for Bangalore. All the information on real-time traffic conditions for Bangalore with ViaMichelin. Our data illustrates traffic conditions on the road and traffic conditions on the motorways in real time.");
                  tts.speak("traffic");
                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.MapsActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("weather update")||(intent.getStringExtra("data").toLowerCase()).contains("weather")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file5);
//                   mediaPlayer.start();
                   tts.speak("weather");
                Intent intent1 = new Intent("com.rathore.newsweathertraffic.WeatherForcast");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if ((intent.getStringExtra("data").toLowerCase()).contains("show news")||(intent.getStringExtra("data").toLowerCase()).contains("news") ||(intent.getStringExtra("data").toLowerCase()).contains("new")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.course_let_me_check_what_is_of_interest_to_you_now);
//                   mediaPlayer.start();
                   tts.speak("here is the latest news for you");
                   Intent intent1 = new Intent("com.rathore.newsweathertraffic.NewsActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
          // sensor madan
//            else if ((intent.getStringExtra("data").toLowerCase()).contains("open sensor")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.are_my_sensor_values);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.ajeet.sensor.MainActivity");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }
               else  if(((intent.getStringExtra("data").toLowerCase()).contains("it's sunny")) || ((intent.getStringExtra("data").toLowerCase()).contains("sunny"))){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.warm);
//                   mediaPlayer.start();
                   tts.speak("you might tuen your AC ON ");

               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("freezing")) {
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.freezing);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.ajeet.sensor.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("play a song for me")) {
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.freezing);
//                   mediaPlayer.start();
                   tts.speak("Here! is your Song");
                   Intent intent1 = new Intent("com.example.anil.videoplayer.VideoSong");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("teach me an exercise")) {
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.freezing);
//                   mediaPlayer.start();
                   tts.speak("Here! is your exercise video");
                   Intent intent1 = new Intent("com.example.anil.videoplayer.VideoExample");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("play motivational video")) {
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.freezing);
//                   mediaPlayer.start();
                   tts.speak("Here! is your motivational video");
                   Intent intent1 = new Intent("com.example.anil.videoplayer.Motivation");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

               else  if((intent.getStringExtra("data").toLowerCase()).contains("hot")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.hot);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.ajeet.sensor.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("ambience")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.how_is_the_ambience);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.ajeet.sensor.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
//               else if((intent.getStringExtra("data").toLowerCase()).contains("i am suffering from cold")||(intent.getStringExtra("data").toLowerCase()).contains("suffering from cold")||((intent.getStringExtra("data").toLowerCase()).contains("from cold")||(intent.getStringExtra("data").toLowerCase()).contains("cold"))){
//                   tts.speak("searching medicine for u");
//                   //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ohyouwantmetosearchformedicines);
//                   //mediaPlayer.start();
//                   Log.i("result",intent.getStringExtra("data"));
//                   Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityDrugInformation");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                   context.startActivity(intent1);
//
//               }


               // vivol madan
               else if ((intent.getStringExtra("data").toLowerCase()).contains("system")||(intent.getStringExtra("data").toLowerCase()).contains("about aido")||(intent.getStringExtra("data").toLowerCase()).contains("power status")||(intent.getStringExtra("data").toLowerCase()).contains("open resource")){
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.madan.vol_sys.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

            //vol up
//            else if ((intent.getStringExtra("data").toLowerCase()).contains("increase volume")){
//                   tts.speak(" ");
//                Intent intent1 = new Intent("com.example.madan.Volumeup.MainActivity");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }

            //vol down
//            else if ((intent.getStringExtra("data").toLowerCase()).contains("decrease volume")){
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.madan.Volumedown.MainActivity");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }
//            //next song
//            else if ((intent.getStringExtra("data").toLowerCase()).contains("next song")){
// tts.speak(" ");
////   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.skipping_to_the_next_song);
////                   mediaPlayer.start();
//                   Intent intent1 = new Intent("com.example.madan.Next.MainActivity");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }
            // prev song
//            else if ((intent.getStringExtra("data").toLowerCase()).contains("previous song")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.me_pull_up_the_previous_song_for_you);
////                   mediaPlayer.start();
//                   tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.madan.Previous.MainActivity");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
//            }


          // set wifi settings
            else if (intent.getStringExtra("data").contains("open Wi-Fi settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.you_can_view_wi_fi_settings_here);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, WifiCredentials.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
// set facerecognition settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open face training")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.of_course_let_me_pull_up_face_recognition);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, FaceRecognition.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
// set user settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open user settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.let_me_open_usesettingsdetails);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, UserInfo.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            // set brightness settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open brightness")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.its_getting_a_little_dark_in_here_isnt_it);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            //tv settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open tv settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.let_me_open_tv_settings);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, TvSetting.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            // tablet settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open tablet settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.are_your_tablets_i_have_in_record);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, TabletSetting.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            // phone settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open phone settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.are_your_phone_settings_iknow_of);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, PhoneSeting.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            // home appliances settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open home appliance settings")){
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, HomeApplianceSetting.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            // universal appliances settings
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open universal remote settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.opening_all_your_remote_settings);
//                   mediaPlayer.start();
                   tts.speak(" ");
                Intent intent1 = new Intent(context, UniversalRemoteSetting.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            // social
            else if ((intent.getStringExtra("data").toLowerCase()).contains("start telepresence call")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.let_me_hope_you_to_start_a_tele_call);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("open social telecast settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.let_me_open_social_telecast_settings);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if(intent.getStringExtra("data").contains("display name of owner")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.is_my_master);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

            //customization

            else if ((intent.getStringExtra("data").toLowerCase()).contains("open behaviour settings")||(intent.getStringExtra("data").toLowerCase()).contains("open petrol path")){
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
               else if((intent.getStringExtra("data").toLowerCase()).contains("open task automation settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.open_task_automation_settings);
//                   mediaPlayer.start();
                   //tts.speak("task automation settings");
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("select")&&(intent.getStringExtra("data").toLowerCase()).contains("personality")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.selectpersonality);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("notification settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.open_notification_settings);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("open data sync settings")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.open_data_sync);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent(context, SettingsActivity.class);
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

            //app recomdetion
            else if ((intent.getStringExtra("data").toLowerCase()).contains("open app recommender")||(intent.getStringExtra("data").toLowerCase()).contains("new apps")||(intent.getStringExtra("data").toLowerCase()).contains("new app")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem18);
//                   mediaPlayer.start();
                   tts.speak("newapps");
                   Intent intent1 = new Intent("com.example.ajeet.app_recomended.UserPrefAct");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

            //ebook reader
//               &&(intent.getStringExtra("data").toLowerCase()).contains("note"))||(intent.getStringExtra("data").toLowerCase()).contains("note")
            else if ((intent.getStringExtra("data").toLowerCase()).contains("create new note")||(intent.getStringExtra("data").toLowerCase()).contains("make new note")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.your_wish_is_my_command);
//                   mediaPlayer.start();
                   tts.speak(" ");
                Intent intent1 = new Intent("com.rathore.evernoteapi.NotesActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

               else  if((((intent.getStringExtra("data").toLowerCase()).contains("create")||(intent.getStringExtra("data").toLowerCase()).contains("make"))&&(intent.getStringExtra("data").toLowerCase()).contains("notebook"))||(intent.getStringExtra("data").toLowerCase()).contains("notebook")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.can_create_a_new_notebook_for_you);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.evernoteapi.NotesActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }



//social telecast
               else if((intent.getStringExtra("data").toLowerCase()).contains("share files")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.share_files);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.socialtelecast.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("send file")&&(intent.getStringExtra("data").toLowerCase()).contains("social telecast") ){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.let_me_help_you_send_file_on_social_telecast_looks_like_you_had_fun);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.socialtelecast.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("share file")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.share_file);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.socialtelecast.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("share")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.share);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.socialtelecast.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }


            else if ((intent.getStringExtra("data").toLowerCase()).contains("social telecast")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.open_social_telecast);
//                   mediaPlayer.start();
                   tts.speak(" ");
                Intent intent1 = new Intent("com.rathore.socialtelecast.MainActivity");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
            else if (((intent.getStringExtra("data").toLowerCase()).contains("add")&&(intent.getStringExtra("data").toLowerCase()).contains("social telecast"))||intent.getStringExtra("data").contains("add new group in social telecast")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.add_social_telecast_group);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.rathore.socialtelecast.createGroup");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
//system monitor
               else if ((intent.getStringExtra("data").toLowerCase()).contains("system monitor")){
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.ajeet.odrinocpuusage.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               //imotion
//               else if((intent.getStringExtra("data").toLowerCase()).contains("people")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file10);
////                   mediaPlayer.start();
//
//                   Intent intent1 = new Intent("com.example.ajeet.emotionfaces.MainActivity");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
               else if ( (intent.getStringExtra("data").toLowerCase()).contains("many faces")||(intent.getStringExtra("data").toLowerCase()).contains("many people")){
                   tts.speak(" ");
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file10);
//                   mediaPlayer.start();
tts.speak("manypeople");
                   Intent intent1 = new Intent("com.example.ajeet.emotionfaces.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }


               //facerecognition
//               else if ((intent.getStringExtra("data").toLowerCase()).contains("i feel")){
////                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.file1);
////                   mediaPlayer.start();
//                   tts.speak("ifeel");
//                  // tts.speak(" ");
//                   Intent intent1 = new Intent("com.example.ajeet.facerecognition1.MainActivity");
//                   intent1.putExtra("data","i feel");
//                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   context.startActivity(intent1);
//               }
               //scene
               else if ((intent.getStringExtra("data").toLowerCase()).contains("kind")||(intent.getStringExtra("data").toLowerCase()).contains("place")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.kind_places);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.ajeet.scene.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else if((intent.getStringExtra("data").toLowerCase()).contains("what things")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.what_things);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.ajeet.scene.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("where are")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.where_are);
//                   mediaPlayer.start();
                   tts.speak(" ");
                   Intent intent1 = new Intent("com.example.ajeet.scene.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);
               }

//               if((intent.getStringExtra("data").toLowerCase()).contains("know me")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem11);
//                   mediaPlayer.start();
//               }
               else  if((intent.getStringExtra("data").toLowerCase()).contains("your health")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem15);
//                   mediaPlayer.start();
                   tts.speak("yourhealth");

                   Intent intent1 = new Intent("com.example.madan.vol_sys.Aidodiskstatus");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);

               }
               else   if((intent.getStringExtra("data").toLowerCase()).contains("do you have enough juice")){
//                   MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.filem17);
//                   mediaPlayer.start();
                   tts.speak("doyouhaveenoughjuice");
                   Intent intent1 = new Intent("com.example.madan.vol_sys.MainActivity");
                   intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent1);

               }
               else {

                   Random random = new Random();
                   int randomnumber = random.nextInt(8);
                   //Log.i("randomgenerator",String.valueOf(randomnumber));
                   MediaPlayer mediaPlayer = MediaPlayer.create(context,begYourPardonmap.get(randomnumber));
                   mediaPlayer.start();
                   tts.speak(String.valueOf(randomnumber));
//                   tts.speak("begyourpardona");
               }





//            List<ApplicationInfo> packages;
//            PackageManager pm;
//            pm = context.getPackageManager();
//            //get a list of installed apps.
//            packages = pm.getInstalledApplications(0);

           // else if(intent.getStringExtra("data").contains("close this")){
//            ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> allTasks = mActivityManager.getRunningTasks(1000);
/* Loop through all tasks returned. */
//            for (ActivityManager.RunningTaskInfo aTask : allTasks)
//            {
//                Log.i("MyApp", "Task: " + aTask.baseActivity.getClassName());
//                //if (aTask.baseActivity.getClassName().equals("com.android.email.activity.MessageList"))
//                   // running=true;
//            }
////            for (ApplicationInfo packageInfo : packages) {
////                Log.i("active apppppp",packageInfo.toString());
//////                if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1)continue;
//////                if(packageInfo.packageName.equals("mypackage")) continue;
//////                mActivityManager.killBackgroundProcesses(packageInfo.packageName);
////            }
//            List<ActivityManager.RunningAppProcessInfo> appProcesses = mActivityManager.getRunningAppProcesses();
//            for(ActivityManager.RunningAppProcessInfo appProcess : appProcesses){
//               // if(appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
//                    Log.i("Foreground App", appProcess.processName);

            //}
}}
        //}






//            ActivityManager.RunningTaskInfo foregroundTaskInfo = mActivityManager.getRunningTasks(1).get(0);
//
//            String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
//            Log.i("foregroundTaskPackage",foregroundTaskPackageName);
//            PackageManager pm = context.getPackageManager();
//            PackageInfo foregroundAppPackageInfo = null;
//            try {
//                foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString();
//            Log.i("foregroundTaskAppName",foregroundTaskAppName);
        }
    //}







//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public static String getForegroundProcess(Context context,String data) {
//        Log.i("getForegroundProcess","called");
//        String topPackageName = null;
//
//        UsageStatsManager usage = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//        Date date=new Date();
//        long time = System.currentTimeMillis();
//        //Log.i(TAG, String.valueOf(time));
//        List<UsageStats> stats = usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, date.getTime() - 60*1000, date.getTime());
//        if (stats != null) {
//            SortedMap<Long, UsageStats> runningTask = new TreeMap<Long,UsageStats>();
//            for (UsageStats usageStats : stats) {
//                runningTask.put(usageStats.getLastTimeUsed(), usageStats);
//            }
//            if (runningTask.isEmpty()) {
//                Log.i("taskk","null");
//                return null;
//
//
//            }
//            topPackageName =  runningTask.get(runningTask.lastKey()).getPackageName();
//           // String className = runningTask.get(runningTask.lastKey()).getPackageName();
//
//        }
//        if(topPackageName==null) {
//
//        }
//        Log.i("toppackagename",topPackageName);
//
////        if(data.equalsIgnoreCase("close it")){
////            if(!topPackageName.equalsIgnoreCase("com.whitesuntech.aidohomerobot")){
////                Log.i("close it","called");
////                Integer PID1= android.os.Process.getUidForName(topPackageName);
////                Log.i("pid",String.valueOf(PID1));
////
////                android.os.Process.killProcess(PID1);
////               // activityManager.killBackgroundProcesses(topPackageName);
////               // Process.killProcess();
////            }
//        //}
//
//
//        return topPackageName;
//    }












//    private String printForegroundTask(Context context) {
//        String currentApp = "NULL";
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            UsageStatsManager usm = (UsageStatsManager)context.getSystemService("usagestats");
//            long time = System.currentTimeMillis();
//            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
//            if (appList != null && appList.size() > 0) {
//                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
//                for (UsageStats usageStats : appList) {
//                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
//                }
//                if (mySortedMap != null && !mySortedMap.isEmpty()) {
//                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//                }
//            }
//        } else {
//            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
//            currentApp = tasks.get(0).processName;
//        }
//
//        Log.e("adapter", "Current App in foreground is: " + currentApp);
//        return currentApp;
//    }

}
