
package com.whitesun.aidoface;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.whitesun.aidoface.memoryDatabase;
import com.whitesuntech.aidohomerobot.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import aido.TextToSpeech.BroadcastTTS;


public class answer3 extends AppCompatActivity {
    BroadcastTTS _ttsaido;
    ArrayList<String> dataa = new ArrayList<>();
    ArrayList<byte[]> image = new ArrayList<>();
    //memoryDatabase md = new memoryDatabase(this);
    memoryDatabase mydb=new memoryDatabase(this);
    String[] data;
    ImageView i1,i2,i3,i4;
    String answer;
    int score;
    String sp,q;
    String[] spp;
    TextToSpeech t1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer3);
        i1 = findViewById(R.id.image1);
        i2 = findViewById(R.id.image2);
        i3 = findViewById(R.id.image3);
        i4 = findViewById(R.id.image4);
        SQLiteDatabase db = mydb.getReadableDatabase();
        SQLiteDatabase db1 = mydb.getWritableDatabase();

        _ttsaido = new BroadcastTTS(answer3.this);
//        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if(status != TextToSpeech.ERROR) {
//                    t1.setLanguage(Locale.UK);
//                }
//            }
//        });
//        t1.speak("hai", TextToSpeech.QUEUE_FLUSH, null);
        Intent intent = getIntent();
        String sp1 = intent.getStringExtra("selected");
        answer =intent.getStringExtra("answer");
        q =  intent.getStringExtra("qno");
        spp = q.split(",");
        sp = spp[3];

        System.out.println("sp1"+sp1);

        String selectQuery1 = "SELECT  rno,qno,score FROM questionNumber where rno=1";
        Cursor cursor1 = db.rawQuery(selectQuery1, null);
        while (cursor1.moveToNext()) {
//            sp = cursor1.getString(1);
            score = Integer.parseInt(cursor1.getString(2));
//            System.out.println("qno " + sp);

        }
        System.out.println("score from table " + score);


//        String selectQuery = "SELECT  rno,image1,image2,image3,image4,question,answer,score FROM memoryGame where rno='"+sp+"'";
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        System.out.println("under curser"+sp);
//
//        while(cursor.moveToNext()) {
//            data = new String[]{cursor.getString(0), cursor.getString(5), cursor.getString(6), cursor.getString(7)};
//            //dataa.add(String.valueOf(Arrays.asList(data)));
//            image.add(cursor.getBlob(1));
//            image.add(cursor.getBlob(2));
//            image.add(cursor.getBlob(3));
//            image.add(cursor.getBlob(4));
//
//            String question = data[1];
//            String answer_ = data[2];
////                String answer ="1";
//            // int score = 0;
//
//            byte[] bb1 = image.get(0);
//            byte[] bb2 = image.get(1);
//            byte[] bb3 = image.get(2);
//            byte[] bb4 = image.get(3);
//
//
//            i1.setImageBitmap(BitmapFactory.decodeByteArray(bb1, 0, bb1.length));
//            i2.setImageBitmap(BitmapFactory.decodeByteArray(bb2, 0, bb2.length));
//            i3.setImageBitmap(BitmapFactory.decodeByteArray(bb3, 0, bb3.length));
//            i4.setImageBitmap(BitmapFactory.decodeByteArray(bb4, 0, bb4.length));
//            System.out.println("Answer" + answer_);
//            answer = answer_;
//
//
//



        if (sp1.contains("1")) {
            if (sp1.contains(answer)) {
                System.out.println("Answer is correct");
                i1.setBackgroundColor(Color.parseColor("#66ff33"));
                score++;
                _ttsaido.speak("Correct answer.You played well");


            }
            if (!sp1.contains(answer)) {

                i1.setBackgroundColor(Color.parseColor("#ff3300"));
                if (answer.contains("2")) {
                    i2.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("3")) {
                    i3.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("4")) {
                    i4.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                _ttsaido.speak("Sorry That was wrong");

            }
        }
        if (sp1.contains("2")) {
            if (sp1.contains(answer)) {
                System.out.println("Answer is correct");
                i2.setBackgroundColor(Color.parseColor("#66ff33"));
                score++;
                _ttsaido.speak("Correct answer.You played well");
//
            }
            if (!sp1.contains(answer)) {
                i2.setBackgroundColor(Color.parseColor("#ff3300"));
                if (answer.contains("1")) {
                    i1.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("3")) {
                    i3.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("4")) {
                    i4.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                _ttsaido.speak("Sorry . That was wrong");

            }
        }
        if (sp1.contains("3")) {
            if (sp1.contains(answer)) {
                System.out.println("Answer is correct");
                i3.setBackgroundColor(Color.parseColor("#66ff33"));
                score++;
                _ttsaido.speak("Correct answer.You played well");
//
            }
            if (!sp1.contains(answer)) {
                i3.setBackgroundColor(Color.parseColor("#ff3300"));
                if (answer.contains("1")) {
                    i1.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("2")) {
                    i2.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("4")) {
                    i4.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                _ttsaido.speak("Sorry . That was wrong");

            }
        }
        if (sp1.contains("4")) {

            if (sp1.contains(answer)) {
                System.out.println("Answer is correct");
                i4.setBackgroundColor(Color.parseColor("#66ff33"));
                score++;
                _ttsaido.speak("Correct answer.You played well");
//
            }
            if (!sp1.contains(answer)) {
                i4.setBackgroundColor(Color.parseColor("#ff3300"));

                if (answer.contains("1")) {
                    i1.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("2")) {
                    i2.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                if (answer.contains("3")) {
                    i3.setBackgroundColor(Color.parseColor("#66ff33"));
                }
                _ttsaido.speak("Sorry . That was wrong");

            }
        }
        System.out.println("updated score"+score);
//        _ttsaido.speak("Here is your next question");




//        try {
//            Thread.sleep(5000);
//           // finish();
//
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game2");
//                intent1.putExtra("game","2");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent1);
//            }
//        }, 5000);
//        SystemClock.sleep(3000);
//        Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game2");
//                intent1.putExtra("game","2");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent1);


//        finish();


        String path1 = Environment.getExternalStorageDirectory().toString() + "/Pictures/gameImages/" + sp;

        File directory1 = new File(path1);
        File[] files1 = directory1.listFiles();
        for (int j = 0; j < 4; j++) {
            Log.d("Files", "FileName:" + files1[j].getName());
//            images1[i] = files1[i].getName();
            File imgFile = new File("/storage/emulated/0/Pictures/gameImages/" + sp + "/" + files1[j].getName());
            if (imgFile.exists()) {
//
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = findViewById(R.id.image + (j + 1));
                myImage.setImageBitmap(myBitmap);



            }
        }
        ContentValues cv1 = new ContentValues();
        cv1.put("score", score);
        db1.update("questionNumber", cv1, "rno=" +1, null);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        Intent intent1 = new Intent("com.whitesun.aidoface.Memory_game4");
                        intent1.putExtra("qno", q);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                    }
                },
                5000
        );


    }
}






//}


