package com.whitesuntech.texttospeech;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import TextToSpeech.TextToSpeechFuncs;
import properties.VoiceProperties;

public class TSService extends Service {

    TextToSpeechFuncs _ttsaido;
    TextToSpeechReceiver _ttsreceiver;

    public TSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _ttsaido = new TextToSpeechFuncs(this)
        {

            @Override
            protected void onReady() {
                super.onReady();
                _ttsreceiver = new TextToSpeechReceiver()
                {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        super.onReceive(context, intent);

                        if(intent.getAction().equalsIgnoreCase(VoiceProperties.PROP_TTS_INTENTID)) {

                            String message = intent.getStringExtra(VoiceProperties.TTS_MESSSAGEFIELD);

                            // Toast.makeText(getBaseContext(),"Got text : " + message, Toast.LENGTH_LONG);


                            Log.i("TTS","Gott text : " + message);
                            Log.i("gotmsg","called");
//
// if(message.equals("I think you would like to   search medicines")){
//                                Log.i("gotmsg","called");
//                                Intent intent1 = new Intent();
//                               // intent1.setAction("com.example.archi.health.start");
//                                //intent.setComponent(new ComponentName("com.whitesuntech.texttospeech", "com.example.archi.health.Activity"));
//                                //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                               // startActivity(intent1);
//                                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.example.archi.health.Activity");
//                                startActivity(LaunchIntent);
//                            }


                            _ttsaido.speak(message);


                        }

                    }
                };

                IntentFilter filter = new IntentFilter();
                filter.addAction(VoiceProperties.PROP_TTS_INTENTID);
                registerReceiver(_ttsreceiver, filter);

            }

            @Override
            protected void onStart() {
                super.onStart();

                AudioManager audioManager =
                        (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                        1);

                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
                        1);

            }


            @Override
            protected void inprogress() {
                super.inprogress();
                AudioManager audioManager =
                        (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                        1);

                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
                        1);

            }

            @Override
            protected void onComplete() {
                super.onComplete();
                /*AudioManager audioManager =
                        (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        0,
                        0);*/


                SetDelay sd = new SetDelay(500);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        Intent intent = new Intent();
                        intent.setAction(VoiceProperties.PROP_TTS_RECEIVER_INTENTID);
                        intent.putExtra(VoiceProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD, "start");
                        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        sendBroadcast(intent);

                    }
                });



            }
        };


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {





        return    super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        try {
            _ttsaido.shutdown();
        }
        catch (Exception ex)
        {

        }
        try {
            unregisterReceiver(_ttsreceiver);
        }
        catch (Exception ex)
        {

        }
        super.onDestroy();
    }



}
