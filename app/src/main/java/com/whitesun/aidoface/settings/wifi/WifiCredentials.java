package com.whitesun.aidoface.settings.wifi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.whitesuntech.aidohomerobot.GmailSetup;
import com.whitesuntech.aidohomerobot.R;
import com.whitesuntech.aidohomerobot.SkypeSetUp;

import aido.common.CommonlyUsed;
import aido.http.asynchttp;
import aido.json.ConfigHandler;
import aido.properties.ConfigProperties;
import aido.setdelay.SetDelay;


/**
 * A login screen that offers login via email/password.
 */
public class WifiCredentials extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;
    StringBuilder sb = new StringBuilder();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("IP");

    List<String> emails = new ArrayList<>();

    //new changes
    public void nextButton(View view){
        Log.i("nextbutton","called");
        Intent intent=new Intent(getApplicationContext(), GmailSetup.class);
        startActivity(intent);
    }
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    ArrayAdapter<String> adapter;

     ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_credentials);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);

        adapter =
                new ArrayAdapter<>(WifiCredentials.this,
                        android.R.layout.simple_spinner_dropdown_item, emails);



       // populateAutoComplete();

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        receiverWifi = new WifiReceiver();

        registerReceiver(receiverWifi,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

         progressBar = new ProgressDialog(WifiCredentials.this, ProgressDialog.THEME_HOLO_LIGHT);




        mainWifi.startScan();

        progressBar.setIndeterminate(true);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setTitle("Scanning Wifi");
        progressBar.setMessage("Please wait...");


        progressBar.show();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            progressBar.dismiss();
            progressBar = null;
            unregisterReceiver(receiverWifi);
        }
        catch (Exception edx)
        {

        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }



    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {


            progressBar.show();
            wifiList = mainWifi.getScanResults();
            for (int i = 0; i < wifiList.size(); i++) {
                sb = new StringBuilder();
                sb.append((wifiList.get(i)).SSID);
                emails.add(sb.toString());

               // CommonlyUsed.showMsg(getApplicationContext(),"adding : " + sb.toString());
            }

            emails.add("");
            emails.add("");
            emails.add("");
            emails.add("");
            emails.add("");

            mEmailView.setThreshold(1);

            mEmailView.setAdapter(adapter);

            mEmailView.setOnTouchListener(new View.OnTouchListener() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                    if (emails.size() > 0) {
                        // show all suggestions
                        if (!mEmailView.getText().toString().equals(""))
                            adapter.getFilter().filter(null);
                        mEmailView.showDropDown();
                    }
                    return false;
                }
            });


            adapter.notifyDataSetChanged();

            progressBar.hide();
           // addEmailsToAutoComplete(emails);

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.

        adapter.notifyDataSetChanged();
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    boolean enableNeworkCustom(String ssid, Context cxt) {
        boolean state = false;
        WifiManager wm = (WifiManager) cxt.getSystemService(Context.WIFI_SERVICE);
        if (wm.setWifiEnabled(true)) {
            List<WifiConfiguration> networks = wm.getConfiguredNetworks();
            Iterator<WifiConfiguration> iterator = networks.iterator();
            while (iterator.hasNext()) {
                WifiConfiguration wifiConfig = iterator.next();
                if (wifiConfig.SSID.equals(ssid))
                    state = wm.enableNetwork(wifiConfig.networkId, true);
                else
                    wm.disableNetwork(wifiConfig.networkId);
            }
            wm.reconnect();
        }
        return state;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = String.format("\"%s\"", mEmail);
            wifiConfig.preSharedKey = String.format("\"%s\"", mPassword);

            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
//remember id
            int netId = wifiManager.addNetwork(wifiConfig);
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);

           // enableNeworkCustom(mEmail,WifiCredentials.this);
            return wifiManager.reconnect();




            // TODO: register the new account here.
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);


            SetDelay sd = new SetDelay(15000);
            sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                @Override
                public void onDelayCompleted() {
                    //String         PI_IP = ConfigHandler.getModuleName(ConfigProperties.PI_IP);

                    String PI_IP = "";


                    String link = Uri.parse(PI_IP  + "/setwifi.php")
                            .buildUpon()
                            .appendQueryParameter("id", mEmail)
                            .appendQueryParameter("pass",mPassword)
                            .build().toString();


                   // String link = PI_IP  + "/setwifi.php?id=" + mEmail + "&pass=" + mPassword;

                    final asynchttp http = new asynchttp(WifiCredentials.this);

                    Log.i("DOWN","Downloading : " + link);

                    http.download(link);



                    http.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
                        @Override
                        public void onDownloadIsCompleted(String downloadstring) {
                            if (success) {

                                CommonlyUsed.showMsg(WifiCredentials.this,"Got : "  + http.getDownloadedString());
                                finish();
                            } else {
                                mPasswordView.setError(getString(R.string.error_incorrect_password));
                                mPasswordView.requestFocus();
                            }

                        }
                    });

                }
            });


        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

