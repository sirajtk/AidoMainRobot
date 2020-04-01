package aido.UI;

import android.content.Context;
import android.widget.ImageView;

import com.whitesuntech.aidohomerobot.R;

import aido.common.CommonlyUsed;
import aido.frameanimation.FrameAnimationCustom;
import aido.http.SocketConnect;
import aido.setdelay.SetDelay;

/**
 * Created by sumeendranath on 18/09/16.
 */
public class Behaviors {



    Context _maincontext;
    SocketConnect _socket;

    ImageView _iv_anim;
    ManageEyes _me;

    boolean complete = false;


    public boolean isComplete()
    {
        return complete;
    }


    public Behaviors(Context context,  ImageView iv_anim, SocketConnect socket) {

        _maincontext = context;
        _socket = socket;
        _iv_anim = iv_anim;

    }

    public void doConcern()
    {
        complete = false;
        CommonlyUsed.showMsg(_maincontext,"Starting");
        _me = new ManageEyes(_maincontext,_iv_anim,"concern")
        {
            @Override
            public void onActionComplete(String id) {
                super.onActionComplete(id);
                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                {
                    @Override
                    public void onActionComplete(String id) {
                        super.onActionComplete(id);

                        SetDelay sd = new SetDelay(500);
                        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                            @Override
                            public void onDelayCompleted() {
                                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                                {
                                    @Override
                                    public void onActionComplete(String id) {
                                        super.onActionComplete(id);
                                        onComplete();
                                        complete = true;

                                    }
                                };

                                _me.playAnimation(
                                        "frame_awareblink_",
                                        1,
                                        43,
                                        5
                                );
                            }
                        });

                    }
                };

                _me.playAnimation(
                        "frame_awareblink_",
                        1,
                        36,
                        5
                );

            }
        };

        _me.playAnimation(
                "frame_awareblink_",
                1,
                36,
                5
        );

    }

    public void doSurprise()
    {
        complete = false;
        CommonlyUsed.showMsg(_maincontext,"Starting");
        _me = new ManageEyes(_maincontext,_iv_anim,"concern")
        {
            @Override
            public void onActionComplete(String id) {
                super.onActionComplete(id);
                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                {
                    @Override
                    public void onActionComplete(String id) {
                        super.onActionComplete(id);
                        complete = true;
                        onComplete();
                    }
                };

                _me.playAnimation(
                        "frame_awareblink_",
                        1,
                        36,
                        5
                );

            }
        };

        _me.playAnimation(
                "frame_irisgrow_",
                1,
                11,
                20
        );

    }



    public void doDart()
    {
        complete = false;
        CommonlyUsed.showMsg(_maincontext,"Starting");
        _me = new ManageEyes(_maincontext,_iv_anim,"dart")
        {
            @Override
            public void onActionComplete(String id) {
                super.onActionComplete(id);
                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                {
                    @Override
                    public void onActionComplete(String id) {
                        super.onActionComplete(id);

                        // look straight

                        SetDelay sd = new SetDelay(200);
                        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                            @Override
                            public void onDelayCompleted() {
                                iv_anim.setBackgroundResource(R.drawable.frame_lookleft_00001);

                                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                                {
                                    @Override
                                    public void onActionComplete(String id) {
                                        super.onActionComplete(id);
                                        onComplete();
                                        complete = true;

                                    }
                                };

                                _me.playAnimation(
                                        "frame_awareblink_",
                                        1,
                                        43,
                                        5
                                );
                            }
                        });

                    }
                };

                _me.playAnimation(
                        "frame_lookleft_",
                        1,
                        32,
                        20
                );

            }
        };

        _me.playAnimation(
                "frame_lookright_",
                1,
                21,
                20
        );

    }

    public void doBored()
    {
        complete = false;
        CommonlyUsed.showMsg(_maincontext,"Starting");
        _me = new ManageEyes(_maincontext,_iv_anim,"concern")
        {
            @Override
            public void onActionComplete(String id) {
                super.onActionComplete(id);
                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                {
                    @Override
                    public void onActionComplete(String id) {
                        super.onActionComplete(id);

                        SetDelay sd = new SetDelay(500);
                        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                            @Override
                            public void onDelayCompleted() {
                                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                                {
                                    @Override
                                    public void onActionComplete(String id) {
                                        super.onActionComplete(id);
                                        _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                                        {
                                            @Override
                                            public void onActionComplete(String id) {
                                                super.onActionComplete(id);
                                                onComplete();
                                                complete = true;

                                            }
                                        };

                                        _me.playAnimation(
                                                "frame_wakeupfromsleep_",
                                                60,
                                                69,
                                                200
                                        );
                                    }
                                };

                                _me.playAnimation(
                                        "frame_awareblink_",
                                        1,
                                        25,
                                        150
                                );
                            }
                        });

                    }
                };

                _me.playAnimation(
                        "frame_wakeupfromsleep_",
                        60,
                        69,
                        200
                );

            }
        };

        _me.playAnimation(
                "frame_awareblink_",
                1,
                25,
                150
        );

    }


    public void doBoredToSleep()
    {
        complete = false;
        CommonlyUsed.showMsg(_maincontext,"Starting");
        _me = new ManageEyes(_maincontext,_iv_anim,"concern")
        {
            @Override
            public void onActionComplete(String id) {
                super.onActionComplete(id);
                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                {
                    @Override
                    public void onActionComplete(String id) {
                        super.onActionComplete(id);

                        SetDelay sd = new SetDelay(500);
                        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                            @Override
                            public void onDelayCompleted() {
                                _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                                {
                                    @Override
                                    public void onActionComplete(String id) {
                                        super.onActionComplete(id);
                                        _me = new ManageEyes(_maincontext,_iv_anim,"concern")
                                        {
                                            @Override
                                            public void onActionComplete(String id) {
                                                super.onActionComplete(id);
                                                onComplete();
                                                complete = true;

                                            }
                                        };



                                        _me.playAnimation(
                                                "frame_wakeupfromsleep_",
                                                60,
                                                69,
                                                200
                                        );
                                    }
                                };



                                _me.playAnimation(
                                        "frame_awareblink_",
                                        1,
                                        25,
                                        150
                                );
                            }
                        });

                    }
                };

                _me.playAnimation(
                        "frame_wakeupfromsleep_",
                        60,
                        69,
                        200
                );

            }
        };

        _me.playAnimation(
                "frame_awareblink_",
                1,
                25,
                150
        );

    }


    protected void onComplete()
    {

    }


}
