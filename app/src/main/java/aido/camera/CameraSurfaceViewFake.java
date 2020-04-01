/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package aido.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 
 * @author openmobster@gmail.com
 */
public class CameraSurfaceViewFake extends SurfaceView implements SurfaceHolder.Callback
{
	private SurfaceHolder holder;
	private Camera   camera;

	boolean previewing = false;

	boolean _preferFrontCamera = true;

	Context _maincontext;

	boolean _frontcamerasuccess = false;


	int _x = 0;
	int _y = 0;
	int _radius = 0;
	int _delay = 0;

	public CameraSurfaceViewFake(Context context, boolean preferfrontcamera, int x, int y, int radius, int delay)
	{
		super(context);
		_maincontext = context;
		
		_preferFrontCamera = preferfrontcamera;

		_x = x;
		_y = y;
		_radius  = radius;
		_delay = delay;
		//Initiate the Surface Holder properly
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}


	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		//final Paint p = new Paint(Color.RED);

		if(_radius > 0) {

		//	canvas.drawCircle(_x, _y, _radius, p);

			/*
			SetDelay sd = new SetDelay(_delay);
			sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
				@Override
				public void onDelayCompleted() {
					canvas.drawCircle(_x, _y, _radius, p);
				}
			});*/
			//Log.w(this.getClass().getName(), "On Draw circle Called");

		}


	}






	@TargetApi(9)
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		setWillNotDraw(false);

		try
		{
			if (Integer.parseInt(Build.VERSION.SDK) >= 9)
			{

				if(_preferFrontCamera)
				{
					if (Camera.getNumberOfCameras() >= 2) {

						//if you want to open front facing camera use this line   
						camera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
						this.camera.setPreviewDisplay(this.holder);

						_frontcamerasuccess = true;
						//CommonlyUsed.showMsg(_maincontext, "opening front camera");
					}
					else
					{
						//if you want to use the back facing camera
						camera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
						this.camera.setPreviewDisplay(this.holder);
						_frontcamerasuccess = false;

					}
				}
				else
				{
					// this is default back camera
					//Open the Camera in preview mode
					this.camera = Camera.open();					
					this.camera.setPreviewDisplay(this.holder);
				}
			}
			else
			{
				this.camera = Camera.open();
				this.camera.setPreviewDisplay(this.holder);

			}

		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(System.out);
		}



	}
	
	
	public boolean isFrontCameraOperated()
	{
		return _frontcamerasuccess;
	}
	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		//CommonlyUsed.showMsg(_maincontext, "Im in surfacechanged");
		 if(previewing){
			  //camera.stopPreview();
			  previewing = false;
			 }

		 if(camera != null)
		 {
			 Camera.Parameters parameters = camera.getParameters();
			 //parameters.setPreviewSize(width, height);
			 //parameters.setPictureFormat(PixelFormat.JPEG);
			 //parameters.setPreviewSize(200, 150); 
			 /*List<Camera.Size> supportedsizes = parameters.getSupportedPictureSizes();
			 Camera.Size first = supportedsizes.get(supportedsizes.size() - 1);
			 parameters.setPictureSize(first.width, first.height);*/
			 
			 //parameters.setJpegQuality(10);
			 
			 //parameters.set("orientation", "portrait");
			 //parameters.setRotation(90);
			 
			 
			 if (Integer.parseInt(Build.VERSION.SDK) >= 8)
			 {
				 if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				 {
					 setDisplayOrientation(camera, 90);
					 parameters.set("rotation", 90);

				 }
				 if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
				 {
					 //parameters.set("orientation", "landscape");
					 //parameters.set("rotation", 90);
				 }
				 

			 }
			 else
			 {
				 if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				 {
					 parameters.set("orientation", "portrait");
					 parameters.set("rotation", 90);
				 }
				 if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
				 {
					 parameters.set("orientation", "landscape");
					 parameters.set("rotation", 90);
				 }
			 }   
			
			 /* if (parameters.getSupportedFocusModes().contains(
			    Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
		 parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			
	 }
	 */
	 
	 
			 
			 //camera.setDisplayOrientation(90);

			 camera.startPreview();
			   previewing = true;
 
		 }
		 
		 
	}

	protected void setDisplayOrientation(Camera camera, int angle){
	    Method downPolymorphic;
	    try
	    {
	        downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", int.class);
	        if (downPolymorphic != null)
	            downPolymorphic.invoke(camera, angle);
	    }
	    catch (Exception e1)
	    {
	    }
 }

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		// Surface will be destroyed when replaced with a new screen
		//Always make sure to release the Camera instance
		try{
		camera.stopPreview();
		camera.release();
		camera = null;
		previewing = false;
		}
		catch(Exception ex)
		{
			
		}
	}
	
	public void stopCamera() 
	{
		camera.unlock();
		camera.stopPreview();
		camera.release();
		previewing = false;
		//camera = null;
	}
	
	public Camera getCamera()
	{
		return this.camera;
	}
}
