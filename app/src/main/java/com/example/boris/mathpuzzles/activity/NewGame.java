package com.example.boris.mathpuzzles.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.datatype.AllLevels;
import com.example.boris.mathpuzzles.datatype.Level;
import com.example.boris.mathpuzzles.help.Global;

public class NewGame extends AppCompatActivity {

    //referencing buttons created in the activity_new_game.xml file
    private Button easy_board_btn;
    private Button medium_board_btn;
    private Button hard_board_btn;
    private Button easy_time_btn;
    private Button medium_time_btn;
    private Button hard_time_btn;
    private Button easy_math_btn;
    private Button medium_math_btn;
    private Button hard_math_btn;

    private TextView timeLabel;
    private TableLayout newGame_table;
    private TableRow timeText_Row, timeButtons_Row, mathButtons_row;

    private boolean easy_board_btn_chosen = false;
    private boolean medium_board_btn_chosen = false;
    private boolean hard_board_btn_chosen = false;
    private boolean easy_time_btn_chosen = false;
    private boolean medium_time_btn_chosen = false;
    private boolean hard_time_btn_chosen = false;
    private boolean easy_math_btn_chosen = false;
    private boolean medium_math_btn_chosen = false;
    private boolean hard_math_btn_chosen = false;

    private MyReceiver receiver;
    private SoundPool soundPool;
    private int soundID = 0;
    private Global global = new Global();


    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("DESTROY")) finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        hideNavStatBar();

        registerReceiver(receiver, new IntentFilter("DESTROY"));

        easy_board_btn = findViewById(R.id.easy_board_btn);
        medium_board_btn = findViewById(R.id.medium_board_btn);
        hard_board_btn = findViewById(R.id.hard_board_btn);
        easy_time_btn = findViewById(R.id.easy_time_btn);
        medium_time_btn = findViewById(R.id.medium_time_btn);
        hard_time_btn = findViewById(R.id.hard_time_btn);
        easy_math_btn = findViewById(R.id.easy_math_btn);
        medium_math_btn = findViewById(R.id.medium_math_btn);
        hard_math_btn = findViewById(R.id.hard_math_btn);
        newGame_table = findViewById(R.id.NewGame_Table);
        timeText_Row = findViewById(R.id.timeText_Row);
        timeButtons_Row = findViewById(R.id.timeButtons_Row);
        mathButtons_row = findViewById(R.id.math_buttons_row);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        if (Settings.getSoundOn()) soundID = soundPool.load(this, R.raw.button_click_1, 1);
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
            medium_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            medium_board_btn_chosen = false;
        }

        if (hard_board_btn_chosen) {
            hard_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            hard_board_btn_chosen = false;
        }


        easy_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        easy_board_btn_chosen = true;
        //saving the chosen level in the class AllLevels to access it later from Game
        AllLevels.setBoard(Level.EASY);
    }


    //level medium for board size chosen
    public void mediumBoard(View v) {

        if (!medium_board_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_board_btn_chosen) {
            easy_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            easy_board_btn_chosen = false;
        }

        if (hard_board_btn_chosen) {
            hard_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            hard_board_btn_chosen = false;
        }

        medium_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        medium_board_btn_chosen = true;
        AllLevels.setBoard(Level.MEDIUM);
    }


    //level hard for board size chosen
    public void hardBoard(View v) {

        if (!hard_board_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_board_btn_chosen) {
            easy_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            easy_board_btn_chosen = false;
        }

        if (medium_board_btn_chosen) {
            medium_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            medium_board_btn_chosen = false;
        }

        hard_board_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        hard_board_btn_chosen = true;
        AllLevels.setBoard(Level.HARD);
    }


    //level easy for time chosen
    public void easyTime(View v) {

        if (!easy_time_btn_chosen) global.playSound(soundPool, soundID);

        if (medium_time_btn_chosen) {
            medium_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            medium_time_btn_chosen = false;
        }

        if (hard_time_btn_chosen) {
            hard_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            hard_time_btn_chosen = false;
        }

        easy_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        easy_time_btn_chosen = true;
        AllLevels.setTime(Level.EASY);
    }


    //level medium for time chosen
    public void mediumTime(View v) {

        if (!medium_time_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_time_btn_chosen) {
            easy_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            easy_time_btn_chosen = false;
        }

        if (hard_time_btn_chosen) {
            hard_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            hard_time_btn_chosen = false;
        }

        medium_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        medium_time_btn_chosen = true;
        AllLevels.setTime(Level.MEDIUM);
    }


    //level hard for time chosen
    public void hardTime(View v) {

        if (!hard_time_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_time_btn_chosen) {
            easy_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            easy_time_btn_chosen = false;
        }

        if (medium_time_btn_chosen) {
            medium_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            medium_time_btn_chosen = false;
        }

        hard_time_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        hard_time_btn_chosen = true;
        AllLevels.setTime(Level.HARD);
    }


    //level easy for math problems chosen
    public void easyMath(View v) {

        if (!easy_math_btn_chosen) global.playSound(soundPool, soundID);

        if (medium_math_btn_chosen) {
            medium_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            medium_math_btn_chosen = false;
        }

        if (hard_math_btn_chosen) {
            hard_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            hard_math_btn_chosen = false;
        }

        easy_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        easy_math_btn_chosen = true;
        AllLevels.setMath(Level.EASY);
    }


    //level medium for math problems chosen
    public void mediumMath(View v) {

        if (!medium_math_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_math_btn_chosen) {
            easy_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            easy_math_btn_chosen = false;
        }

        if (hard_math_btn_chosen) {
            hard_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            hard_math_btn_chosen = false;
        }

        medium_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        medium_math_btn_chosen = true;
        AllLevels.setMath(Level.MEDIUM);
    }


    //level hard for math problems chosen
    public void hardMath(View v) {

        if (!hard_math_btn_chosen) global.playSound(soundPool, soundID);

        if (easy_math_btn_chosen) {
            easy_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            easy_math_btn_chosen = false;
        }

        if (medium_math_btn_chosen) {
            medium_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            medium_math_btn_chosen = false;
        }

        hard_math_btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButtonChosen));
        hard_math_btn_chosen = true;
        AllLevels.setMath(Level.HARD);
    }


    //setting border to the button to be visible fro the user what level has been chosen
    private Drawable createButtonBorder() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(5, Color.MAGENTA);
        drawable.setColor(Color.WHITE);
        return drawable;
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

}







