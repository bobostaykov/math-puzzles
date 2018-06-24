package com.example.boris.mathpuzzles.help;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.activity.Game;
import com.example.boris.mathpuzzles.activity.MainMenu;
import com.example.boris.mathpuzzles.activity.Settings;

import java.util.Locale;

//to be able to get context from non-activity classes
public class Global extends Application {

    private static Application instance;
    private static int boardColumns, screenHeight, screenWidth;
    private static Context gameContext;
    private static boolean buttonThemeDark;
    private static String locale;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//        MainMenu main = new MainMenu();
//        main.applySavedSettings();
        applySavedSettings();
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

    public static void setScreenSize(int height, int width) {
        screenHeight = height;
        screenWidth = width;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setButtonThemeDark(boolean dark) {
        buttonThemeDark = dark;
    }

    public static boolean getButtonThemeDark() {
        return buttonThemeDark;
    }


    public static void createDialog(int dialogTitle, String dialogMessage, int negativeButton, View.OnClickListener whatToDoWhenNeg, int neutralButton, View.OnClickListener whatToDoWhenNeut, int positiveButton, View.OnClickListener whatToDoWhenPos) {

        Dialog dialog = new Dialog(gameContext);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.mydialog);
        double dialogWidth = screenWidth*0.85;
        //double dialogHeight = dialogWidth*0.83*0;
        dialog.getWindow().setLayout((int) dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView title = dialog.findViewById(R.id.dialog_title);
        title.setText(dialogTitle);

        TextView message = dialog.findViewById(R.id.dialog_message);
        message.setText(dialogMessage);

        Button positive = dialog.findViewById(R.id.dialog_positive_btn);
        positive.setText(positiveButton);
        positive.setOnClickListener(whatToDoWhenPos);

        Button neutral = dialog.findViewById(R.id.dialog_neutral_btn);
        neutral.setText(neutralButton);
        neutral.setOnClickListener(whatToDoWhenNeut);

        Button negative = dialog.findViewById(R.id.dialog_negative_btn);
        negative.setText(negativeButton);
        negative.setOnClickListener(whatToDoWhenNeg);

    }

    public static void setLocale(String lang) {
        locale = lang;
    }

    public static String getLocale() {
        return locale;
    }

    public void playSound(SoundPool soundPool, final int soundID) {
        // Getting the user sound settings
        AudioManager audioManager = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final float volume = actualVolume / maxVolume;
        soundPool.play(soundID, volume, volume, 1, 0, 1f);
    }


    public void applySavedSettings() {
        Settings settings = new Settings();
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.shared_prefs_file_name), Context.MODE_PRIVATE);

        locale = sharedPrefs.getString(getString(R.string.key_locale), getString(R.string.default_locale));
        settings.setSoundOn(sharedPrefs.getBoolean(getString(R.string.key_sound), getResources().getBoolean(R.bool.default_sound)));
        settings.setForTimeOn(sharedPrefs.getBoolean(getString(R.string.key_time), getResources().getBoolean(R.bool.default_time)));
        Global.setButtonThemeDark(sharedPrefs.getBoolean(getString(R.string.key_theme), getResources().getBoolean(R.bool.default_theme_dark)));
    }

}