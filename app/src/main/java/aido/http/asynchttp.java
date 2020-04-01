package aido.http;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.crypto.spec.PSource;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

import aido.common.CommonlyUsed;
import aido.file.fileReadWrite;

public class asynchttp {


	protected OnDownloadCompletedListener mOnDownloadCompeletdListener;
	protected OnUploadCompletedListener mOnUploadCompeletdListener;

	HttpURLConnection connection;

	String download_url;
	String upload_url;
	
	String save_filename;
	
	Hashtable<String, String> _keyvaluepairs = new Hashtable<String, String>();

	Context maincontext;
	

	
	String _finalDownloadResult = "";
	String _finalUploadResult = "";

	public interface OnDownloadCompletedListener
	{
		void onDownloadIsCompleted (String downloadstring);
	}
	
	public void setOnDownloadCompletedListener(OnDownloadCompletedListener l)
	{
		this.mOnDownloadCompeletdListener = l;
	}
	
	public interface OnUploadCompletedListener
	{
		void onUploadIsCompleted ();
	}
	
	public void setOnUploadCompletedListener(OnUploadCompletedListener l)
	{
		this.mOnUploadCompeletdListener = l;
	}
	
	

	public asynchttp(Context maincontext_local)
	{
		maincontext = maincontext_local;
	}
	
	
	
	public void download(String download_url_local)  
	{
		/*if(!CommonlyUsed.checkNetworkConnectivity(maincontext))
		{
			CommonlyUsed.showMsg(maincontext, "No network connectivity");
			return;
		}*/
		
		
		///// if file exists do not download unless force is issued
		download_url = download_url_local;

		downloadFiles DF = new downloadFiles(maincontext);
		DF.execute();				
				
	}
	
	downloadFilesAndSave _downloadandsaveobject;
	public void downloadIntoFile(String download_url_local, String savefile)  
	{
		if(!CommonlyUsed.checkNetworkConnectivity(maincontext))
		{
			CommonlyUsed.showMsg(maincontext, "No Network Connectivity");
			return;
		}
		///// if file exists do not download unless force is issued
		download_url = download_url_local;
		save_filename = savefile;
		downloadFilesAndSave _downloadandsaveobject = new downloadFilesAndSave(maincontext);
		_downloadandsaveobject.execute();				
		
	}
	public void downloadIntoFile_noconnectioncheck(String download_url_local, String savefile)  
	{
		///// if file exists do not download unless force is issued
		download_url = download_url_local;
		save_filename = savefile;
		downloadFilesAndSave _downloadandsaveobject = new downloadFilesAndSave(maincontext);
		_downloadandsaveobject.execute();				
		
	}
	public downloadFilesAndSave getDownLOadAndSaveObject()
	{
		return _downloadandsaveobject;
	}
	
	public void upload(String upload_url_local, Hashtable<String, String> keyvaluepairs)   
	{
		///// if file exists do not download unless force is issued
		if(!CommonlyUsed.checkNetworkConnectivity(maincontext))
		{
			CommonlyUsed.showMsg(maincontext, "No Network Connectivity");
			//return;
		}
		
		upload_url = upload_url_local;
		_keyvaluepairs = keyvaluepairs;
		uploadvalues UP = new uploadvalues(maincontext);
		UP.execute();				
				
	}
	
	
	public String getDownloadedString()  
	{
		return _finalDownloadResult;
	}
	
	public String getUploadedString()   
	{
		return _finalUploadResult;
	}
	
	
	private String DownloadFromUrlThreadAndSave() {  //this is the actual downloaing in
		
		String retValue = "";
		try {
			
			
			//File file = new File(fileName);
			
			URL url = new URL(download_url);
			System.out.println("link url"+download_url);


			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			
			
			fileReadWrite.removeFile(save_filename);
			fileReadWrite.writeIntoBinaryFile(save_filename, baf.toByteArray());
			
			
			retValue = "";

		} catch (IOException e) {
			//Log.d("ImageManager", "Error: " + e);
			retValue = e.getMessage();
		}
		
		//this.mOnDownloadCompeletdListener.onDownloadCompleted();
		
		//this.mOnDownloadCompeletdListener.onDownloadCompleted();

		return retValue;

	}
	
		
	
	
	
public class downloadFiles extends AsyncTask<Object, String, Void> {
		

