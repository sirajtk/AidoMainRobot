package com.whitesun.aidoface;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.whitesuntech.aidohomerobot.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import aido.TextToSpeech.BroadcastTTS;

public class Memory_game1 extends AppCompatActivity {
    BroadcastTTS _ttsaido;
    ArrayList<String> dataa = new ArrayList<>();
    ArrayList<byte[]> image = new ArrayList<>();
    //memoryDatabase md = new memoryDatabase(this);
    memoryDatabase mydb = new memoryDatabase(this);
    String[] data;
    ImageView i1, i2, i3, i4;
    String answer;
    int score;
    ImageButton b1,b2,b3,b4;
    ArrayList<String> aListColors
            = new ArrayList<String>();
    String color;
    String[] gameValue = new String[5];
    String qnum;
    String qlist="0";
    String ccc;
    String question;
    int count = 0;
    static Memory_game1 memory_game1;





    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game1);
        _ttsaido = new BroadcastTTS(Memory_game1.this);
        memory_game1 = this;

        i1 = findViewById(R.id.image1);
        i2 = findViewById(R.id.image2);
        i3 = findViewById(R.id.image3);
        i4 = findViewById(R.id.image4);


//        b1 = findViewById(R.id.button2);
//        b2 = findViewById(R.id.button4);
//        b3 = findViewById(R.id.button5);
//        b4 = findViewById(R.id.button6);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view) {
//                System.out.println("button 1");
//                Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
//                intent1.putExtra("answer","1");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent1);
//            }
//        });
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view) {
//                System.out.println("button 2");
//                Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
//                intent1.putExtra("answer","2");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent1);
//            }
//        });
//        b3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view) {
//                System.out.println("button 3");
//                Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
//                intent1.putExtra("answer","3");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent1);
//            }
//        });
//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view) {
//                System.out.println("button 4");
//                Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
//                intent1.putExtra("answer","4");
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent1);
//            }
//        });
//
//        Intent intent = getIntent();
//        String sp = intent.getStringExtra("game");
////        String sp="1";
//        System.out.println("game num"+sp);
//
        SQLiteDatabase db = mydb.getWritableDatabase();
//        String selectQuery = "SELECT  rno,image1,image2,image3,image4,question,answer,score FROM memoryGame where rno='" + sp + "'";
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        System.out.println("under curser" + cursor.getColumnCount());
//
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
            _ttsaido.speak("Here is your question number one");



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
//        String selectQuery1 = "UPDATE questionNumber SET qno ='"+qnum+"' where rno=1";
//        db.rawQuery(selectQuery1, null);
        ContentValues cv = new ContentValues();
//        cv.put("qno",);
        cv.put("score","0");
        db.update("questionNumber", cv, "rno="+1, null);
//



        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures/gameImages";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        String[] images = new String[20];
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
            System.out.println("total list"+files[i].getName());
            images[i] = files[i].getName();
            aListColors.add(files[i].getName());

        }



//shuffle the list and get elements from it
        Collections.shuffle( aListColors );



//           for (String s:aListColors) {
//               System.out.println("values" + s);;
//
//           }
        String[] mStringArray = new String[aListColors.size()];
        mStringArray = aListColors.toArray(mStringArray);

        for(int i = 0; i < 5 ; i++) {
            Log.d("string is", mStringArray[i]);
            qlist = qlist + "," + mStringArray[i];
            System.out.println("qlist" + qlist);
        }

        String path1 = Environment.getExternalStorageDirectory().toString()+"/Pictures/gameImages/"+ mStringArray[0];
        Log.d("Files", "game 1 path: " + path1);
        File directory1 = new File(path1);
        File[] files1 = directory1.listFiles();
//        Log.d("Files", "Size: "+ files.length);
//        String[] images1 = new String[20];
        for (int j = 0; j < files1.length; j++)
        {
            Log.d("Files", "FileName:" + files1[j].getName());
            String qname = files1[1].getName();
            String[] qnamee = qname.split(",");
             question = qnamee[1];
            System.out.println("question"+question);
            String c = qnamee[2];
            System.out.println("c"+c);
            String[] cc = c.split("\\.");
              ccc = cc[0];
            System.out.println("ccc"+ccc);



//            images1[i] = files1[i].getName();
            File imgFile = new File("/storage/emulated/0/Pictures/gameImages/"+ mStringArray[0] +"/"+files1[j].getName());
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
                Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
                intent1.putExtra("qno",qlist);
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
                        Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
                        intent1.putExtra("qno",qlist);
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
                        Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
                        intent1.putExtra("qno",qlist);
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
                        Intent intent1 = new Intent("com.whitesun.aidoface.answer1");
                        intent1.putExtra("qno",qlist);
                        intent1.putExtra("selected","4");
                        intent1.putExtra("answer",ccc);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent1);
                    }
                });



             }
        }
//            if (count==1)
//            {
                _ttsaido.speak(question);
//            }



//        }




//        String[] gameNumber = new String[20];
////        String[] text =
//        System.out.println("list"+images);

//         for (int i=0;i<files.length;i++) {
//             System.out.println("folder name" + images[i] + "\n");
//
//             File imgFile = new File("/storage/emulated/0/Pictures/gameImages/"+files[i].getName());
//
//             if (imgFile.exists()) {
//
//                 Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//                 ImageView myImage = (ImageView) findViewById(R.id.image+(i+1));
//
//                 myImage.setImageBitmap(myBitmap);
//
//             }
//         }

    }
    public static Memory_game1 getInstance(){
        return   memory_game1;
    }


}


