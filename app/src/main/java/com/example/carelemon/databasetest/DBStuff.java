package com.example.carelemon.databasetest;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carelemon on 27/03/2015.
 */
public class DBStuff extends SQLiteOpenHelper{

    private static  final String DATABASE_NAME = "mp3database";
    private static  final String TABLE_NAME = "MP3TABLE";
    private static  final int DATABASE_VERSION = 1;
    private static  final String UID = "_id";
    private static  final String COMPOSER = "Composer";
    private static  final String PIECE = "Piece";
    private static  final String WORK = "Work";
    private static  final String GENRE = "Genre";
    private static  final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT,Composer TEXT, Piece TEXT, Work TEXT, Genre TEXT);";
    private static  final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + " IF EXISTS";

    public DBStuff(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ctr+alt+T for "surround with try catch exception"
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
