package com.example.carelemon.databasetest;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;

public class FillDB extends DBStuff{


    public FillDB(Context c) {
       super(c);
    }

    public void populateDB() {

        final String MUSIC_PATH =
                Environment.getExternalStoragePublicDirectory("").getAbsolutePath() + "/Music/";

        File directory = new File(MUSIC_PATH);
        if (directory.listFiles(new Mp3Filter()).length > 0) {
            for (File f : directory.listFiles(new Mp3Filter())) {
                Log.d("file", f.getAbsolutePath());
                MediaMetadataRetriever tagRetriever = new MediaMetadataRetriever();
                tagRetriever.setDataSource(f.getAbsolutePath());


                String composer, piece, work, genre;

                composer = tagRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER);
                piece = tagRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                work = tagRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                genre = tagRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);


                HashMap<String, String> record = new HashMap<>();
                if (composer != null && !composer.equals("") &&
                        piece != null && !piece.equals("")) {
                    record.put(COMPOSER, composer);
                    record.put(PIECE, piece);
                    record.put(WORK, work);
                    record.put(GENRE, genre);
                    record.put(LOCATION,f.getAbsolutePath());
                    insertMp3(record);

                }
            }
        }
    }
}
