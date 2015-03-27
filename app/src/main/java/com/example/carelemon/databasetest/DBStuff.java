package com.example.carelemon.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

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

    // get all mp3s
    public ArrayList<HashMap<String, String>> getAllMp3s() {

        ArrayList<HashMap<String, String>> mp3ArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM mp3database";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {

            do {

                HashMap<String, String> mp3Map = new HashMap<String, String>();

                mp3Map.put("UID", cursor.getString(0));    // "cursor.getString(0)" is the first column of the DB (mp3ID)
                mp3Map.put("Composer", cursor.getString(1));
                mp3Map.put("Piece", cursor.getString(2));
                mp3Map.put("Work", cursor.getString(3));
                mp3Map.put("Genre", cursor.getString(4));

                mp3ArrayList.add(mp3Map);


            } while(cursor.moveToNext());

        }

        return mp3ArrayList;

    }

    public HashMap<String, String> getMp3Info(String id) {

        HashMap<String, String> mp3Map = new HashMap<String, String>();

        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM mp3s WHERE UID='" + id + "'";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {

                mp3Map.put("UID", cursor.getString(0));    // "cursor.getString(0)" is the first field of the DB (mp3ID)
                mp3Map.put("Composer", cursor.getString(1));
                mp3Map.put("Piece", cursor.getString(2));
                mp3Map.put("Work", cursor.getString(3));
                mp3Map.put("Genre", cursor.getString(4));


            } while (cursor.moveToNext());

        }

        return mp3Map;
    }

    //delete mp3
    public void deleteMp3(String id) {

        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM mp3database WHERE UID='" + id + "'";

        database.execSQL(deleteQuery);

    }

    public int updateMp3(HashMap<String, String> queryValues) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("UID", queryValues.get("UID"));
        values.put("Composer", queryValues.get("Composer"));
        values.put("Piece", queryValues.get("Piece"));
        values.put("Work", queryValues.get("Work"));
        values.put("Genre", queryValues.get("Genre"));


        return database.update("mp3database", values, "UID" + " = ?", new String[] {queryValues.get("UID")});

    }

}
