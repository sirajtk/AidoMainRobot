package aido.frameanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.whitesuntech.aidohomerobot.R;

/**
 * Created by sumeendranath on 31/05/16.
 */
public class FrameAnimationCustom {


    AnimationDrawableCustom _animation;
    ImageView _imageView;

    ResourceManager _resourcemanager;
    Context _maincontext;

    String lastframe = "";

    public interface FrameAnimationFinishListener
    {
        void onFrameAnimationFinished();
    }


    private FrameAnimationFinishListener frameAnimationFinishListener;

    public FrameAnimationFinishListener getAnimationFinishListener()
    {
        return frameAnimationFinishListener;
    }

    public void setAnimationFinishListener(FrameAnimationFinishListener animationFinishListener)
    {
        this.frameAnimationFinishListener = animationFinishListener;
    }


    public FrameAnimationCustom(Context context, ImageView imageview) {

        _maincontext = context;

        _imageView = imageview;


         _animation = new AnimationDrawableCustom();


        _resourcemanager = new ResourceManager(context);

    }


    public void playFrames(String frametype, int from, int to, int framespeed)
    {
        _animation = new AnimationDrawableCustom();
        _animation.setOneShot(true);


        while (from <= to) {
            String name = frametype + String.format("%05d", from);



            _animation.addFrame(_resourcemanager.getBitmapDrawable(name), framespeed);
            from++;

            lastframe = name;

            //Toast.makeText(_maincontext,name, Toast.LENGTH_SHORT).show();
        }

        _imageView.setBackgroundDrawable(_animation);
       // _animation.start();

        _imageView.post(new Runnable(){
            @Override
            public void run() {
                _animation.start();
            }
        });


        _animation.setAnimationFinishListener(new AnimationDrawableCustom.IAnimationFinishListener() {
            @Override
            public void onAnimationFinished() {

                //_imageView.setBackgroundResource(R.drawable.frame_lookleft_00001);
                //_imageView.setBackgroundResource(0);

                for (int i = 0; i < _animation.getNumberOfFrames()  ; ++i) {
                    Drawable frame = _animation.getFrame(i);

                    if (frame instanceof BitmapDrawable) {
                        BitmapDrawable castedFrame = (BitmapDrawable) frame;

                        if (frame != null) {
                            Bitmap bitmap = castedFrame.getBitmap();
                            bitmap.recycle();
                            frame.setCallback(null);
                        }
                    }
                }

                _animation = null;

                _imageView.setBackground(_resourcemanager.getBitmapDrawable(lastframe));


                frameAnimationFinishListener.onFrameAnimationFinished();

              //  Toast.makeText(_maincontext,"Completed Animation",Toast.LENGTH_SHORT).show();
            }
        });
    }




}
