package com.whitesuntech.speechtotext;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import googlevoice.SpeechRecognize;

public class STService extends Service {

    SpeechRecognize _sttaido;
    SpeechToTextReceiver _sttreceiver;

    String _broadcastIntentId = "com.whitesuntech.speechtotext";
    String _receiverid = "com.whitesuntech.stt";
    String _messageField = "message";
    public int counter=0;
    public STService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();


            _sttreceiver = new SpeechToTextReceiver()

            {
                @Override
                public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);

                if(intent.getAction().equalsIgnoreCase(_receiverid)) {

                    String message = intent.getStringExtra(_messageField);

                    // Toast.makeText(getBaseContext(),"Got text : " + message, Toast.LENGTH_LONG);

                    Log.i("STT", "Got Command : " + message);


                    if(message.equalsIgnoreCase("start")) {
                        doSpeechRecognition();
                    }




                }

            }
            };

            IntentFilter filter = new IntentFilter();
            filter.addAction(_receiverid);
            registerReceiver(_sttreceiver, filter);



    }

    void doSpeechRecognition()
    {
       /* AudioManager audioManager =
                (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                0,0);*/


        Log.i("STT","recreating speech to text");

        _sttaido = new SpeechRecognize(this)
        {
            @Override
            public void onComplete(final String result) {
                super.onComplete(result);
                Log.i("STT", "Got text : " + result);
                Log.i("GotTextt",result);



                    //CommonlyUsed.showMsg(getApplicationContext(), "GOT= " + result);
                    Intent intent = new Intent();
                    intent.setAction(_broadcastIntentId);
                    intent.putExtra(_messageField, result);
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    sendBroadcast(intent);

            }



        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*AudioManager audioManager =
                (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                3,
                AudioManager.FLAG_SHOW_UI);*/


        startService(new Intent(this,MyService.class));


        return    super.onStartCommand(intent, flags, startId);//


    }
//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        super.onTaskRemoved(rootIntent);
//        Log.i("destroy","called");
//        Toast.makeText(getApplicationContext(),"Service Stops",Toast.LENGTH_SHORT).show();
//        // Your job when the service stops.
//    }

    @Override
    public void onDestroy() {
//Log.i("destroy","called");
        //new code
//        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
//        sendBroadcast(broadcastIntent);
        //stoptimertask();
        //old one
        try {
            _sttaido.close();
        }
        catch (Exception ex)
        {

        }
        try {
            unregisterReceiver(_sttreceiver);
        }
        catch (Exception ex)
        {

        }
        super.onDestroy();
    }
    //new code
//    private Timer timer;
//    private TimerTask timerTask;
//    long oldTime=0;
//    public void startTimer() {
//        //set a new Timer
//        timer = new Timer();
//
//        //initialize the TimerTask's job
//        initializeTimerTask();
//
//        //schedule the timer, to wake up every 1 second
//        timer.schedule(timerTask, 1000, 1000); //
//    }
//
//    /**
//     * it sets the timer to print the counter every x seconds
//     */
//    public void initializeTimerTask() {
//        timerTask = new TimerTask() {
//            public void run() {
//                Log.i("in timer", "in timer ++++  "+ (counter++));
//            }
//        };
//    }
//
//    /**
//     * not needed
//     */
//    public void stoptimertask() {
//        //stop the timer, if it's not already null
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
   // }
}
