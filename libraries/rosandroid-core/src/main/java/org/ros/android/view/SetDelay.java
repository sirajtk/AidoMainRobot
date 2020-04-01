package org.ros.android.view;

import android.os.Handler;
import android.os.Message;

public class SetDelay {

	RefreshHandler rh;
	int _delayInMilliSecs;
	
	////// delay listener ///////
	protected OnDelayCompletedListener _OnDelayCompeletdListener;

	
	
	public SetDelay(int milliSecs) {
		// TODO Auto-generated constructor stub
		rh = new RefreshHandler();
		_delayInMilliSecs = milliSecs;
		rh.sleep(_delayInMilliSecs);
		
	}
	
	public interface OnDelayCompletedListener
	{
		 void onDelayCompleted ();
	}
	
	public void setOnDelayCompletedListener(OnDelayCompletedListener l)
	{
		this._OnDelayCompeletdListener = l;
	}
	
	
	
	
	class RefreshHandler extends Handler {

		
		////// delay listener ///////

	    public void handleMessage(Message msg) {
		      this.removeMessages(0);
		      _OnDelayCompeletdListener.onDelayCompleted();
		      
	    }


	    public void sleep(long delayMillis) {
	      this.removeMessages(0);
	      sendMessageDelayed(obtainMessage(0), delayMillis);
	    }

	  }
}
