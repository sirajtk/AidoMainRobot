package aido.UI;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;


import com.whitesun.aidoface.AidoFace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import aido.common.CommonlyUsed;
import aido.file.fileReadWrite;
import aido.http.SocketConnect;
import aido.properties.StorageProperties;
import aido.setdelay.SetDelay;


/**
 * Created by sumeendranath on 07/09/16.
 */
public class BehaviorUI {
    JSONObject _jsonhandle;
    String _jsonstring = "";


    BehaveTime _behavetime;

    long _startdelay = 0;


    boolean loop = false;

    Context _maincontext;

    ImageView _iv_anim;

    boolean _interrupt = false;

    public boolean is_interrupt() {
        return _interrupt;
    }

    public void set_interrupt(boolean _interrupt) {
        this._interrupt = _interrupt;
    }

    public void interrupt() {
        this._interrupt = true;
    }
    public void resetInterrupt() {
        this._interrupt = false;
    }


    SocketConnect _socket;


    MediaPlayer mp;


    boolean complete = false;

    public BehaviorUI(Context context, String behavename, String modulename, long starttime, ImageView iv_anim, SocketConnect socket) {

        _maincontext = context;
        _startdelay =  0;//System.currentTimeMillis() - starttime;

        _socket = socket;

        _iv_anim = iv_anim;
        try {
            _jsonhandle  =  new JSONObject(fileReadWrite.readStringFromFile(StorageProperties.getBehaviorFile(behavename)));//new JsonHandle(fileReadWrite.readStringFromFile(StorageProperties.getBehaviorFile(behavename)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            _jsonhandle =  _jsonhandle.getJSONObject(modulename);
        } catch (JSONException e) {
            e.printStackTrace();
        }





        executeBehavior();
    }





