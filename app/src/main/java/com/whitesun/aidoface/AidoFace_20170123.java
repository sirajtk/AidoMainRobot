package com.whitesun.aidoface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whitesuntech.aidohomerobot.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aido.Affectiva.affdexme.FaceDataModel;
import aido.Affectiva.affdexme.MainActivityAffectiva;
import aido.OutPan.OutPanSearch;
import aido.OutPan.OutpanObject;
import aido.Recipe.RecipeDialog;
import aido.Recipe.SearchForRecipe;
import aido.Scandit.ScanditScanning;
import aido.TextToSpeech.BroadcastTTS;
import aido.UI.BehaviorUI;
import aido.UI.Behaviors;
import aido.UI.ComboBoxClass;
import aido.UI.DemoPlay;
import aido.UI.DemoPlayOld;
import aido.UI.ManageEyes;
import aido.UI.PlayMovie;
import aido.camera.ClickPhoto;
import aido.clarifai.ClarifyFuncs;
import aido.common.CommonlyUsed;
import aido.emotion.EmotionHandling;
import aido.file.fileReadWrite;
import aido.googlevoice.BroadcastSTT;
import aido.googlevoice.GoogleVoiceSearch;
import aido.googlevoice.SpeechRecognize;
import aido.http.SocketConnect;
import aido.http.SocketConnectReceive;
import aido.http.asynchttp;
import aido.json.ConfigHandler;
import aido.kiosk.PrefUtils;
import aido.polling.HttpDownloadHandlerPolling;
import aido.polling.IncomingMessageHandler;
import aido.properties.AutomateProperties;
import aido.properties.ConfigProperties;
import aido.properties.HttpProperties;
import aido.properties.PermittedApps;
import aido.properties.StorageProperties;
import aido.setdelay.SetDelay;
import aido.shoppinglist.MainActivityForShoppingList;

public class AidoFace_20170123 extends AppCompatActivity  implements TextureView.SurfaceTextureListener {


    int numberofmoves = 1;
    ImageView iv_anim;

    TextureView _videoview;

    FloatingActionButton fab;

    static int INTENT_SCANDIT = 1002;
    static int INTENT_SHOPPINGLIST = 1003;


    TextView tv_httpmsg;

    //TextToSpeechFuncs _ttsaido;
    BroadcastTTS _ttsaido;
    BroadcastSTT _sttaido;


    boolean pausespeechanimation = false;


    SpeechRecognize _speechToText;


    FaceDataModel _previousFace;

    Float _prevfacepoint_x = 0f;
    Float _prevfacepoint_y = 0f;

    long _emotionchangedtime = SystemClock.elapsedRealtime();

    ManageEyes _manageEyes;

    TextView _tv_diagnosis;

    String _latestSpeechToTextMessage = "";

    HttpDownloadHandlerPolling _handlerpoll;

    LinearLayout _ll_roslayout;

    Intent _intent_ROS;
    Intent _intent_TTS;
    Intent _intent_STT;
    Intent _intent_ROS_tolinux;
    Intent _rosintent;

    int ROS_ACTIVITY_REQ_CODE = 1022;

    public static String PROP_HTTPDOWNLOAD_INTENTID = "com.whitesun.httpupload";
    public static String HTTPDOWNLOAD_MESSSAGEFIELD = "message";

    long timetostart = 0;

    boolean _pauseSpeechRecognition = false;

    SocketConnect socket;

    BehaviorUI behave;

    Intent     launchprojector;

    Behaviors randombehave;

    int movieloop = 1;


    PlayMovie _playmovie;

    SurfaceTexture _videosurface;

    Map<String,String> _newnotifications = new HashMap<String,String>();

    boolean demomode = false;

    MainActivityAffectiva affectivaActivity;

    DemoPlayOld _dp;

    String demofilename = "demo.txt";


    String _idlemovie = "blink.m4v";

