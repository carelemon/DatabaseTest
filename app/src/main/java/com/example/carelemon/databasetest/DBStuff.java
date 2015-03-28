package com.example.carelemon.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBStuff extends SQLiteOpenHelper{

    private static  final String DATABASE_NAME = "mp3database";
    private static  final String TABLE_NAME = "MP3TABLE";
    private static  final int DATABASE_VERSION = 1;
    protected static  final String UID = "_id";
    protected static  final String COMPOSER = "Composer";
    protected static  final String PIECE = "Piece";
    protected static  final String WORK = "Work";
    protected static  final String GENRE = "Genre";
    protected static final String LOCATION = "location";
    private static  final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT,Composer TEXT, Piece TEXT, Work TEXT, Genre TEXT);";
    private static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBStuff(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // ctr+alt+T for "surround with try/catch exception"
        try {

            Log.d("message","called");
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropTable() {
        getWritableDatabase().execSQL(DROP_TABLE);
        onCreate(getWritableDatabase());
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

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {

            do {

                HashMap<String, String> mp3Map = new HashMap<String, String>();

                mp3Map.put(UID, cursor.getString(0));    // "cursor.getString(0)" is the first column of the DB (mp3ID)
                mp3Map.put(COMPOSER, cursor.getString(1));
                mp3Map.put(PIECE, cursor.getString(2));
                mp3Map.put(WORK, cursor.getString(3));
                mp3Map.put(GENRE, cursor.getString(4));
                mp3Map.put(LOCATION,cursor.getString(5));

                mp3ArrayList.add(mp3Map);


            } while(cursor.moveToNext());

        }

        return mp3ArrayList;

    }

    public HashMap<String, String> getMp3Info(String id) {



        HashMap<String, String> mp3Map = new HashMap<String, String>();

        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM MP3TABLE WHERE UID ='" + id + "'";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {

                mp3Map.put(UID, cursor.getString(0));    // "cursor.getString(0)" is the first field of the DB (mp3ID)
                mp3Map.put(COMPOSER, cursor.getString(1));
                mp3Map.put(PIECE, cursor.getString(2));
                mp3Map.put(WORK, cursor.getString(3));
                mp3Map.put(GENRE, cursor.getString(4));
                mp3Map.put(LOCATION, cursor.getString(5));
                //where you put everything here...

            } while (cursor.moveToNext());

        }

        return mp3Map;
    }

    //delete mp3
    public void deleteMp3(String id) {

        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM MP3TABLE WHERE UID='" + id + "'";

        database.execSQL(deleteQuery);

    }

    public int updateMp3(HashMap<String, String> queryValues) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UID, queryValues.get(UID));
        values.put(COMPOSER, queryValues.get(COMPOSER));
        values.put(PIECE, queryValues.get(PIECE));
        values.put(WORK, queryValues.get(WORK));
        values.put(GENRE, queryValues.get(GENRE));
        values.put(LOCATION, queryValues.get(LOCATION));

        return database.update(TABLE_NAME, values, UID + " = ?", new String[] {queryValues.get("UID")});

    }

    public void insertMp3(HashMap<String, String> queryValues) {

        Log.d("inserting",queryValues.get(GENRE));
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //it works now, try adding something that hasnt been added if its gonna increase then its done. Cool, how can I delete the db now?
        if(!isInRecord(queryValues.get(COMPOSER),queryValues.get(PIECE))) {
        values.put(UID, queryValues.get(UID));
        values.put(COMPOSER, queryValues.get(COMPOSER));
        values.put(PIECE, queryValues.get(PIECE));
        values.put(WORK, queryValues.get(WORK));
        values.put(GENRE, queryValues.get(GENRE));
            values.put(LOCATION, queryValues.get(LOCATION));
        database.insert(TABLE_NAME, null, values);

        database.close();
        }
            ///try running the app now


    }

    public boolean isInRecord(String author, String track) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c;
        c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE composer = '" + author + "' AND piece = '" + track + "'",null);

        if(c.getCount() > 0) {
            return true;
        }

        //try this. Where should I use this method now?
        return false;
    }
}
