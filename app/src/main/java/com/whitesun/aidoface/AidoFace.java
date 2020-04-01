package com.whitesun.aidoface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.*;
import com.whitesun.aidoface.settings.SettingsActivity;
import com.whitesuntech.aidohomerobot.R;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.security.auth.login.LoginException;

import aido.Affectiva.affdexme.FaceDataModel;
import aido.Affectiva.affdexme.MainActivityAffectiva;
import aido.OutPan.OutPanSearch;
import aido.OutPan.OutpanObject;
import aido.Recipe.RecipeDialog;
import aido.Recipe.SearchForRecipe;
import aido.Scandit.ScanditScanning;
import aido.TextToSpeech.BroadcastTTS;
import aido.UI.AlertBehaveEngine;
import aido.UI.AlertTriggerMapping;
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
import aido.controller.Controller;
import aido.controller.SensorsPI;
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

import static android.content.ContentValues.TAG;


public class AidoFace extends AppCompatActivity  implements TextureView.SurfaceTextureListener {
    private static final String TAG = "AidoFace";
    private Socket mSocket;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

//    {
//        try {
//            mSocket = IO.socket("http://192.168.2.4:4001");
//            Log.e("Socket", String.valueOf(mSocket));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
    int numberofmoves = 1;
    ImageView iv_anim;

    TextureView _videoview;
    public int i = 0;

    FloatingActionButton fab;

    static int INTENT_SCANDIT = 1002;
    static int INTENT_SHOPPINGLIST = 1003;
    static int INTENT_HEADPROJECTOR = 3000;
    Map<Integer,String> begYourPardonmap ;
String message1;


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

    public static java.lang.String PROP_HTTPDOWNLOAD_INTENTID = "com.whitesun.httpupload";
    public static java.lang.String HTTPDOWNLOAD_MESSSAGEFIELD = "message";

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

    DemoPlay _dp;

    AlertBehaveEngine _ABE;


    AlertTriggerMapping _ATM;

    String demofilename = "demo.txt";


    String _idlemovie = "blink.m4v";

    int prevx = -1;
    int prevy = -1;

    FirebaseDatabase _firedbhandle = FirebaseDatabase.getInstance();
    DatabaseReference _firedbreference = _firedbhandle.getReference();





    public static String fire_aidoid = "AidoRobot1";
    public  static  String fire_aidoid2 = "AidoRobot2";
    public static String fire_aidoid_facedetect = "face";

   // public static String fire_aidoid = "923y9374934793473";
   // public static String fire_aidoid_facedetect = "face9279127419274";


    Intent _intent_showcam;

    int INTENT_SHOWCAM_REQCODE = 1014;

   // String PI_IP="";

     MotorMessage mm;

    Date  saveddate = null;


    HashMap<String,String> alertbehaviorvariablehash = new HashMap<>();
    private String PI_IP;
    private static View view;
    private int tepan;
    private int temtilt;
    private String pann,tiltt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aido_face);
        //startService(new Intent(this,MyService.class));
        //shared preference to save time for i am watching you that is facetracking behaviour
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        view = getWindow().getDecorView().getRootView();






        editor = sharedPref.edit();
        editor.putInt("notcalledyet",0);
        editor.putInt("notcalledyet1",0);
        //editor.putString("current time", new Date().toString());
        editor.commit();



        begYourPardonmap = new HashMap<>();
        begYourPardonmap.put(1,"");
        begYourPardonmap.put(2,"");
        begYourPardonmap.put(3,"");
        begYourPardonmap.put(4,"");
        begYourPardonmap.put(5,"");
        //begYourPardonmap.put(6,R.raw.sorrylostyouthere);
        begYourPardonmap.put(6,"");
        begYourPardonmap.put(0,"");
//        begYourPardonmap.put(9,R.raw.add_a_recipt);
//        begYourPardonmap.put(0,R.raw.add_a_recipt);
//        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//        startActivity(intent);
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
                                Intent settingintent = new Intent(AidoFace.this, SettingsActivity.class);
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


        //PI_IP = ConfigHandler.getModuleName(ConfigProperties.PI_IP);
       // PI_IP = "192.168.0.128";
//        SharedPreferences sharedPreferences = getSharedPreferences("your_preferences", Activity.MODE_PRIVATE);
//         PI_IP  = sharedPreferences.getString("your_integer_key", "0");
//         Bundle bundle;
//         bundle = this.getIntent().getExtras();
//        assert bundle != null;
//        String addre = bundle.getString("Valu");
         PI_IP = "http://192.168.0.104";

         //bundle.clear();
//PI_IP="192.168.0.128";




        _newnotifications.clear();

        timetostart = System.currentTimeMillis() + 5000;

        startTTSActivity();

        startSpeechToTextActivity();

        _ttsaido = new BroadcastTTS(AidoFace.this);
        _sttaido = new BroadcastSTT(AidoFace.this);


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
        System.out.println("Deleay called");
        sd3.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                _playmovie.setSurface(_videosurface);

                _ABE = new AlertBehaveEngine(getApplicationContext(),PI_IP,_videoview, _videosurface,_ttsaido);


                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AidoFace.this);

                String personality = settings.getString("personalitytype", "");
                System.out.println("personality aido face:" +personality);
                Log.d(TAG, "onDelayCompleted: ");

                String personalityfile = AlertBehaviorProperties.getPeronalityPath() + personality;




                if(fileReadWrite.fileExists(personalityfile))
                {
                    CommonlyUsed.showMsg(getApplicationContext(),"Personality = " + personality);

                    _ATM = new AlertTriggerMapping(personalityfile);
                }

                isAidoIdle();
            }
        });

     //   mSocket.connect();
//        mSocket.on("FromAPI", onNewMessage);
//        mSocket.on("FromAPI", onTraningMessage);
//        mSocket.on("motor", onMotorMessage);
//        mSocket.on("val",onValMessage);
        //mSocket.on("FromAPI", onEmotion);

//        _firedbreference.child(fire_aidoid).child("Aido2").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                if(value.contentEquals("health")) {
//                    Intent intent1 = new Intent("com.example.archi.health.Activity.ActivityDrugInformation");
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   // startActivityForResult(intent1,1111);
//
//                    getApplicationContext().startActivity(intent1);
//
//                }else if(value.contentEquals("home")){
//                    Log.i("val",value);
//                    Intent intent1 = new Intent("com.example.archi.homemaintenance.Activity.MainActivity");
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivityForResult(intent1,1112);
//
//                    getApplicationContext().startActivity(intent1);
//
//                }
//                else if(value.contentEquals("null")){
//                    Intent intent1 = new Intent("com.example.archi.health.CloseActivity");
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent1.putExtra("data","close it");
//                    getApplicationContext().startActivity(intent1);
//                    }
//                //_firedbreference.child(fire_aidoid).child("Aido2").setValue("null");
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

        if(ControllerProperties.MOTOR_CONTROLLER_ANDROID)
        {/// if motor is in android, reset the motor
            MotorExecute.reset();
        }


        if(ControllerProperties.CONTROLLER_ENABLED && ControllerProperties.EXECUTION_IN_ODROID)
        {
            Controller.reset();
        }

        mm = new MotorMessage(AidoFace.this,PI_IP);

        _firedbreference.child(fire_aidoid2).child("face").child("facedetect").child("x").setValue("-1");
        _firedbreference.child(fire_aidoid2).child("face").child("facedetect").child("y").setValue("-1");
        _firedbreference.child(fire_aidoid2).child("face").child("facedetect").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String value =  dataSnapshot.getValue().toString();

                String x =  dataSnapshot.child("x").getValue().toString();
                String y =  dataSnapshot.child("y").getValue().toString();
                Log.i("MOTOR","GOT : x=" + x + ",y=" + y);

                if(x.equalsIgnoreCase("-1")) {return;}
                if(y.equalsIgnoreCase("-1")) {return;}



                //x is taken from 160 to 520. (x - 160)/2 will give degrees

                int xint = CommonlyUsed.getIntegerValueFromString(x);
                xint = (xint-300)/4;
                String pan = "" + xint;

                int yint = CommonlyUsed.getIntegerValueFromString(y);
                yint = (int) ((200-yint)/4.5);
                String tilt = "" + yint;


                if(xint < 250)
                {
                    AlertBehaviorProperties._lastFaceDetection = " towards my left ";
                }

                if(xint >= 250 && yint <=320)
                {
                    AlertBehaviorProperties._lastFaceDetection = " in front ";
                }

                if(xint >= 320 && yint <=520)
                {
                    AlertBehaviorProperties._lastFaceDetection = " towards my right ";
                }


                if(!mm.isRunning()) {
                    Log.i("FIREDB","Value changed ! : " + key + "=" + value);

                    Log.i("FACEDETECT","fired detect " + pan + "," + tilt);
                    mm.runPi(pan, tilt);
                   // mm.run(pan, tilt,"15");
//                   _ABE.registerNotification(AlertBehaviorProperties.FaceTracking);
                   // Log.i("watching")

                    //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if(sharedPref.getInt("notcalledyet",0)==1){
                        long savedTime  = sharedPref.getLong("currenttime",0);
                        Log.i("savedTime",String.valueOf(savedTime));

                        Date d = new Date();

                        long currentTime = d.getTime();
                        Log.i("currentTime",String.valueOf(currentTime));
                        long diff = currentTime - savedTime;
                        Log.i("differenceTimeFaceTrack",String.valueOf(diff));
                        if(diff >= 120000){
                            _ABE.registerNotification(AlertBehaviorProperties.FaceTracking);
                            Log.i("calledaftyeronemin","called");
                            Date date1 = new Date();
                            long currentTime1 = date1.getTime();
                            Log.i("currenttie1",String.valueOf(date1.getTime()));
                            editor.putLong("currenttime",currentTime1);
                            editor.commit();

                        }


                    }
                    if(sharedPref.getInt("notcalledyet",0)==0){
                        Log.i("notcalledyet","called");
                        editor.putInt("notcalledyet",1);
                        Date date = new Date();
                        long currentTime = date.getTime();
                        Log.i("currenttie",String.valueOf(date.getTime()));
                        editor.putLong("currenttime",currentTime);
                        editor.commit();
                        _ABE.registerNotification(AlertBehaviorProperties.FaceTracking);

                    }


