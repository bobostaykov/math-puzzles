package com.thejokerstudios.mathpuzzles.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.thejokerstudios.mathpuzzles.R;
import com.thejokerstudios.mathpuzzles.datatype.AllLevels;
import com.thejokerstudios.mathpuzzles.datatype.Level;
import com.thejokerstudios.mathpuzzles.help.Global;

public class NewGame extends AppCompatActivity {

    //referencing buttons created in the activity_new_game.xml file
    private Button easy_board_btn,
                   medium_board_btn,
                   hard_board_btn,
                   easy_time_btn,
                   medium_time_btn,
                   hard_time_btn,
                   easy_math_btn,
                   medium_math_btn,
                   hard_math_btn;

    private boolean easy_board_btn_chosen = false,
                    medium_board_btn_chosen = false,
                    hard_board_btn_chosen = false,
                    easy_time_btn_chosen = false,
                    medium_time_btn_chosen = false,
                    hard_time_btn_chosen = false,
                    easy_math_btn_chosen = false,
                    medium_math_btn_chosen = false,
                    hard_math_btn_chosen = false;

    private SoundPool soundPool;
    private int soundID = 0;
    private Global global = new Global();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        hideNavStatBar();

        if (Global.getButtonThemeDark()) applyDarkTheme();
        else applyLightTheme();

        easy_board_btn = findViewById(R.id.easy_board_btn);
        medium_board_btn = findViewById(R.id.medium_board_btn);
        hard_board_btn = findViewById(R.id.hard_board_btn);
        easy_time_btn = findViewById(R.id.easy_time_btn);
        medium_time_btn = findViewById(R.id.medium_time_btn);
        hard_time_btn = findViewById(R.id.hard_time_btn);
        easy_math_btn = findViewById(R.id.easy_math_btn);
        medium_math_btn = findViewById(R.id.medium_math_btn);
        hard_math_btn = findViewById(R.id.hard_math_btn);
        TableLayout newGame_table = findViewById(R.id.NewGame_Table);
        TableRow timeText_Row = findViewById(R.id.timeText_Row);
        TableRow timeButtons_Row = findViewById(R.id.timeButtons_Row);
        TableRow mathButtons_row = findViewById(R.id.math_buttons_row);

