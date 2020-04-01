package aido.UI;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

/**
 * Created by sumeendranath on 18/09/16.
 */
public class PlayMovie {


    TextureView _tv;
    SurfaceTexture _surface;
    MediaPlayer mediaPlayer;


    public PlayMovie(TextureView tv) {
        _tv = tv;
    }


    public void setSurface(SurfaceTexture s)
    {
        _surface = s;
    }

    boolean isplaying = false;

    public void play(String moviefile)
    {

         mediaPlayer = new MediaPlayer();
        mediaPlayer.setSurface(new Surface(_surface));
        try {
            isplaying = true;
            mediaPlayer.setDataSource(moviefile);
           mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //socket.close();
                    if(mediaPlayer != null) {
                        mediaPlayer.release();
                        //                onComplete();
                        isplaying = false;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public boolean isplaying()
    {
        return isplaying;
    }


    protected  void onComplete()
    {

    }


    public void interrupt()
    {
        mediaPlayer.reset();
        mediaPlayer.release();

    }


}
