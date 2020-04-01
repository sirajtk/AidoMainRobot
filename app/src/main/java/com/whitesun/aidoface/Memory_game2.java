package com.whitesun.aidoface;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.whitesuntech.aidohomerobot.R;

import java.io.File;
import java.util.ArrayList;

import aido.TextToSpeech.BroadcastTTS;

public class Memory_game2 extends AppCompatActivity {
    BroadcastTTS _ttsaido;
    ArrayList<String> dataa = new ArrayList<>();
    ArrayList<byte[]> image = new ArrayList<>();
    //memoryDatabase md = new memoryDatabase(this);
    memoryDatabase mydb = new memoryDatabase(this);
    String[] data;
    ImageView i1, i2, i3, i4;
    String answer;
//   int score;
   String q,sp;
   String[] spp;
    String ccc;
    String question;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game2);
        _ttsaido = new BroadcastTTS(Memory_game2.this);

        i1 = findViewById(R.id.image1);
        i2 = findViewById(R.id.image2);
        i3 = findViewById(R.id.image3);
        i4 = findViewById(R.id.image4);

        Intent intent = getIntent();
        q =  intent.getStringExtra("qno");
        spp = q.split(",");
        sp = spp[2];
        System.out.println("game num"+sp);

//        SQLiteDatabase db = mydb.getWritableDatabase();

//        while (cursor.moveToNext()) {
//            data = new String[]{cursor.getString(0), cursor.getString(5), cursor.getString(6), cursor.getString(7)};
//            //dataa.add(String.valueOf(Arrays.asList(data)));
//            image.add(cursor.getBlob(1));
//            image.add(cursor.getBlob(2));
//            image.add(cursor.getBlob(3));
//            image.add(cursor.getBlob(4));
//            System.out.println("imagecount"+image.get(0));
//
//            String question = data[1];
//            _ttsaido.speak(question);
//            // String answer_ = data[2];
//            byte[] bb1 = image.get(0);
//            byte[] bb2 = image.get(1);
//            byte[] bb3 = image.get(2);
//            byte[] bb4 = image.get(3);
//            System.out.println("after image bb");
////            t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
////                @Override
////                public void onInit(int status) {
////                    if(status == TextToSpeech.SUCCESS) {
////                     //   t1.setLanguage(Locale.UK);
////
////                    }
////                }
////            });
////
//
////            t1.speak("here is your question", TextToSpeech.QUEUE_FLUSH, null);
////            t1.speak(question, TextToSpeech.QUEUE_FLUSH, null);
//
//            System.out.println("bbcount"+bb1);
//            i1.setImageBitmap(BitmapFactory.decodeByteArray(bb1, 0, bb1.length));
//            i2.setImageBitmap(BitmapFactory.decodeByteArray(bb2, 0, bb2.length));
//            i3.setImageBitmap(BitmapFactory.decodeByteArray(bb3, 0, bb3.length));
//            i4.setImageBitmap(BitmapFactory.decodeByteArray(bb4, 0, bb4.length));
//        }
//        System.out.println("sp"+sp);
//        String selectQuery1 = "UPDATE questionNumber SET qno ='"+sp+"' where rno=1";
//        db.rawQuery(selectQuery1, null);


        String path1 = Environment.getExternalStorageDirectory().toString()+"/Pictures/gameImages/"+sp;
        Log.d("Files", "Path1: " + path1);
        File directory1 = new File(path1);
        File[] files1 = directory1.listFiles();
//        Log.d("Files", "Size: "+ files.length);
        String[] images1 = new String[20];
        for (int j = 0; j < files1.length; j++)
        {
            Log.d("Files", "FileName:" + files1[j].getName());
//            images1[i] = files1[i].getName();
            String qname = files1[1].getName();
            String[] qnamee = qname.split(",");
            question = qnamee[1];
            String c = qnamee[2];
            String[] cc = c.split("\\.");
            ccc = cc[0];

            File imgFile = new File("/storage/emulated/0/Pictures/gameImages/"+sp+"/"+files1[j].getName());
//            qnum = (String)mStringArray[0];

            if (imgFile.exists()) {
//
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = findViewById(R.id.image+(j+1));
                myImage.setImageBitmap(myBitmap);



                i1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        System.out.println("button 1");
                        Intent intent1 = new Intent("com.whitesun.aidoface.answer2");
                        intent1.putExtra("qno",q);
                        intent1.putExtra("selected","1");
                        intent1.putExtra("answer",ccc);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);

                    }
                });
                i2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        System.out.println("button 1");
                        Intent intent1 = new Intent("com.whitesun.aidoface.answer2");
                        intent1.putExtra("qno",q);
                        intent1.putExtra("selected","2");
                        intent1.putExtra("answer",ccc);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                    }
                });
                i3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        System.out.println("button 1");
                        Intent intent1 = new Intent("com.whitesun.aidoface.answer2");
                        intent1.putExtra("qno",q);
                        intent1.putExtra("selected","3");
                        intent1.putExtra("answer",ccc);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                    }
                });
                i4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        System.out.println("button 1");
                        Intent intent1 = new Intent("com.whitesun.aidoface.answer2");
                        intent1.putExtra("qno",q);
                        intent1.putExtra("selected","4");
                        intent1.putExtra("answer",ccc);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                    }
                });



            }
        }
        _ttsaido.speak("question number two");
        _ttsaido.speak(question);
//        ContentValues cv = new ContentValues();
//        cv.put("qno",sp);
//        db.update("questionNumber", cv, "rno="+1, null);

    }











    }