        //so the user can control the volume of the sounds
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        //if sound is off the buttonSoundID stays 0 and no sound will be played on button touch
        if (Settings.getSoundOn()) soundID = soundPool.load(this, R.raw.button_click_1, 1);
        //if the user chose not to play for time, the NewGame screen is changed accordingly
        if (!Settings.getForTimeOn()) {
            newGame_table.removeView(timeText_Row);
            newGame_table.removeView(timeButtons_Row);
            TableRow.LayoutParams params = new TableRow.LayoutParams(mathButtons_row.getLayoutParams());
            params.setMargins(80, 0, 80, 350);
            mathButtons_row.setLayoutParams(params);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavStatBar();
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


    //level easy for board size chosen
    public void easyBoard(View v) {

        if (!easy_board_btn_chosen) global.playSound(soundPool, soundID);

        if (medium_board_btn_chosen) {
            if (Global.getButtonThemeDark()) medium_board_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else medium_board_btn.setBackgroundResource(R.drawable.mybutton_light);
            medium_board_btn_chosen = false;
        }

        if (hard_board_btn_chosen) {
            if (Global.getButtonThemeDark()) hard_board_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else hard_board_btn.setBackgroundResource(R.drawable.mybutton_light);
            hard_board_btn_chosen = false;
        }


        if (Global.getButtonThemeDark()) easy_board_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else easy_board_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        easy_board_btn_chosen = true;
        //saving the chosen level in the class AllLevels to access it later from Game
        AllLevels.setBoard(Level.EASY);
    }


    //level medium for board size chosen
    public void mediumBoard(View v) {

        if (!medium_board_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_board_btn_chosen) {
            if (Global.getButtonThemeDark()) easy_board_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else easy_board_btn.setBackgroundResource(R.drawable.mybutton_light);
            easy_board_btn_chosen = false;
        }

        if (hard_board_btn_chosen) {
            if (Global.getButtonThemeDark()) hard_board_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else hard_board_btn.setBackgroundResource(R.drawable.mybutton_light);
            hard_board_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) medium_board_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else medium_board_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        medium_board_btn_chosen = true;
        AllLevels.setBoard(Level.MEDIUM);
    }


    //level hard for board size chosen
    public void hardBoard(View v) {

        if (!hard_board_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_board_btn_chosen) {
            if (Global.getButtonThemeDark()) easy_board_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else easy_board_btn.setBackgroundResource(R.drawable.mybutton_light);
            easy_board_btn_chosen = false;
        }

        if (medium_board_btn_chosen) {
            if (Global.getButtonThemeDark()) medium_board_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else medium_board_btn.setBackgroundResource(R.drawable.mybutton_light);
            medium_board_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) hard_board_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else hard_board_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        hard_board_btn_chosen = true;
        AllLevels.setBoard(Level.HARD);
    }


    //level easy for time chosen
    public void easyTime(View v) {

        if (!easy_time_btn_chosen) global.playSound(soundPool, soundID);

        if (medium_time_btn_chosen) {
            if (Global.getButtonThemeDark()) medium_time_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else medium_time_btn.setBackgroundResource(R.drawable.mybutton_light);
            medium_time_btn_chosen = false;
        }

        if (hard_time_btn_chosen) {
            if (Global.getButtonThemeDark()) hard_time_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else hard_time_btn.setBackgroundResource(R.drawable.mybutton_light);
            hard_time_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) easy_time_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else easy_time_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        easy_time_btn_chosen = true;
        AllLevels.setTime(Level.EASY);
    }


    //level medium for time chosen
    public void mediumTime(View v) {

        if (!medium_time_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_time_btn_chosen) {
            if (Global.getButtonThemeDark()) easy_time_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else easy_time_btn.setBackgroundResource(R.drawable.mybutton_light);
            easy_time_btn_chosen = false;
        }

        if (hard_time_btn_chosen) {
            if (Global.getButtonThemeDark()) hard_time_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else hard_time_btn.setBackgroundResource(R.drawable.mybutton_light);
            hard_time_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) medium_time_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else medium_time_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        medium_time_btn_chosen = true;
        AllLevels.setTime(Level.MEDIUM);
    }


    //level hard for time chosen
    public void hardTime(View v) {

        if (!hard_time_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_time_btn_chosen) {
            if (Global.getButtonThemeDark()) easy_time_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else easy_time_btn.setBackgroundResource(R.drawable.mybutton_light);
            easy_time_btn_chosen = false;
        }

        if (medium_time_btn_chosen) {
            if (Global.getButtonThemeDark()) medium_time_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else medium_time_btn.setBackgroundResource(R.drawable.mybutton_light);
            medium_time_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) hard_time_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else hard_time_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        hard_time_btn_chosen = true;
        AllLevels.setTime(Level.HARD);
    }


    //level easy for math problems chosen
    public void easyMath(View v) {

        if (!easy_math_btn_chosen) global.playSound(soundPool, soundID);

        if (medium_math_btn_chosen) {
            if (Global.getButtonThemeDark()) medium_math_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else medium_math_btn.setBackgroundResource(R.drawable.mybutton_light);
            medium_math_btn_chosen = false;
        }

        if (hard_math_btn_chosen) {
            if (Global.getButtonThemeDark()) hard_math_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else hard_math_btn.setBackgroundResource(R.drawable.mybutton_light);
            hard_math_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) easy_math_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else easy_math_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        easy_math_btn_chosen = true;
        AllLevels.setMath(Level.EASY);
    }


    //level medium for math problems chosen
    public void mediumMath(View v) {

        if (!medium_math_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_math_btn_chosen) {
            if (Global.getButtonThemeDark()) easy_math_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else easy_math_btn.setBackgroundResource(R.drawable.mybutton_light);
            easy_math_btn_chosen = false;
        }

        if (hard_math_btn_chosen) {
            if (Global.getButtonThemeDark()) hard_math_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else hard_math_btn.setBackgroundResource(R.drawable.mybutton_light);
            hard_math_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) medium_math_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else medium_math_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        medium_math_btn_chosen = true;
        AllLevels.setMath(Level.MEDIUM);
    }


    //level hard for math problems chosen
    public void hardMath(View v) {

        if (!hard_math_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_math_btn_chosen) {
            if (Global.getButtonThemeDark()) easy_math_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else easy_math_btn.setBackgroundResource(R.drawable.mybutton_light);
            easy_math_btn_chosen = false;
        }

        if (medium_math_btn_chosen) {
            if (Global.getButtonThemeDark()) medium_math_btn.setBackgroundResource(R.drawable.mybutton_dark);
            else medium_math_btn.setBackgroundResource(R.drawable.mybutton_light);
            medium_math_btn_chosen = false;
        }

        if (Global.getButtonThemeDark()) hard_math_btn.setBackgroundResource(R.drawable.mybutton_dark_chosen);
        else hard_math_btn.setBackgroundResource(R.drawable.mybutton_light_chosen);
        hard_math_btn_chosen = true;
        AllLevels.setMath(Level.HARD);
    }


    //when "Back" button has been pressed
    public void backToMainActivity(View v) {
        global.playSound(soundPool, soundID);
        finish();
    }


    //when "Start" button has been pressed
    public void switchToGameActivity(View v) {
        global.playSound(soundPool, soundID);
        if (!notAllLevelsChosen()) {
            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.choose_level, Toast.LENGTH_SHORT).show();
        }
    }


