package com.example.boris.mathpuzzles;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

//to be able to get context from non-activity classes
public class Global extends Application {

    private static Application instance;
    private static int boardColumns;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static void setBoardColumns(int columns) {
        boardColumns = columns;
    }

    public static int getBoardColumns() {
        return boardColumns;
    }

    public static Resources getGlobalResources() {
        return instance.getResources();
    }

    public static String getGlobalPackageName() {
        return instance.getPackageName();
    }
}