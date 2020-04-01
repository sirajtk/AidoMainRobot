package aido.common;

import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import aido.setdelay.SetDelay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class CommonlyUsed {
	
	static public void showMsg(Context context,String text)
	{
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	static public void showMsgLong(Context context,String text)
	{
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
	

	
	

	public static String encryptlogin(String login)
	{
		
		return "6464747668484848923793272979" + login + "637373838383893992837923470349";

	}


	public static String decryptlogin(String login)
	{
		
		return login.replace("6464747668484848923793272979", "").replace("637373838383893992837923470349", "");		

	}
	
	public static boolean checkNetworkConnectivity(Context context)
	{
		/*
		    boolean status=false;
		    try{
		    	ActivityCustom AC = (ActivityCustom) context;
		        ConnectivityManager cm = (ConnectivityManager) AC.getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo netInfo = cm.getNetworkInfo(0);
		        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
		            status= true;
		        }else {
		            netInfo = cm.getNetworkInfo(1);
		            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
		                status= true;
		        }
		    }catch(Exception e){
		        e.printStackTrace();  
		        return false;
		    }
		    return status;
		    */
    	Activity AC = (Activity) context;

		    ConnectivityManager cm =
		        (ConnectivityManager) AC.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    return netInfo != null && netInfo.isConnectedOrConnecting();
		

		     
	}

	public static String [] removeLastNstringFromStringArray(String[] inputArr, int N)
	{
        String[] outputArr = new String[inputArr.length - N];
		
		for(int i=0; i< inputArr.length - N; i++)
		{
			outputArr[i] = inputArr[i];
		}
		
		return outputArr;
	}
	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static float round(float value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.floatValue();
	}
	    
	
	public static void  messageBox(Context context, String title, String message, String buttontext)
	{
		new AlertDialog.Builder(context)
	    .setTitle(title)
	    .setMessage(message)
	    .setPositiveButton(buttontext, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        	dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	public static void  messageBoxTimeOut(Context context, String title, String message, String buttontext, int dismisstime)
	{
		final AlertDialog alert = new AlertDialog.Builder(context)
	    .setTitle(title)
	    .setMessage(message)
	    .setPositiveButton(buttontext, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        	dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
		SetDelay sd = new SetDelay(500);
		sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
			
			@Override
			public void onDelayCompleted() {
				// TODO Auto-generated method stub
				alert.dismiss();
			}
		});
	}
	
	static public float getFloatValueFromString(String str)
	{
            float ret = 0.0f;
		if(str == null)
		{
		}	
		try
		{
			ret = Float.valueOf(str);
		}
		catch(Exception ex)
		{	
			ret = 0.0f;
		}
		
		return ret;
	}
	
	

	static public String getCheckSumMD5(String str)
	{
		
		String ret = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

            byte[] byteData = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			ret = sb.toString();
			
		} catch (Exception ex) {

		}
		
		return ret;
	}
	
	static public String createCSVstringFromList(List<String> LC, String separator)
	{
		String retString = "";
		for(int i=0;i<LC.size();i++)
		{
			if(i>0) {retString += separator;}
			retString += LC.get(i);
		}
		
		return retString;
	}
	
	static public String getIMEIofDevice(Context context)
	{
		//TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		//return telephonyManager.getDeviceId();
		
		String identifier = null;
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null)
		      identifier = tm.getDeviceId();
		if (identifier == null || identifier .length() == 0)
		      identifier = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
		
		
		return identifier;
		
	}
	
	static public String getPhoneNumber(Context context)
	{
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
	}
	
	
	static public BitmapDrawable getDrawableFromImageFileName(String filename)
	{
		Bitmap bm = BitmapFactory.decodeFile(filename);
		return new BitmapDrawable(bm);
	}
	
	static public Integer getIntegerValueFromString(String str)
	{
		Integer retInt = 0;
		if(str == null)
		{
			//CommonlyUsed.showMsg(_mainContext, "null string for int conversion");
			return 0;
		}
		
		try
		{
			retInt = Integer.valueOf(str);
		}
		catch(Exception ex)
		{
			
			retInt = Integer.valueOf(0);
			//CommonlyUsed.showMsg(_mainContext, "Int conversion failed - " + ex.getMessage());

		}
		
		return retInt;
	}
	
	
	static public boolean stringIsNullOrEmpty(String text)
	{

        return text == null || text.length() <= 0 || text == "";
		
	}
	
	
	static  public int getRandomInteger(int start, int end)
	{

		Random random = new Random();

		if ( start > end ) {
			return 0;
		}
		//get the range, casting to long to avoid overflow problems
		long range = (long)end - (long)start + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long)(range * random.nextDouble());


		return (int)(fraction + start);  

	    
	}

	static public void putImageOnButton(ImageButton imgbutton, String filename)
	{
		imgbutton.setImageBitmap(BitmapFactory.decodeFile(filename));
		imgbutton.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imgbutton.refreshDrawableState();
	}

	static public void putImageOnImageView(ImageView imgview, String filename)
	{
		imgview.setImageBitmap(BitmapFactory.decodeFile(filename));
		imgview.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imgview.refreshDrawableState();
	}
	
	public static byte[] invertByteArray(byte[] data)  {
		//checkNull(data, "Data");
		
		int d = 0;
		for (int i = 0; i < data.length; ++i) {
			d = data[i] & 0xFF;
			d = ~d;
			data[i] = (byte)d;
		}
		
		return data;
	}
	
	
	public static int getDisplayHeight(Context context)
	{
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int height = display.getHeight();
		return height;
	}
	
	public static int getDisplayWidth(Context context)
	{
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		return width;
	}
	
	static public boolean addStringPriorToBinary(String stringToAdd, String inputFileName)
	{
		try
		{
			RandomAccessFile file = new RandomAccessFile(inputFileName, "rws");
			byte[] text = new byte[(int) file.length()];
			file.readFully(text);
			file.seek(0);
			file.writeBytes(stringToAdd);
			file.write(text);
			file.close();
		}
		catch (Exception ex)
		{
			return false;
		}
		
		

		return true;
	}
	
	static public String getCurrentdateTimeString(String sep)
	{
		String retValue = "";
		
		Calendar c1 = Calendar.getInstance();
		
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int month = c1.get(Calendar.MONTH) + 1;
		int year = c1.get(Calendar.YEAR);
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);
		int second = c1.get(Calendar.SECOND);
		
		retValue = 	
		convertNumberTodateTimeFormat(hour) + sep + 
		convertNumberTodateTimeFormat(minute) + sep + 
		convertNumberTodateTimeFormat(second) + sep + 
		convertNumberTodateTimeFormat(day) + sep + 
		convertNumberTodateTimeFormat(month) + sep + 
		convertNumberTodateTimeFormat(year) + sep;
		
		
		return retValue;
	}

	static public String getCurrentdateTimeString_shortID(String sep)
	{
		String retValue = "";
		
		Calendar c1 = Calendar.getInstance();
		
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int month = c1.get(Calendar.MONTH) + 1;
		int year = c1.get(Calendar.YEAR);
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);
		int second = c1.get(Calendar.SECOND);
		
		retValue = 	
				convertNumberTodateTimeFormat(day) + sep + 
				convertNumberTodateTimeFormat(month) + sep + 
				convertNumberTodateTimeFormat(year) + sep + 
				convertNumberTodateTimeFormat(hour) + sep + 
				convertNumberTodateTimeFormat(minute) + sep + 
				convertNumberTodateTimeFormat(second)
				;

		
		return retValue;
	}
	
	static public String getDateTimeStringFromDateNumber(String datetime, String sep)
	{
		String retString = "";
		
		if(datetime.length() < 14)
		{
			return "";
		}
		
		if(CommonlyUsed.stringIsNullOrEmpty(sep))
		{
			retString = 
					        "Date: " + datetime.charAt(0) + datetime.charAt(1) + "/" + 
							datetime.charAt(2) + datetime.charAt(3) + "/" + 
							datetime.charAt(4) + datetime.charAt(5) + datetime.charAt(6) + datetime.charAt(7) 
							+ " Time: " + 
							datetime.charAt(8) + datetime.charAt(9) + ":" + 
							datetime.charAt(10) + datetime.charAt(11) + ":" + 
							datetime.charAt(12) + datetime.charAt(13);

					
					
		}
		
		return retString;
	}
	
	static public String convertNumberTodateTimeFormat( int No)
	{
		
		if(No < 10)
		{
			return "0" + No;
		}
		else
		{
			return "" + No;
		}
	}


	static public String getRealPathFromURI(Context context, Uri contentURI) {
		String result;
		Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file path
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}


	static public String getAlphaUniqueID()
	{
		String retValue = "";
		
		RandomString RS = new RandomString(3);
		
		Calendar c1 = Calendar.getInstance();
		
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int month = c1.get(Calendar.MONTH) + 1;
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);
		int second = c1.get(Calendar.SECOND);
		
		retValue = 	RS.nextString() + hour + minute +	second + day + month; 
		
		
		return retValue;
	}
	
	
	static public String getCurrentTimeString(String sep)
	{
		String retValue = "";
		
		Calendar c1 = Calendar.getInstance();
		
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);
		
		retValue = 	hour + sep + 
					minute;
		return retValue;
	}
	
	static public String getCurrentDateString(String sep)
	{
		String retValue = "";
		
		Calendar c1 = Calendar.getInstance();
		
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int month = c1.get(Calendar.MONTH) + 1;
		int year = c1.get(Calendar.YEAR);
	
		retValue =  day + sep + 
					month + sep + 
					year ;
		return retValue;
	}
	
	 public static String[] split(String original, String separator) {
	        List<String> nodes = new ArrayList<String>();
	        // Parse nodes into vector
	        int index = original.indexOf(separator);
	        while (index >= 0) {
	            nodes.add(original.substring(0, index));
	            original = original.substring(index + separator.length());
	            index = original.indexOf(separator);
	        }
	        // Get the last node
	        nodes.add(original);
	        // Create splitted string array
	        String[] result = new String[nodes.size()];
	        if (nodes.size() > 0) {
	            for (int loop = 0; loop < nodes.size(); loop++) {
	                result[loop] = nodes.get(loop);
	                System.out.println(result[loop]);
	            }

	        }
	        return result;
	    }
	 
	 public static List<String> splitToList(String original, String separator) {
	        List<String> nodes = new ArrayList<String>();
	        // Parse nodes into vector
	        int index = original.indexOf(separator);
	        while (index >= 0) {
	            nodes.add(original.substring(0, index));
	            original = original.substring(index + separator.length());
	            index = original.indexOf(separator);
	        }
	        // Get the last node
	        nodes.add(original);
	        
	        return nodes;
	    }

	 public static String[] convertListToStringArray(List<String> inputlist)
	 {
		 String[] retStringList = new String[inputlist.size()];
		 if (inputlist.size() > 0) {
	            for (int loop = 0; loop < inputlist.size(); loop++) {
	            	retStringList[loop] = inputlist.get(loop);
	            }

	        }
	        return retStringList;
	 }


	public static void muteAudioOutput(Context context)
	{
//		AudioManager amanager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//		amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
//		amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
//		amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//		amanager.setStreamMute(AudioManager.STREAM_RING, true);
//		amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC,0,AudioManager.FLAG_SHOW_UI);
	}

	public static void unmuteAudioOutput(Context context)
	{
//		AudioManager amanager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//		amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
//		amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
//		amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
//		amanager.setStreamMute(AudioManager.STREAM_RING, false);
//		amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
	}


	public static long timeElapsed(long prevtime)
	{
		long tEnd = SystemClock.elapsedRealtime();
		long tDelta = tEnd - prevtime;

		return tDelta;

	}

	public static boolean checkIfPackageIsInstalled(Context context, String targetPackage){
		PackageManager pm=context.getPackageManager();
		try {
			PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return true;
	}
}
