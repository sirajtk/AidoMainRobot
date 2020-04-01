package my.camera;

import android.os.Handler;
import android.os.Message;

public class RefreshHandler extends Handler{
	
		////// delay listener ///////
		protected OnMessageCompletedListener _OnMessageCompeletdListener;

	    public void handleMessage(Message msg) {
		      this.removeMessages(0);
		      _OnMessageCompeletdListener.onMessageCompleted();
	    }


	    public interface OnMessageCompletedListener
		{
			 void onMessageCompleted();
		}
		
		public void setOnDelayCompletedListener(OnMessageCompletedListener l)
		{
			this._OnMessageCompeletdListener = l;
		}

	    public void sleep(long delayMillis) {
	      this.removeMessages(0);
	      sendMessageDelayed(obtainMessage(0), delayMillis);
	    }

	  }