		Context maincontext_l;
	
		
		
		
		public downloadFiles(Context context) {
			// TODO Auto-generated constructor stub
			maincontext_l = context;
		
		}
		 @Override
		    protected void onPreExecute() {
			 
		    }
		 
		 @Override
		    protected void onCancelled() {
		    }
		@Override
		protected Void doInBackground(Object... arg0) {
			//int i= 0;
			
				
				String ret = "";
					try
					{
						_finalDownloadResult = httpGet();
					}
					catch(Exception ex)
					{
						
					}
					 
			

			return null;
		}

		 

		    @Override
		    protected void onPostExecute(Void result) {
		        try {
		        	mOnDownloadCompeletdListener.onDownloadIsCompleted(_finalDownloadResult);
		        } catch(Exception e) {
		        }

		        
		    }
	}

	
public class downloadFilesAndSave extends AsyncTask<Object, String, Void> {
	

	Context maincontext_l;

	
	
	
	public downloadFilesAndSave(Context context) {
		// TODO Auto-generated constructor stub
		maincontext_l = context;
	
	}
	 @Override
	    protected void onPreExecute() {
		 
	    }
	 
	 @Override
	    protected void onCancelled() {
	    }
	@Override
	protected Void doInBackground(Object... arg0) {
		//int i= 0;
		
			
			String ret = "";
				try
				{
					_finalDownloadResult = DownloadFromUrlThreadAndSave();
				}
				catch(Exception ex)
				{
					
				}
				 
		

		return null;
	}

	 

	    @Override
	    protected void onPostExecute(Void result) {
	        try {
	        	mOnDownloadCompeletdListener.onDownloadIsCompleted(_finalDownloadResult);
	        } catch(Exception e) {
	        }

	        
	    }
}
	
	
	
	
	
	public String httpGet()
	{
		String retResponse = "";

		try{

	    HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet(download_url); 

	    // Execute the request
	    HttpResponse response;
	    
	        response = httpclient.execute(httpget);
	        // Examine the response status
	        Log.i("Praeda",response.getStatusLine().toString());
	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release
	        if (entity != null) {

	        	
                retResponse = EntityUtils.toString(entity);

	        }
	    
		} catch (ClientProtocolException e) {  
	    	// TODO Auto-generated catch block      
	    } catch (IOException e) {  
	    	// TODO Auto-generated catch block  
	    } 
		
		
	    return retResponse;

	}


	
	public String postData(Hashtable<String, String> keyvaluepair) throws IOException {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(upload_url);
		String retResponse = "";

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        
	        Enumeration<String> iter = keyvaluepair.keys();
	        
	        while(iter.hasMoreElements())
	        {
	        	String key = iter.nextElement();
	        	String value = keyvaluepair.get(key);
	        	
		        nameValuePairs.add(new BasicNameValuePair(key, value));
	        }
	        
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response  = httpclient.execute(httppost);
	        
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {

                retResponse = EntityUtils.toString(entity);

	        }
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	    
	    return  retResponse;
	    
	} 
	
public class uploadvalues extends AsyncTask<Object, String, Void> {
		

		Context maincontext_l;
	
		
		
		
		public uploadvalues(Context context) {
			// TODO Auto-generated constructor stub
			maincontext_l = context;
		
		}
		 @Override
		    protected void onPreExecute() {
			 
		    }
		 
		 @Override
		    protected void onCancelled() {
		    }
		@Override
		protected Void doInBackground(Object... arg0) {
			//int i= 0;
			
				
				String ret = "";
					try
					{
						_finalUploadResult = postData(_keyvaluepairs);
					}
					catch(Exception ex)
					{
						
					}
					 
			

			return null;
		}

		 

		    @Override
		    protected void onPostExecute(Void result) {
		        try {
		        	mOnUploadCompeletdListener.onUploadIsCompleted();	            
		        } catch(Exception e) {
		        }

		        
		    }
	}
	
}
