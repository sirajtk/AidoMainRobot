package aido.googlevoice;

        import android.content.Context;
        import android.os.Bundle;
        import java.util.ArrayList;
        import java.util.List;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.content.pm.ResolveInfo;
        import android.speech.RecognitionListener;
        import android.speech.RecognizerIntent;
        import android.speech.SpeechRecognizer;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;
        import android.widget.TextView;
        import android.app.Activity;
        import android.view.Menu;

        import aido.common.CommonlyUsed;

public class SpeechRecognize {

    private static final int VR_REQUEST = 999;
    public static final String TAG = null;
    private ListView wordList;
    private final String LOG_TAG = "SpeechRepeatActivity";
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mIslistening = false;
    Context _maincontext;

    public SpeechRecognize(Context context) {
        _maincontext = context;

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(_maincontext);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                _maincontext.getPackageName());

        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);

        if (!mIslistening) {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        } else {
            Toast.makeText(_maincontext, "Oops - Speech Recognition Not Supported!",
                    Toast.LENGTH_LONG).show();
        }


    }


    public void onComplete(String result)
    {
        if (mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }

    }


    public void close()
    {
        if (mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }
    }

    public void onEndOfSpeechCompleted()
    {

    }


    public void onReadyToListenCompleted()
    {

    }


    protected class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onBeginningOfSpeech() {
            //Log.d(TAG, "onBeginingOfSpeech");
            onReadyToListenCompleted();
        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {
            //Log.d(TAG, "onEndOfSpeech");
            onEndOfSpeechCompleted();
        }

        @Override
        public void onError(int error) {

            //Log.d(TAG, "error = " + error);
            onComplete("GOT ERROR");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "OnReadyForSpeech"); //$NON-NLS-1$
        }

        @Override
        public void onResults(Bundle results) {
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            mIslistening = false;
            ArrayList<String> suggestedWords = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do




            onComplete(suggestedWords.get(0));
            System.out.println("06/02/19: got text" + suggestedWords.get(0));

           // CommonlyUsed.showMsg(_maincontext,"sp: " + complete);
            //wordList.setAdapter(new ArrayAdapter<String>(this, R.layout.word, suggestedWords));




        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

    }
}