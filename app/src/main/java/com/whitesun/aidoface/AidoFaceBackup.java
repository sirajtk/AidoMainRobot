package com.whitesun.aidoface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whitesuntech.aidohomerobot.R;

import java.util.Map;

import aido.Affectiva.affdexme.FaceDataModel;
import aido.Affectiva.affdexme.MainActivityAffectiva;
import aido.OutPan.OutPanSearch;
import aido.OutPan.OutpanObject;
import aido.ROS.RosFragment;
import aido.Recipe.RecipeDialog;
import aido.Recipe.SearchForRecipe;
import aido.Scandit.ScanditScanning;
import aido.TextToSpeech.BroadcastTTS;
import aido.UI.ManageEyes;
import aido.common.CommonlyUsed;
import aido.emotion.EmotionHandling;
import aido.file.fileReadWrite;
import aido.googlevoice.GoogleVoiceSearch;
import aido.googlevoice.SpeechRecognize;
import aido.http.asynchttp;
import aido.kiosk.PrefUtils;
import aido.polling.HttpDownloadHandlerPolling;
import aido.polling.IncomingMessageHandler;
import aido.properties.HttpProperties;
import aido.properties.PermittedApps;
import aido.properties.StorageProperties;
import aido.setdelay.SetDelay;
import aido.shoppinglist.MainActivityForShoppingList;

public class AidoFaceBackup extends AppCompatActivity implements RosFragment.OnFragmentInteractionListener {


    int numberofmoves = 1;
    ImageView iv_anim;

    FloatingActionButton fab;

    static int INTENT_SCANDIT = 1002;
    static int INTENT_SHOPPINGLIST = 1003;


    TextView tv_httpmsg;

