package com.whitesun.aidoface;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.whitesuntech.aidohomerobot.R;

import aido.TextToSpeech.BroadcastTTS;

public class result extends AppCompatActivity {
    TextView t1;
    BroadcastTTS _ttsaido;
    int score;
    memoryDatabase mydb = new memoryDatabase(this);



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


//        Intent intent = getIntent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        _ttsaido = new BroadcastTTS(result.this);
        SQLiteDatabase db = mydb.getReadableDatabase();
        String selectQuery1 = "SELECT  rno,qno,score FROM questionNumber where rno=1";
        Cursor cursor1 = db.rawQuery(selectQuery1, null);

        while (cursor1.moveToNext()) {
            score = Integer.parseInt(cursor1.getString(2));
            System.out.println("score " + score);

        }
        //Intent intent = getIntent();
        t1=findViewById(R.id.textView4);
//        _ttsaido.speak("Well played");
        score=score*20;
        _ttsaido.speak("Your Score is "+score+" out of 100");
        t1.setText("Your Score is "+score+" out of 100");
        _ttsaido.speak("thank you for playing the game");

        ContentValues cv = new ContentValues();
        cv.put("score","0");
        db.update("questionNumber", cv, "rno="+1, null);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        finish();
                    }
                },
                5000
        );
        Memory_game5.getInstance().finish();
        answer5.getInstance().finish();
        Memory_game1.getInstance().finish();
    }
}
