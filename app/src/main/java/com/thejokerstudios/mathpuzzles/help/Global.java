package com.thejokerstudios.mathpuzzles.help;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import com.thejokerstudios.mathpuzzles.R;
import com.thejokerstudios.mathpuzzles.activity.Game;
import com.thejokerstudios.mathpuzzles.activity.MainMenu;
import com.thejokerstudios.mathpuzzles.activity.Settings;

import java.util.Objects;

//a global class that always provides application context and other values when needed

public class Global extends Application {

    private static Application instance;
    private static int boardColumns, screenWidth, totalSounds = 0;
    private static boolean buttonThemeDark;
    private static String locale;
    /*
    currentImageTag is the (tag of the) image that is currently chosen and displayed.
    currentImageRealTag is the (tag of the) image that should be chosen and displayed.
    E.g. after pressing the button "restart", the activity is restarted and the image
    that should be chosen is the actual image of the activity before it was restarted.
    */
    private static int currentImageTag = 0, currentImageRealTag = 0;
    private Context gameContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applySavedSettings();
    }


    public void createDialog(boolean dismiss, int dialogTitle, String dialogMessage, int negativeButton, View.OnClickListener whatToDoWhenNeg, int neutralButton, View.OnClickListener whatToDoWhenNeut, int positiveButton, View.OnClickListener whatToDoWhenPos) {

        final Dialog dialog = new Dialog(gameContext);

        //to prevent the status bar from showing when the dialog appears
        Objects.requireNonNull(dialog.getWindow()).getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                                                                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                                                                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                                                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                                                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.mydialog);
        //setting dialog size relative to the device screen
        double dialogWidth = screenWidth*0.85;
        dialog.getWindow().setLayout((int) dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView title = dialog.findViewById(R.id.dialog_title);
        if (dialogTitle != 0) {
            title.setText(dialogTitle);
        } else {
            ((ViewManager)title.getParent()).removeView(title);
        }

        TextView message = dialog.findViewById(R.id.dialog_message);
        if (!dialogMessage.equals("")) {
            message.setText(dialogMessage);
        } else {
            ((ViewManager)message.getParent()).removeView(message);
        }

        Button positive = dialog.findViewById(R.id.dialog_positive_btn);
        if (whatToDoWhenPos != null) {
            positive.setText(positiveButton);
            positive.setOnClickListener(whatToDoWhenPos);
        } else {
            ((ViewManager)positive.getParent()).removeView(positive);
        }

        Button neutral = dialog.findViewById(R.id.dialog_neutral_btn);
        if (whatToDoWhenNeut != null) {
            neutral.setText(neutralButton);
            neutral.setOnClickListener(whatToDoWhenNeut);
        } else {
            ((ViewManager)neutral.getParent()).removeView(neutral);
        }

        Button negative = dialog.findViewById(R.id.dialog_negative_btn);
        if (whatToDoWhenNeg != null) {
            negative.setText(negativeButton);
            negative.setOnClickListener(whatToDoWhenNeg);
        } else if (dismiss) {
            negative.setText(negativeButton);
            negative.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (Settings.getSoundOn())
                        playSound(MainMenu.getSoundPool(), MainMenu.getButtonSoundID());
                    dialog.dismiss();
                    ((Game) gameContext).hideNavStatBar();
                }
            });
        } else {
            ((ViewManager)negative.getParent()).removeView(negative);
        }

    }


    public void playSound(SoundPool soundPool, final int soundID) {
        // Getting the user sound settings
        AudioManager audioManager = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) Objects.requireNonNull(audioManager).getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final float volume = actualVolume / maxVolume;
        soundPool.play(soundID, volume, volume, 1, 0, 1f);
        totalSounds++;
    }


    //configuring the settings saved from the last time the app was used
    public void applySavedSettings() {
        Settings settings = new Settings();
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.shared_prefs_file_name), Context.MODE_PRIVATE);

        locale = sharedPrefs.getString(getString(R.string.key_locale), getString(R.string.default_locale));
        settings.setSoundOn(sharedPrefs.getBoolean(getString(R.string.key_sound), getResources().getBoolean(R.bool.default_sound)));
        settings.setForTimeOn(sharedPrefs.getBoolean(getString(R.string.key_time), getResources().getBoolean(R.bool.default_time)));
        Global.setButtonThemeDark(sharedPrefs.getBoolean(getString(R.string.key_theme), getResources().getBoolean(R.bool.default_theme_dark)));
    }


    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static String getGlobalPackageName() {
        return getContext().getPackageName();
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

    public void setGameContext(Context con) {
        gameContext = con;
    }

    public static void setScreenWidth(int width) {
        screenWidth = width;
    }

    public static void setButtonThemeDark(boolean dark) {
        buttonThemeDark = dark;
    }

    public static boolean getButtonThemeDark() {
        return buttonThemeDark;
    }

    public static void setLocale(String lang) {
        locale = lang;
    }

    public static String getLocale() {
        return locale;
    }

    public static int getCurrentImageTag() {
        return currentImageTag;
    }

    public static void setCurrentImageTag(int newCurr) {
        currentImageTag = newCurr;
    }

    public static int getCurrentImageRealTag() {
        return currentImageRealTag;
    }

    public static void setCurrentImageRealTag(int newCurr) {
        currentImageRealTag = newCurr;
    }

    public static int getTotalSounds() {
        return totalSounds;
    }

}