//
//                    if(sharedPref.getInt("notcalledyet",0)==0){
//                        Log.i("notcalledyet","called");
//                        editor.putInt("notcalledyet",1);
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                        Date date = new Date();
//                       String date1  = dateFormat.format(date);
//                        Log.i("firsttime",date.toString());
//                       editor.putString("currentTime", date1);
//                        editor.commit();
//                        //_ABE.registerNotification(AlertBehaviorProperties.FaceTracking);
//                    }
//                    if(sharedPref.getInt("notcalledyet",0)==1){
//                        Log.i("calledSecondTime","called");
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date currentDate = new Date();
//                   //   String  currentDate1 =  dateFormat.format(currentDate);
////                        try {
//////                            currentDate = dateFormat.format(currentDate);
//////                            Log.i("currentdate",currentDate.toString());
////                        } catch (ParseException e) {
////                            e.printStackTrace();
////                        }
//
//                        Log.i("currentdate",currentDate.toString());
//                        String calledDate = sharedPref.getString("currentTime",null);
//                        Log.i("calledDate",calledDate);
//                        if(calledDate!=null){
//
//                            Date saveddatee = new Date();
//
//                            try {
//                                  saveddatee = dateFormat.parse(calledDate);
//                                Log.i("saveddate",saveddatee.toString());
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            long secondsInMilli = 1000;
//                            long minutesInMilli = secondsInMilli * 60;
//                            long hoursInMilli = minutesInMilli * 60;
//                            long daysInMilli = hoursInMilli * 24;
////                            currentDate.getTime();
////                            saveddate.getTime();
//                            Log.i("currentdatetime",String.valueOf(currentDate.getTime()));
//                            Log.i("saveddatetime",String.valueOf(saveddatee.getTime()));
//
//                            long diff =   currentDate.getTime()-saveddatee.getTime();
//                            Log.i("longtime",String.valueOf(diff));
//                            long elapsedDays = diff / daysInMilli;
//                            diff = diff % daysInMilli;
//
//                            long elapsedHours = diff / hoursInMilli;
//                            diff = diff % hoursInMilli;
//                            long elapsedMinutes = diff / minutesInMilli;
//                            Log.i("longtime",String.valueOf(elapsedMinutes));
//                            //different = different % minutesInMilli;
//
//                            //int min= (int) (diff / (1000 * 60));
//                            Log.i("minpassed",String.valueOf(elapsedMinutes));
//                            Log.i("minpassed",String.valueOf(elapsedMinutes));
//                            if(elapsedMinutes>=2) {
//                               Log.i("elapsedMinutes","called");
//                                //_ABE.registerNotification(AlertBehaviorProperties.FaceTracking);
//                                editor.putString("currentTime", currentDate.toString());
//                                editor.commit();
//                            }
//                            else {
//                                Log.i("elapsedMinutes","not called");
//                            }
//                            }
//
//                        }



                    //commented code to set timer for i am watching you
//                    new CountDownTimer(5000, 1000) {
//
//                        public void onTick(long millisUntilFinished) {
//                           Log.i("seconds remaining", String.valueOf(millisUntilFinished / 1000));
//                           // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
//                        }
//
//                        public void onFinish() {
//                           // mTextField.setText("done!");
//                            Log.i("donecountdowntimer","done");


//                        }
//                    }.start();

                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        _firedbreference.child(fire_aidoid).child("companion").child("restmotorpan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String rest = dataSnapshot.getValue(String.class);
                if(!mm.isRunning()){
                    mm.runPi(rest,rest);
                    _firedbreference.child(fire_aidoid).child("companion").child("restmotorpan").setValue("0");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        _firedbreference.child("fiveaction").child("facerecoganition").child("CommandAido").setValue("0");
        _firedbreference.child("fiveaction").child("facerecoganition").child("commandemotionresult").setValue("0");
        _firedbreference.child("fiveaction").child("facerecoganition").child("commandaidoresult").setValue("0");
        _firedbreference.child("fiveaction").child("facerecoganition").child("trainresult").setValue("0");


        _firedbreference.child("fiveaction").child("facerecoganition").child("commandemotionresult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String emotion = dataSnapshot.getValue(String.class);
                message1 = emotion;
                Log.e("checking", "onDataChange: "+message1 );

             //   Neutral:0.900753,Happy:0.644705,Sad:0.113542,Surprise:0.107674,Anger:0.033363
             //   Neutral:0.100753,Happy:0.644705,Sad:0.113542,Surprise:0.107674,Anger:0.033363
              //  Neutral:0.100753,Happy:0.144705,Sad:0.913542,Surprise:0.107674,Anger:0.033363
               // Neutral:0.100753,Happy:0.644705,Sad:0.113542,Surprise:0.907674,Anger:0.033363
               // Neutral:0.100753,Happy:0.644705,Sad:0.113542,Surprise:0.107674,Anger:0.933363

                if(!emotion.toLowerCase().contains("value 0")) {

//                    String[] emotionset=emotion.split(",");
//                    String[] emotion0=emotionset[0].split(":");
//                    String em1= emotion0[0];
//                    Float empercent1= Float.valueOf(emotion0[1]);
//                    String[] emotion1=emotionset[1].split(":");
//                    String em2=emotion1[0];
//                    Float empercent2= Float.valueOf(emotion1[1]);
//                    String[] emotion2=emotionset[2].split(":");
//                    String em3=emotion2[0];
//                    Float empercent3= Float.valueOf(emotion2[1]);
//                    String[] emotion3=emotionset[3].split(":");
//                    String em4=emotion3[0];
//                    Float empercent4= Float.valueOf(emotion3[1]);
//                    String[] emotion4=emotionset[4].split(":");
//                    String em5=emotion4[0];
//                    Float empercent5= Float.valueOf(emotion4[1]);

//                    Float[] e= new Float[]{empercent1,empercent2, empercent3,empercent4,empercent5};
//                    Float max=e[0];
//                    for(int i = 0; i < 5; i++)
//                    {
//                        if(max < e[i])
//                        {
//                            max = e[i];
//                        }
//                    }


//                    String currentEmotion=null;
//                    if(max.equals(empercent1))
//                    {
//                       currentEmotion=em1;
//
//                    }
//                    if(max.equals(empercent2))
//                    {
//                       currentEmotion=em2;
//                    }
//                    if(max.equals(empercent3))
//                    {
//                       currentEmotion=em3;
//                    }
//                    if(max.equals(empercent4))
//                    {
//                        currentEmotion=em4;
//                    }
//                    if(max.equals(empercent5))
//                    {
//                       currentEmotion=em5;
//
//                    }


                    if(message1.matches("happy"))
                    {
                        _ttsaido.speak("You look very happy. ");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
//                        Intent intent1 = new Intent("com.example.newmenu.family1");
//                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent1);

                    }

                    if(message1.matches("sad"))
                    {
                        _ttsaido.speak("You look sad. Let me show you some video to lift your mood up");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        Intent intent1 = new Intent("com.example.newmenu.video1");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);

                    }
                    if(message1.matches("surprise"))
                    {
                        _ttsaido.speak("you look so surprised");
                    }
                    if(message1.matches("anger"))
                    {
                        _ttsaido.speak("why are you so angry");
                    }
                    if(message1.matches("neutral"))
                    {
                        _ttsaido.speak("you look pretty okay");
                    }



                }

//                _firedbreference.child("fiveaction").child("facerecoganition").child("commandemotionresult").setValue("value 0");

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        _firedbreference.child("Emotion_x1").child("Fall").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String fall = dataSnapshot.getValue(String.class);
                message1 = fall;

                Log.e("checking", "onDataChange: "+message1 );
                if (!fall.toLowerCase().contains("value 0")) {
                    _ttsaido.speak("Fall detected");
                    Intent intent1 = new Intent("com.example.newreminder.FallDetected");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);

                }

                _firedbreference.child("Emotion_x1").child("Fall").setValue("value 0");

            }
//

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        _firedbreference.child("Key").child("Action").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String fall = dataSnapshot.getValue(String.class);
                message1 = fall;

                Log.e("checking", "onDataChange: "+message1 );
                if (!fall.toLowerCase().contains("0")) {
                    if(message1.contains("A"))
                    {
                        String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("strrrrr", ">>" + response);


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //displaying the error in toast if occurrs
                                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //creating a request queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        //adding the string request to request queue
                        requestQueue.add(stringRequest);

                        Intent intent1 = new Intent("com.example.anil.alrammanagerexample.video");
                        intent1.putExtra("username","komputer");
                        intent1.putExtra("callername","doctor");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                         URLstring = "http://192.168.0.104/motor_reset.php";
                         stringRequest = new StringRequest(Request.Method.GET, URLstring,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("strrrrr", ">>" + response);


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //displaying the error in toast if occurrs
                                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //creating a request queue
                         requestQueue = Volley.newRequestQueue(getApplicationContext());

                        //adding the string request to request queue
                        requestQueue.add(stringRequest);






                        }
                    if(message1.contains("D"))
                    {
                        Intent intent1 = new Intent("com.example.newmenu.videosong1");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
//                        String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
//                        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        Log.d("strrrrr", ">>" + response);
//
//do
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        //displaying the error in toast if occurrs
//                                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                        //creating a request queue
//                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                        //adding the string request to request queue
//                        requestQueue.add(stringRequest);
//                        try {
//                            Thread.sleep(10000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        URLstring = "http://192.168.0.104/motor_reset.php";
//                        stringRequest = new StringRequest(Request.Method.GET, URLstring,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        Log.d("strrrrr", ">>" + response);
//
//
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        //displaying the error in toast if occurrs
//                                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                        //creating a request queue
//                        requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                        //adding the string request to request queue
//                        requestQueue.add(stringRequest);
                    }
                    if(message1.contains("C"))
                    {
                        Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game1");
                        intent1.putExtra("game","1");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);

                        String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("strrrrr", ">>" + response);


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //displaying the error in toast if occurrs
                                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //creating a request queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        //adding the string request to request queue
                        requestQueue.add(stringRequest);
//                        try {
//                            Thread.sleep(10000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                         URLstring = "http://192.168.0.104/motor_reset.php";
//                         stringRequest = new StringRequest(Request.Method.GET, URLstring,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        Log.d("strrrrr", ">>" + response);
//
//
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        //displaying the error in toast if occurrs
//                                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                        //creating a request queue
//                         requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                        //adding the string request to request queue
//                        requestQueue.add(stringRequest);


                    }
                    if(message1.contains("B"))
                    {

                        String URLstring = "http://192.168.0.104/motor_reset.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("strrrrr", ">>" + response);


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //displaying the error in toast if occurrs
                                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //creating a request queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        //adding the string request to request queue
                        requestQueue.add(stringRequest);
                        _firedbreference.child("AidoRobot1").child("end").setValue("1");


                    }
                    _firedbreference.child("Key").child("Action").setValue("0");

                }



            }
