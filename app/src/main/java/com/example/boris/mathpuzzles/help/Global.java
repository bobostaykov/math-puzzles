package com.example.boris.mathpuzzles.help;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;

import com.example.boris.mathpuzzles.activity.Settings;

import java.util.Locale;

//to be able to get context from non-activity classes
public class Global extends Application {

    private static Application instance;
    private static int boardColumns;
    private static Context gameContext;

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

    public static void setGameContext(Context con) {
        gameContext = con;
    }

    public static Context getGameContext() {
        return gameContext;
    }


    public static void createDialog(int dialogTitle, String dialogText, int negativeButton, DialogInterface.OnClickListener whatToDoWhenNeg, int neutralButton, DialogInterface.OnClickListener whatToDoWhenNeut, int positiveButton, DialogInterface.OnClickListener whatToDoWhenPos) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(gameContext);
        builder.setTitle(dialogTitle)
                .setMessage(dialogText)
                .setNegativeButton(negativeButton, whatToDoWhenNeg)
                .setPositiveButton(positiveButton, whatToDoWhenPos);
        //.setIcon(android.R.drawable.ic_dialog_alert)
        if (neutralButton != -1) builder.setNeutralButton(neutralButton, whatToDoWhenNeut);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res = getGlobalResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

    public void playSound(SoundPool soundPool, final int soundID) {
        // Getting the user sound settings
        AudioManager audioManager = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final float volume = actualVolume / maxVolume;
        soundPool.play(soundID, volume, volume, 1, 0, 1f);
    }
}