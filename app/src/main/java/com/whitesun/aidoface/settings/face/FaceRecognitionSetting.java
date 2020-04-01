package com.whitesun.aidoface.settings.face;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whitesun.aidoface.AidoFace;
import com.whitesuntech.aidohomerobot.R;

import aido.TextToSpeech.BroadcastTTS;
import aido.camera.ShowCamera;
import aido.common.CommonlyUsed;

public class FaceRecognitionSetting extends AppCompatActivity {


    FirebaseDatabase _firedbhandle = FirebaseDatabase.getInstance();
    DatabaseReference _firedbreference = _firedbhandle.getReference();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
   // DatabaseReference tripsRef = rootRef.child("AidoRobot2").child("face");


    Intent _intent_showcam;

    static BroadcastTTS _ttsaido;

    int INTENT_SHOWCAM_REQCODE = 1014;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition_setting);

        _ttsaido = new BroadcastTTS(FaceRecognitionSetting.this);

        _firedbreference.child(AidoFace.fire_aidoid2).child("face").child("training").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String key = dataSnapshot.getKey();
                    String completionvalue =  dataSnapshot.child("status").getValue().toString();

                    Log.i("FIREDB","enrollment Value changed ! : " + key + "=" + completionvalue);

                    if(!CommonlyUsed.stringIsNullOrEmpty(completionvalue)
                            )
                    {
                        if(completionvalue.contains("2")) {
                            _ttsaido.speak("The enrollment is complete");
                            try
                            {
                                _firedbreference.child(AidoFace.fire_aidoid2).child("face").child("training").child("status").setValue("0");
                                finishActivity(INTENT_SHOWCAM_REQCODE);
                                String error = dataSnapshot.child("error").getValue().toString();


                                if(error.contains("1"))
                                {
                                    _ttsaido.speak("However there seems to be something wrong. Sorry. you will have to try again");
                                }
                                else
                                {
                                    _ttsaido.speak("You are successfully enrolled");
                                    userInput.setText("");
                                }

                            }
                            catch (Exception ex)
                            {

                            }



                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    enrollFace();
    }
     EditText userInput;

    void enrollFace()
    {

        final TextView textview_error = findViewById(R.id.dialog_error);
        textview_error.setText("");




         userInput = findViewById(R.id.edittext_facename);

        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                textview_error.setText("");
                return false;
            }
        });

        final Button button_submit = findViewById(R.id.dialog_ok);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!CommonlyUsed.stringIsNullOrEmpty(userInput.getText().toString()))
                {

                    Toast.makeText(getApplicationContext(), "Enrollment Started!", Toast.LENGTH_SHORT).show();
                    rootRef.child("AidoRobot2").child("face").child("training").child("name").setValue(userInput.getText().toString());
                    rootRef.child("AidoRobot2").child("face").child("training").child("train").setValue("1");

//                    _firedbreference.child(AidoFace.fire_aidoid2).child("face").child("training").child("name").setValue(userInput.getText().toString());
 //                   _firedbreference.child(AidoFace.fire_aidoid2).child("face").child("training").child("train").setValue("1");


                    _ttsaido.speak("Enrollment has started. Please make random movements of face in front of the camera");
                    _intent_showcam = new Intent(FaceRecognitionSetting.this,ShowCamera.class);
                    _intent_showcam.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(_intent_showcam,INTENT_SHOWCAM_REQCODE);



                }
                else
                {
                    textview_error.setText("Enter a valid name");
                }


            }
        });


        final Button button_cancel = findViewById(R.id.dialog_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview_error.setText(R.string.exit_dialog_cancel);
                finish();
            }
        });
        // show it


        userInput.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