    //level for not every parameter (board size, time, math) chosen
    public boolean notAllLevelsChosen() {
        boolean result;
        if (Settings.getForTimeOn()) result = (!easy_board_btn_chosen &&
                                                !medium_board_btn_chosen &&
                                                !hard_board_btn_chosen) ||
                                                (!easy_time_btn_chosen &&
                                                !medium_time_btn_chosen &&
                                                !hard_time_btn_chosen) ||
                                                (!easy_math_btn_chosen &&
                                                !medium_math_btn_chosen &&
                                                !hard_math_btn_chosen);
        else result = (!easy_board_btn_chosen &&
                        !medium_board_btn_chosen &&
                        !hard_board_btn_chosen) ||
                        (!easy_math_btn_chosen &&
                        !medium_math_btn_chosen &&
                        !hard_math_btn_chosen);
        return result;
    }


    private void applyDarkTheme() {

        Button button1 = findViewById(R.id.easy_board_btn);
        button1.setTextColor(getResources().getColor(R.color.white));
        button1.setBackgroundResource(R.drawable.mybutton_dark);

        Button button2 = findViewById(R.id.medium_board_btn);
        button2.setTextColor(getResources().getColor(R.color.white));
        button2.setBackgroundResource(R.drawable.mybutton_dark);

        Button button3 = findViewById(R.id.hard_board_btn);
        button3.setTextColor(getResources().getColor(R.color.white));
        button3.setBackgroundResource(R.drawable.mybutton_dark);

        Button button4 = findViewById(R.id.easy_math_btn);
        button4.setTextColor(getResources().getColor(R.color.white));
        button4.setBackgroundResource(R.drawable.mybutton_dark);

        Button button5 = findViewById(R.id.medium_math_btn);
        button5.setTextColor(getResources().getColor(R.color.white));
        button5.setBackgroundResource(R.drawable.mybutton_dark);

        Button button6 = findViewById(R.id.hard_math_btn);
        button6.setTextColor(getResources().getColor(R.color.white));
        button6.setBackgroundResource(R.drawable.mybutton_dark);

        Button button7 = findViewById(R.id.easy_time_btn);
        button7.setTextColor(getResources().getColor(R.color.white));
        button7.setBackgroundResource(R.drawable.mybutton_dark);

        Button button8 = findViewById(R.id.medium_time_btn);
        button8.setTextColor(getResources().getColor(R.color.white));
        button8.setBackgroundResource(R.drawable.mybutton_dark);

        Button button9 = findViewById(R.id.hard_time_btn);
        button9.setTextColor(getResources().getColor(R.color.white));
        button9.setBackgroundResource(R.drawable.mybutton_dark);

        Button button10 = findViewById(R.id.backToMain_NG);
        button10.setTextColor(getResources().getColor(R.color.white));
        button10.setBackgroundResource(R.drawable.mybutton_dark);

        Button button11 = findViewById(R.id.start_btn);
        button11.setTextColor(getResources().getColor(R.color.white));
        button11.setBackgroundResource(R.drawable.mybutton_dark);
    }


    private void applyLightTheme() {

        Button button1 = findViewById(R.id.easy_board_btn);
        button1.setTextColor(getResources().getColor(R.color.colorAccent));
        button1.setBackgroundResource(R.drawable.mybutton_light);

        Button button2 = findViewById(R.id.medium_board_btn);
        button2.setTextColor(getResources().getColor(R.color.colorAccent));
        button2.setBackgroundResource(R.drawable.mybutton_light);

        Button button3 = findViewById(R.id.hard_board_btn);
        button3.setTextColor(getResources().getColor(R.color.colorAccent));
        button3.setBackgroundResource(R.drawable.mybutton_light);

        Button button4 = findViewById(R.id.easy_math_btn);
        button4.setTextColor(getResources().getColor(R.color.colorAccent));
        button4.setBackgroundResource(R.drawable.mybutton_light);

        Button button5 = findViewById(R.id.medium_math_btn);
        button5.setTextColor(getResources().getColor(R.color.colorAccent));
        button5.setBackgroundResource(R.drawable.mybutton_light);

        Button button6 = findViewById(R.id.hard_math_btn);
        button6.setTextColor(getResources().getColor(R.color.colorAccent));
        button6.setBackgroundResource(R.drawable.mybutton_light);

        Button button7 = findViewById(R.id.easy_time_btn);
        button7.setTextColor(getResources().getColor(R.color.colorAccent));
        button7.setBackgroundResource(R.drawable.mybutton_light);

        Button button8 = findViewById(R.id.medium_time_btn);
        button8.setTextColor(getResources().getColor(R.color.colorAccent));
        button8.setBackgroundResource(R.drawable.mybutton_light);

        Button button9 = findViewById(R.id.hard_time_btn);
        button9.setTextColor(getResources().getColor(R.color.colorAccent));
        button9.setBackgroundResource(R.drawable.mybutton_light);

        Button button10 = findViewById(R.id.backToMain_NG);
        button10.setTextColor(getResources().getColor(R.color.colorAccent));
        button10.setBackgroundResource(R.drawable.mybutton_light);

        Button button11 = findViewById(R.id.start_btn);
        button11.setTextColor(getResources().getColor(R.color.colorAccent));
        button11.setBackgroundResource(R.drawable.mybutton_light);
    }


}







