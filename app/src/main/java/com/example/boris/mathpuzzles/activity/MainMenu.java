package com.example.boris.mathpuzzles.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.help.Global;

public class MainMenu extends AppCompatActivity {

    private static boolean localeChanged = false;
    private static boolean soundSettingChanged = false;
    private SoundPool soundPool;
    private int soundID = 0;
    private Global global = new Global();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavStatBar();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        if (Settings.getSoundOn()) soundID = soundPool.load(this, R.raw.button_click_1, 1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavStatBar();
        //otherwise the changed language does not appear in the main activity
        if (localeChanged || soundSettingChanged) {
            localeChanged = false;
            soundSettingChanged = false;
            restartMain();
        }
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
        global.playSound(soundPool, soundID);
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }


    //when "Settings" button has been pressed
    public void switchToSettingsActivity(View v) {
        global.playSound(soundPool, soundID);
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


    //when "Exit" button has been pressed
    public void quitApp(View v) {
        global.playSound(soundPool, soundID);
        finishAndRemoveTask();
    }


    public void restartMain() {
        finish();
        startActivity(new Intent(this, MainMenu.class));
        overridePendingTransition(0,0);
    }


    public static void setLocaleChanged() {
        localeChanged = true;
    }


    public static void setSoundSettingChanged() {
        soundSettingChanged = true;
    }


}
