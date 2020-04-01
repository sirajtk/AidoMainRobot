package aido.UI;

import android.content.Context;
import android.widget.ImageView;

import com.whitesuntech.aidohomerobot.R;

import aido.frameanimation.FrameAnimationCustom;

/**
 * Created by sumeendranath on 24/06/16.
 */
public class ManageEyes {


    public static String MOVE_EYE_LEFT = "MOVE_LEFT";
    public static String MOVE_EYE_RIGHT = "MOVE_RIGHT";
    public static String UI = "UI";
    public static String SHOW_EYE = "SHOW_EYE";
    public static String BEHAVE= "BEHAVE";


    /////// AUTOMATE PATTERNS




    Context _maincontext;
    ImageView iv_anim;

    String _id;

    public ManageEyes(Context context, ImageView iv) {
        _maincontext = context;
        iv_anim = iv;

    }

    public ManageEyes(Context context, ImageView iv, String ID) {
        _maincontext = context;
        iv_anim = iv;

        _id = ID;
    }

    public void onActionComplete(String id)
    {

    }


    public void moveEyesToRight(String framename, int start, int end, int speed)
    {

        FrameAnimationCustom FAC = new FrameAnimationCustom(_maincontext,iv_anim);
        {
            FAC.playFrames("frame_lookright_", start, end, speed);

            FAC.setAnimationFinishListener(new FrameAnimationCustom.FrameAnimationFinishListener() {
                @Override
                public void onFrameAnimationFinished() {

                    onActionComplete(_id);

                }
            });


        }
    }

    public void playAnimation(String framename, int start, int end, int speed)
    {


        FrameAnimationCustom FAC = new FrameAnimationCustom(_maincontext,iv_anim);
        {
            FAC.playFrames(framename, start, end, speed);

            FAC.setAnimationFinishListener(new FrameAnimationCustom.FrameAnimationFinishListener() {
                @Override
                public void onFrameAnimationFinished() {

                    onActionComplete(_id);


                }
            });


        }
    }

    public void moveEyesToLeft(String framename, int start, int end, int speed)
    {


        FrameAnimationCustom FAC = new FrameAnimationCustom(_maincontext,iv_anim);
        {
            FAC.playFrames(framename, start, end, speed);

            FAC.setAnimationFinishListener(new FrameAnimationCustom.FrameAnimationFinishListener() {
                @Override
                public void onFrameAnimationFinished() {

                    onActionComplete(_id);


                }
            });


        }
    }
    public void moveEyesToLeft()
    {


        FrameAnimationCustom FAC = new FrameAnimationCustom(_maincontext,iv_anim);
        {
            FAC.playFrames("frame_lookleft_", 1, 13, 100);

            FAC.setAnimationFinishListener(new FrameAnimationCustom.FrameAnimationFinishListener() {
                @Override
                public void onFrameAnimationFinished() {

                    onActionComplete(_id);


                }
            });


        }
    }

    public void moveEyesToRight()
    {

        FrameAnimationCustom FAC = new FrameAnimationCustom(_maincontext,iv_anim);
        {
            FAC.playFrames("frame_lookright_", 1, 9, 50);

            FAC.setAnimationFinishListener(new FrameAnimationCustom.FrameAnimationFinishListener() {
                @Override
                public void onFrameAnimationFinished() {

                    onActionComplete(_id);

                }
            });


        }
    }

    public void blink()
    {

        FrameAnimationCustom FAC = new FrameAnimationCustom(_maincontext,iv_anim);
        {
            FAC.playFrames("frame_awareblink_", 1, 45, 50);

            FAC.setAnimationFinishListener(new FrameAnimationCustom.FrameAnimationFinishListener() {
                @Override
                public void onFrameAnimationFinished() {

                    onActionComplete(_id);

                }
            });


        }
    }

    /*
    public void justLookLeft()
    {
        iv_anim.setBackgroundResource(R.drawable.frame_lookleft_00013);

        onActionComplete(_id);
    }

    public void justLookRight()
    {
        iv_anim.setBackgroundResource(R.drawable.frame_lookright_00009);

        onActionComplete(_id);
    }

    */

    public void justLookStraight()
    {
        iv_anim.setBackgroundResource(R.drawable.frame_lookleft_00001);

        onActionComplete(_id);
    }
    /*public void halfawake()
    {
        iv_anim.setBackgroundResource(R.drawable.frame_wakeupfromsleepcustom);

        onActionComplete(_id);
    }*/
}
