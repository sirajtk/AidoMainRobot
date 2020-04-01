package com.whitesuntech.aidohomerobot;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import aido.TextToSpeech.BroadcastTTS;
import aido.skype.SkypeAPIs;

/**
 * Created by rathore on 04/08/17.
 */

public class UserInfo extends PreferenceActivity {

    static BroadcastTTS _ttsaido;
    Button nextbutton;
    public void nextButtonClick(View view){
        Log.i("nextbutton","clicked");
        Intent intent=new Intent(getApplicationContext(),FaceRecognition.class);
        startActivity(intent);
    }
    public void previousButtonClick(View view){

        Intent intent=new Intent(getApplicationContext(),SkypeSetUp.class);
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    _ttsaido = new BroadcastTTS(this);
    setContentView(R.layout.customlaoutforfooter);
    nextbutton = findViewById(R.id.nextbutton);


    addPreferencesFromResource(R.xml.userinfo);

        bindPreferenceSummaryToValue(findPreference("userFirstName"));
        bindPreferenceSummaryToValue(findPreference("userLastname"));
        bindPreferenceSummaryToValue(findPreference("userDOB"));
        bindPreferenceSummaryToValue(findPreference("userPhoneDetail"));
        bindPreferenceSummaryToValue(findPreference("userEmailDetail"));
        bindPreferenceSummaryToValue(findPreference("userSkypeDetail"));






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