//

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        _firedbreference.child("Application").child("Child2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String message = dataSnapshot.getValue(String.class);
                message1 = message;
                Log.e("checking", "onDataChange: "+message1 );
                //message1 = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);
                if (_pauseSpeechRecognition) {

                    //_latestSpeechToTextMessage = "";

                    return;
                }

                broadcastMessage(_latestSpeechToTextMessage);

                int delay = 500;

                _pauseSpeechRecognition = false;//BIG


                if (!message.toLowerCase().contains("got error 8")) {
                    CommonlyUsed.showMsg(getApplicationContext(), "I Heard : " + message);
                    Log.i(TAG, "onDataChange: " + message);
                    if (processSpeechToTextResults(message)) {
                        _pauseSpeechRecognition = true;//BIG
                    }
                }

                if (message.toLowerCase().contains("got error 8")) {
                    Log.i("STT", "Delaying more..... 5000");
                    delay = 5000;
                }


                // new ManageEyes(AidoFace.this,iv_anim).justLookRight();

//
//                            SetDelay sd = new SetDelay(delay);
//                                @Override
//                                public void onDelayCompleted() {
//                                    // _latestSpeechToTextMessage = "";
//
//
//                                    if(!_pauseSpeechRecognition) {
//                                        //  new ManageEyes(AidoFace.this,iv_anim).justLookLeft();
//
//
//                                        sendCompletionBroadcast(AutomateProperties.BROADCAST_SPEECH_TO_TEXT,message);
//
//                                        //startSpeechRecognition(); ///06/02/19
//                                        if(!pausespeechanimation) {
//                                /*
//                            ManageEyes manageEyes = new ManageEyes(AidoFace.this,iv_anim)
//                            {
//                                @Override
//                                public void onActionComplete() {
//                                    super.onActionComplete();
//
//                                    //   IMH.setTaskAsComplete(AidoFace.this);
//                                }
//                            };
//                            manageEyes.playAnimation(
//                                    "frame_aidolistening_",
//                                    0,
//                                    19,
//                                    130);*/
//                                        }
//
//
//
//                                    }
//
//                                }
//                            });
                _firedbreference.child("Application").child("Child2").setValue("got error 8");





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        _firedbreference.child("fiveaction").child("facerecoganition").child("commandaidoresult").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue().toString();
//                if(value.equals("no")){
//                    _ttsaido.speak("I, don't know you");
//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandaidoresult").setValue("0");
//                }else if(value.equals("0")){
//                    System.out.println("do nothing");
//                }
//                else { //_firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").setValue("0");
//                    _ttsaido.speak("Hello" + value + "I am good! How are you");
//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandaidoresult").setValue("0");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

        _firedbreference.child("fiveaction").child("facerecoganition").child("CommandAido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String value = dataSnapshot.getValue().toString();
                if(value.equals("1")){
                    _ttsaido.speak("started Training");
                    String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("strrrrr", ">>" + response);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs
                                    //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    //creating a request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    //adding the string request to request queue
                    requestQueue.add(stringRequest);


                }else if(value.equals("2"))
                {
                    _ttsaido.speak("your face is started to recognize");
                    String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("strrrrr", ">>" + response);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs
                                    //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    //creating a request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    //adding the string request to request queue
                    requestQueue.add(stringRequest);


                }
                else if (value.equals("3"))
                {
                    _ttsaido.speak("please wait...let me check your mood");
                }
                else if (value.equals("4"))
                {
                    _ttsaido.speak("starting face traking");
                }
                else if (value.equals("114"))
                {
                    _ttsaido.speak("stopped face tracking");
                }
                else if (value.equals("115"))
                {
                    _ttsaido.speak("stopped voice tracking");
                }
                else if (value.equals("5"))
                {
                    _ttsaido.speak("started voice tracking");
                }
                else
                {
                    System.out.println("do nothing");
                }
                _firedbreference.child("fiveaction").child("facerecoganition").child("CommandAido").setValue("value 0");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        _firedbreference.child("fiveaction").child("facerecoganition").child("commandaidoresult").addValueEventListener(new ValueEventListener() {
           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                if(value.equals("Unknown")){
                    _ttsaido.speak("sorry,i am not able to recognize you");
                    String URLstring = "http://192.168.0.104/motor_reset.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("strrrrr", ">>" + response);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs
                                    //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    //creating a request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    //adding the string request to request queue
                    requestQueue.add(stringRequest);

//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandaidoresult").setValue("0");

                }else if(value.equals("0")){
                    System.out.println("do nothing");
                }
                else { //_firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").setValue("0");


                    _ttsaido.speak("Hello" + value + "I am good! How are you");
                    String URLstring = "http://192.168.0.104/motor_reset.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("strrrrr", ">>" + response);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs
                                    //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    //creating a request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    //adding the string request to request queue
                    requestQueue.add(stringRequest);

//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandaidoresult").setValue("0");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        _firedbreference.child("fiveaction").child("facerecoganition").child("trainresult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                if(value.equals("success"))
                {
                    _ttsaido.speak("successfully trained your face");

                    String URLstring = "http://192.168.0.104/motor_reset.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("strrrrr", ">>" + response);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs
                                    //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    //creating a request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    //adding the string request to request queue
                    requestQueue.add(stringRequest);

//                    _firedbreference.child("fiveaction").child("facerecoganition").child("trainresult").setValue("0");

                }else {
                    System.out.println("do nothing");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        _firedbreference.child("fiveaction").child("facerecoganition").child("commandemotionresult").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue().toString();
//                if(value.equals("not_found")){
//                    _ttsaido.speak("I, don't know you");
//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandemotionresult").setValue("0");
//                }
//                else if(value.equals("0")){
//                    System.out.println("do nothing");
//                }else { //_firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").setValue("0");
//                    _ttsaido.speak("you looks" + value);
//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandemotionresult").setValue("0");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

//        _firedbreference.child("fiveaction").child("facerecoganition").child("commandobjectresult").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue().toString();
//                if(value.equals("not_found")){
//                    _ttsaido.speak("I, don't know you");
//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandobjectresult").setValue("0");
//                }else if(value.equals("0")){
//                    System.out.println("do nothing");
//                }else { //_firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").setValue("0");
//                    _ttsaido.speak("I found " + value);
//                    _firedbreference.child("fiveaction").child("facerecoganition").child("commandobjectresult").setValue("0");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });



        _firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").setValue("0");
        _firedbreference.child(fire_aidoid2).child("face").child("facerecog").child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String key = dataSnapshot.getKey();
                    String value =  dataSnapshot.getValue().toString();

                    Log.i("FIREDB","FACE RECOG Value changed ! : " + key + "=" + value);

                    if(!CommonlyUsed.stringIsNullOrEmpty(value) && !value.equalsIgnoreCase("0"))
                    {
                       
                       // _ttsaido.speak("Hello " + value + " . How are you ? ");
                        AlertBehaviorProperties._lastFaceRecognition = value;
                       // _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);

                        //set timer on how are you
                        if(sharedPref.getInt("notcalledyet1",0)==1){
                            long savedTime  = sharedPref.getLong("currenttime1",0);
                            Log.i("savedTimeFaceRecog",String.valueOf(savedTime));

                            Date d = new Date();

                            long currentTime = d.getTime();
                            Log.i("currentTime",String.valueOf(currentTime));
                            long diff = currentTime - savedTime;
                            Log.i("differenceTimeFAceREcog",String.valueOf(diff));
                            if(diff >= 120000){
                                //_ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
                                _ttsaido.speak("Hello " + value + " . How are you ? ");
                                _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
                                Date date1 = new Date();
                                long currentTime1 = date1.getTime();
                                Log.i("currenttie1",String.valueOf(date1.getTime()));
                                editor.putLong("currenttime1",currentTime1);
                                editor.commit();

                            }


                        }
                        if(sharedPref.getInt("notcalledyet1",0)==0){
                            Log.i("facerecognitionSharedP","called");
                            editor.putInt("notcalledyet1",1);
                            Date date = new Date();
                            long currentTime = date.getTime();
                            Log.i("currenttieFaceRecog",String.valueOf(date.getTime()));
                            editor.putLong("currenttime1",currentTime);
                            editor.commit();
                           // _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
                            _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
                            _ttsaido.speak("Hello " + value + " . How are you ? ");
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

//        _firedbreference.child(fire_aidoid2).child("face").child("emotion").child("emotion").setValue("0");
//        _firedbreference.child(fire_aidoid2).child("face").child("emotion").child("emotion").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                try {
//                    String key = dataSnapshot.getKey();
//                    String value =  dataSnapshot.getValue().toString();
//
//                    Log.i("FIREDB","emotion Value changed ! : " + key + "=" + value);
//
//                    if(!CommonlyUsed.stringIsNullOrEmpty(value) && !value.equalsIgnoreCase("0")
//                            && !value.equalsIgnoreCase("neutral")
//                            )
//                    {
//                        _ttsaido.speak("You seem to be having a lot of " + value + " .  ");
//                       // _ABE.registerNotification(AlertBehaviorProperties.EmotionRecognition);
//                        _ABE.registerNotification(AlertBehaviorProperties.HELLO);
//
//
//                        AlertBehaviorProperties._lastEmotionRecognition = value;
//
//                    }
//
//
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


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
                            //_ttsaido.speak("The enrollment is complete");
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
        _firedbreference.child(fire_aidoid).child("companion").child("call").setValue("0");
        _firedbreference.child(fire_aidoid).child("companion").child("call").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String value =  dataSnapshot.getValue().toString();

                Log.i("FIREDB","Value changed ! : " + key + "=" + value);

                if(CommonlyUsed.stringIsNullOrEmpty(value)){return;}
                if(value.equalsIgnoreCase("0")){return;}
                if(value.equalsIgnoreCase("1"))
                {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AidoFace.this);

                    String aidoskypename = settings.getString("partnerskypename", "");


                    Log.i("SKYPE","got partner :" + aidoskypename);

                    if(CommonlyUsed.stringIsNullOrEmpty(aidoskypename))
                    {
                        _firedbreference.child(fire_aidoid).child("companion").child("call").setValue("2");
                        return;
                    }

                    Log.i("SKYPE","calling skype :" + aidoskypename);

                    SkypeAPIs.callCompanionVideo(AidoFace.this,aidoskypename);
                }


                _firedbreference.child(fire_aidoid).child("companion").child("call").setValue("0");

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
                final String tilt =  String.valueOf(tiltint);

                ///// PAN Calculation
                float yvaluefloat = CommonlyUsed.getFloatValueFromString(accy);
                yvaluefloat += 5; // range is -5 to +5.. normalised to 0 to 10 by adding 10;
                yvaluefloat = yvaluefloat * 18; // normalised to 1 to 120 debgrees;
                int panint =  130 - (int) yvaluefloat;
                final String pan = String.valueOf(panint);


                //Log.i("MOTOR","fired acc " + pan + "," + tilt);

                if(!mm.isRunning()) {
                    Log.i("counter", "onDataChange: ");
                    try {
                        Thread.sleep(250);
                        mm.runPi(pan,tilt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                       MotorMessage mm = new MotorMessage(AidoFace.this, PI_IP);
                    try {
                        Thread.sleep(1000);
                        int pan1 = Integer.valueOf(pan);
                        int tilt1 = Integer.valueOf(tilt);
                        if (i == 0) {

                            pann = pan;
                            tiltt = tilt;

                            tepan = Integer.valueOf(pann);
                            temtilt = Integer.valueOf(tiltt);
                            i++;
                            mm.runPi(String.valueOf(pann), String.valueOf(tiltt));
                            Log.i("CO0", "fired acc " +pan1+","+ pann + "," + tiltt+ "," +i);
                        } else if (i > 0 && i <= 90) {
                            if (pan1 > Integer.valueOf(pann) && tilt1 > Integer.valueOf(tiltt)) {
                                tepan = tepan + 10;
                                temtilt = temtilt + 10;
                                mm.runPi(String.valueOf(tepan), String.valueOf(temtilt));
                                Log.i("CO1", "fired acc " +pan1+","+ tepan + "," + temtilt+ "," +i);
                            } else if (pan1 < Integer.valueOf(pann) && tilt1 < Integer.valueOf(tiltt)) {
                                tepan = tepan - 10;
                                temtilt = temtilt - 10;
                                Log.i("CO2", "fired acc " +pan1+","+ tepan + "," + temtilt+ "," +i);
                            } else if (pan1 > Integer.valueOf(pann) && tilt1 < Integer.valueOf(tiltt)) {
                                tepan = tepan + 10;
                                temtilt = temtilt - 10;
                                Log.i("CO3", "fired acc " +pan1+","+ tepan + "," + temtilt+ "," +i);
                            } else if (pan1 < Integer.valueOf(pann) && tilt1 > Integer.valueOf(tiltt)) {
                                tepan = tepan - 10;
                                temtilt = temtilt + 10;
                                mm.runPi(String.valueOf(tepan), String.valueOf(temtilt));
                                Log.i("CO4", "fired acc "+pan1+"," + tepan + "," + temtilt + "," + i);
                                System.out.println("do nothing");
                            }i++;
//                                         tepan = pan1;
//                                         temtilt = tilt1;
                            // mm.runPi(String.valueOf(pan1), String.valueOf(tilt1));
                        } else {
                             mm.runPi("130","130");
                            i = 0;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    new CountDownTimer(15000, 10) { //Set Timer for 5 seconds
//                        public void onTick(long millisUntilFinished) {
//                            Log.i("notrunningcom", "onTick: ");
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            int pan1 = Integer.valueOf(pan);
//                            int tilt1 = Integer.valueOf(tilt);
//                            if (i == 0) {
//
//                                pann = pan;
//                                tiltt = tilt;
//
//                                tepan = Integer.valueOf(pann);
//                                temtilt = Integer.valueOf(tiltt);
//                                i++;
//                                mm.runPi(String.valueOf(pann), String.valueOf(tiltt));
//                                Log.i("CO0", "fired acc " +pan1+","+ pann + "," + tiltt+ "," +i);
//                            } else if (i > 0 && i <= 5) {
//                                if (pan1 > Integer.valueOf(pann) && tilt1 > Integer.valueOf(tiltt)) {
//                                    tepan = tepan + 10;
//                                    temtilt = temtilt + 10;
//                                    mm.runPi(String.valueOf(tepan), String.valueOf(temtilt));
//                                    Log.i("CO1", "fired acc " +pan1+","+ tepan + "," + temtilt+ "," +i);
//                                } else if (pan1 < Integer.valueOf(pann) && tilt1 < Integer.valueOf(tiltt)) {
//                                    tepan = tepan + 10;
//                                    temtilt = temtilt + 10;
//                                    Log.i("CO2", "fired acc " +pan1+","+ tepan + "," + temtilt+ "," +i);
//                                } else if (pan1 > Integer.valueOf(pann) && tilt1 < Integer.valueOf(tiltt)) {
//                                    tepan = tepan + 10;
//                                    temtilt = temtilt + 10;
//                                    Log.i("CO3", "fired acc " +pan1+","+ tepan + "," + temtilt+ "," +i);
//                                } else if (pan1 < Integer.valueOf(pann) && tilt1 > Integer.valueOf(tiltt)) {
//                                    tepan = tepan + 10;
//                                    temtilt = temtilt + 10;
//                                    mm.runPi(String.valueOf(tepan), String.valueOf(temtilt));
//                                    Log.i("CO4", "fired acc "+pan1+"," + tepan + "," + temtilt + "," + i);
//                                    System.out.println("do nothing");
//                                }i++;
////                                         tepan = pan1;
////                                         temtilt = tilt1;
//                                // mm.runPi(String.valueOf(pan1), String.valueOf(tilt1));
//                            } else {
//                                // mm.runPi("130","130");
//                                i = 0;
//                            }
//
//                        }
//                         mm.runPi(pan, tilt);
//
//
//                    }.start();
//                    new CountDownTimer(90000, 10) { //Set Timer for 5 seconds
//                        public void onTick(long millisUntilFinished) {
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            mm.runPi("130","130");
//
//                        }
//
//
//                    }.start();


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








       /* CommonlyUsed.showMsg(this,"Simulating");
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
        });*/




        SetDelay sd100 = new SetDelay(1000);
        sd100.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                // new ManageEyes(AidoFace.this,iv_anim).justLookLeft();
                _latestSpeechToTextMessage="";
                startSpeechRecognition();
    //06/02/19

                // doAffectiva();
            }
        });



    }

//    private String url = "http://192.168.2.4:5000/books?name=abc";
//    private RequestQueue mRequestQueue;
//    private StringRequest mStringRequest;
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        private String name;
//
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Object room = args[0];
//                    try {
//                        // Create the root JSONObject from the JSON string.
//                        JSONObject jsonRootObject = new JSONObject(String.valueOf(room));
//
//                        //Get the instance of JSONArray that contains JSONObjects
//                        JSONArray jsonArray = jsonRootObject.optJSONArray("application");
//
//                        //Iterate the jsonArray and print the info of JSONObjects
//                        assert jsonArray != null;
//                        for(int i = 0; i < jsonArray.length(); i++){
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                            name = jsonObject.optString("name");
//
//                        }
//
//                    if (name.toLowerCase().contains("open")) {
//                        String openapp = name.replace("open", "").replace(" ", "").toLowerCase();
//                        //_ttsaido.speak("Looks like you want to add to shopping list? Come on,  Show me the item!");
//
//                        if (openapp.contains("box")) {
//                            //Intent affective_intent = new Intent(this, AidoMain.class);
//                            //affective_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            //this.startActivity(affective_intent);
//
//                            openBox();
//
//                            //thereisspeech = true;
//                            _ttsaido.speak("Looks like you want to see app box. Here you go!");
//
//                            //return thereisspeech;
//
//                        }
////                        String my_url = "http://192.168.2.4:5000/api/v1/resources/books?name=abc";// Replace this with your own url
////                        String my_data = "Hello my First Request Without any library";// Replace this with your data
////                        new MyHttpRequestTask().execute(my_url,my_data);
//
//
//                        for (Map.Entry<String, String> entry : PermittedApps.PROP_APPNAMES.entrySet()) {
//
//                            if (entry.getValue().equalsIgnoreCase("na")) {
//                                continue;
//                            }
//                            //System.out.println(entry.getKey() + "/" + entry.getValue());
//                            if (openapp.contains(entry.getValue().replace(" ", "").toLowerCase())) {
//                                try {
//
//                                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(entry.getKey());
//                                    startActivity(LaunchIntent);
//                                    //thereisspeech = true;
//                                    // _ttsaido.speak("Looks like you want to check " + entry.getValue() + ". Here you go!");
//                                    _ttsaido.speak(" ");
//
//
//                                } catch (Exception ex) {
//                                    _ttsaido.speak("Sorry could not open " + entry.getValue() + ". Is it installed?");
//                                }
//
//                                //return thereisspeech;
//                            }
//                        }
//
//                    }
//                    } catch (JSONException e) {e.printStackTrace();}
//
//
//                }
//
//
//            });
////            Thread thread = new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    postData();
////                }
////            });thread.start();
//
//            postData();
//
//
//        }
//
//    };
//    private Emitter.Listener onTraningMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                private String name;
//
//                @Override
//                public void run() {
//                    Object room =   args[0];
//                    Log.e(TAG, "run: "+room );
//                    StringBuilder data = new StringBuilder();
//                    try {
//                        // Create the root JSONObject from the JSON string.
//                        JSONObject jsonRootObject = new JSONObject(String.valueOf(room));
//
//                        //Get the instance of JSONArray that contains JSONObjects
//                        JSONArray jsonArray = jsonRootObject.optJSONArray("traning");
//
//                        //Iterate the jsonArray and print the info of JSONObjects
//                        assert jsonArray != null;
//                        for(int i = 0; i < jsonArray.length(); i++){
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                            name = jsonObject.optString("name");
//
//                        }
//                        if(!CommonlyUsed.stringIsNullOrEmpty(name) && !name.equalsIgnoreCase("0"))
//                        {
//
//                            // _ttsaido.speak("Hello " + value + " . How are you ? ");
//                            AlertBehaviorProperties._lastFaceRecognition = name;
//                            // _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
//
//                            //set timer on how are you
//                            if(sharedPref.getInt("notcalledyet1",0)==1){
//                                long savedTime  = sharedPref.getLong("currenttime1",0);
//                                Log.i("savedTimeFaceRecog",String.valueOf(savedTime));
//
//                                Date d = new Date();
//
//                                long currentTime = d.getTime();
//                                Log.i("currentTime",String.valueOf(currentTime));
//                                long diff = currentTime - savedTime;
//                                Log.i("differenceTimeFAceREcog",String.valueOf(diff));
//                                if(diff >= 120000){
//                                    //_ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
//                                    _ttsaido.speak("Hello " + name + " . How are you ? ");
//                                    _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
//                                    Date date1 = new Date();
//                                    long currentTime1 = date1.getTime();
//                                    Log.i("currenttie1",String.valueOf(date1.getTime()));
//                                    editor.putLong("currenttime1",currentTime1);
//                                    editor.commit();
//
//                                }
//
//
//                            }
//                            if(sharedPref.getInt("notcalledyet1",0)==0){
//                                Log.i("facerecognitionSharedP","called");
//                                editor.putInt("notcalledyet1",1);
//                                Date date = new Date();
//                                long currentTime = date.getTime();
//                                Log.i("currenttieFaceRecog",String.valueOf(date.getTime()));
//                                editor.putLong("currenttime1",currentTime);
//                                editor.commit();
//                                // _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
//                                _ABE.registerNotification(AlertBehaviorProperties.FaceRecognition);
//                                _ttsaido.speak("Hello " + name + " . How are you ? ");
//                            }
//
//                        }
//                    } catch (JSONException e) {e.printStackTrace();}
//                }
//            });
//        }
//    };
//
//    private Emitter.Listener onMotorMessage = new Emitter.Listener() {
//
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    String motor = (String) args[0];
//                    motor = motor.replace("[", " ").replace(",", " ").replace("]", " ");
//                    String[] a = motor.split(" ", 10);
//                    System.out.println("anilmotor" + a[1] + ":" + a[3] + ":" + a[5]);
//
//
//                    if (CommonlyUsed.stringIsNullOrEmpty(a[1])) {
//                        return;
//                    }
//                    if (a[1].equalsIgnoreCase("-200")) {
//                        return;
//                    }
//
//                    if (CommonlyUsed.stringIsNullOrEmpty(a[3])) {
//                        return;
//                    }
//                    if (a[3].equalsIgnoreCase("-200")) {
//                        return;
//                    }
//
//                    if (CommonlyUsed.stringIsNullOrEmpty(a[5])) {
//                        return;
//                    }
//                    if (a[5].equalsIgnoreCase("-200")) {
//                        return;
//                    }
//
//
//                    ///// TILT Calculation
//                    float zvaluefloat = CommonlyUsed.getFloatValueFromString(a[5]);
//                    zvaluefloat += 10; // range is -10 to +10.. normalised to 0 to 20 by adding 10;
//                    zvaluefloat = zvaluefloat * 9; // normalised to 1 to 120 debgrees;
//                    int tiltint = 180 - (int) zvaluefloat;
//                    final String tilt = String.valueOf(tiltint);
//
//                    ///// PAN Calculation
//                    float yvaluefloat = CommonlyUsed.getFloatValueFromString(a[3]);
//                    yvaluefloat += 5; // range is -5 to +5.. normalised to 0 to 10 by adding 10;
//                    yvaluefloat = yvaluefloat * 18; // normalised to 1 to 120 debgrees;
//                    int panint = 130 - (int) yvaluefloat;
//                    final String pan = String.valueOf(panint);
//
//
//                    Log.i("MOTOR","fired acc " + mm.isRunning());
//
//                    if (!mm.isRunning()) {
////                        Log.i("counter", "onDataChange: ");
////                        try {
////                            Thread.sleep(20);
//                            mm.runPi(pan, tilt);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                }
//            });
//
//        }
//    };
//    private Emitter.Listener onValMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    final String message = (String) args[0];
//
//                    //final String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);
//                    // here change needed for alexa
//                    message1 = message;
//                    //message1 = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);
////                Intent intentperticularactivity = new Intent();
////                intentperticularactivity.setAction("perticularActivity");
////                intentperticularactivity.putExtra("data",message);
////                sendBroadcast(intentperticularactivity);
//
//
//                    if(_pauseSpeechRecognition) {
//
//                        //_latestSpeechToTextMessage = "";
//
//                        return;}
//
//                    broadcastMessage(_latestSpeechToTextMessage);
//
//                    int delay = 500;
//
//                    _pauseSpeechRecognition = false;//BIG
//
//
//                    if(!message.toLowerCase().contains("got error")) {
//                        CommonlyUsed.showMsg(getApplicationContext(), "I Heard : " + message);
//                        if(processSpeechToTextResults(message))
//                        {
//                            _pauseSpeechRecognition = true;//BIG
//                        }
//                    }
//
//                    if(message.toLowerCase().contains("got error 8")) {
//                        Log.i("STT","Delaying more..... 5000");
//                        delay = 5000;
//                    }
//
//
//
//                    // new ManageEyes(AidoFace.this,iv_anim).justLookRight();
//
//
//                    SetDelay sd = new SetDelay(delay);
//                    sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
//                        @Override
//                        public void onDelayCompleted() {
//                            // _latestSpeechToTextMessage = "";
//
//
//                            if(!_pauseSpeechRecognition) {
//                                //  new ManageEyes(AidoFace.this,iv_anim).justLookLeft();
//
//
//                                sendCompletionBroadcast(AutomateProperties.BROADCAST_SPEECH_TO_TEXT,message);
//
//                                startSpeechRecognition(); ///06/02/19
//                                if(!pausespeechanimation) {
//                                /*
//                            ManageEyes manageEyes = new ManageEyes(AidoFace.this,iv_anim)
//                            {
//                                @Override
//                                public void onActionComplete() {
//                                    super.onActionComplete();
//
//                                    //   IMH.setTaskAsComplete(AidoFace.this);
//                                }
//                            };
//                            manageEyes.playAnimation(
//                                    "frame_aidolistening_",
//                                    0,
//                                    19,
//                                    130);*/
//                                }
//
//
//
//                            }
//
//                        }
//                    });
//
//
//                }
//            });
//        }
//    };
//
//    public void postData(){
////        HttpClient httpclient = new DefaultHttpClient();
////        HttpPost httppost = new HttpPost("http://192.168.2.4:5000/api/v1/resources/books?name=abc");
////        System.out.println("execute");
//        mRequestQueue = Volley.newRequestQueue(this);
//
//        //String Request initialized
//        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                //Toast.makeText(getApplicationContext(),"Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen
//                Log.i(TAG, "onResponse: "+response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.i(TAG,"Error :" + error.toString());
//            }
//        });
//
//        mRequestQueue.add(mStringRequest);
////        try {
////            // Add your data
////
////
////            // Execute HTTP Post Request
////            HttpResponse response = httpclient.execute(httppost);
////
////        } catch (ClientProtocolException e) {
////            // TODO Auto-generated catch block
////        } catch (IOException e) {
////            // TODO Auto-generated catch block
////        }
//    }
    private class MyHttpRequestTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            String my_url = params[0];
            String my_data = params[1];
            try {
                URL url = new URL(my_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                // setting the  Request Method Type
                httpURLConnection.setRequestMethod("POST");
                // adding the headers for request
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                try{
                    //to tell the connection object that we will be wrting some data on the server and then will fetch the output result
                    httpURLConnection.setDoOutput(true);
                    // this is used for just in case we don't know about the data size associated with our request
                    httpURLConnection.setChunkedStreamingMode(0);

                    // to write tha data in our request
                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write(my_data);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();

                    // to log the response code of your request
                    Log.d(TAG, "MyHttpRequestTask doInBackground : " +httpURLConnection.getResponseCode());
                    // to log the response message from your server after you have tried the request.
                    Log.d(TAG, "MyHttpRequestTask doInBackground : " +httpURLConnection.getResponseMessage());


                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    // this is done so that there are no open connections left when this task is going to complete
                    httpURLConnection.disconnect();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }




    void captureAndPredictScene()
    {
        Intent camerashow = new Intent(AidoFace.this, ClickPhoto.class);

        startActivityForResult(camerashow,1010);

    }


    void registerAllReceivers()
    {
        //// this is the receiver for AUTOMATE
        IntentFilter filter = new IntentFilter();
        filter.addAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
        registerReceiver(receiver, filter);


        //// this is the SPEECH to text RECEIVER
        IntentFilter filter_speechtotext = new IntentFilter();   // new comment
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

                value = BehaveProperties.getRawBehaviorDir() + value;

                        _ABE.directRun(value);



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
                //Intent LaunchIntent2 = getPackageManager().getLaunchIntentForPackage("com.whitesuntech.playmovie");
                //startActivityForResult( LaunchIntent2, 1112);

                Intent pickMedia = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                //pickMedia.setType("video/*");
                startActivityForResult(pickMedia,INTENT_HEADPROJECTOR);


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
                IMH.postValueToServer(AidoFace.this,_previousFace.getDominantEmotion_irrespectiveofscore() + "," + _previousFace.getDominantScore() );
            }
        }





        //}
    };


    void showcam(int x, int y, int r, int time)
    {
        Intent camerashow = new Intent(AidoFace.this, ClickPhoto.class);
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
                behave = new BehaviorUI(AidoFace.this, "reademail.json.txt", "reademail", timetostart, iv_anim, socket);
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
                    behave = new BehaviorUI(AidoFace.this, "reademail.json.txt", "reademail", timetostart, iv_anim, socket);
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
        AlertBehaviorProperties._lastShowingNotifications = stringtospeak;
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
            CommonlyUsed.unmuteAudioOutput(AidoFace.this);
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

           // unregisterReceiver(_speechreceiver); //new comment

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
        public void onReceive(Context context, final Intent intent) {
            super.onReceive(context, intent);


//            if (intent.getAction().equalsIgnoreCase(HttpProperties.PROP_STT_RECEIVER_INTENTID)) {
//
//
//                 final String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);
//               // here change needed for alexa
//                message1 = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);
////                Intent intentperticularactivity = new Intent();
////                intentperticularactivity.setAction("perticularActivity");
////                intentperticularactivity.putExtra("data",message);
////                sendBroadcast(intentperticularactivity);
//
//
//                if(_pauseSpeechRecognition) {
//
//                    //_latestSpeechToTextMessage = "";
//
//                    return;}
//
//                broadcastMessage(_latestSpeechToTextMessage);
//
//                int delay = 500;
//
//                _pauseSpeechRecognition = false;//BIG
//
//
//                if(!message.toLowerCase().contains("got error")) {
//                    CommonlyUsed.showMsg(getApplicationContext(), "I Heard : " + message);
//                    if(processSpeechToTextResults(message))
//                    {
//                        _pauseSpeechRecognition = true;//BIG
//                    }
//                }
//
//                if(message.toLowerCase().contains("got error 8")) {
//                    Log.i("STT","Delaying more..... 5000");
//                    delay = 5000;
//                }
//
//
//
//               // new ManageEyes(AidoFace.this,iv_anim).justLookRight();
//
//
//                SetDelay sd = new SetDelay(delay);
//                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
//                    @Override
//                    public void onDelayCompleted() {
//                       // _latestSpeechToTextMessage = "";
//
//
//                        if(!_pauseSpeechRecognition) {
//                          //  new ManageEyes(AidoFace.this,iv_anim).justLookLeft();
//
//
//                            sendCompletionBroadcast(AutomateProperties.BROADCAST_SPEECH_TO_TEXT,message);
//
//                            startSpeechRecognition(); ///06/02/19
//                            if(!pausespeechanimation) {
//                                /*
//                            ManageEyes manageEyes = new ManageEyes(AidoFace.this,iv_anim)
//                            {
//                                @Override
//                                public void onActionComplete() {
//                                    super.onActionComplete();
//
//                                    //   IMH.setTaskAsComplete(AidoFace.this);
//                                }
//                            };
//                            manageEyes.playAnimation(
//                                    "frame_aidolistening_",
//                                    0,
//                                    19,
//                                    130);*/
//                            }
//
//
//
//                        }
//
//                    }
//                });
//
//            }






            }

      //  }
  };




    private TTSBroadcastReceiver _ttsreceiver = new TTSBroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if(intent.getAction().equalsIgnoreCase(HttpProperties.PROP_TTS_RECEIVER_INTENTID)) {

                String message = intent.getStringExtra(HttpProperties.PROP_SPEECHTOTEXT_MESSSAGEFIELD);


                sendCompletionBroadcast(AutomateProperties.BROADCAST_TEXT_TO_SPEECH,"");

                //CommonlyUsed.showMsg(getApplicationContext(),"Speech over");

                _pauseSpeechRecognition = false;
                SetDelay sd = new SetDelay(500);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        //_latestSpeechToTextMessage = "";
                        if(!_pauseSpeechRecognition) {
                            startSpeechRecognition(); //06/02/19
                        }
                    }
                });

               // behave.interrupt();


            }

        }
    };



// commenting start speechRecognition 06/02/19
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
        Intent intentservice = new Intent(AidoFace.this,BeckgroundServicee.class);
        startService(intentservice);


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
        behave = new BehaviorUI(AidoFace.this, "headproject.json.txt", "reademail", timetostart, iv_anim, socket);


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


    static boolean bodyprojecton = false;
    static boolean headprojecton = false;


    boolean processSpeechToTextResults(String speechtotext) {
        boolean thereisspeech = false;

        if (speechtotext.length() < 4) {
            return thereisspeech;
        }


        int matchpercent = 0;
        String behavior = "";
        String matchedquestion = "";
        if (_ATM != null && _ATM.isValid()) {
            Log.i("ALERT", "trying to match : " + speechtotext.toLowerCase());
            behavior = _ATM.getMatchedBehavior(speechtotext.toLowerCase());

            Log.i("ALERT", "GOT Match : " + behavior + " with percent " + _ATM.getMatchPercent());

            matchedquestion = _ATM.getMatchQuestion();
            matchpercent = _ATM.getMatchPercent();


        }


        _latestSpeechToTextMessage = speechtotext;


        if (speechtotext.toLowerCase().contains("shopping")
                || AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.shopping, behavior)) {
            thereisspeech = true;
            _ttsaido.speak("Looks like you want to add to your list? Come on,  Show me the item!");

            _ABE.registerNotification(AlertBehaviorProperties.shopping);

            SetDelay sd = new SetDelay(2000);
            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {
                    doScandit();
                }
            });

            return thereisspeech;
        }

        if (speechtotext.toLowerCase().contains("scene") || speechtotext.toLowerCase().contains("identify")
                || AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.scene, behavior)) {
            thereisspeech = true;
            // _ABE.registerNotification(AlertBehaviorProperties.scene);
            // _ttsaido.speak("Let me show you what I see !");
            _ttsaido.speak(" ");
            SetDelay sd = new SetDelay(2000);
            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {
                    captureAndPredictScene();
                }
            });
            return thereisspeech;
        }


        if ((speechtotext.toLowerCase().contains("head") && speechtotext.toLowerCase().contains("projector"))
                || (
                AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.HeadProjector_ON, behavior)

                        && matchpercent > 0
        )) {
            thereisspeech = true;
            _ttsaido.speak("Let me switch on the projector while you choose the movie");


            Controller.run(AidoFace.this, PI_IP, ControllerProperties.FIELD_CONTROLLER_HEADPROJECTOR_ON);

            _ABE.registerNotification(AlertBehaviorProperties.HeadProjector_ON);


            SetDelay sd = new SetDelay(5000);
            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {
                    Intent pickMedia = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickMedia, INTENT_HEADPROJECTOR);

                }
            });

            headprojecton = true;
            return thereisspeech;
        }


        if ((speechtotext.toLowerCase().contains("led") && speechtotext.toLowerCase().contains("on"))


                || (AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.LEDA_ON, behavior)
                && matchpercent > 0
        )) {
            thereisspeech = true;
            _ttsaido.speak("Let me switch on LED");

            Controller.run(AidoFace.this, PI_IP, ControllerProperties.FIELD_CONTROLLER_LEDA_ON);

            _ABE.registerNotification(AlertBehaviorProperties.LEDA_ON);


            return thereisspeech;
        }

        if ((speechtotext.toLowerCase().contains("led") && speechtotext.toLowerCase().contains("off"))


                || (AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.LEDA_OFF, behavior)
                && matchpercent > 0
        ))


        {
            thereisspeech = true;
            _ttsaido.speak("Let me switch off LED");

            Controller.run(AidoFace.this, PI_IP, ControllerProperties.FIELD_CONTROLLER_LEDA_OFF);

            _ABE.registerNotification(AlertBehaviorProperties.LEDA_OFF);


            return thereisspeech;
        }


        if ((speechtotext.toLowerCase().contains("body") && speechtotext.toLowerCase().contains("projector"))


                || (AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.MainProjector_ON, behavior)

                && matchpercent > 0
        ))

        {
            thereisspeech = true;
            _ttsaido.speak("Oh! let me play me your favorite movie. I hope you like this one. Its hi def");

            Controller.run(AidoFace.this, PI_IP, ControllerProperties.FIELD_CONTROLLER_BODYPROJECTOR_ON);

            _ABE.registerNotification(AlertBehaviorProperties.MainProjector_ON);


            SetDelay sd = new SetDelay(5000);
            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {
                    Intent pickMedia = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickMedia, INTENT_HEADPROJECTOR);
                }
            });

            bodyprojecton = true;

            return thereisspeech;
        }


        if ((speechtotext.toLowerCase().contains("projector") && (speechtotext.toLowerCase().contains("off") || speechtotext.toLowerCase().contains("close")))


                || (AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.MainProjector_OFF, behavior)
                && matchpercent > 0
        ))

        {
            thereisspeech = true;
            _ttsaido.speak("Let me switch off all projectors");

            if (bodyprojecton) {
                Controller.run(AidoFace.this, PI_IP, ControllerProperties.FIELD_CONTROLLER_BODYPROJECTOR_OFF);
                _ABE.registerNotification(AlertBehaviorProperties.MainProjector_OFF);
            }
            Controller.run(AidoFace.this, PI_IP, ControllerProperties.FIELD_CONTROLLER_HEADPROJECTOR_OFF);


            bodyprojecton = false;
            headprojecton = false;

            return thereisspeech;
        }


        if (AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.SkypeCall_Make, behavior)
                && matchpercent > 0) {
            thereisspeech = false;

            _firedbreference.child(fire_aidoid).child("companion").child("call").setValue("1");
            //// swicthing off the head projector


            Log.i("ALERT", "GOT SKYPE REQUEST!!");


            // return thereisspeech;
        }

        if (AlertBehaviorProperties.checkIfBehaviorNameMatchesRequest(AlertBehaviorProperties.ShowingNotifications, behavior)
                && matchpercent > 0) {
            thereisspeech = true;

            speakAllNotifications();

            _ABE.registerNotification(AlertBehaviorProperties.ShowingNotifications);


            return thereisspeech;
        }
       // _firedbreference.child("Application").child("Child").setValue("null");
