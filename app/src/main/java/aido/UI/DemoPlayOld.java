package aido.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.TextureView;

import java.net.Socket;
import java.util.List;

import aido.TextToSpeech.BroadcastTTS;
import aido.camera.ClickPhoto;
import aido.common.CommonlyUsed;
import aido.file.fileReadWrite;
import aido.http.MotorMessage;
import aido.http.SocketConnect;
import aido.properties.StorageProperties;
import aido.setdelay.SetDelay;

/**
 * Created by sumeendranath on 23/09/16.
 */
public class DemoPlayOld {

    public static int DEMO_TIME         = 0;
    public static int DEMO_MOV          = 1;
    public static int DEMO_MOTPAN       = 2;
    public static int DEMO_MOTTILT      = 3;
    public static int DEMO_MP3          = 4;
    public static int DEMO_SPEED        = 5;


    public static int CAM_X          = 2;
    public static int CAM_Y          = 3;
    public static int CAM_R          = 4;
    public static int CAM_T          = 5;

    /*public static int DEMO_           = ;
    public static int DEMO_             = ;
    public static int DEMO_             = ;
    public static int DEMO_             = ;
    public static int DEMO_             = ;*/


    public static String MOV_LOOKLEFT = "lookleft.m4v";
    public static String MOV_LOOKRIGHT = "lookright.m4v";
    public static String MOV_LOOKLEFTMID = "blinkleftmid.mp4";
    public static String MOV_JUSTLOOKLEFT = "justlookleft.mp4";
    public static String MOV_LOOKRIGHTMID = "blinkrightmid.mp4";
    public static String MOV_JUSTLOOKRIGHT = "justlookright.mp4";
    public static String MOV_BLINK = "blink.m4v";


    String _demofile = StorageProperties.getDemoFile();

    List<String> _demolines;


    int _lineplaying = -1;

    //SocketConnect _socket;

    SocketConnect _motor;

    PlayMovie _playmovie;


    Context _maincontext;

    MediaPlayer mp;

    boolean interrupted = false;


    boolean isplaying = false;

    BroadcastTTS _ttsaido;


    //    public DemoPlay(Context context, SocketConnect socket, TextureView videoview, SurfaceTexture st, String demofile,BroadcastTTS ttsaido) {

    public DemoPlayOld(Context context, SocketConnect PI_IP, TextureView videoview, SurfaceTexture st, String demofile, BroadcastTTS ttsaido) {


        _ttsaido = ttsaido;

       // _demofile = demofile;//StorageProperties.getDemoFile(demofile);
        _demolines = fileReadWrite.readFileLinesIntoList(_demofile);

        _maincontext = context;
        _lineplaying = -1;
        //_socket = socket;



        _playmovie = new PlayMovie(videoview)
        {
            @Override
            protected void onComplete() {
                super.onComplete();


            }
        };

        _playmovie.setSurface(st);

    }


    public String getDemoFile()
    {
        return _demofile;
    }

    public void play()
    {
        if(interrupted)
        {
            return;
        }
        _lineplaying = -1;

        playNext();
    }

    int time = 0;

