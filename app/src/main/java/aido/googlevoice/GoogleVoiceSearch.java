package aido.googlevoice;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class GoogleVoiceSearch {


   Context _maincontext;


  public static final int GOOGLE_VOICE_INTENT = 1;




   public GoogleVoiceSearch(Context context) {
       // TODO Auto-generated constructor stub
       _maincontext = context;

	   Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);


       intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 6);
       intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hey man");


       intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3000);
       try {
		   ((Activity) _maincontext).startActivityForResult(intent, GOOGLE_VOICE_INTENT);
	   } catch (ActivityNotFoundException a) {
		   Toast.makeText(_maincontext, "Oops! Your device doesn't support Speech to Text",Toast.LENGTH_SHORT).show();
	   }



   }


   public static String processIntentResult(int requestCode, int resultCode, Intent data)
   {


       if(requestCode != GOOGLE_VOICE_INTENT)
       {
           return "";
       }




       if (resultCode == Activity.RESULT_CANCELED)
       {
		   return "";
       }
       else
       {
           if (resultCode == Activity.RESULT_OK && null != data)
           {



			   String yourResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);

			  return yourResult;

           }
           else
           {
			   return "";
           }
       }



   }


}
