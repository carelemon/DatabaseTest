package com.example.carelemon.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class DBTools extends SQLiteOpenHelper {

   public DBTools(Context applicationContext) {

       super(applicationContext, "mp3.db", null, 1);
   }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String query = "CREATE TABLE mp3s (mp3ID INTEGER PRIMARY KEY, composer TEXT, piece TEXT, work TEXT, genre TEXT)";

        database.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS mp3s";

        database.execSQL(query);
        onCreate(database);

    }

    public void insertMp3(HashMap<String, String> queryValues) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("mp3ID", queryValues.get("mp3ID"));
        values.put("composer", queryValues.get("composer"));
        values.put("piece", queryValues.get("piece"));
        values.put("work", queryValues.get("work"));
        values.put("genre", queryValues.get("genre"));

        database.insert("mp3s", null, values);

        database.close();

    }


    public int updateMp3(HashMap<String, String> queryValues) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("mp3ID", queryValues.get("mp3ID"));
        values.put("composer", queryValues.get("composer"));
        values.put("piece", queryValues.get("piece"));
        values.put("work", queryValues.get("work"));
        values.put("genre", queryValues.get("genre"));


        return database.update("mp3s", values, "mp3ID" + " = ?", new String[] {queryValues.get("mp3ID")});

    }

    //delete mp3
    public void deleteMp3(String id) {

        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM mp3s WHERE mp3ID='" + id + "'";

        database.execSQL(deleteQuery);

    }

    // get all mp3s
    public ArrayList<HashMap<String, String>> getAllMp3s() {

        ArrayList<HashMap<String, String>> mp3ArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM mp3s";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {

           do {

               HashMap<String, String> mp3Map = new HashMap<String, String>();

               mp3Map.put("mp3ID", cursor.getString(0));    // "cursor.getString(0)" is the first column of the DB (mp3ID)
               mp3Map.put("composer", cursor.getString(1));
               mp3Map.put("piece", cursor.getString(2));
               mp3Map.put("work", cursor.getString(3));

               mp3ArrayList.add(mp3Map);


           } while(cursor.moveToNext());

        }

        return mp3ArrayList;

    }

    // get just one mp3
    public HashMap<String, String> getMp3Info(String id) {

        HashMap<String, String> mp3Map = new HashMap<String, String>();

        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM mp3s WHERE mp3ID='" + id + "'";

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {

                mp3Map.put("mp3ID", cursor.getString(0));    // "cursor.getString(0)" is the first field of the DB (mp3ID)
                mp3Map.put("composer", cursor.getString(1));
                mp3Map.put("piece", cursor.getString(2));
                mp3Map.put("work", cursor.getString(3));
                mp3Map.put("genre", cursor.getString(4));


            } while (cursor.moveToNext());

        }

        return mp3Map;
    }
}
