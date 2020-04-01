package com.whitesun.aidoface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whitesun.aidoface.settings.SettingsActivity;
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
import aido.UI.AlertBehaveEngine;
import aido.UI.BehaviorUI;
import aido.UI.Behaviors;
import aido.UI.ComboBoxClass;
import aido.UI.DemoPlay;
import aido.UI.ManageEyes;
import aido.UI.PlayMovie;
import aido.camera.ClickPhoto;
import aido.camera.ShowCamera;
import aido.clarifai.ClarifyFuncs;
import aido.common.CommonlyUsed;
import aido.file.fileReadWrite;
import aido.googlevoice.BroadcastSTT;
import aido.googlevoice.GoogleVoiceSearch;
import aido.googlevoice.SpeechRecognize;
import aido.http.MotorMessage;
import aido.http.SocketConnect;
import aido.http.asynchttp;
import aido.json.ConfigHandler;
import aido.kiosk.PrefUtils;
import aido.motor.MotorExecute;
import aido.polling.HttpDownloadHandlerPolling;
import aido.polling.IncomingMessageHandler;
import aido.properties.AlertBehaviorProperties;
import aido.properties.AutomateProperties;
import aido.properties.BehaveProperties;
import aido.properties.ConfigProperties;
import aido.properties.ControllerProperties;
import aido.properties.HttpProperties;
import aido.properties.PermittedApps;
import aido.properties.StorageProperties;
import aido.setdelay.SetDelay;
import aido.shoppinglist.MainActivityForShoppingList;
import aido.skype.SkypeAPIs;

public class AidoFace_15feb17 extends AppCompatActivity  implements TextureView.SurfaceTextureListener {


    int numberofmoves = 1;
    ImageView iv_anim;

    TextureView _videoview;

    FloatingActionButton fab;

    static int INTENT_SCANDIT = 1002;
    static int INTENT_SHOPPINGLIST = 1003;


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


    String _latestSpeechToTextMessage = "";

    HttpDownloadHandlerPolling _handlerpoll;

    Intent _intent_ROS;
    Intent _intent_TTS;
    Intent _intent_STT;
    Intent _intent_ROS_tolinux;
    Intent _rosintent;

    int ROS_ACTIVITY_REQ_CODE = 1022;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("IP");


    public static String PROP_HTTPDOWNLOAD_INTENTID = "com.whitesun.httpupload";
    public static String HTTPDOWNLOAD_MESSSAGEFIELD = "message";

    long timetostart = 0;

    boolean _pauseSpeechRecognition = false;

    SocketConnect socket;

    BehaviorUI behave;

    Intent     launchprojector;

    Behaviors randombehave;

    int movieloop = 1;
    Bundle bundle = new Bundle();


    PlayMovie _playmovie;

    SurfaceTexture _videosurface;

    Map<String,String> _newnotifications = new HashMap<String,String>();

    boolean demomode = false;

    MainActivityAffectiva affectivaActivity;

    DemoPlay _dp;

    AlertBehaveEngine _ABE;



    String demofilename = "demo.txt";


    String _idlemovie = "blink.m4v";

    int prevx = -1;
    int prevy = -1;

    FirebaseDatabase _firedbhandle = FirebaseDatabase.getInstance();
    DatabaseReference _firedbreference = _firedbhandle.getReference();



    public static String fire_aidoid = "AidoRobot1";
    public static String fire_aidoid2 = "AidoRobot2";

    public static String fire_aidoid_facedetect = "face";



    Intent _intent_showcam;

    int INTENT_SHOWCAM_REQCODE = 1014;

    String PI_IP="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aido_face);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("ip");
                String ip = (String) dataSnapshot1.getValue();
               PI_IP = ip;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Aido Settings", Snackbar.LENGTH_LONG)

