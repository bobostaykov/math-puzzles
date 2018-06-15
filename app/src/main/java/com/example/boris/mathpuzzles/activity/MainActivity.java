package com.example.boris.mathpuzzles.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.boris.mathpuzzles.R;

public class MainActivity extends AppCompatActivity {

    private static boolean localeChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavStatBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavStatBar();
        //otherwise the changed language does not appear in the main activity
        if (localeChanged) {
            localeChanged = false;
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
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }


    //when "Settings" button has been pressed
    public void switchToSettingsActivity(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


    //when "Exit" button has been pressed
    public void quitApp(View v) {
        finishAndRemoveTask();
    }


    public void restartMain() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0,0);
    }


    public static void setLocaleChanged() {
        localeChanged = true;
    }


}