    void playSound(String filename)
    {
        try
        {

            MediaPlayer mp;

            if(fileReadWrite.fileExists(StorageProperties.getImageDir() + filename))
            {
                mp = MediaPlayer.create(_maincontext, Uri.parse(StorageProperties.getImageDir() + filename));
            }
            else {
                mp = MediaPlayer.create(_maincontext, _maincontext.getResources().getIdentifier(filename,
                        "raw", _maincontext.getPackageName()));
            }

            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }

            });

        }
        catch(Exception ex)
        {

        }
    }

    int getGuidance()
    {
        int ret = 0;
        try {
            ret = _jsonhandle.getInt("guidance");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    JSONObject getAnticipateObject()
    {
        JSONObject ret = null;
        try {
            ret = _jsonhandle.getJSONObject("anticipate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    JSONObject getEaseInObject()
    {
        JSONObject ret = null;
        try {
            ret = _jsonhandle.getJSONObject("easein");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    JSONObject getKeyFrameObject()
    {
        JSONObject ret = null;
        try {
            ret = _jsonhandle.getJSONObject("keyframe");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    JSONObject getEaseOutObject()
    {
        JSONObject ret = null;
        try {
            ret = _jsonhandle.getJSONObject("easeout");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    JSONObject getFollowThorughsObject()
    {
        JSONObject ret = null;
        try {
            ret = _jsonhandle.getJSONObject("followthrough");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    int getPrinTime(JSONObject principleObject)
    {
        int ret = 0;
        try {
            ret = principleObject.getInt("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;

    }



    JSONArray getExecModule(JSONObject execModule)
    {
        JSONArray ret = null;
        try {
            ret = execModule.getJSONArray("exec");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;

    }

    void iterateExecModule(JSONArray execModule, long timetoexec, boolean loopenabled)
    {

        Log.i("TIME","timetoexec : " + timetoexec);

        long timeslice = timetoexec/execModule.length();
        Log.i("TIME","timeslice : " + timeslice);

        long execslitime = 0;
        for (int i = 0; i < execModule.length(); i++) {
            JSONObject row = null;
            try {
                row = execModule.getJSONObject(i);
                final String ui = row.getString("ui");
                final String move = row.getString("move");
                final String sound = row.getString("sound");
                long overridetime = timeslice;



                String idforui= "none";
                if(loopenabled && (i==execModule.length() -1 ))
                {
                    idforui="last";
                }
                final String id=idforui;

                if(row.has("time"))
                {
                    long time = row.getLong("time");
                    if(time > 0)
                    {
                        overridetime = timetoexec * time/100;
                    }
                }



                SetDelay sd = new SetDelay(execslitime);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {

                        displayUI(ui,id);

                        if(!CommonlyUsed.stringIsNullOrEmpty(move)) {
                            _socket.sendMessage(move);
                        }

                        if(!CommonlyUsed.stringIsNullOrEmpty(sound))
                        {
                            //playSound(sound);
                        }


                    }
                });

                execslitime +=overridetime;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    void displayUI(String ui, String id)
    {
        String message = ui;
        Log.i("TIME","executing : " + message);

        List<String> tokens = CommonlyUsed.splitToList(message,",");


        //  CommonlyUsed.showMsg(getApplicationContext(),"Playing anim : " + IMH.getTextValue());
        ManageEyes manageEyes = new ManageEyes(_maincontext,_iv_anim,id)
        {
            @Override
            public void onActionComplete(String id) {
                super.onActionComplete(id);
                if(id.equalsIgnoreCase("last"))
                {
                    if(!is_interrupt()) {

                        if (loop) {
                            CommonlyUsed.showMsg(_maincontext,"Looping!!");
                            executeKeyFrame();
                        }
                    }
                    else
                    {
                        CommonlyUsed.showMsg(_maincontext,"Received Interrupt!!");
                        loop=false;
                        executeEaseOutTime();

                    }
                }
            }
        };
        manageEyes.playAnimation(
                tokens.get(0),
                CommonlyUsed.getIntegerValueFromString(tokens.get(1)),
                CommonlyUsed.getIntegerValueFromString(tokens.get(2)),
                CommonlyUsed.getIntegerValueFromString(tokens.get(3))
        );
    }


    protected void displayMesssage(String message)
    {

    }



    void executeBehavior()
    {

        complete = false;
        int _guidance = getGuidance();

        Log.i("TIME","Guidance = " + _guidance);

        _behavetime = new BehaveTime(_guidance);

        //first execution

        SetDelay sd = new SetDelay(_startdelay);

        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {

                executeAnticipate();
            }
        });
    }


    public void executeModule(JSONObject module, long timetoexec,boolean loopenabled)
    {
        iterateExecModule(getExecModule(module),timetoexec,loopenabled);
    }


    void executeAnticipate()
    {
        JSONObject anticipate = getAnticipateObject();
        int overridetime = getPrinTime(anticipate);
        long anticipatetime = _behavetime.getAnticipateTime(overridetime);

        Log.i("TIME","anticipate time  = " + anticipatetime);

        executeModule(anticipate,anticipatetime,false);

        SetDelay sd = new SetDelay(anticipatetime);

        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {

                executeEaseIn();

            }
        });


    }


    void executeEaseIn()
    {
        JSONObject easein = getEaseInObject();
        int overridetime = getPrinTime(easein);
        long easeintime = _behavetime.getEaseInTime(overridetime);
        Log.i("TIME","EASEIN time  = " + easeintime);

        executeModule(easein,easeintime,false);

        SetDelay sd = new SetDelay(easeintime);

        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {

                executeKeyFrame();

            }
        });

    }

    void executeKeyFrame()
    {
        JSONObject keyframe = getKeyFrameObject();
        int overridetime = getPrinTime(keyframe);
        long keyframetime = _behavetime.getKeyStageTime(overridetime);
        Log.i("TIME","KEY time  = " + keyframetime);


        if(overridetime < 0)
        {
            loop = true;
            resetInterrupt();
        }
        executeModule(keyframe,keyframetime,loop);

        if(!loop) {
            SetDelay sd = new SetDelay(keyframetime);

            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {

                    executeEaseOutTime();

                }
            });
        }

    }

    void executeEaseOutTime()
    {
        JSONObject easeout = getEaseOutObject();
        int overridetime = getPrinTime(easeout);
        long easeouttime = _behavetime.getEaseOutTime(overridetime);
        executeModule(easeout,easeouttime,false);

        SetDelay sd = new SetDelay(easeouttime);

        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {

                executeFollowThrough();

            }
        });

    }
    void executeFollowThrough()
    {
        JSONObject followthrough = getFollowThorughsObject();
        int overridetime = getPrinTime(followthrough);
        long followthroughtime = _behavetime.getFollowThroughTime(overridetime);
        executeModule(followthrough,followthroughtime,false);

        SetDelay sd = new SetDelay(followthroughtime);

        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {



            }
        });


        complete = true;
    }


    public boolean isComplete()
    {
        return complete;
    }
}
