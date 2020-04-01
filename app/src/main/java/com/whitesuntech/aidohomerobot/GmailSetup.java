package com.whitesuntech.aidohomerobot;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.whitesun.aidoface.settings.wifi.WifiCredentials;

import aido.TextToSpeech.BroadcastTTS;

import static android.R.id.summary;
import static android.R.id.title;

public class GmailSetup extends PreferenceActivity {
    static BroadcastTTS _ttsaido;
    Button nextbutton;



    public void nextButtonClick(View view){
        Log.i("nextbutton","clicked");
        Intent intent=new Intent(getApplicationContext(),SkypeSetUp.class);
        startActivity(intent);

    }
    public void previousButtonClick(View view){

        Intent intent=new Intent(getApplicationContext(),WifiCredentials.class);
        startActivity(intent);

    }
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.


        try {
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }catch (Exception ex)
        {
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getBoolean(preference.getKey(), true));

        }
    }


//    public void Login(View view){
//        //create gmail account
//        Intent mailClient = new Intent(Intent.ACTION_VIEW);
//        mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
//
//        startActivity(mailClient);
//    }
//    public void next(View view) {
//        Intent intent = new Intent(getApplicationContext(),SkypeSetup.class);
//        startActivity(intent);
//        //Uri skypeUri = Uri.parse(mySkypeUri);
////        Uri skypeUri = Uri.parse("skype:");
////        Intent myIntent = new Intent(Intent.ACTION_VIEW,skypeUri);
////        //myIntent.setData(Uri.parse("skype:" + "ajeetkumar"));
//////        myIntent.setComponent(new ComponentName(
//////                "com.skype.android.lite",
//////                "com.skype.android.lite.SkypeActivity"
//////        ));
////        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
////        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        myIntent.setClassName("com.skype.raider",
////                "com.skype.raider.Main");
//        //startActivity(myIntent);
//
//    }


//    @Nullable
//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = inflater.inflate(R.layout.preferencecustomlayout, null);
//
//        TextView ivNameTextColor = (TextView) row.findViewById(R.id.preferencetitleq);
//        ivNameTextColor.setText("vinita");
//        return row;
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gmail_setup);
        _ttsaido = new BroadcastTTS(this);


        nextbutton = findViewById(R.id.nextbutton);
        addPreferencesFromResource(R.xml.gmailsetup);
        setContentView(R.layout.wifisetup1);
       // PreferenceManager manager = getApplicationContext().();
       // PreferenceScreen preferenceScreen = new PreferenceManager().createPreferenceScreen(getApplicationContext());
//        PreferenceCategory preferenceCategory = findPreference("")
//        PreferenceScreen as = manager.createPreferenceScreen(getApplicationContext());
//        as.setTitle(title);
//        as.setSummary(summary);
//        as.setWidgetLayoutResource(R.layout.prefcutomlayout);
//        myCategory.addPreference(as);

        Preference gmailpreference=findPreference("gmailsetting");

//        gmailpreference.setWidgetLayoutResource(R.layout.prefcutomlayout);
//        gmailpreference.setTitle("sddsfdfsd");




            gmailpreference.setOnPreferenceClickListener (new Preference.OnPreferenceClickListener(){
                public boolean onPreferenceClick(Preference preference){


                    _ttsaido.speak("Lets try setting up Gmail for Aido");

                    Intent mailClient = new Intent(Intent.ACTION_VIEW);
                    mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
                    startActivity(mailClient);



                    return false;
                }
            });
        bindPreferenceSummaryToValue(findPreference("aidogmail"));

    }



    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };


}
