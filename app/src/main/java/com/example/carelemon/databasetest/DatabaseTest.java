package com.example.carelemon.databasetest;

import android.app.Application;
import android.content.Context;


public class DatabaseTest extends Application {

    public static Context context;

    public void onCreate() {

        super.onCreate();
        DatabaseTest.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
