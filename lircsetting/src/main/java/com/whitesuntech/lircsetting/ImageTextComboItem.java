package com.whitesuntech.lircsetting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class ImageTextComboItem {
	
	public  static  int IMAGE = 0;
	public  static  int TEXT = 1;
	
	private String _stringItem;
	private String _imageFilename;
	
	public ImageTextComboItem(String strItem, String imagefilename) {
		// TODO Auto-generated constructor stub
		_stringItem = strItem;
		_imageFilename = imagefilename;
	}
	
	public ImageTextComboItem(String strItem) {
		// TODO Auto-generated constructor stub
		_stringItem = strItem;
		_imageFilename = "";
	}

	public boolean isValid(int itemType)
	{
		
		if(itemType == TEXT)
		{
            return !CommonlyUsed.stringIsNullOrEmpty(_stringItem);
		}
		

		if(itemType == IMAGE)
		{
            return !CommonlyUsed.stringIsNullOrEmpty(_imageFilename);
		}
		
		return true;
	}
	
	public String getItemString(int itemType)
	{
		
		if(itemType == TEXT)
		{
			if(CommonlyUsed.stringIsNullOrEmpty(_stringItem))
			{
				return "";
			}
			else
			{
				return _stringItem;
			}
		}
		

		if(itemType == IMAGE)
		{
			if(CommonlyUsed.stringIsNullOrEmpty(_imageFilename))
			{
				return "";
			}
			else
			{
				return _imageFilename;
			}
		}
		
		return "";
	}
	
	public BitmapDrawable getImageDrawable()
	{
		return CommonlyUsed.getDrawableFromImageFileName(_imageFilename);
	}
	
	public BitmapDrawable getImageDrawableScaledToView(View myview)
	{
	     		
		return scaleDrawableForViewFromFile(myview, _imageFilename);
	}


	

	
	public static BitmapDrawable scaleDrawableForViewFromFile(View myview, String filename)
	{
		// bmp is your Bitmap object
		
	     Bitmap bmp = BitmapFactory.decodeFile(filename);
				
		int imgHeight = bmp.getHeight();
		int imgWidth = bmp.getWidth();
		int containerHeight = myview.getHeight();
		int containerWidth = myview.getWidth();
		
		
		return new BitmapDrawable(bmp);
	}
}