    //TextToSpeechFuncs _ttsaido;
    BroadcastTTS _ttsaido;

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
                Snackbar.make(view, "Open Dashoard", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //new GoogleVoiceSearch(AidoFace.this);


                               // doScandit();


                                openBox();


                                //doAffectiva();
                            }
                        }).show();
            }
        });

        fab.setImageResource(R.mipmap.mic_on);

        iv_anim = findViewById(R.id.imageView_animate);



        startTTSActivity();
        _ttsaido = new BroadcastTTS(AidoFaceBackup.this);


        /*_ttsaido = new TextToSpeechFuncs(AidoFace.this)
        {
            @Override
            protected void onStart() {
                if(_speechToText != null)
                {
                    _speechToText.close();
                }



                CommonlyUsed.unmuteAudioOutput(AidoFace.this);

                super.onStart();



            }


            @Override
            protected void onComplete() {
                super.onComplete();
                SetDelay sd = new SetDelay(1000);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {

                        if(!_ttsaido.getTTSspeakingOrNot()) {
                            doSpeechRecognition();
                        }


                    }
                });

            }
        };*/



        //doFaceDetection();



        new ManageEyes(AidoFaceBackup.this,iv_anim).justLookStraight();


        doSpeechRecognition();


        IntentFilter filter = new IntentFilter();
        filter.addAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
        registerReceiver(receiver, filter);


        //_handlerpoll = new HttpDownloadHandlerPolling(AidoFace.this,HttpProperties.PROP_POLLFREQ_DOWNLOAD);
        //_handlerpoll.startRepeatingTask();

        //startRosActivity();




        CommonlyUsed.unmuteAudioOutput(AidoFaceBackup.this);



       // SetDelay sd  = new SetDelay(10000);
       // sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
       //     @Override
       //     public void onDelayCompleted() {
       //         _ttsaido.speak("Hi this is testing Aido Speech. Lets see if this works!");
       //     }
       // });

    }



    void openBox()
    {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.whitesuntech.aidobox");
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
            //_ttsaido.shutdown();
        }
        catch(Exception ex)
        {

        }

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
            CommonlyUsed.unmuteAudioOutput(AidoFaceBackup.this);
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
            stopService(_intent_ROS);
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
        finish();
    }


    private RosBroadcastReceiver receiver = new RosBroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equalsIgnoreCase(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID)) {

                String message = intent.getStringExtra(HttpProperties.HTTPDOWNLOAD_MESSSAGEFIELD);


                CommonlyUsed.showMsg(getApplicationContext(),"Got Msg : " + message);

                final IncomingMessageHandler IMH = new IncomingMessageHandler(message);

                if(IMH.getTask().equalsIgnoreCase(ManageEyes.MOVE_EYE_LEFT))
                {
                    ManageEyes manageEyes = new ManageEyes(AidoFaceBackup.this,iv_anim)
                    {
                        @Override
                        public void onActionComplete(String id) {
                            super.onActionComplete(id);

                            IMH.setTaskAsComplete(AidoFaceBackup.this);
                        }
                    };
                    manageEyes.moveEyesToLeft();
                }


                if(IMH.getTask().equalsIgnoreCase(ManageEyes.MOVE_EYE_RIGHT))
                {
                    ManageEyes manageEyes = new ManageEyes(AidoFaceBackup.this,iv_anim)
                    {
                        @Override
                        public void onActionComplete(String id) {
                            super.onActionComplete(id);

                            IMH.setTaskAsComplete(AidoFaceBackup.this);

                        }
                    };
                    manageEyes.moveEyesToRight();
                }



                if(IMH.getTask().equalsIgnoreCase(HttpProperties.HTTP_SPEECH_TO_TEXT_TAG))
                {
                    IMH.postValueToServer(AidoFaceBackup.this,_latestSpeechToTextMessage);
                }

                if(IMH.getTask().equalsIgnoreCase(HttpProperties.HTTP_TEXT_TO_SPEECH_TAG))
                {
                    String messageforspeech = IMH.getTextValue();
                    _ttsaido.speak(messageforspeech);
                }
                if(IMH.getTask().equalsIgnoreCase(HttpProperties.HTTP_EMOTION_DETECT_TAG))
                {

                    IMH.postValueToServer(AidoFaceBackup.this,_previousFace.getDominantEmotion_irrespectiveofscore() + "," + _previousFace.getDominantScore() );
                }
            }

        }
    };


    void startRosActivity()
    {





        _intent_ROS= new Intent();
        _intent_ROS.setComponent(new ComponentName("org.ollide.rosandroid", "org.ollide.rosandroid.RosBackgroundService"));
        ComponentName c = startService(_intent_ROS);



    }


    void startTTSActivity()
    {

        _intent_TTS= new Intent();
        _intent_TTS.setComponent(new ComponentName("com.whitesuntech.texttospeech", "com.whitesuntech.texttospeech.TSService"));
        ComponentName c = startService(_intent_TTS);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    void doFaceDetection()
    {


        FragmentManager fm = getSupportFragmentManager();

        MainActivityAffectiva affectivaActivity = new MainActivityAffectiva();

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
                                _ttsaido.speak("Hey Why Did U stop smiling?");
                                noneofthese = false;
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

    void doSpeechRecognition()
    {

         CommonlyUsed.muteAudioOutput(AidoFaceBackup.this);


        count++;
      //  CommonlyUsed.muteAudioOutput(AidoFace.this);

        fab.setImageResource(R.mipmap.mic_on);

        _speechToText = new SpeechRecognize(AidoFaceBackup.this)
        {
            @Override
            public void onEndOfSpeechCompleted() {
                super.onEndOfSpeechCompleted();
                fab.setImageResource(R.mipmap.mic_off);
            }

            @Override
            public void onReadyToListenCompleted() {
                super.onReadyToListenCompleted();
                fab.setImageResource(R.mipmap.mic_on);
            }

            /*@Override
            public void onReadyToListen() {
                super.onReadyToListen();
                fab.setBackgroundResource(R.mipmap.mic_on);

            }

            @Override
            public void onReadyToListen() {
                fab.setBackgroundResource(R.mipmap.mic_off);

            }*/

            @Override
            public void onComplete(final String result) {
                super.onComplete(result);





                if(!result.toLowerCase().contains("got error")) {
                    CommonlyUsed.showMsg(getApplicationContext(), "GOT= " + result);
                }

                SetDelay sd = new SetDelay(1000);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        CommonlyUsed.unmuteAudioOutput(AidoFaceBackup.this);
                        fab.setImageResource(R.mipmap.mic_off);

                        if(!processSpeechToTextResults(result))
                        {
                            //if(!_ttsaido.getTTSspeakingOrNot()) {
                                doSpeechRecognition();
                            //}
                        }
                        else
                        {
                            doSpeechRecognition();
                        }

                    }
                });


               /* if(count < 20) {
                    SetDelay sd = new SetDelay(5000);
                    sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                        @Override
                        public void onDelayCompleted() {
                            doSpeechRecognition();
                        }
                    });
                }*/

                /*
                                String query = result;
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
                intent.putExtra("query", query);
                startActivityForResult(intent,100);


                 */
            }

        };
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
            _ttsaido.speak("Looks like you want to add to shopping list? Come on,  Show me the item!");

            doScandit();
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GoogleVoiceSearch.GOOGLE_VOICE_INTENT) {
            String retstring = GoogleVoiceSearch.processIntentResult(requestCode, resultCode, data);

            CommonlyUsed.showMsg(getApplicationContext(),"GOT:" + retstring);
        }

        if(requestCode == INTENT_SCANDIT)
        {
            if(resultCode == Activity.RESULT_OK) {
                String scannedvalue = data.getStringExtra(ScanditScanning.SCANDIT_SCANVALUE);
                CommonlyUsed.showMsg(getApplicationContext(), "Scanned value = '" + scannedvalue + "'");


                final OutPanSearch OPS = new OutPanSearch(AidoFaceBackup.this);

                OPS.setOnTaskCompletedListener(new OutPanSearch.LoadingTaskFinishedListener() {
                    @Override
                    public void onTaskFinished(OutpanObject outpanobject) {
                        final OutpanObject outpanobject2 = OPS.getObject();


                        if(!CommonlyUsed.stringIsNullOrEmpty(outpanobject2.name.trim())) {
                            CommonlyUsed.showMsg(getApplicationContext(), "Product name = " + outpanobject2.name + ",url = " + outpanobject2.outpan_url);


                            _ttsaido.speak("Hey you have shown me,  " + outpanobject2.name + ". Adding it to Shopping List");


                            Intent intent_shoppinglist = new Intent(AidoFaceBackup.this, MainActivityForShoppingList.class);
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

                            SearchForRecipe SFR = new SearchForRecipe(AidoFaceBackup.this, outpanobject2.name.replace(" ",","))
                            {

                                @Override
                                protected void onReceivedRecipe(final String recipename, final String recipedetails, final String imageurl) {
                                    super.onReceivedRecipe(recipename, recipedetails, imageurl);


                                    if(!CommonlyUsed.stringIsNullOrEmpty(recipename.trim())) {
                                        _ttsaido.speak("You know what ? I have found an interesting recipe using called " + recipename + ". You need to use the following :  " + recipedetails
                                        );

                                        //CommonlyUsed.showMsg(AidoFace.this, "Recipe : " + recipename + "\n details : " + recipedetails);

                                        asynchttp downloadrecipeimage = new asynchttp(AidoFaceBackup.this);
                                        downloadrecipeimage.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
                                            @Override
                                            public void onDownloadIsCompleted(String downloadstring) {
                                                FragmentManager fm = getSupportFragmentManager();

                                                RecipeDialog recipedialog = RecipeDialog.newInstance(recipename,recipedetails,StorageProperties.getRecipeImage());
                                                recipedialog.show(fm, "Dialog Fragment");

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
                            _ttsaido.speak("Sorry dude, I don't know this product. Try something else");

                        }



                    }
                });

                OPS.execute(scannedvalue);

            }
            else
            {
                CommonlyUsed.showMsg(getApplicationContext(), "Unable to read barcode");
            }

        }

    }



    void performExitCheck()
    {
        LayoutInflater li = LayoutInflater.from(AidoFaceBackup.this);
        View promptsView = li.inflate(R.layout.alert_exit, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AidoFaceBackup.this);

        // set alert_exit.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final TextView textview_error = promptsView
                .findViewById(R.id.dialog_error);
        textview_error.setText("");



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
                Toast.makeText(AidoFaceBackup.this, "Canceling..", Toast.LENGTH_SHORT);
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

