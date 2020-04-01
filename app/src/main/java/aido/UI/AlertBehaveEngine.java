package aido.UI;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;

import aido.TextToSpeech.BroadcastTTS;
import aido.properties.AlertBehaviorProperties;
import aido.properties.BehaveProperties;

/**
 * Created by sumeendranath on 15/02/17.
 */
public class AlertBehaveEngine {


    //public DemoPlay(Context context, String PI_IP, TextureView videoview, SurfaceTexture st, String demofile, BroadcastTTS ttsaido) {


    Context _context;
    String _PI_IP;
    TextureView _videoview;
    SurfaceTexture _st;
    String _demofile;
    BroadcastTTS _ttsaido;



    DemoPlay _dp;


    public AlertBehaveEngine(Context context, String PI_IP, TextureView videoview, SurfaceTexture st, BroadcastTTS ttsaido) {


        _context = context;
        _PI_IP = PI_IP;
        _videoview = videoview;
        _st = st;
        _ttsaido = ttsaido;
    }


    public void updateTTS(BroadcastTTS ttsaido)
    {
        _ttsaido = ttsaido;
    }

    public void reset()
    {
        if(_dp != null)
        {
            if(!_dp.isPlaying())
            {
                _dp.setPriority(-1);
                System.out.println("_dp priority set to -1");
            }
        }
        else
        {

        }
    }

    public void registerNotification(int alertbehaveid)
    {
        int priority = AlertBehaviorProperties.ALERTBEHAVIORPRIORITY.get(alertbehaveid);
        System.out.println("Priority anil" +priority);



        if(_dp != null)
        {
            Log.i("_dp is 1","not null");
            System.out.println("dppriority anil" +_dp.getPriority());

            if(priority >= _dp.getPriority() )
            {
                Log.i("_dp is","not null");
                Log.i("_dp is",String.valueOf(_dp.getPriority()));
               _dp.interrupt();

                _demofile = BehaveProperties.getRawBehaviorDir() +  AlertBehaviorProperties.ALERTBEHAVIORHASH.get(alertbehaveid);
                _dp = new DemoPlay(_context,_PI_IP,_videoview, _st,_demofile, _ttsaido, priority,alertbehaveid);
                _dp.play();
                Log.i("BEHAVE","Interrupted. Playing : " + _demofile);


            }
        }
        else
        {
            Log.i("_dp is","null");
            _demofile = BehaveProperties.getRawBehaviorDir() +  AlertBehaviorProperties.ALERTBEHAVIORHASH.get(alertbehaveid);
            _dp = new DemoPlay(_context,_PI_IP,_videoview, _st,_demofile, _ttsaido, priority,alertbehaveid);
            _dp.play();
            Log.i("BEHAVE","no prior behavior. Playing : " + _demofile);

        }


    }


    public void directRun(String behaviorfile)
    {
        int priority = 50;



        if(_dp != null)
        {
            if(priority >= _dp.getPriority() )
            {
                _dp.interrupt();
                //_demofile = AlertBehaviorProperties.ALERTBEHAVIORHASH.get(alertbehaveid);
                _demofile = behaviorfile;
                _dp = new DemoPlay(_context,_PI_IP,_videoview, _st,_demofile, _ttsaido, priority,AlertBehaviorProperties.directrun);
                _dp.play();

            }
        }
        else
        {
            _demofile = behaviorfile;
            _dp = new DemoPlay(_context,_PI_IP,_videoview, _st,_demofile, _ttsaido, priority,AlertBehaviorProperties.directrun);
            _dp.play();
        }


    }


    public void interrupt()
    {
        if(_dp != null) {
            _dp.interrupt();
        }
    }


    public boolean isPlaying()
    {
        if(_dp !=null)
        {
            return _dp.isPlaying();
        }

        return false;
    }


    public Integer currentBehavior()
    {
        if(_dp!=null)
        {
            if(_dp.isPlaying())
            {
                return _dp.getBehaveid();
            }
        }

        return AlertBehaviorProperties.None;
    }


    public String getDemoFile()
    {
        if(_dp!=null)
        {
            if(_dp.isPlaying())
            {
                return _dp.getDemoFile();
            }
        }

        return _demofile;
    }

    public void setSurface(SurfaceTexture surfaceTexture)
    {
        _st = surfaceTexture;
        if(_dp !=null)
        {
            _dp.setSurface(surfaceTexture);
        }
    }


}
