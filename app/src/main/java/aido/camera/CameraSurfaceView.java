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
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * @author openmobster@gmail.com
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	private SurfaceHolder holder;
	private Camera   camera;

	boolean previewing = false;
	
	boolean _preferFrontCamera = true;
	
	Context _maincontext;
	
	boolean _frontcamerasuccess = false;
	
	public CameraSurfaceView(Context context, boolean preferfrontcamera) 
	{
		super(context);
		_maincontext = context;
		
		_preferFrontCamera = preferfrontcamera;
		
		//Initiate the Surface Holder properly
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@TargetApi(9)
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
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


			 Camera.Parameters parameters = camera.getParameters();
			 
			 //CommonlyUsed.showMsg(_maincontext, "Parameter contain:" + parameters);
			 //CommonlyUsed.showMsg(_maincontext, "no of modes = " + parameters.getSupportedFocusModes().size());

			 try
				{
			 if(parameters.getSupportedFocusModes().size() > 0)
			 {
				 //CommonlyUsed.showMsg(_maincontext, " modes = " + parameters.getSupportedFocusModes().get(0));

			 }

			 
				 if (parameters.getSupportedFocusModes().contains(
						    Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
					 parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
						//CommonlyUsed.showMsg(_maincontext, "video focus");

						
				 }
				 
				 
				 if (parameters.getSupportedFocusModes().contains(
						    Camera.Parameters.FOCUS_MODE_MACRO)) {
					 parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
						//CommonlyUsed.showMsg(_maincontext, "macro focus");

						
				 }
				 

				 
				 if (parameters.getSupportedFocusModes().contains(
						    Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
					 parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
						//CommonlyUsed.showMsg(_maincontext, "picture focus");

				 }
				 

				 if (parameters.getSupportedFocusModes().contains(
						    Camera.Parameters.FOCUS_MODE_AUTO)) {
					 parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
						//CommonlyUsed.showMsg(_maincontext, "auto focus");

						
				 }
				 
				 
				 camera.setParameters(parameters);

				}catch(Exception ex)
			 {
					
			 }
				 
				
				
				try
				{
				 Camera.Parameters parameters4 = camera.getParameters();
				 parameters4.setExposureCompensation(parameters4.getMinExposureCompensation());
			        camera.setParameters(parameters4);

				}
				catch(Exception ex)
				{
					
				}
				
				
				 Camera.Parameters parameters2 = camera.getParameters();


				 try
				 {
				parameters.set("mode", "m");

				 parameters.set("aperture", "14"); //can be 28 32 35 40 45 50 56 63 71 80 on default zoom

				 parameters.set("shutter-speed", 9); // depends on camera, eg. 1 means longest

				 parameters.set("iso", 200);
				 
				 
				 
				// parameters.setExposureCompensation(parameters.getMinExposureCompensation() + (int) (camera.getParameters().getExposureCompensationStep() *10));
				// parameters.setExposureCompensation(parameters.getMinExposureCompensation());
				 
				 
				 camera.setParameters(parameters);
				 }catch(Exception ex)
				 {
					 
				 }
				 
				 
				 try
					{
					 Camera.Parameters parameters3 = camera.getParameters();
					 
					 List<Camera.Size> sizes = parameters3.getSupportedPictureSizes();
					 int heightoset = 0;
					 int widthtoset = 0;

					 Camera.Size sizetoset;
					 for(int i=0;i<sizes.size();i++)
					 {
						 if(sizes.get(i).height > heightoset)
						 {
							 heightoset = sizes.get(i).height;
							 widthtoset = sizes.get(i).width;
						 }
						 
						 

					 }
					 
					 
					 //CommonlyUsed.showMsg(_maincontext, "Selected : w="  + widthtoset + ",h=" +  heightoset );
					 
					 parameters3.setPictureSize(widthtoset,heightoset);

				        camera.setParameters(parameters3);
					}
					catch(Exception ex)
					{
						
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
