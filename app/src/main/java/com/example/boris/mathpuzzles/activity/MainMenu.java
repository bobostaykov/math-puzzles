//comments ok
package com.example.boris.mathpuzzles.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.help.Global;

import java.util.Locale;

public class MainMenu extends AppCompatActivity {

    private static boolean localeChanged = false, soundSettingChanged = false, themeChanged = false, firstTime = true;
    private SoundPool soundPool;
    private int buttonSoundID = 0;
    private Global global = new Global();
    private Settings settings = new Settings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavStatBar();

        setLocale(Global.getLocale());
        //restart the activity only when the app is launched so the saved language setting (SharedPreferences) appears
        if (firstTime) {
            firstTime = false;
            restartMain();
        }

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        //if sound is off the buttonSoundID stays 0 and no sound will be played on button touch
        if (Settings.getSoundOn()) buttonSoundID = soundPool.load(this, R.raw.button_click_1, 1);

        if (Global.getButtonThemeDark()) applyDarkTheme();
        else applyLightTheme();
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavStatBar();
        //so that the changes appear in the main activity
        if (localeChanged || soundSettingChanged || themeChanged) {
            localeChanged = false;
            soundSettingChanged = false;
            themeChanged = false;
            restartMain();
        }
    }


    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res;
        res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        Global.setLocale(lang);
    }


    //entering fullscreen
    private void hideNavStatBar() {
        View decorView = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }


    //when "New Game" button has been pressed
    public void switchToNewGameActivity(View v) {
        global.playSound(soundPool, buttonSoundID);
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }


    //when "Settings" button has been pressed
    public void switchToSettingsActivity(View v) {
        global.playSound(soundPool, buttonSoundID);
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


    //when "Exit" button has been pressed
    public void quitApp(View v) {
        global.playSound(soundPool, buttonSoundID);
        finishAndRemoveTask();
    }


    public void restartMain() {
        finish();
        startActivity(new Intent(this, MainMenu.class));
        //no transition animation
        overridePendingTransition(0,0);
    }


    public static void setLocaleChanged() {
        localeChanged = true;
    }


    public static void setSoundSettingChanged() {
        soundSettingChanged = true;
    }


    public static void setThemeChanged() {
        themeChanged = true;
    }


    private void applyDarkTheme() {
        //to all buttons on the main screen

        Button button1 = findViewById(R.id.new_game_btn);
        button1.setTextColor(getResources().getColor(R.color.white));
        button1.setBackgroundResource(R.drawable.mybutton_dark);

        Button button2 = findViewById(R.id.settings_btn);
        button2.setTextColor(getResources().getColor(R.color.white));
        button2.setBackgroundResource(R.drawable.mybutton_dark);

        Button button3 = findViewById(R.id.exit_btn);
        button3.setTextColor(getResources().getColor(R.color.white));
        button3.setBackgroundResource(R.drawable.mybutton_dark);
    }


    private void applyLightTheme() {
        //to all buttons on the main screen
        Button button1 = findViewById(R.id.new_game_btn);
        button1.setTextColor(getResources().getColor(R.color.colorAccent));
        button1.setBackgroundResource(R.drawable.mybutton_light);

        Button button2 = findViewById(R.id.settings_btn);
        button2.setTextColor(getResources().getColor(R.color.colorAccent));
        button2.setBackgroundResource(R.drawable.mybutton_light);

        Button button3 = findViewById(R.id.exit_btn);
        button3.setTextColor(getResources().getColor(R.color.colorAccent));
        button3.setBackgroundResource(R.drawable.mybutton_light);
    }


    //when the "rate" star button has been pressed
    public void goToGameUrl(View v) {
        global.playSound(soundPool, buttonSoundID);
        Toast.makeText(this, R.string.toast_rate, Toast.LENGTH_LONG).show();
                                /*    change url with game url    */
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome"));
        startActivity(browse);
    }


}
