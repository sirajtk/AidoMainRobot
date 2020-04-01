package com.whitesun.aidoface;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class memoryDatabase extends SQLiteOpenHelper {

    static final int version = 1;

    private static final String dbname = "game";
//    private static final String table_name1 = "memoryGame";
    private static final String table_name2 = "questionNumber";

    public memoryDatabase(Context context) {

        super(context, dbname, null, version);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("create table memoryGame (rno INTEGER PRIMARY KEY AUTOINCREMENT,image1 BLOB,image2 BLOB,image3 BLOB,image4 BLOB,question varchar(50)," +
//                "answer varchar(20),score INTEGER)");
        db.execSQL("create table questionNumber(rno INTEGER,qno INTEGER,score INTEGER)");
        ContentValues contentValues = new ContentValues();
        contentValues.put("rno","1");
        contentValues.put("qno","1");
        contentValues.put("score","1");
        db.insert("questionNumber", null, contentValues);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("drop table if exists " + table_name1);
        db.execSQL("drop table if exists " + table_name2);
        onCreate(db);

    }




}