                        .setAction("Setup", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //captureAndPredictScene();

                                    //enrollFace();
                              // Intent runbehavedefine = new Intent(AidoFace.this, BehaviorRawFileCreate.class);

                               // startActivityForResult(runbehavedefine,2000);



                                 //Intent setwifiintent = new Intent(AidoFace.this, WifiCredentials.class);
                                 //startActivityForResult(setwifiintent,2001);
                                Intent settingintent = new Intent(AidoFace_15feb17.this, SettingsActivity.class);
                                startActivityForResult(settingintent,2002);




                            }
                        }).show();
            }
        });

        fab.setImageResource(R.mipmap.mic_on);

        iv_anim = findViewById(R.id.imageView_animate);

        _videoview = findViewById(R.id.videoview);


        ConfigHandler.dumpDefaultConfig(false);

        ConfigHandler.copyDefaultUI(this, false);


       // PI_IP = ConfigHandler.getModuleName(ConfigProperties.PI_IP);




        _newnotifications.clear();

        timetostart = System.currentTimeMillis() + 5000;

        startTTSActivity();

        //startSpeechToTextActivity();

        _ttsaido = new BroadcastTTS(AidoFace_15feb17.this);
        _sttaido = new BroadcastSTT(AidoFace_15feb17.this);


        registerAllReceivers();

        _playmovie = new PlayMovie(_videoview);

        _videoview.setSurfaceTextureListener(this);


        SetDelay sd = new SetDelay(1000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                _latestSpeechToTextMessage="";
            }
        });


        iv_anim.setVisibility(View.GONE);
        _videoview.setVisibility(View.VISIBLE);


        ////////// INIT BEHAVIOR

        SetDelay sd3 = new SetDelay(1000);
        sd3.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                _playmovie.setSurface(_videosurface);

                _ABE = new AlertBehaveEngine(getApplicationContext(),PI_IP,_videoview, _videosurface,_ttsaido);

                isAidoIdle();




               /* socket = new SocketConnect("172.10.10.1",1001);

                SocketConnectReceive _bcgmsg = new SocketConnectReceive("172.10.10.1",1004)
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

                                        _dp = new DemoPlay(getApplicationContext(),PI_IP,_videoview, _videosurface,Message, _ttsaido);
                                        _dp.play();

                                    }


                                });




                    }
                };
                _bcgmsg.sendMessage("none");


*/
            }
        });



        if(ControllerProperties.MOTOR_CONTROLLER_ANDROID)
        {/// if motor is in android, reset the motor
            MotorExecute.reset();
        }

        final MotorMessage mm = new MotorMessage(AidoFace_15feb17.this,PI_IP);

        _firedbreference.child(fire_aidoid2).child("face").child("facedetect").child("x").setValue("-1");
        _firedbreference.child(fire_aidoid2).child("face").child("facedetect").child("y").setValue("-1");
        _firedbreference.child(fire_aidoid2).child("face").child("facedetect").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String value =  dataSnapshot.getValue().toString();

                String x =  dataSnapshot.child("x").getValue().toString();
                String y =  dataSnapshot.child("y").getValue().toString();

                if(x.equalsIgnoreCase("-1")) {return;}
                if(y.equalsIgnoreCase("-1")) {return;}


                Log.i("MOTOR","GOT : x=" + x + ",y=" + y);

                //x is taken from 160 to 520. (x - 160)/2 will give degrees

                int xint = CommonlyUsed.getIntegerValueFromString(x);
                xint = (xint-300)/4;
                String pan = "" + xint;

                int yint = CommonlyUsed.getIntegerValueFromString(y);
                yint = (int) ((200-yint)/4.5);
                String tilt = "" + yint;




                if(!mm.isRunning()) {
                    Log.i("FIREDB","Value changed ! : " + key + "=" + value);

                    Log.i("FACEDETECT","fired detect " + pan + "," + tilt);
                    mm.run(pan, tilt, "15");
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        _firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").setValue("0");
        _firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String key = dataSnapshot.getKey();
                    String value =  dataSnapshot.getValue().toString();

                    Log.i("FIREDB","FACE RECOG Value changed ! : " + key + "=" + value);

                    if(!CommonlyUsed.stringIsNullOrEmpty(value) && !value.equalsIgnoreCase("0"))
                    {
                        _ttsaido.speak("Hello " + value + " . How are you ? ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        _firedbreference.child(fire_aidoid2).child("face").child("emotion").child("emotion").setValue("0");
        _firedbreference.child(fire_aidoid2).child("face").child("emotion").child("emotion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String key = dataSnapshot.getKey();
                    String value =  dataSnapshot.getValue().toString();

                    Log.i("FIREDB","emotion Value changed ! : " + key + "=" + value);

                    if(!CommonlyUsed.stringIsNullOrEmpty(value) && !value.equalsIgnoreCase("0")
                            && !value.equalsIgnoreCase("neutral")
                            )
                    {
                        _ttsaido.speak("You seem to be having a lot of " + value + " .  ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        _firedbreference.child(fire_aidoid2).child("face").child("training").child("train").setValue("0");
        _firedbreference.child(fire_aidoid2).child("face").child("training").child("status").setValue("0");
        _firedbreference.child(fire_aidoid2).child("face").child("training").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String key = dataSnapshot.getKey();
                    String completionvalue =  dataSnapshot.child("status").getValue().toString();

                    Log.i("FIREDB","enrollment Value changed ! : " + key + "=" + completionvalue);

                    if(!CommonlyUsed.stringIsNullOrEmpty(completionvalue)
                            )
                    {
                        if(completionvalue.contains("2")) {
                            _ttsaido.speak("The enrollment is complete");
                            try
                            {
                                _firedbreference.child(fire_aidoid2).child("face").child("training").child("status").setValue("0");
                                finishActivity(INTENT_SHOWCAM_REQCODE);
                                String error = dataSnapshot.child("error").getValue().toString();


                                if(error.contains("1"))
                                {
                                    _ttsaido.speak("However there seems to be something wrong. Sorry. you will have to try again");
                                }
                                else
                                {
                                    _ttsaido.speak("You are successfully enrolled");
                                }

                            }
                            catch (Exception ex)
                            {

                            }



                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        _firedbreference.child(fire_aidoid2).child("companion").child("call").setValue("0");
        _firedbreference.child(fire_aidoid2).child("companion").child("call").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String value =  dataSnapshot.getValue().toString();

                Log.i("FIREDB","Value changed ! : " + key + "=" + value);

                if(CommonlyUsed.stringIsNullOrEmpty(value)){return;}
                if(value.equalsIgnoreCase("0")){return;}
                if(value.equalsIgnoreCase("1")){
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AidoFace_15feb17.this);
                    String aidoskypename = settings.getString("partnerskypename", "");

                    if(CommonlyUsed.stringIsNullOrEmpty(aidoskypename))
                    {
                        _firedbreference.child(fire_aidoid2).child("companion").child("call").setValue("2");
                        return;
                    }

                    SkypeAPIs.callCompanionVideo(AidoFace_15feb17.this,aidoskypename);
                }


                _firedbreference.child(fire_aidoid2).child("companion").child("call").setValue("0");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        _firedbreference.child(fire_aidoid).child("companion").child("acc").child("accx").setValue("-200");
        _firedbreference.child(fire_aidoid).child("companion").child("acc").child("accy").setValue("-200");
        _firedbreference.child(fire_aidoid).child("companion").child("acc").child("accz").setValue("-200");

        _firedbreference.child(fire_aidoid).child("companion").child("acc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String accx =  dataSnapshot.child("accx").getValue().toString();
                String accy =  dataSnapshot.child("accy").getValue().toString();
                String accz =  dataSnapshot.child("accz").getValue().toString();

                if(CommonlyUsed.stringIsNullOrEmpty(accx)){return;}
                if(accx.equalsIgnoreCase("-200")){return;}

                if(CommonlyUsed.stringIsNullOrEmpty(accy)){return;}
                if(accy.equalsIgnoreCase("-200")){return;}

                if(CommonlyUsed.stringIsNullOrEmpty(accz)){return;}
                if(accz.equalsIgnoreCase("-200")){return;}



                ///// TILT Calculation
                float zvaluefloat = CommonlyUsed.getFloatValueFromString(accz);
                zvaluefloat += 10; // range is -10 to +10.. normalised to 0 to 20 by adding 10;
                zvaluefloat = zvaluefloat * 9; // normalised to 1 to 120 debgrees;
                int tiltint = 180 - (int) zvaluefloat;
                String tilt = "abs:" + tiltint;

                ///// PAN Calculation
                float yvaluefloat = CommonlyUsed.getFloatValueFromString(accy);
                yvaluefloat += 5; // range is -5 to +5.. normalised to 0 to 10 by adding 10;
                yvaluefloat = yvaluefloat * 18; // normalised to 1 to 120 debgrees;
                int panint =  130 - (int) yvaluefloat;
                String pan = "abs:" + panint;


                Log.i("MOTOR","fired acc " + pan + "," + tilt);

                if(!mm.isRunning())
                    {
                     //   MotorMessage mm = new MotorMessage(AidoFace.this, PI_IP);
                        mm.run(pan, tilt, "15");
                    }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CommonlyUsed.showMsg(this,"Simulating");
        mm.run("HOME","HOME","100");
        SetDelay sd100 = new SetDelay(2000);
        sd100.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                mm.run("10","10","100");
                SetDelay sd101 = new SetDelay(2000);
                sd101.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        mm.run("-20","-20","100");
                    }
                });
            }
        });


    }



    void captureAndPredictScene()
    {
        Intent camerashow = new Intent(AidoFace_15feb17.this, ClickPhoto.class);

        startActivityForResult(camerashow,1010);

    }


    void registerAllReceivers()
    {
        //// this is the receiver for AUTOMATE
        IntentFilter filter = new IntentFilter();
        filter.addAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
        registerReceiver(receiver, filter);

        //// this is the SPEECH to text RECEIVER
        IntentFilter filter_speechtotext = new IntentFilter();
        filter_speechtotext.addAction(HttpProperties.PROP_STT_RECEIVER_INTENTID);
        registerReceiver(_speechreceiver, filter_speechtotext);

        //// this is the text to SPEECH RECEIVER
        IntentFilter filter_texttospeech = new IntentFilter();
        filter_texttospeech.addAction(HttpProperties.PROP_TTS_RECEIVER_INTENTID);
        registerReceiver(_ttsreceiver, filter_texttospeech);


        IntentFilter filter_automate = new IntentFilter();
        registerReceiver(_automatereceiver, filter_automate);


        LocalBroadcastManager.getInstance(this).registerReceiver(_notificationreceiver, new IntentFilter("Msg"));

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String message = intent.getStringExtra(HttpProperties.HTTPDOWNLOAD_MESSSAGEFIELD);

            Log.i("AUTOMATE","got ros message inside aido !!! " +  message);


            final IncomingMessageHandler IMH = new IncomingMessageHandler(message);

            if(IMH.getTask().equalsIgnoreCase(AutomateProperties.RUNBEHAVIOR))
            {
                pausespeechanimation = true;
                String value = IMH.getTextValue();

                if(_dp != null)
                {
                    _dp.interrupt();
                }

                //_dp = new DemoPlay(getApplicationContext(),PI_IP,_videoview, _videosurface,value,_ttsaido);
                //_dp.play();



                //_ABE.registerNotification();



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
                IMH.postValueToServer(AidoFace_15feb17.this,_previousFace.getDominantEmotion_irrespectiveofscore() + "," + _previousFace.getDominantScore() );
            }
        }





        //}
    };


    void showcam(int x, int y, int r, int time)
    {
        Intent camerashow = new Intent(AidoFace_15feb17.this, ClickPhoto.class);
        camerashow.putExtra("x", x);
        camerashow.putExtra("y", y);
        camerashow.putExtra("r", r);
        camerashow.putExtra("t", time);


        //camerashow.putExtra(MainActivityForShoppingList.BUNDLE_FROM_AIDO_UNIT, "Each");
        startActivity(camerashow);

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


            //CommonlyUsed.showMsg(getApplicationContext(),"New notification : " + title + "\n" + text);
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
                behave = new BehaviorUI(AidoFace_15feb17.this, "reademail.json.txt", "reademail", timetostart, iv_anim, socket);
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
                    behave = new BehaviorUI(AidoFace_15feb17.this, "reademail.json.txt", "reademail", timetostart, iv_anim, socket);
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
            CommonlyUsed.unmuteAudioOutput(AidoFace_15feb17.this);
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
        behave = new BehaviorUI(AidoFace_15feb17.this, "headproject.json.txt", "reademail", timetostart, iv_anim, socket);


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
            behave = new BehaviorUI(AidoFace_15feb17.this, "headproject.json.txt", "reademail", timetostart, iv_anim, socket);


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
            behave = new BehaviorUI(AidoFace_15feb17.this, "bodyproject.json.txt", "reademail", timetostart, iv_anim, socket);

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


                final OutPanSearch OPS = new OutPanSearch(AidoFace_15feb17.this);

                OPS.setOnTaskCompletedListener(new OutPanSearch.LoadingTaskFinishedListener() {
                    @Override
                    public void onTaskFinished(OutpanObject outpanobject) {
                        final OutpanObject outpanobject2 = OPS.getObject();


                        if(!CommonlyUsed.stringIsNullOrEmpty(outpanobject2.name.trim())) {
                            CommonlyUsed.showMsg(getApplicationContext(), "Product name = " + outpanobject2.name + ",url = " + outpanobject2.outpan_url);


                            _ttsaido.speak("Hey you have shown me,  " + outpanobject2.name + ". Adding it to List");


                            Intent intent_shoppinglist = new Intent(AidoFace_15feb17.this, MainActivityForShoppingList.class);
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
                            SearchForRecipe SFR = new SearchForRecipe(AidoFace_15feb17.this, outpanobject2.name.replace(" ",","))
                            {

                                @Override
                                protected void onReceivedRecipe(final String recipename, final String recipedetails, final String imageurl) {
                                    super.onReceivedRecipe(recipename, recipedetails, imageurl);


                                    if(!CommonlyUsed.stringIsNullOrEmpty(recipename.trim())) {
                                        _ttsaido.speak("You know what ? I have found an interesting recipe using called " + recipename + ". You need to use the following :  " + recipedetails
                                        );

                                        behave = new BehaviorUI(AidoFace_15feb17.this, "shopping.json.txt", "reademail", timetostart, iv_anim, socket);

                                        //CommonlyUsed.showMsg(AidoFace.this, "Recipe : " + recipename + "\n details : " + recipedetails);

                                        asynchttp downloadrecipeimage = new asynchttp(AidoFace_15feb17.this);
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
        LayoutInflater li = LayoutInflater.from(AidoFace_15feb17.this);
        View promptsView = li.inflate(R.layout.alert_exit, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AidoFace_15feb17.this);

        // set alert_exit.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final TextView textview_error = promptsView
                .findViewById(R.id.dialog_error);
        textview_error.setText("");


        final ComboBoxClass combo = promptsView.findViewById(R.id.combo);

        combo.clearAllItems(true);


        List<String> items = new ArrayList<String>();

        combo.appendItem("Select Behavior");

        List<String> files = aido.camera.fileReadWrite.getAllLeafFilesWithin(BehaveProperties.getRawBehaviorDir());


        //CommonlyUsed.showMsg(getApplicationContext(),"files = " + files.size() + "," + BehaveProperties.getRawBehaviorDir());

        for(int i=0;i<files.size();i++)
        {
            combo.appendItem("" + files.get(i));
        }

        combo.setSelectedIndex(0);

/*

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
*/
        //combo.additems(items);





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
                Toast.makeText(AidoFace_15feb17.this, "Canceling..", Toast.LENGTH_SHORT);
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



                if(!CommonlyUsed.stringIsNullOrEmpty(combo.getSelectedText()) && combo.getSelectedIndex() > 0) {

                    SetDelay sd = new SetDelay(5000);
                    sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                        @Override
                        public void onDelayCompleted() {
                            demofilename = BehaveProperties.getRawBehaviorDir() + combo.getSelectedText();
                           // _dp = new DemoPlay(getApplicationContext(), PI_IP, _videoview, _videosurface, demofilename,_ttsaido);
                           // _dp.play();
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
                    if(_ABE != null)
                    {
                        if(!_ABE.isPlaying())
                        {
                            if(_ABE != null)
                            {
                            }


                           // if(!_dp.getDemoFile().equalsIgnoreCase("idle.txt"))
                            if(_ABE.currentBehavior() != AlertBehaviorProperties.None && _ABE.currentBehavior() != AlertBehaviorProperties.idle)
                            {
                                sendCompletionBroadcast(AutomateProperties.BROADCAST_RUNBEHAVIOR,_dp.getDemoFile());
                            }


                            //_ABE = new AlertBehaveEngine(getApplicationContext(),PI_IP,_videoview, _videosurface,_ttsaido);


                            //_dp = new DemoPlay(getApplicationContext(),PI_IP,_videoview, _videosurface,"idle.txt",_ttsaido);
                            //_dp.play();

                            _ABE.registerNotification(AlertBehaviorProperties.idle);

                        }
                    }
                    else
                    {
                        //_dp = new DemoPlay(getApplicationContext(),PI_IP,_videoview, _videosurface,"idle.txt",_ttsaido);
                        //_dp.play();


                        _ABE.registerNotification(AlertBehaviorProperties.idle);


                        // _playmovie.play(StorageProperties.getDemoMovieFile(_idlemovie));
                    }

                }catch (Exception e)
                {

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





    void enrollFace()
    {
        LayoutInflater li = LayoutInflater.from(AidoFace_15feb17.this);
        View promptsView = li.inflate(R.layout.face_enroll, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AidoFace_15feb17.this);

        // set alert_exit.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final TextView textview_error = promptsView
                .findViewById(R.id.dialog_error);
        textview_error.setText("");




        final EditText userInput = promptsView
                .findViewById(R.id.edittext_facename);

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
                if(!CommonlyUsed.stringIsNullOrEmpty(userInput.getText().toString()))
                {

                    Toast.makeText(getApplicationContext(), "Enrollment Started!", Toast.LENGTH_SHORT).show();

                    _firedbreference.child(fire_aidoid2).child("face").child("training").child("name").setValue(userInput.getText().toString());
                    _firedbreference.child(fire_aidoid2).child("face").child("training").child("train").setValue("1");


                    _ttsaido.speak("Enrollment has started. Please make random movements of face in front of the camera");
                    _intent_showcam = new Intent(AidoFace_15feb17.this,ShowCamera.class);
                    _intent_showcam.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(_intent_showcam,INTENT_SHOWCAM_REQCODE);



                }
                else
                {
                    textview_error.setText("Enter a valid name");
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
                Toast.makeText(AidoFace_15feb17.this, "Canceling..", Toast.LENGTH_SHORT);
                textview_error.setText(R.string.exit_dialog_cancel);

                alertDialog.dismiss();
            }
        });
        // show it
        alertDialog.show();
        // super.onBackPressed();



        userInput.performClick();
    }


}