//        _firedbreference.child("AidoRobot1").child("Application").child("Child").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //String setting = dataSnapshot.getValue().toString();
//                String setting = dataSnapshot.getValue(String.class);
//                //Toast.makeText(AidoFace.this, setting, Toast.LENGTH_SHORT).show();
//
//                if (setting.toLowerCase().contains("open") || setting == null) {
//                    String openapp = setting.replace("open", "").replace(" ", "").toLowerCase();
//                    //_ttsaido.speak("Looks like you want to add to shopping list? Come on,  Show me the item!");
//
//                    if (openapp.contains("box")) {
//                        //Intent affective_intent = new Intent(this, AidoMain.class);
//                        //affective_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        //this.startActivity(affective_intent);
//
//                        openBox();
//
//                        //thereisspeech = true;
//                        _ttsaido.speak("Looks like you want to see app box. Here you go!");
//
//                        //return thereisspeech;
//
//                    }
//
//
//
//                    for (Map.Entry<String, String> entry : PermittedApps.PROP_APPNAMES.entrySet()) {
//
//                        if (entry.getValue().equalsIgnoreCase("na")) {
//                            continue;
//                        }
//                        //System.out.println(entry.getKey() + "/" + entry.getValue());
//                        if (openapp.contains(entry.getValue().replace(" ", "").toLowerCase())) {
//                            try {
//
//                                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(entry.getKey());
//                                startActivity(LaunchIntent);
//                                //thereisspeech = true;
//                                // _ttsaido.speak("Looks like you want to check " + entry.getValue() + ". Here you go!");
//                                _ttsaido.speak(" ");
//
//
//                            } catch (Exception ex) {
//                                _ttsaido.speak("Sorry could not open " + entry.getValue() + ". Is it installed?");
//                            }
//
//                            //return thereisspeech;
//                        }
//                    }
//
//                }
//                _firedbreference.child(fire_aidoid).child("Application").child("Child").setValue("null");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        if (speechtotext.toLowerCase().contains("suffering from fever")) {
            _ttsaido.speak("sorry to hear that! What can i do for you");


        }


        if (speechtotext.toLowerCase().contains("feeling warm")) {
            _ttsaido.speak("ok! i can switch on th ac");

        }

        if (speechtotext.toLowerCase().contains("teach me an exercise")) {
            _ttsaido.speak("ok! watch this video");

        }
        if (speechtotext.toLowerCase().contains("add diet for me")) {
            _ttsaido.speak("ok! you can add your diet here");

        }
        if (speechtotext.toLowerCase().contains("having back pain")) {
            _ttsaido.speak("For how long you are having this pain....");

        }
        if (speechtotext.toLowerCase().contains("last 1 week")) {
            _ttsaido.speak("it seems you are suffering for a long time... please go and consult a doctor as soon as possible");

        }
        if (speechtotext.toLowerCase().contains("i am feeling bored")) {
            _ttsaido.speak("i hope this video will change your mood");

        }
        if ((speechtotext.toLowerCase().contains("i'm having like pain")) || (speechtotext.toLowerCase().contains("i'm having leg pin")) || (speechtotext.toLowerCase().contains("i'm having leg pain"))) {
            _ttsaido.speak("which leg!? lef or right?" );

        }
        if (speechtotext.toLowerCase().contains("left leg")) {
            _ttsaido.speak("if you want, i will show some exercise for you");

        }

        if (speechtotext.toLowerCase().contains("right leg")) {
            _ttsaido.speak("if you want, i will show some exercise for you");

        }

        if (speechtotext.toLowerCase().contains("ok proceed")) {
            _ttsaido.speak("i hope this exercise will make you feel better");

        }
        if (speechtotext.toLowerCase().contains("search dolo")) {
            _ttsaido.speak("ok! you can see all details here");

        }
        if (speechtotext.toLowerCase().contains("suffering from stomach pain")) {
            _ttsaido.speak("what did you had last. i think that one made you some acidity in your stomach");

        }








        if (speechtotext.toLowerCase().contains("call doctor li")||speechtotext.toLowerCase().contains("call doctor lee")) {
            _firedbreference.child("AidoRobot1").child("companion").child("call").setValue("1");
            _ttsaido.speak("Let me set it for you");

        }

        if (speechtotext.toLowerCase().contains("quit video call")) {
            _ttsaido.speak("stopping video call");
            Intent intent = new Intent(getApplicationContext(), AidoFace.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
        if (speechtotext.toLowerCase().contains("do you know me")) {
            Intent intent = new Intent(this,FaceRecoService.class);
            intent.putExtra("value","1");
            startService(intent);
            if(!mm.isRunning()){
                mm.runPi("10","10");
            }
            _ttsaido.speak("ok! let me check");

        }
        if (speechtotext.toLowerCase().contains("how do i look")) {
            Intent intent = new Intent(this,FaceRecoService.class);
            intent.putExtra("value","3");
            startService(intent);
            if(!mm.isRunning()){
                mm.runPi("10","10");
            }
//            _ttsaido.speak("ok! let me check");


        }
        if (speechtotext.toLowerCase().contains("tell me your surroundings")) {
            Intent intent = new Intent(this,FaceRecoService.class);
            intent.putExtra("value","2");
            startService(intent);
            _ttsaido.speak("ok! let me check");


        }

        if (speechtotext.toLowerCase().contains("motivational video 1")) {
           // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.video1");
          //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("motivational video to")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.motivational2");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("motivational video 3")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.motivational3");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("motivational video for")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.motivational4");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }

        if (speechtotext.toLowerCase().contains("entertainment video 1")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.entertainment1");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("entertainment video to")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.entertainment2");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("entertainment video 3")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.entertainment3");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("entertainment video for")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.entertainment4");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }


        if (speechtotext.toLowerCase().contains("exercise video 1")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.exercise1");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("exercise video 2")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.exercise2");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("exercise video 3")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.exercise3");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }

        if (speechtotext.toLowerCase().contains("exercise video for"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.exercise4");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }


        if (speechtotext.toLowerCase().contains("video song 1")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.videosong1");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("video song to")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.videosong2");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);


        }
        if (speechtotext.toLowerCase().contains("video song 3")) {
            // String[] sp1 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.videosong3");
            //  intent1.putExtra("moti_video",sp1[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);

        }

        if (speechtotext.toLowerCase().contains("video song for"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.videosong4");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("menu screen 1"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.mainactivity");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("menu screen to"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.menu2");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("menu screen 3"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.Menu3");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("menu health screens"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.Healthrecord");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("open videos screens"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.videos");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("open photo screens"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.Photos");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }

//        if (speechtotext.toLowerCase().contains("drug details "))
//        {
//            String[] sp = speechtotext.split(" ");
//            Intent intent1 = new Intent("com.example.newmenu.DrugInfo");
//            intent1.putExtra("medicine_name",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//            this.startActivity(intent1);
//
//
//        }

        if (speechtotext.toLowerCase().contains("drug details fever"))
        {

            Intent intent1 = new Intent("com.example.newmenu.DrugInfo");
            intent1.putExtra("medicine_name","tylenol");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);


        }
        if (speechtotext.toLowerCase().contains("drug details antibiotic"))
        {

            Intent intent1 = new Intent("com.example.newmenu.DrugInfo1");
            intent1.putExtra("medicine_name","aleve");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);


        }
        if(speechtotext.toLowerCase().contains("end call")){
            _firedbreference.child("AidoRobot1").child("end").setValue("1");
        }
        if (speechtotext.toLowerCase().contains("call doctor"))
        {
            String[] sp = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.anil.alrammanagerexample.video");
            intent1.putExtra("username","komputer");
            intent1.putExtra("callername",sp[1]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);


        }
        if (speechtotext.toLowerCase().contains("call mathhew"))
        {
            String[] sp = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.anil.alrammanagerexample.video");
            intent1.putExtra("username","komputer");
            intent1.putExtra("callername",sp[1]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);


        }

        if (speechtotext.toLowerCase().contains("add reminder this"))
        {
           // String[] sp = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newreminder.AddReminder");
            System.out.println("hai"+speechtotext);
            intent1.putExtra("reminder",speechtotext);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
           // _firedbreference.child("Application").child("Child2").setValue("got error 8");

            this.startActivity(intent1);
        }

        if (speechtotext.toLowerCase().contains("add reminder next"))
        {

            Intent intent1 = new Intent("com.example.newreminder.AddReminderNext");
            intent1.putExtra("remindernext",speechtotext);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
          //  _firedbreference.child("Application").child("Child2").setValue("got error 8");

            this.startActivity(intent1);


        }

        if (speechtotext.toLowerCase().contains("show reminder this"))
        {
//
            Intent intent1 = new Intent("com.example.newreminder.ShowReminder");
            intent1.putExtra("show_reminder_this",speechtotext);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
         //  _firedbreference.child("Application").child("Child2").setValue("got error 8");

            this.startActivity(intent1);


        }

        if (speechtotext.toLowerCase().contains("show reminder next"))
        {
//
            Intent intent1 = new Intent("com.example.newreminder.ShowReminderNext");
            intent1.putExtra("show_reminder_next",speechtotext);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
           // _firedbreference.child("Application").child("Child2").setValue("got error 8");

            this.startActivity(intent1);


        }

        if (speechtotext.toLowerCase().contains("add pressure"))
        {
            Intent intent1 = new Intent("com.example.newreminder.AddPressure");
            intent1.putExtra("pressure",speechtotext);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent1);
        }

        if (speechtotext.toLowerCase().contains("show pressure"))
        {
            Intent intent1 = new Intent("com.example.newreminder.ShowPressure");
            intent1.putExtra("pressure",speechtotext);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("memory game 1"))
        {
            Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game1");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("game",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
            //this.startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("memory game 2"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.Memory_game2");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("game",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game2");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("game",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("memory game 3"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.Memory_game3");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("game",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game3");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("game",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("memory game 4"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.Memory_game4");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("game",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game4");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("game",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("memory game 5"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.Memory_game5");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("game",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game5");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("game",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

        if (speechtotext.toLowerCase().contains("answer is 1"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.answer");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("answer",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("answer",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("answer is 2"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.answer2");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("answer",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.answer2");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("answer",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("answer is 3"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.answer3");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("answer",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.answer3");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("answer",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("answer is 4"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.answer4");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("answer",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.answer4");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("answer",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("answer is 5"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.answer5");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("answer",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.answer5");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("answer",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("show me result"))
        {
//            Intent intent1 = new Intent("com.example.memorygame.answer5");
//            String[] sp=speechtotext.split(" ");
//            intent1.putExtra("answer",sp[2]);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent1);
            Intent intent1 = new Intent("com.whitesun.aidoface.result");
            String[] sp=speechtotext.split(" ");
            intent1.putExtra("result",sp[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

        if (speechtotext.toLowerCase().contains("my health plot"))
        {
            Intent intent1 = new Intent("com.whitesun.aidoface.HealthPlot");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("switch on"))
        {
            _ttsaido.speak("bedroom light on");
            Intent intent1 = new Intent("com.example.newreminder.LightOn");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        if (speechtotext.toLowerCase().contains("switch off"))
        {
            _ttsaido.speak("bedroom light off");
            Intent intent1 = new Intent("com.example.newreminder.LightOff");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }


        if (speechtotext.toLowerCase().contains("family photo 1"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.family1");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("family photo to"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.family2");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("family photo 3"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.family3");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("family photo for"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.family4");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }



        if (speechtotext.toLowerCase().contains("photo of friend 1"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.friend1");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("photo of friend to"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.friend2");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("photo of friend 3"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.friend3");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("photo of friend for"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.friend4");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("nostalgia photo 1"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.nostalgia1");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("nostalgia photo to"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.nostalgia2");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("nostalgia photo 3"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.nostalgia3");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("nostalgia photo for"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.nostalgia4");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }


        if (speechtotext.toLowerCase().contains("photo of grand kid 1"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.grandkid1");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("photo of grand kid to"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.grandkid2");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("photo of grand kid 3"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.grandkid3");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("photo of grand kid for"))
        {
            //String[] sp2 = speechtotext.split(" ");
            Intent intent1 = new Intent("com.example.newmenu.grandkid4");
            //intent1.putExtra("exe_video",sp2[2]);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent1);

        }
        if (speechtotext.toLowerCase().contains("look up"))
        {


            String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }

        if (speechtotext.toLowerCase().contains("look straight"))
        {


            String URLstring = "http://192.168.0.104/motor_reset.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }

        if (speechtotext.toLowerCase().contains("look up extreme"))
        {


            String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-40";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }

        if (speechtotext.toLowerCase().contains("turn left"))
        {


            String URLstring = "http://192.168.0.104/motor.php?pan=-30&tilt=0";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }
        if (speechtotext.toLowerCase().contains("turn left extreme"))
        {


            String URLstring = "http://192.168.0.104/motor.php?pan=-100&tilt=0";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }

        if (speechtotext.toLowerCase().contains("turn right"))
        {


            String URLstring = "http://192.168.0.104/motor.php?pan=30&tilt=0";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }

        if (speechtotext.toLowerCase().contains("turn right extreme"))
        {


            String URLstring = "http://192.168.0.104/motor.php?pan=100&tilt=0";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }

        if (speechtotext.toLowerCase().contains("radio station 1"))
        {

            Intent intent1 = new Intent("com.yusufcakmak.exoplayersample.RadioPlayerActivity");
            intent1.putExtra("flag","1");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);


        }

        if (speechtotext.toLowerCase().contains("radio station to"))
        {

            Intent intent1 = new Intent("com.yusufcakmak.exoplayersample.RadioPlayer2Activity");
            intent1.putExtra("flag","2");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);


            this.startActivity(intent1);


        }

        if (speechtotext.toLowerCase().contains("pause radio"))
        {

            Intent intent1 = new Intent("com.yusufcakmak.exoplayersample.MainActivity");
            //  intent1.putExtra("flag1","3");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);



        }

        if (speechtotext.toLowerCase().contains("pause radio to"))
        {

            Intent intent1 = new Intent("com.yusufcakmak.exoplayersample.MainActivity");
            // intent1.putExtra("flag","3");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags (intent.FLAG_ACTIVITY_SINGLE_TOP);

            this.startActivity(intent1);



        }
        final WindowManager.LayoutParams params = this.getWindow().getAttributes();

        if (speechtotext.toLowerCase().contains("turn on"))
        {


            params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.screenBrightness = -1f;
            getWindow().setAttributes(params);


            _ttsaido.speak("Powering on");
            String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            //adding the string request to request queue
            requestQueue.add(stringRequest);

            try {
                Thread.sleep(1000);



            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            URLstring = "http://192.168.0.104/motor_reset.php";
            stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            //adding the string request to request queue
            requestQueue.add(stringRequest);



        }

        if((_firedbreference.child("Application").child("Child2").getKey()).equals("1"))
        {
            System.out.println("turn on    ......");
        }

        if (speechtotext.toLowerCase().contains("turn off"))
        {

            params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.screenBrightness = 0;
            getWindow().setAttributes(params);

            _ttsaido.speak("Powering off");

            String URLstring = "http://192.168.0.104/motor.php?pan=0&tilt=-10";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            //adding the string request to request queue
            requestQueue.add(stringRequest);
            try {
                Thread.sleep(1000);



            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            URLstring = "http://192.168.0.104/motor_reset.php";
            stringRequest = new StringRequest(Request.Method.GET, URLstring,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("strrrrr", ">>" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            //adding the string request to request queue
            requestQueue.add(stringRequest);


        }

        if (speechtotext.toLowerCase().contains("open")) {
            String openapp = speechtotext.replace("open", "").replace(" ", "").toLowerCase();
            //_ttsaido.speak("Looks like you want to add to shopping list? Come on,  Show me the item!");

            if (openapp.contains("box")) {
                //Intent affective_intent = new Intent(this, AidoMain.class);
                //affective_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //this.startActivity(affective_intent);

                openBox();

                thereisspeech = true;
                _ttsaido.speak("Looks like you want to see app box. Here you go!");

                return thereisspeech;

            }

            if (openapp.contains("notification")) {
                //Intent affective_intent = new Intent(this, AidoMain.class);
                //affective_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //this.startActivity(affective_intent);

                thereisspeech = true;
                speakAllNotifications();

                return thereisspeech;

            }



            for (Map.Entry<String, String> entry : PermittedApps.PROP_APPNAMES.entrySet()) {

                if (entry.getValue().equalsIgnoreCase("na")) {
                    continue;
                }
                //System.out.println(entry.getKey() + "/" + entry.getValue());
                if (openapp.contains(entry.getValue().replace(" ", "").toLowerCase())) {
                    try {

                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(entry.getKey());
                        startActivity(LaunchIntent);
                        thereisspeech = true;
                        // _ttsaido.speak("Looks like you want to check " + entry.getValue() + ". Here you go!");
                        _ttsaido.speak(" ");


                    } catch (Exception ex) {
                        _ttsaido.speak("Sorry could not open " + entry.getValue() + ". Is it installed?");
                    }

                    return thereisspeech;
                }
            }

        }




        if(_ATM !=null && _ATM.isValid())
        {



            if(!CommonlyUsed.stringIsNullOrEmpty(behavior))
            {
                behavior = BehaveProperties.getRawBehaviorDir() + behavior;
                        matchpercent = _ATM.getMatchPercent();
                if(matchpercent < 5)
                {
                 //  _ttsaido.speak("Beg your pardon? ");//(" + matchpercent + ")");
                   Random random = new Random();
                   int randomnumber = random.nextInt(7);
                    Log.i("randomgenerator",String.valueOf(randomnumber));
                  String bg =  begYourPardonmap.get(randomnumber);
                    _ttsaido.speak(bg);
//                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),begYourPardonmap.get(randomnumber));
//                     mediaPlayer.start();
                   // _ttsaido.speak("I am Sorry? ");
//                    String intentdata = speechtotext.toLowerCase();
//                    Log.i("intentdataaaa",intentdata);
//                    Intent intentJioPardy = new Intent();
//                    intentJioPardy.setAction("JIPPARDYINTENT");
//                    intentJioPardy.putExtra("data",intentdata);
//                    sendBroadcast(intentJioPardy);
                    return true;
                }



                String append = "";

                if(!CommonlyUsed.stringIsNullOrEmpty(_ATM.getDesc()))
                {
                    append = "  " + _ATM.getDesc();
                }


                if(!CommonlyUsed.stringIsNullOrEmpty(matchedquestion) && matchedquestion.startsWith("openapp"))
                {
                   // _ttsaido.speak("I think you would like to " + append);
                    //_ttsaido.speak(" " + append);

                    for (Map.Entry<String, String> entry : PermittedApps.PROP_APPNAMES.entrySet())
                    {

                        if(entry.getValue().equalsIgnoreCase("na")){continue;}
                        if(behavior.toLowerCase().contains(entry.getValue().replace(" ","").toLowerCase()))
                        {
                            Log.i("STT","Trying to open...'" + entry.getKey() + "'");
                           /* Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(entry.getKey());
                            startActivity( LaunchIntent );
                            thereisspeech = true;*/



                            try {

                                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(entry.getKey());
                                startActivity( LaunchIntent );
                                thereisspeech = true;
                                //_ttsaido.speak("Looks like you want to check " + entry.getValue() + ". Here you go!");
                                _ttsaido.speak(" ");
                            }
                            catch (Exception ex)
                            {
                                _ttsaido.speak("Sorry could not open " + entry.getValue() + ". Is it installed?");
                            }

                            return thereisspeech;


                        }
                    }



                    return true;
                }

                //new change-openperticular activity using question field
                //

                if(!CommonlyUsed.stringIsNullOrEmpty(matchedquestion) && matchedquestion.startsWith("startactivity"))
                {
                    Log.i("startactivitybroadcast",message1);

                       // if(behavior.toLowerCase().contains())
                    Intent intentperticularactivity = new Intent();
                intentperticularactivity.setAction("perticularActivity");
                intentperticularactivity.putExtra("data",message1);
                sendBroadcast(intentperticularactivity);


                    return true;
                }




                if(matchpercent > 5 && matchpercent < 50)
                {

                    _ttsaido.speak("I am not sure if I understood." + append);
                    _ABE.directRun(behavior);
                    return true;
                }

                if(!CommonlyUsed.stringIsNullOrEmpty(append)) {
                    thereisspeech = true;
                   // _ttsaido.speak("I think you would like to " + append);
                    _ttsaido.speak(" " + append);
                }
                Log.i(TAG, "processSpeechToTextResults: "+behavior);
                _ABE.directRun(behavior);

            }

        }



        return thereisspeech;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GoogleVoiceSearch.GOOGLE_VOICE_INTENT) {
            String retstring = GoogleVoiceSearch.processIntentResult(requestCode, resultCode, data);

            CommonlyUsed.showMsg(getApplicationContext(),"GOT voice:" + retstring);
        }


        if(requestCode == INTENT_HEADPROJECTOR) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedVideoLocation = data.getData();

                Intent intent = new Intent(Intent.ACTION_VIEW, selectedVideoLocation);
                intent.setDataAndType( selectedVideoLocation, "video/*");

                startActivity(intent);


                //String filepath = CommonlyUsed.getRealPathFromURI(AidoFace.this,selectedVideoLocation)

                Log.i("VIDEO","selected head projector path : " + selectedVideoLocation.toString());

            }

        }



        if(requestCode == 1010) { //// SCENE RCOG PHOTO INTENT


            if(!fileReadWrite.fileExists(StorageProperties.getSceneDir() + "1.jpg"))
            {
                CommonlyUsed.showMsg(getApplicationContext(),"Capture failed!");
                return;
            }

            CommonlyUsed.showMsg(getApplicationContext(),"Captured scene successsfully!");



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


                final OutPanSearch OPS = new OutPanSearch(AidoFace.this);

                OPS.setOnTaskCompletedListener(new OutPanSearch.LoadingTaskFinishedListener() {
                    @Override
                    public void onTaskFinished(OutpanObject outpanobject) {
                        final OutpanObject outpanobject2 = OPS.getObject();


                        if(!CommonlyUsed.stringIsNullOrEmpty(outpanobject2.name.trim())) {
                            CommonlyUsed.showMsg(getApplicationContext(), "Product name = " + outpanobject2.name + ",url = " + outpanobject2.outpan_url);


                            _ttsaido.speak("Hey you have shown me,  " + outpanobject2.name + ". Adding it to List");


                            Intent intent_shoppinglist = new Intent(AidoFace.this, MainActivityForShoppingList.class);
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
                            SearchForRecipe SFR = new SearchForRecipe(AidoFace.this, outpanobject2.name.replace(" ",","))
                            {

                                @Override
                                protected void onReceivedRecipe(final String recipename, final String recipedetails, final String imageurl) {
                                    super.onReceivedRecipe(recipename, recipedetails, imageurl);


                                    if(!CommonlyUsed.stringIsNullOrEmpty(recipename.trim())) {
                                        _ttsaido.speak("You know what ? I have found an interesting recipe using called " + recipename + ". You need to use the following :  " + recipedetails
                                        );

                                        behave = new BehaviorUI(AidoFace.this, "shopping.json.txt", "reademail", timetostart, iv_anim, socket);

                                        //CommonlyUsed.showMsg(AidoFace.this, "Recipe : " + recipename + "\n details : " + recipedetails);

                                        asynchttp downloadrecipeimage = new asynchttp(AidoFace.this);
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
        LayoutInflater li = LayoutInflater.from(AidoFace.this);
        View promptsView = li.inflate(R.layout.alert_exit, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AidoFace.this);

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
                Toast.makeText(AidoFace.this, "Canceling..", Toast.LENGTH_SHORT);
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
                            //_dp = new DemoPlay(getApplicationContext(), PI_IP, _videoview, _videosurface, demofilename,_ttsaido);
                            //_dp.play();

                            _ABE.directRun(demofilename);

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

               /* if(_pauseSpeechRecognition == false) {
                    SetDelay sd = new SetDelay(500);
                    sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                        @Override
                        public void onDelayCompleted() {
                            //_latestSpeechToTextMessage = "";
                            if (!_pauseSpeechRecognition) {
                                startSpeechRecognition();
                            }
                        }
                    });
                }*/

               System.out.println("IDLE CALLED ANIL");


                Log.i("BEHAVE","Looping idle...");

                try
                {
                    if(_ABE != null)
                    {
                        if(!_ABE.isPlaying())
                        {

                            Log.e(TAG, "called "+_ABE );
                           // if(!_dp.getDemoFile().equalsIgnoreCase("idle.txt"))
                            if(_ABE.currentBehavior() != AlertBehaviorProperties.None && _ABE.currentBehavior() != AlertBehaviorProperties.idle)
                            {
                                sendCompletionBroadcast(AutomateProperties.BROADCAST_RUNBEHAVIOR,_ABE.getDemoFile());
                            }


                            //_ABE = new AlertBehaveEngine(getApplicationContext(),PI_IP,_videoview, _videosurface,_ttsaido);


                            //_dp = new DemoPlay(getApplicationContext(),PI_IP,_videoview, _videosurface,"idle.txt",_ttsaido);
                            //_dp.play();

                            _ABE.reset();

                            Log.i("BEHAVE","Playing looped...");

                            _ABE.registerNotification(AlertBehaviorProperties.idle);
                            System.out.println("Priority of idel:" +AlertBehaviorProperties.idle);
                            if(mm!=null)
                            {
                                if(!mm.isRunning())
                                {
                                    //mm.run("HOME", "HOME", "15");
                                    mm.runPi("0", "0");
                                }
                            }


                        }
                        else {
                            Log.i("BEHAVE","ABE is playing ...");

                        }
                    }
                    else
                    {
                        //_dp = new DemoPlay(getApplicationContext(),PI_IP,_videoview, _videosurface,"idle.txt",_ttsaido);
                        //_dp.play();


                        Log.i("BEHAVE","ABE is null...");

                        _ABE.reset();
                        Log.i(TAG, "Reset");

                        _ABE.registerNotification(AlertBehaviorProperties.idle);


                        // _playmovie.play(StorageProperties.getDemoMovieFile(_idlemovie));
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();

                }

                updateSensorValues();

                h.postDelayed(this, 15000);
            }
        };

        h.postDelayed(r, 15000);
    }



    public void updateSensorValues()
    {

        SensorsPI.updateSensorValues(AidoFace.this,PI_IP);

        AlertBehaviorProperties._lastTemperatureSensor = SensorsPI.SENSORVALUESHASH.get( SensorsPI.SENSOR_TEMPERATURE);
        AlertBehaviorProperties._lastPressure = SensorsPI.SENSORVALUESHASH.get(SensorsPI.SENSOR_PRESSURE);
        AlertBehaviorProperties._lastAirquality = SensorsPI.SENSORVALUESHASH.get(SensorsPI.SENSOR_AIRQUALITY);
        AlertBehaviorProperties._lastPowerRemaining = SensorsPI.SENSORVALUESHASH.get(SensorsPI.SENSOR_POWERREMAINING);

    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

        _videosurface = surfaceTexture;
        if(_ABE != null) {
            Log.i("SURFACE","surface is ASSIGNED !!!");

            _ABE.setSurface(surfaceTexture);
        }


        Log.i("SURFACE","surface is available again !!!");
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.i("SURFACE","surface size has changed   !!!");

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {


        Log.i("SURFACE","surface size has been destroyed   !!!");

        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        Log.i("SURFACE","surface size is now updated !!   !!!");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void enrollFace()
    {
        LayoutInflater li = LayoutInflater.from(AidoFace.this);
        View promptsView = li.inflate(R.layout.face_enroll, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AidoFace.this);

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

                    _firedbreference.child("AidoRobot2").child("face").child("training").child("name").setValue(userInput.getText().toString());
                    _firedbreference.child("AidoRobot2").child("face").child("training").child("train").setValue("1");


                    _ttsaido.speak("Enrollment has started. Please make random movements of face in front of the camera");
                    _intent_showcam = new Intent(AidoFace.this,ShowCamera.class);
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
                Toast.makeText(AidoFace.this, "Canceling..", Toast.LENGTH_SHORT);
                textview_error.setText(R.string.exit_dialog_cancel);

                alertDialog.dismiss();
            }
        });
        // show it
        alertDialog.show();
        // super.onBackPressed();



        userInput.performClick();
    }
    public static void setSnackBar(String s) {
        Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
    }



}