    void playNext()
    {
        if(interrupted)
        {
            isplaying = false;
            return;
        }
        _lineplaying++;

        if(_lineplaying >= _demolines.size())
        {

            isplaying = false;
            return;
        }

        String _line = _demolines.get(_lineplaying);



        if(CommonlyUsed.stringIsNullOrEmpty(_line) || _line.trim().startsWith("#"))
        {
            //CommonlyUsed.showMsg(_maincontext,"playing " + _line);
            //CommonlyUsed.showMsg(_maincontext,"playing " + _lineplaying);
            playNext();
            return;
        }

        List<String> tokens = CommonlyUsed.splitToList(_line,",");




        time = CommonlyUsed.getIntegerValueFromString(tokens.get(DEMO_TIME));
       // CommonlyUsed.showMsg(_maincontext,"time " + time);

        String movname = tokens.get(DEMO_MOV);
        String panval = tokens.get(DEMO_MOTPAN);
        String tiltval = tokens.get(DEMO_MOTTILT);
        String mp3 = tokens.get(DEMO_MP3);


        String speed = "0";

        if(tokens.size() > DEMO_SPEED)
        {
            speed = tokens.get(DEMO_SPEED);
        }



        if(!movname.equalsIgnoreCase("0"))
        {
            if(movname.equalsIgnoreCase("cam"))
            {
                String x = tokens.get(CAM_X);
                String y = tokens.get(CAM_Y);
                String r = tokens.get(CAM_R);
                String text = tokens.get(CAM_T);

                showcam(
                        CommonlyUsed.getIntegerValueFromString(x),
                        CommonlyUsed.getIntegerValueFromString(y),
                        CommonlyUsed.getIntegerValueFromString(r),
                        text,
                        time

                        );


                time= 1;

                //playNext();
                //return;

            }
            else
            {
                if(movname.equalsIgnoreCase("traffic"))
                {
                    double destinationLatitude = 37.3382;
                    double destinationLongitude = 121.8863;

                    String url = "http://maps.google.com/maps?f=d&daddr="+ destinationLatitude+","+destinationLongitude+"&dirflg=d&layer=t";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _maincontext.startActivity(intent);

                }
                else {
                    if(interrupted)
                    {
                        isplaying = false;
                        return;
                    }
                    isplaying = true;
                    _playmovie.play(StorageProperties.getDemoMovieFile(movname));

                    //if(!panval.equalsIgnoreCase("0") && !tiltval.equalsIgnoreCase("0"))
                    {
                        //_socket.sendMessage(panval + "," + tiltval + "," + speed);
                    }


                    if (!mp3.equalsIgnoreCase("0")) {
                        if(interrupted)
                        {
                            isplaying = false;
                            return;
                        }
                        playSound(mp3);
                    }
                }
            }
        }
        else
        {
            //if(!panval.equalsIgnoreCase("0") && !tiltval.equalsIgnoreCase("0"))
            {
                //_socket.sendMessage(panval + "," + tiltval + "," +  speed);

            }


            if(!mp3.equalsIgnoreCase("0"))
            {
                playSound(mp3);
            }
        }




        SetDelay sd = new SetDelay(time * 1000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                CommonlyUsed.showMsg(_maincontext,"playing time=" + time + " line=" + _lineplaying);
                if(interrupted)
                {
                    isplaying = false;
                    return;
                }
                playNext();
            }
        });
    }

    void showcam(int x, int y, int r,String text,  int time)
    {

        if(interrupted)
        {
            isplaying = false;
            return;
        }

        isplaying = true;
        Intent camerashow = new Intent(_maincontext, ClickPhoto.class);
        camerashow.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        camerashow.putExtra("x", x);
        camerashow.putExtra("y", y);
        camerashow.putExtra("r", r);
        camerashow.putExtra("t", time * 1000);
        camerashow.putExtra("s", text);



        //camerashow.putExtra(MainActivityForShoppingList.BUNDLE_FROM_AIDO_UNIT, "Each");
        _maincontext.startActivity(camerashow);

    }

    public void setSurface(SurfaceTexture s)
    {
        _playmovie.setSurface(s);
    }


   public  void interrupt()
    {

        interrupted = true;
        try {
            mp.stop();
            mp.release();
            isplaying = false;
        }
        catch (Exception ex)
        {

        }
    }


    public boolean isPlaying()
    {
        if(_playmovie.isplaying())
        {
            return true;
        }

        return isplaying;
    }

    void playSound(String filename)
    {

        if(!fileReadWrite.fileExists(StorageProperties.getDemoMovieFile(filename)))
        {
            _ttsaido.speak(filename);
            return;
        }


        if(interrupted)
        {
            isplaying = false;
            return;
        }
        try
        {


            if(fileReadWrite.fileExists(StorageProperties.getDemoMovieFile(filename)))
            {
                mp = MediaPlayer.create(_maincontext, Uri.parse(StorageProperties.getDemoMovieFile(filename)));
            }
            else {
                mp = MediaPlayer.create(_maincontext, _maincontext.getResources().getIdentifier(filename,
                        "raw", _maincontext.getPackageName()));
            }

            isplaying = true;

            mp.start();

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    isplaying = false;
                }

            });

        }
        catch(Exception ex)
        {

        }
    }

}
