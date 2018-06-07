package com.example.boris.mathpuzzles;

import android.app.Application;
import android.content.Context;

//to be able to get context from non-activity classes
public class App extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}