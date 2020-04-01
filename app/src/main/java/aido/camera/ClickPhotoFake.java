package aido.camera;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import aido.setdelay.SetDelay;


public class ClickPhotoFake extends Activity {
    /** Called when the activity is first created. */
	
    private  CameraSurfaceViewFake mysurface;
    String sep = "_";
    int delay = 0;

    
	int _numPhotos = 0;

	
    boolean clickInProgress = false;
    
    boolean _frontcamera_preferred = true;

    Camera mycam_local;
    
    
    public static int MODE_NORMAL = 0;
    public static int MODE_BURST = 1;
    
    int _mode = MODE_NORMAL; 


	int PHOTO_LIMIT=20;

	int _photocount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




        RelativeLayout RL = new RelativeLayout(ClickPhotoFake.this);


        int x = getIntent().getIntExtra("x",0);
        int y = getIntent().getIntExtra("y",0);
        int radius = getIntent().getIntExtra("r",0);
        int time = getIntent().getIntExtra("t",0);
        String text =  getIntent().getStringExtra("s");

		mysurface = new CameraSurfaceViewFake(ClickPhotoFake.this, _frontcamera_preferred,50,50,50,3000);

     //   RL.addView(mysurface);


        final DrawView dv = new DrawView(ClickPhotoFake.this, Color.RED,x,y,radius);
        RL.addView(mysurface);

        RL.addView(dv);


        TextView myText = new TextView(ClickPhotoFake.this);

        myText.setText(text);

        myText.setTextSize(40f);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);




        myText.setX(500);
        myText.setY(200);


        RL.addView(myText);
       /* SetDelay sd = new SetDelay(10000);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
            dv.set(300,300,500);
                dv.invalidate();
            }
        });*/
        setContentView(RL);


        aido.common.CommonlyUsed.showMsg(getApplicationContext(),"SHowing cam for " + time + "secs");
        SetDelay sd = new SetDelay(time);
        sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                finish();
            }
        });
        
    }


}

