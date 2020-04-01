package my.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;


import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.whitesuntech.facerecognitionpics.R;

import properties.StorageProperties;


public class ClickPhoto extends Activity {
    /** Called when the activity is first created. */
	
    private  CameraSurfaceView mysurface;
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
        
        //setRequestedOrientation(1);
        
        _frontcamera_preferred = true;


    	fileReadWrite.removeDirectoryComplete(StorageProperties.getFaceRecGalleryPath());

		_mode = MODE_BURST;
		pushintogallery();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        

        
    }


	public void clickOnePhoto()
	{
		mycam_local = mysurface.getCamera();

		try
		{
			MediaPlayer mp = MediaPlayer.create(ClickPhoto.this, R.raw.camera);
			mp.start();
			mp.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}

			});

		}
		catch(Exception ex)
		{

		}

		Camera.Parameters parameters = mycam_local.getParameters();

		 mycam_local.takePicture(null, null,new HandlePictureClassBack1());
		/*if(parameters.getSupportedFocusModes().size() > 0)
		{

			mycam_local.autoFocus(new AutoFocusCallback() {

				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					// TODO Auto-generated method stub
					if(success){


						mycam_local.takePicture(null, null,new HandlePictureClassBack1());
					}
					else
					{
						mycam_local.takePicture(null, null,new HandlePictureClassBack1());
					}
				}
			});
		}
		else
		{
			mycam_local.takePicture(null, null,new HandlePictureClassBack1());

		}*/

	}





	public void clickAllPhotos()
	{
		clickOnePhoto();
		if(_photocount < PHOTO_LIMIT)
		{
			SetDelay sd = new SetDelay(2000);
			sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
				@Override
				public void onDelayCompleted() {
					clickAllPhotos();
				}
			});
		}
		else
		{
			List<String> allphotos = fileReadWrite.getAllFilesWithin(StorageProperties.getFaceRecGalleryPath());
			zipUtils.zip(allphotos,StorageProperties.facereczipfile());
			Runtime rt = Runtime.getRuntime();
			Process proc;
			try {
				proc = rt.exec(new String[] { "su", "-c", "chmod 777 " + StorageProperties.facereczipfile() });
				proc.waitFor();
			} catch (Exception e) { //DOSTUFFS
			}

				SetDelay sd = new SetDelay(5000);
				sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
					@Override
					public void onDelayCompleted() {
						finish();
					}
				});

		}
	}

    public void pushintogallery() {
		// TODO Auto-generated method stub
    	mysurface = new CameraSurfaceView(ClickPhoto.this, _frontcamera_preferred);



        setContentView(mysurface);


		SetDelay sd  = new SetDelay(3000);
		sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
			@Override
			public void onDelayCompleted() {
				clickAllPhotos();

			}
		});

	}




    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger 
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }

    

    
    @Override 
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
		//Toast.makeText(this,"somethng changed", Toast.LENGTH_SHORT).show();

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == 2)
        {
    		Toast.makeText(this,"landscape mode", Toast.LENGTH_SHORT).show();
        }
        if(orientation == 1)
        {
    		Toast.makeText(this,"portrait mode", Toast.LENGTH_SHORT).show();
        }
       
    }


	public Bitmap toGrayscale(Bitmap bmpOriginal)
	{
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	public static Bitmap cropToSquare(Bitmap bitmap){
		int width  = bitmap.getWidth();
		int height = bitmap.getHeight();
		int newWidth = (height > width) ? width : height;
		int newHeight = (height > width)? height - ( height - width) : height;
		int cropW = (width - height) / 2;
		cropW = (cropW < 0)? 0: cropW;
		int cropH = (height - width) / 2;
		cropH = (cropH < 0)? 0: cropH;
		Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

		return cropImg;
	}

    private Bitmap decodeFile(File f){
    	
    	int IMAGE_MAX_SIZE = 100;
        Bitmap b = null;
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (IOException e) {
        }
        return b;
    }
    

    
    class HandlePictureClassBack1 implements PictureCallback{

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			try
			{
				_photocount++;

				String nameofgalleryphoto = "" + _photocount + ".jpg";
				RandomAccessFile raf = new RandomAccessFile(StorageProperties.getFaceRecGalleryPath() + nameofgalleryphoto, "rw");
    			raf.write(data, 0, data.length);
    			raf.close();
    			
    			Bitmap combitmap = decodeFile(new File(StorageProperties.getFaceRecGalleryPath() + nameofgalleryphoto));

    			Matrix rotateMatrix = new Matrix();
    			
    			if(mysurface.isFrontCameraOperated())
    			{
    				rotateMatrix.setTranslate(10 + combitmap.getWidth(), 10 + combitmap.getHeight());
    				rotateMatrix.preScale(-1.0f, 1.0f);
    				rotateMatrix.postRotate(360);
    			}
    			else
    			{
    				rotateMatrix.postRotate(360);
    			}

    			Bitmap rotatedBitmap = Bitmap.createBitmap(combitmap, 0, 0, combitmap.getWidth(), combitmap.getHeight(), rotateMatrix, false);

				Bitmap croppedtosquare = cropToSquare(rotatedBitmap);

				//rotatedBitmap.recycle();

				Bitmap croppedAndGreyScale = toGrayscale(croppedtosquare);

				//croppedtosquare.recycle();
				//combitmap.recycle();

				Bitmap bMapScaled;
    			if(combitmap.getWidth() > combitmap.getHeight())
    			{
    			  bMapScaled =  Bitmap.createScaledBitmap(croppedAndGreyScale, 100, 100, false);// scaleCenterCrop(rotatedBitmap, 450, 600);// Bitmap.createScaledBitmap(rotatedBitmap, 600, 450, false);
    			}
    			else
    			{
     			   bMapScaled =  Bitmap.createScaledBitmap(croppedAndGreyScale, 100, 100, false);//scaleCenterCrop(rotatedBitmap, 450, 600);;//Bitmap.createScaledBitmap(rotatedBitmap, 450, 600, false);
    			}
    			
    			//bMapScaled = rotatedBitmap;
    			
    			try {
    			       FileOutputStream out = new FileOutputStream(StorageProperties.getFaceRecGalleryPath() + nameofgalleryphoto);
    			       bMapScaled.compress(Bitmap.CompressFormat.JPEG, 90, out);

    			} catch (Exception e) {
    			       e.printStackTrace();
    			}



                
    			Toast.makeText(ClickPhoto.this, "Captured photo", Toast.LENGTH_SHORT).show();


                //setResult(Activity.RESULT_OK);
                
                _numPhotos++;
                
                

				//CommonlyUsed.showMsg(getApplicationContext(), ""+nameofgalleryphoto);
				//fileReadWrite.moveFile(projectSpecific.getFileGalleryPhotoPreUploadBasedonType(), projectSpecific.getDirGalleryFileDirectory() + nameofgalleryphoto);
				//CommonlyUsed.showMsg(getApplicationContext(), "Near burst camera");
				Camera burstcamera = mysurface.getCamera(); 
	            burstcamera.stopPreview();
	            burstcamera.startPreview();

				
			}
			catch(Exception ex)
			{
				
			}

		}
    	
    }
    
    class RefreshHandler extends Handler {

	    public void handleMessage(Message msg) {

	      //sugarlevel.this.updateUI();
	    	if(clickInProgress)
    		{
    			return;
    		}
    		clickInProgress = true;
	    	
		      this.removeMessages(0);


	    		MediaPlayer mp = MediaPlayer.create(ClickPhoto.this, R.raw.camera);   
	            mp.start();
	            mp.setOnCompletionListener(new OnCompletionListener() {

	                @Override
	                public void onCompletion(MediaPlayer mp) {
	                    // TODO Auto-generated method stub
	                    mp.release();
	                }

	            });
	    		
		     
	    	//mysurface.getCamera().takePicture(null, null,new HandlePictureClassBack());
	    	
	    
	    	
	    }



	    public void sleep(long delayMillis) {

	      this.removeMessages(0);

	      sendMessageDelayed(obtainMessage(0), delayMillis);

	      
	    }

	  }

}