    int prevx = -1;
    int prevy = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aido_face);


        tv_httpmsg = findViewById(R.id.pollingvalupdate);

        _tv_diagnosis = findViewById(R.id.tv_face_diagnosis);

        _ll_roslayout = findViewById(R.id.ros_fragment);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start Demo", Snackbar.LENGTH_LONG)
                        .setAction("PLAY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//new GoogleVoiceSearch(AidoFace.this);


                                // doScandit();


                                // openBox();


                                // doFaceDetection();
                                //demoMode();
                                //if(_dp != null)
                                {
                                    //_dp.interrupt();
                                }

                                //_dp = new DemoPlay(getApplicationContext(),socket,_videoview, _videosurface,demofilename);
                                //_dp.play();





                                // ClarifyFuncs CF = new ClarifyFuncs();

                                captureAndPredictScene();


                            }
                        }).show();
            }
        });

        fab.setImageResource(R.mipmap.mic_on);

        iv_anim = findViewById(R.id.imageView_animate);

        _videoview = findViewById(R.id.videoview);


        ConfigHandler.dumpDefaultConfig(false);

        ConfigHandler.copyDefaultUI(this, false);

        _newnotifications.clear();

        timetostart = System.currentTimeMillis() + 5000;


        //new ManageEyes(AidoFace.this,iv_anim).halfawake();



        startTTSActivity();

        //startSpeechToTextActivity();



        //startRosActivity();

        _ttsaido = new BroadcastTTS(AidoFace_20170123.this);
        _sttaido = new BroadcastSTT(AidoFace_20170123.this);

        //doFaceDetection();





        //doSpeechRecognition();


        IntentFilter filter = new IntentFilter();
        filter.addAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
        registerReceiver(receiver, filter);

        IntentFilter filter_speechtotext = new IntentFilter();
        filter_speechtotext.addAction(HttpProperties.PROP_STT_RECEIVER_INTENTID);
        registerReceiver(_speechreceiver, filter_speechtotext);


        IntentFilter filter_texttospeech = new IntentFilter();
        filter_texttospeech.addAction(HttpProperties.PROP_TTS_RECEIVER_INTENTID);
        registerReceiver(_ttsreceiver, filter_texttospeech);


        IntentFilter filter_automate = new IntentFilter();
        registerReceiver(_automatereceiver, filter_automate);




        LocalBroadcastManager.getInstance(this).registerReceiver(_notificationreceiver, new IntentFilter("Msg"));

        _playmovie = new PlayMovie(_videoview);

        _videoview.setSurfaceTextureListener(this);


        SetDelay sd = new SetDelay(1000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                _latestSpeechToTextMessage="";
            }
        });


        String rosip = ConfigHandler.getModuleName(ConfigProperties.ROS_IP);
       // socket = new SocketConnect("172.10.10.1",1001);
       // socket.sendMessage("kill");

       // doFaceDetection();


        iv_anim.setVisibility(View.GONE);
        _videoview.setVisibility(View.VISIBLE);


        ////////// INIT BEHAVIOR

        SetDelay sd3 = new SetDelay(1000);
        sd3.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                _playmovie.setSurface(_videosurface);

                isAidoIdle();

                socket = new SocketConnect("192.168.0.128",1001);

                SocketConnectReceive _bcgmsg = new SocketConnectReceive("192.168.0.128",1004)
                {
                    @Override
                    protected void onReceive(final String Message) {
                        super.onReceive(Message);

                        Log.i("BCG","BCG : " + Message);



                        if(CommonlyUsed.stringIsNullOrEmpty(Message))
                        {
                            return;
                        }


                        if(Message.contains("stop"))
                        {
                            if(_dp != null)
                            {
                                _dp.interrupt();
                            }

                            return;

                        }




                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        if(_dp != null)
                                        {
                                            _dp.interrupt();
                                        }

                                        _dp = new DemoPlayOld(getApplicationContext(),socket,_videoview, _videosurface,Message, _ttsaido);
                                        _dp.play();

                                    }


                                });




                    }
                };
                _bcgmsg.sendMessage("none");


            }
        });





    }


    void captureAndPredictScene()
    {
        Intent camerashow = new Intent(AidoFace_20170123.this, ClickPhoto.class);

        startActivityForResult(camerashow,1010);

    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            //CommonlyUsed.showMsg(getApplicationContext(),"Got Msg on ROS !!! " );



            //if(intent.getAction().equalsIgnoreCase(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID)) {

            String message = intent.getStringExtra(HttpProperties.HTTPDOWNLOAD_MESSSAGEFIELD);

            Log.i("AUTOMATE","got ros message inside aido !!! " +  message);


            // CommonlyUsed.showMsg(getApplicationContext(),"Got Msg : " + message);

            final IncomingMessageHandler IMH = new IncomingMessageHandler(message);

            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.RUNBEHAVIOR))
            {
                pausespeechanimation = true;
                String value = IMH.getTextValue();

                if(_dp != null)
                {
                    _dp.interrupt();
                }

                _dp = new DemoPlayOld(getApplicationContext(),socket,_videoview, _videosurface,value,_ttsaido);
                _dp.play();


            }


            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.SCENERECOG))
            {
                pausespeechanimation = true;
                String value = IMH.getTextValue();

                captureAndPredictScene();
            }



            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.SHOPPING))
            {
                _ttsaido.speak("Looks like you want to add an item? Come on,  Show me the item!");

                doScandit();


                SetDelay sd = new SetDelay(20000);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                      //  sendCompletionBroadcast("Thank God Its done !");
                    }
                });

            }

            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.BODYPROJECTOR))
            {
                //Intent LaunchIntent2 = getPackageManager().getLaunchIntentForPackage("com.androidhive.musicplayer");

                Intent LaunchIntent2 = getPackageManager().getLaunchIntentForPackage(ConfigHandler.getModuleName(ConfigProperties.MEDIA));
                startActivity( LaunchIntent2 );
            }

            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.HEADPROJECTOR))
            {
                Intent LaunchIntent2 = getPackageManager().getLaunchIntentForPackage("com.whitesuntech.playmovie");
                startActivityForResult( LaunchIntent2, 1112);

            }



            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.SPEECH_TO_TEXT))
            {
                if(!_latestSpeechToTextMessage.equalsIgnoreCase("") && _pauseSpeechRecognition == false) {
                    CommonlyUsed.showMsg(getApplicationContext(),"Broadcasting : " + _latestSpeechToTextMessage);
                    broadcastMessage(_latestSpeechToTextMessage);
                }
                //  IMH.postValueToServer(AidoFace.this,_latestSpeechToTextMessage);
            }

            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.TEXT_TO_SPEECH))
            {
                _pauseSpeechRecognition = false;//BIG
                //_latestSpeechToTextMessage = "";
                //_sttaido.sendCommand("pause");
                String messageforspeech = IMH.getTextValue();
                _ttsaido.speak(messageforspeech);
            }
            if(IMH.getTask().equalsIgnoreCase(HttpProperties.HTTP_EMOTION_DETECT_TAG))
            {
                IMH.postValueToServer(AidoFace_20170123.this,_previousFace.getDominantEmotion_irrespectiveofscore() + "," + _previousFace.getDominantScore() );
            }
        }





        //}
    };


    void showcam(int x, int y, int r, int time)
    {
        Intent camerashow = new Intent(AidoFace_20170123.this, ClickPhoto.class);
        camerashow.putExtra("x", x);
        camerashow.putExtra("y", y);
        camerashow.putExtra("r", r);
        camerashow.putExtra("t", time);


        //camerashow.putExtra(MainActivityForShoppingList.BUNDLE_FROM_AIDO_UNIT, "Each");
        startActivity(camerashow);

    }




    void doTimeSync()
    {
        final Resources res = this.getResources();
        final int id = Resources.getSystem().getIdentifier(
                "config_ntpServer", "string","android");
        final String defaultServer = res.getString(id);
        Log.i("time",defaultServer);

    }

    void broadcastMessage(String message)
    {
        Intent intent = new Intent();
        intent.setAction(PROP_HTTPDOWNLOAD_INTENTID);
        intent.putExtra(HTTPDOWNLOAD_MESSSAGEFIELD, message);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

    }



    private BroadcastReceiver _notificationreceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");


            CommonlyUsed.showMsg(getApplicationContext(),"New notification : " + title + "\n" + text);
            _newnotifications.put(title,text);

        }
    };



    void speakAllNotifications()
    {
        Iterator it = _newnotifications.entrySet().iterator();
        String stringtospeak = "";
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());

            count++;
            if(!pair.getKey().toString().toLowerCase().contains("ros"))
            {
                stringtospeak += count + " . " + pair.getKey().toString() + "." +  pair.getValue().toString();
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        if(count > 0)
        {
            stringtospeak = "You have " + count + " new notifications ! " + stringtospeak;
            if(!CommonlyUsed.stringIsNullOrEmpty(stringtospeak.trim()))
            {
                behave = new BehaviorUI(AidoFace_20170123.this, "reademail.json.txt", "reademail", timetostart, iv_anim, socket);
            }

            if(count == 1)
            {
                SetDelay sd = new SetDelay(4000);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        behave.interrupt();
                    }
                });
            }
            else
            {
                SetDelay sd = new SetDelay(10000);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        behave.interrupt();
                    }
                });
            }
        }
        else
        {
            if(demomode)
            {
                demomode = false;
                    behave = new BehaviorUI(AidoFace_20170123.this, "reademail.json.txt", "reademail", timetostart, iv_anim, socket);
                    SetDelay sd = new SetDelay(4000);
                    sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        behave.interrupt();
                        }
                });

            }
            stringtospeak = "there are No new notifications";
        }

        _newnotifications.clear();
        _ttsaido.speak(stringtospeak);

    }



    void openBox()
    {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(ConfigHandler.getModuleName(ConfigProperties.BOX));

//        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.whitesuntech.aidobox");
        startActivity( LaunchIntent );


    }




    @Override
    protected void onPause() {
        super.onPause();

        //_ttsaido.pauseSpeech();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        performExitCheck();
    }

    public void closeActivity()
    {
        try{
            socket.sendMessage("kill");
        }
        catch(Exception ex)
        {

        }


        try{
            //_rosintent
            finishActivity(ROS_ACTIVITY_REQ_CODE);
        }
        catch(Exception ex)
        {

        }


        try{
            LocalBroadcastManager.getInstance(this).unregisterReceiver(_notificationreceiver);
        }catch (Exception ex){}


        try{
            _speechToText.close();
        }
        catch(Exception ex)
        {

        }

        try{

            _handlerpoll.stopRepeatingTask();
        }
        catch(Exception ex)
        {

        }

        try{
            CommonlyUsed.unmuteAudioOutput(AidoFace_20170123.this);
        }
        catch(Exception ex)
        {

        }

        try{
            // registerReceiver(receiver, filter);

            unregisterReceiver(receiver);

        }
        catch(Exception ex)
        {

        }

        try{
            // registerReceiver(receiver, filter);

            unregisterReceiver(_automatereceiver);

        }
        catch(Exception ex)
        {

        }




        try{
            // registerReceiver(receiver, filter);

            unregisterReceiver(_speechreceiver);

        }
        catch(Exception ex)
        {

        }

        try{
            // registerReceiver(receiver, filter);

            unregisterReceiver(_ttsreceiver);

        }
        catch(Exception ex)
        {

        }
        try{
            stopService(_intent_ROS);
        }
        catch (Exception ex)
        {

        }

        try{
            stopService(_intent_ROS_tolinux);
        }
        catch (Exception ex)
        {

        }

        try{
            stopService(_intent_TTS);
        }
        catch (Exception ex)
        {

        }
        try{
            stopService(_intent_STT);
        }
        catch (Exception ex)
        {

        }




        finish();
        //this.finishAffinity();


    }


    private AutomateBroadcastReceiver _automatereceiver = new AutomateBroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            //super.onReceive(context, intent);

            String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);

           // CommonlyUsed.showMsg(AidoFace.this,"got message : " + message);

            Log.i("AUTOMATE","got ros message inside aido !!! " + message);


        }
    };


    private SpeechBroadcastReceiver _speechreceiver = new SpeechBroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if(intent.getAction().equalsIgnoreCase(HttpProperties.PROP_STT_RECEIVER_INTENTID)) {

               final String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);




                if(_pauseSpeechRecognition) {

                    //_latestSpeechToTextMessage = "";

                    return;}

                broadcastMessage(_latestSpeechToTextMessage);

                if(!message.toLowerCase().contains("got error")) {
                    CommonlyUsed.showMsg(getApplicationContext(), "I Heard : " + message);
                    if(processSpeechToTextResults(message))
                    {
                        _pauseSpeechRecognition = false;//BIG
                    }
                }


               // new ManageEyes(AidoFace.this,iv_anim).justLookRight();


                SetDelay sd = new SetDelay(500);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                       // _latestSpeechToTextMessage = "";


                        if(!_pauseSpeechRecognition) {
                          //  new ManageEyes(AidoFace.this,iv_anim).justLookLeft();


                            sendCompletionBroadcast(AutomateProperties.BROADCAST_SPEECH_TO_TEXT,message);

                            startSpeechRecognition();
                            if(!pausespeechanimation) {
                                /*
                            ManageEyes manageEyes = new ManageEyes(AidoFace.this,iv_anim)
                            {
                                @Override
                                public void onActionComplete() {
                                    super.onActionComplete();

                                    //   IMH.setTaskAsComplete(AidoFace.this);
                                }
                            };
                            manageEyes.playAnimation(
                                    "frame_aidolistening_",
                                    0,
                                    19,
                                    130);*/
                            }



                        }

                    }
                });

            }

        }
    };


    private TTSBroadcastReceiver _ttsreceiver = new TTSBroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if(intent.getAction().equalsIgnoreCase(HttpProperties.PROP_TTS_RECEIVER_INTENTID)) {

                String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);


                sendCompletionBroadcast(AutomateProperties.BROADCAST_TEXT_TO_SPEECH,"");

                CommonlyUsed.showMsg(getApplicationContext(),"Speech over");

                _pauseSpeechRecognition = false;
                SetDelay sd = new SetDelay(500);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        //_latestSpeechToTextMessage = "";
                        if(!_pauseSpeechRecognition) {
                            startSpeechRecognition();
                        }
                    }
                });

               // behave.interrupt();


            }

        }
    };

    private void startSpeechRecognition()
    {
        _sttaido.sendCommand("start");
    }

    void sendCompletionBroadcast(String actiontag, String msg)
    {
        Intent intent = new Intent();
        intent.setAction(actiontag);
        intent.putExtra("msg", msg);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //CommonlyUsed.showMsg(AidoFace.this,"Broadcast is sent");
        Log.i("AUTOMATE","Broadcast " + actiontag + " with value " + msg + " is sent to automate");
        sendBroadcast(intent);
    }





    void startRosActivity()
    {


        _rosintent = getPackageManager().getLaunchIntentForPackage(ConfigHandler.getModuleName(ConfigProperties.ROS_APP));

        //_rosintent = getPackageManager().getLaunchIntentForPackage("org.ollide.rosandroid");
        startActivityForResult(_rosintent,ROS_ACTIVITY_REQ_CODE);
        //startActivity( _rosintent );


        /*SetDelay sd = new SetDelay(6000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                Intent LaunchIntent2 = getPackageManager().getLaunchIntentForPackage("com.androidhive.musicplayer");
                startActivity( LaunchIntent2 );

            }
        });*/

        //_intent_ROS= new Intent();
        //_intent_ROS.setComponent(new ComponentName("org.ollide.rosandroid", "org.ollide.rosandroid.RosBackgroundService"));
        //ComponentName c = startService(_intent_ROS);

      //  _intent_ROS_tolinux = new Intent();
      //  _intent_ROS_tolinux.setComponent(new ComponentName("org.ollide.rosandroid", "org.ollide.rosandroid.RosBackgroundService"));
      //  ComponentName c2 = startService(_intent_ROS_tolinux);


    }


    void startTTSActivity()
    {

        _intent_TTS= new Intent();
//        _intent_TTS.setComponent(new ComponentName("com.whitesuntech.texttospeech", "com.whitesuntech.texttospeech.TSService"));

        _intent_TTS.setComponent(new ComponentName(ConfigHandler.getPackageName(ConfigProperties.TEXT_TO_SPEECH),
                ConfigHandler.getModuleName(ConfigProperties.TEXT_TO_SPEECH)));
        ComponentName c = startService(_intent_TTS);

    }


    void startSpeechToTextActivity()
    {

        _intent_STT= new Intent();
//        _intent_STT.setComponent(new ComponentName("com.whitesuntech.speechtotext", "com.whitesuntech.speechtotext.STService"));

        _intent_STT.setComponent(new ComponentName(ConfigHandler.getPackageName(ConfigProperties.SPEECH_TO_TEXT),
                ConfigHandler.getModuleName(ConfigProperties.SPEECH_TO_TEXT)));
        ComponentName c = startService(_intent_STT);

    }





    void pauseAffectiva()
    {
        affectivaActivity.onPause();
    }


    void restartAffectiva()
    {
        affectivaActivity.onResume();
    }

    void doFaceDetection()
    {

        CommonlyUsed.showMsg(getApplicationContext(),"Enabling Emotion");

        FragmentManager fm = getSupportFragmentManager();

        affectivaActivity = new MainActivityAffectiva();

//        Bundle bundle = new Bundle();
//        SLF.setArguments(bundle);



        affectivaActivity.setOnDataAvailable(new MainActivityAffectiva.OnDataAvailable() {
            @Override
            public void onDataAvailable(FaceDataModel facedata) {

               // CommonlyUsed.showMsg(getApplicationContext(),"AFF : " + facedata.getFacePoint().x + " : " + facedata.getFacePoint().y);

/*

                _tv_diagnosis.setText("x : " + facedata.getFacePoint().x + ", y : " + facedata.getFacePoint().y);

                if(_prevfacepoint_x < 180 && facedata.getFacePoint().x >=180 )
                {
                    new ManageEyes(AidoFace.this,iv_anim).justLookLeft();
                }

                if(_prevfacepoint_x > 60 & facedata.getFacePoint().x <= 60)
                {
                    new ManageEyes(AidoFace.this,iv_anim).justLookRight();
                }

                if((_prevfacepoint_x < 180 || _prevfacepoint_x < 60) && (facedata.getFacePoint().x < 180 && facedata.getFacePoint().x > 60))
                {
                    new ManageEyes(AidoFace.this,iv_anim).justLookStraight();
                }

                _prevfacepoint_x = facedata.getFacePoint().x;


                 _prevfacepoint_x =  facedata.getFacePoint().x;
*/


                if(CommonlyUsed.timeElapsed(_emotionchangedtime) < 3000  ) {
                    _previousFace = facedata;

                    _emotionchangedtime = SystemClock.elapsedRealtime();
                    return;
                }


               /* if(_ttsaido.getTTSspeakingOrNot())
                {
                    return;
                }*/

                if(_previousFace == null)
                {
                    _previousFace = facedata;
                    if(facedata.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_JOY))
                    {
                        _ttsaido.speak("WOW! Thats a beautiful smile there !");
                    }
                    else
                    {
                        if(!CommonlyUsed.stringIsNullOrEmpty(facedata.getDominantEmotion()))
                        {
                            _ttsaido.speak("Hey you are full of " + facedata.getDominantEmotion());
                        }
                    }
                }
                else
                {
                    boolean noneofthese = true;
                    if(facedata.getDominantEmotion() != _previousFace.getDominantEmotion())
                    {
                        if(!_previousFace.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_JOY))
                        {
                            if(facedata.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_JOY))
                            {
                                _ttsaido.speak("WOW! Thats a beautiful smile there !");
                                noneofthese = false;
                            }


                        }

                        if(_previousFace.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_JOY))
                        {
                            if(!facedata.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_JOY))
                            {
                               // _ttsaido.speak("Hey Why Did U stop smiling?");
                                //noneofthese = false;
                            }

                        }

                        if(facedata.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_ANGER))
                        {
                            _ttsaido.speak("Hey are you angry with me ? What did I do ?");
                            noneofthese = false;

                        }
                        if(facedata.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_SURPRISE))
                        {
                            _ttsaido.speak("You look surprised ? Everything ok ?");
                            noneofthese = false;
                        }

                        if(facedata.getDominantEmotion().equalsIgnoreCase(EmotionHandling.EMOTION_SADNESS))
                        {
                            _ttsaido.speak("Hey dont be sad. Everything will be ok soon. I promise.");
                            noneofthese = false;
                        }

                        if(noneofthese && !CommonlyUsed.stringIsNullOrEmpty(facedata.getDominantEmotion()))
                        {
                            _ttsaido.speak("Hey you are full of " + facedata.getDominantEmotion());
                        }



                    }
                }



                _previousFace = facedata;



            }
        });

        Fragment affectivafragment = fm.findFragmentById(R.id.affectiva_fragment);
        if (affectivafragment == null) {
            fm.beginTransaction()
                    .add(R.id.affectiva_fragment, affectivaActivity)
                    .commit();
        }


    }



    void doAffectiva()
    {
        Intent affective_intent = new Intent(this, MainActivityAffectiva.class);
        affective_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(affective_intent);

    }

    void doScandit()
    {
        Intent scanditIntent = new Intent(this, ScanditScanning.class);
       // scanditIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // scanditIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        this.startActivityForResult(scanditIntent, INTENT_SCANDIT);

        //((Activity) AidoFace.this).overridePendingTransition(0, 0);



    }


    int count = 0;




    void playMovieHead()
    {
        _ttsaido.speak("Oh! This is your favorite movie.");
        //Intent LaunchIntent
        behave = new BehaviorUI(AidoFace_20170123.this, "headproject.json.txt", "reademail", timetostart, iv_anim, socket);


        //socket.sendMessage("headon");

        SetDelay sd = new SetDelay(5000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {

                launchprojector = getPackageManager().getLaunchIntentForPackage("com.whitesuntech.playmovie");
                startActivityForResult(launchprojector, 1112);
            }
        });


    }

    boolean processSpeechToTextResults(String speechtotext)
    {
        boolean thereisspeech = false;

        if(speechtotext.length() < 10)
        {
          // return  thereisspeech;
        }

        _latestSpeechToTextMessage = speechtotext;


        /*if(speechtotext.toLowerCase().contains("demo"))
        {
            thereisspeech = true;
            CommonlyUsed.showMsg(getApplicationContext(),"Entering demo mode");
            demoMode();
            return thereisspeech;
        }*/

        if(speechtotext.toLowerCase().contains("shopping"))
        {
            thereisspeech = true;
            _ttsaido.speak("Looks like you want to add to your list? Come on,  Show me the item!");

            doScandit();
            return thereisspeech;
        }

        if(speechtotext.toLowerCase().contains("play") && speechtotext.toLowerCase().contains("movie"))
        {
            thereisspeech = true;
            //_ttsaido.speak("Oh thats nice. Would you like head or body projector?");
            //Intent LaunchIntent2 = getPackageManager().getLaunchIntentForPackage("com.whitesuntech.playmovie");
            //startActivity( LaunchIntent2 );

            playMovieHead();
            return thereisspeech;
        }


        if(speechtotext.toLowerCase().contains("head") && speechtotext.toLowerCase().contains("projector") )
        {
            thereisspeech = true;
            _ttsaido.speak("Oh! let me play your favorite movie.");
            //Intent LaunchIntent
            behave = new BehaviorUI(AidoFace_20170123.this, "headproject.json.txt", "reademail", timetostart, iv_anim, socket);


            //socket.sendMessage("headon");

            SetDelay sd = new SetDelay(5000);
            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {

                    launchprojector = getPackageManager().getLaunchIntentForPackage("com.whitesuntech.playmovie");
                    startActivityForResult(launchprojector, 1112);
                }
            });

            return thereisspeech;
        }

        if(speechtotext.toLowerCase().contains("body") && speechtotext.toLowerCase().contains("projector") )
        {
            thereisspeech = true;
            _ttsaido.speak("Oh! let me play me your favorite movie. I hope you like this one. Its hi def");
            behave = new BehaviorUI(AidoFace_20170123.this, "bodyproject.json.txt", "reademail", timetostart, iv_anim, socket);

            SetDelay sd = new SetDelay(4000);
            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {
                    launchprojector = getPackageManager().getLaunchIntentForPackage(ConfigHandler.getModuleName(ConfigProperties.MEDIA));
                    startActivityForResult( launchprojector ,1111);

                }
            });
            return thereisspeech;
        }

        if(speechtotext.toLowerCase().contains("close") && speechtotext.toLowerCase().contains("projector") )
        {
            thereisspeech = true;
            _ttsaido.speak("Hope you enjoyed movie");

            finishActivity(1111);
            return thereisspeech;
        }
        if(speechtotext.toLowerCase().contains("open"))
        {
            String openapp = speechtotext.replace("open","").replace(" ","").toLowerCase();
            //_ttsaido.speak("Looks like you want to add to shopping list? Come on,  Show me the item!");

            if(openapp.contains("box"))
            {
                //Intent affective_intent = new Intent(this, AidoMain.class);
                //affective_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //this.startActivity(affective_intent);

                openBox();

                thereisspeech = true;
                _ttsaido.speak("Looks like you want to see app box. Here you go!");

                return thereisspeech;

            }

            if(openapp.contains("notification"))
            {
                //Intent affective_intent = new Intent(this, AidoMain.class);
                //affective_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //this.startActivity(affective_intent);

                thereisspeech = true;
                speakAllNotifications();

                return thereisspeech;

            }

            for (Map.Entry<String, String> entry : PermittedApps.PROP_APPNAMES.entrySet())
            {

                if(entry.getValue().equalsIgnoreCase("na")){continue;}
                //System.out.println(entry.getKey() + "/" + entry.getValue());
                if(openapp.contains(entry.getValue().replace(" ","").toLowerCase()))
                {
                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(entry.getKey());
                    startActivity( LaunchIntent );
                    thereisspeech = true;
                    _ttsaido.speak("Looks like you want to check " + entry.getValue() + ". Here you go!");

                    return thereisspeech;
                }
            }

            //doScandit();
            return thereisspeech;

        }

        return thereisspeech;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GoogleVoiceSearch.GOOGLE_VOICE_INTENT) {
            String retstring = GoogleVoiceSearch.processIntentResult(requestCode, resultCode, data);

            CommonlyUsed.showMsg(getApplicationContext(),"GOT:" + retstring);
        }


        if(requestCode == 1010) { //// SCENE RCOG PHOTO INTENT

            CommonlyUsed.showMsg(getApplicationContext(),"Captured Photo!");

            ClarifyFuncs CF = new ClarifyFuncs(StorageProperties.getSceneDir() + "1.jpg")
            {
                @Override
                protected void onComplete(String message) {
                    super.onComplete(message);

                    _ttsaido.speak(message);

                    sendCompletionBroadcast(AutomateProperties.BROADCAST_SCENERECOG,"");

                }
            };

        }


        if(requestCode == 1111)/// movie projector
        {
            //broadcastMessage("1234|VIDEO|OFF");
            //socket.sendMessage("bodyoff");

            sendCompletionBroadcast(AutomateProperties.BROADCAST_BODYPROJECTOR,"");

        }

        if(requestCode == 1112)/// movie projector
        {
            if(resultCode ==Activity.RESULT_OK) {
                //broadcastMessage("1234|VIDEO|OFF");
                //socket.sendMessage("headoff");
            }

            sendCompletionBroadcast(AutomateProperties.BROADCAST_HEADPROJECTOR,"");

            //doScandit();


        }

        if(requestCode == INTENT_SCANDIT)
        {
            if(resultCode == Activity.RESULT_OK) {
                String scannedvalue = data.getStringExtra(ScanditScanning.SCANDIT_SCANVALUE);
                CommonlyUsed.showMsg(getApplicationContext(), "Scanned value = '" + scannedvalue + "'");


                final OutPanSearch OPS = new OutPanSearch(AidoFace_20170123.this);

                OPS.setOnTaskCompletedListener(new OutPanSearch.LoadingTaskFinishedListener() {
                    @Override
                    public void onTaskFinished(OutpanObject outpanobject) {
                        final OutpanObject outpanobject2 = OPS.getObject();


                        if(!CommonlyUsed.stringIsNullOrEmpty(outpanobject2.name.trim())) {
                            CommonlyUsed.showMsg(getApplicationContext(), "Product name = " + outpanobject2.name + ",url = " + outpanobject2.outpan_url);


                            _ttsaido.speak("Hey you have shown me,  " + outpanobject2.name + ". Adding it to List");


                            Intent intent_shoppinglist = new Intent(AidoFace_20170123.this, MainActivityForShoppingList.class);
                            intent_shoppinglist.putExtra(MainActivityForShoppingList.BUNDLE_FROM_AIDO_ITEMNAME, outpanobject2.name);
                            intent_shoppinglist.putExtra(MainActivityForShoppingList.BUNDLE_FROM_AIDO_QUANTITY, "1");
                            intent_shoppinglist.putExtra(MainActivityForShoppingList.BUNDLE_FROM_AIDO_UNIT, "Each");
                            startActivityForResult(intent_shoppinglist, INTENT_SHOPPINGLIST);

                            SetDelay sd = new SetDelay(5000);
                            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                                @Override
                                public void onDelayCompleted() {
                                    finishActivity(INTENT_SHOPPINGLIST);


                                }
                            });


                            _pauseSpeechRecognition = false;//true BIG;
                            SetDelay sd2 = new SetDelay(3000);
                            sd2.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                                @Override
                                public void onDelayCompleted() {
                                    _pauseSpeechRecognition = false;
                                }
                            });
                            SearchForRecipe SFR = new SearchForRecipe(AidoFace_20170123.this, outpanobject2.name.replace(" ",","))
                            {

                                @Override
                                protected void onReceivedRecipe(final String recipename, final String recipedetails, final String imageurl) {
                                    super.onReceivedRecipe(recipename, recipedetails, imageurl);


                                    if(!CommonlyUsed.stringIsNullOrEmpty(recipename.trim())) {
                                        _ttsaido.speak("You know what ? I have found an interesting recipe using called " + recipename + ". You need to use the following :  " + recipedetails
                                        );

                                        behave = new BehaviorUI(AidoFace_20170123.this, "shopping.json.txt", "reademail", timetostart, iv_anim, socket);

                                        //CommonlyUsed.showMsg(AidoFace.this, "Recipe : " + recipename + "\n details : " + recipedetails);

                                        asynchttp downloadrecipeimage = new asynchttp(AidoFace_20170123.this);
                                        downloadrecipeimage.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
                                            @Override
                                            public void onDownloadIsCompleted(String downloadstring) {
                                                FragmentManager fm = getSupportFragmentManager();

                                                final RecipeDialog recipedialog = RecipeDialog.newInstance(recipename,recipedetails,StorageProperties.getRecipeImage());
                                                recipedialog.show(fm, "Dialog Fragment");
                                                SetDelay sd = new SetDelay(8000);
                                                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                                                    @Override
                                                    public void onDelayCompleted() {
                                                        recipedialog.dismiss();

                                                        sendCompletionBroadcast(AutomateProperties.BROADCAST_SHOPPING,"");



                                                    }
                                                });

                                            }
                                        });

                                        if(fileReadWrite.fileExists(StorageProperties.getRecipeImage()))
                                        {
                                            fileReadWrite.removeFile(StorageProperties.getRecipeImage());
                                        }

                                        downloadrecipeimage.downloadIntoFile(imageurl, StorageProperties.getRecipeImage());

                                    }
                                }
                            };



                        }
                        else
                        {
                            _ttsaido.speak("Hey I am Sorry, I don't know this product. Try something else");


                            sendCompletionBroadcast(AutomateProperties.BROADCAST_SHOPPING,"");


                        }



                    }
                });

                OPS.execute(scannedvalue);

            }
            else
            {
                CommonlyUsed.showMsg(getApplicationContext(), "Unable to read barcode");
                sendCompletionBroadcast(AutomateProperties.BROADCAST_SHOPPING,"");

            }

        }

    }



    void performExitCheck()
    {
        LayoutInflater li = LayoutInflater.from(AidoFace_20170123.this);
        View promptsView = li.inflate(R.layout.alert_exit, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AidoFace_20170123.this);

        // set alert_exit.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final TextView textview_error = promptsView
                .findViewById(R.id.dialog_error);
        textview_error.setText("");


        final ComboBoxClass combo = promptsView.findViewById(R.id.combo);

        combo.clearAllItems(true);


        List<String> items = new ArrayList<String>();


        items.add("");
        items.add("facerecog.txt");
        items.add("facetrack.txt");
        items.add("lirc.txt");
        items.add("soundlocal.txt");
        items.add("object.txt");
        items.add("scene6.txt");
        items.add("scene7a.txt");
        items.add("scene7b.txt");
        items.add("scene7c.txt");
        items.add("scene8.txt");
        items.add("scene9.txt");
        items.add("f1.txt");
        items.add("f2.txt");
        items.add("f3.txt");
        items.add("f4.txt");

        combo.additems(items);





        final EditText userInput = promptsView
                .findViewById(R.id.edittext_password);

        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                textview_error.setText("");
                return false;
            }
        });

        final Button button_submit = promptsView.findViewById(R.id.dialog_ok);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userInput.getText().toString().equalsIgnoreCase("123"))
                {


                    PrefUtils.setKioskModeActive(false, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "You can leave the app now!", Toast.LENGTH_SHORT).show();
                   // finish();
                    closeActivity();
                }
                else
                {
                    textview_error.setText(R.string.exit_dialog_password_error);
                }


            }
        });

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
        ;

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final Button button_cancel = promptsView.findViewById(R.id.dialog_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AidoFace_20170123.this, "Canceling..", Toast.LENGTH_SHORT);
                textview_error.setText(R.string.exit_dialog_cancel);

                alertDialog.dismiss();
            }
        });
        // show it
        alertDialog.show();
        // super.onBackPressed();




        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                if(!CommonlyUsed.stringIsNullOrEmpty(combo.getSelectedText())) {

                    SetDelay sd = new SetDelay(5000);
                    sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                        @Override
                        public void onDelayCompleted() {
                            demofilename = combo.getSelectedText();
                            _dp = new DemoPlayOld(getApplicationContext(), socket, _videoview, _videosurface, demofilename,_ttsaido);
                            _dp.play();
                            alertDialog.dismiss();
                        }
                    });
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        userInput.performClick();
    }



    protected void isAidoIdle(){

        final Handler h =new Handler();

        Runnable r = new Runnable() {

            public void run() {

               // if (behave.isComplete() && randombehave.isComplete()) {

                    //new ManageEyes(AidoFace.this,iv_anim).blink();
                    //behave = new BehaviorUI(AidoFace.this,"random.json.txt","reademail",timetostart,iv_anim, socket);

                //}




                try
                {
                    if(_dp != null)
                    {
                        if(!_dp.isPlaying())
                        {
                           // _playmovie.play(StorageProperties.getDemoMovieFile(_idlemovie));
                            if(_dp != null)
                            {
                               // _dp.interrupt();
                            }


                            if(!_dp.getDemoFile().equalsIgnoreCase("idle.txt"))
                            {
                                sendCompletionBroadcast(AutomateProperties.BROADCAST_RUNBEHAVIOR,_dp.getDemoFile());
                            }

                            _dp = new DemoPlayOld(getApplicationContext(),socket,_videoview, _videosurface,"idle.txt",_ttsaido);
                            _dp.play();

                        }
                    }
                    else
                    {
                        _dp = new DemoPlayOld(getApplicationContext(),socket,_videoview, _videosurface,"idle.txt",_ttsaido);
                        _dp.play();

                       // _playmovie.play(StorageProperties.getDemoMovieFile(_idlemovie));
                    }

                }catch (Exception e)
                {

                }


                //if(!_playmovie.isplaying())
                {
                  //  _playmovie.play(StorageProperties.getDemoMovieFile(_idlemovie));
                }


                h.postDelayed(this, 7000);
            }
        };

        h.postDelayed(r, 7000);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

        _videosurface = surfaceTexture;
        if(_dp != null) {
            _dp.setSurface(surfaceTexture);
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}

