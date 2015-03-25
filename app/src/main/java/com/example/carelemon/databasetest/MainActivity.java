package com.example.carelemon.databasetest;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;


class Mp3Filter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String filename) {
        return filename.endsWith(".mp3");
    }
}



public class MainActivity extends ListActivity {


    MediaMetadataRetriever tagRetriever = new MediaMetadataRetriever();
    byte[] art; // xxxxx

    private MediaPlayer mediaPlayer;
    private static final String MUSIC_PATH =
            Environment.getExternalStoragePublicDirectory("").getAbsolutePath() + "/Music/";
    private List<String> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        songList = new ArrayList<>();

        //get mp3 files and populate them into list view
        updatePlaylist();
    }

    public void updatePlaylist()
    {
        File directory = new File(MUSIC_PATH);
        if(directory.listFiles(new Mp3Filter()).length > 0)
        {
            for(File f : directory.listFiles(new Mp3Filter()))
            {
                songList.add(f.getName());

            }

            ArrayAdapter<String> songAdapter = new ArrayAdapter<String>(this, R.layout.mp3list,songList);
            setListAdapter(songAdapter);
        }
    }


    // To play/display mp3's tags in 'Pieces' screen
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id)
    {


        TextView composer = (TextView) findViewById(R.id.composerTagInfo);
        TextView work = (TextView) findViewById(R.id.workTagInfo);
        TextView piece = (TextView) findViewById(R.id.pieceTagInfo);
        ImageView album_art = (ImageView) findViewById(R.id.albumArt); // xxxxx

        try {

            mediaPlayer.reset();
            mediaPlayer.setDataSource(MUSIC_PATH + songList.get(position));

            mediaPlayer.prepare();
            mediaPlayer.start();


            // retrieving ID3 tags and displaying them on the screen
            tagRetriever.setDataSource(MUSIC_PATH + songList.get(position));

            composer.setText(tagRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER));
            work.setText(tagRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            piece.setText(tagRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));

            // retrieving ID3 artwork
            art = tagRetriever.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            album_art.setImageBitmap(songImage);


        } catch (Exception e)
        {
            Log.e("error", "error in on list item click");
        }
    }


    }







