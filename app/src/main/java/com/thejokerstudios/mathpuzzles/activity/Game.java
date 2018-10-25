package com.thejokerstudios.mathpuzzles.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.thejokerstudios.mathpuzzles.R;
import com.thejokerstudios.mathpuzzles.datatype.AllLevels;
import com.thejokerstudios.mathpuzzles.datatype.Level;
import com.thejokerstudios.mathpuzzles.help.Global;
import com.thejokerstudios.mathpuzzles.main.ImageAdapter;
import com.thejokerstudios.mathpuzzles.main.Puzzle;

import java.util.concurrent.TimeUnit;

public class Game extends AppCompatActivity {

    private TextView timerView;
    private CountDownTimer timer;
    private Game game = this;
    private SoundPool soundPool;
    private int boardColumns, timePassed = 0, soundIdButton = 0, soundIdLose = 0;
    private Global global = new Global();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        hideNavStatBar();

        if (Global.getButtonThemeDark()) applyDarkTheme();
        else applyLightTheme();

        //finding device screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        Global.setScreenWidth(screenWidth);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        if (Settings.getSoundOn()) {
            soundIdButton = soundPool.load(this, R.raw.button_click_1, 1);
            soundIdLose = soundPool.load(this, R.raw.lose_sound, 1);
        }

        global.setGameContext(this);

        GridView game_board = findViewById(R.id.game_board);
        TextView moves_number = findViewById(R.id.moves_number);
        timerView = findViewById(R.id.timer);
        TextView timeText = findViewById(R.id.timeText);

        if (AllLevels.getBoard() == Level.EASY) {
            game_board.setNumColumns(3);
            boardColumns = 3;
        }
        if (AllLevels.getBoard() == Level.MEDIUM) {
            game_board.setNumColumns(4);
            boardColumns = 4;
        }
        if (AllLevels.getBoard() == Level.HARD) {
            game_board.setNumColumns(5);
            boardColumns = 5;
        }

        int millisInFuture = 0;

        //setting time, depending on the board size and the time level chosen
        if (Settings.getForTimeOn()) {
            switch (boardColumns) {
                case 3:
                    switch (AllLevels.getTime()) {
                        case EASY:
                            millisInFuture = 90 * 1000;
                            break;
                        case MEDIUM:
                            millisInFuture = 40 * 1000;
                            break;
                        case HARD:
                            millisInFuture = 15 * 1000;
                            break;
                    }
                    break;

                case 4:
                    switch (AllLevels.getTime()) {
                        case EASY:
                            millisInFuture = 240 * 1000;
                            break;
                        case MEDIUM:
                            millisInFuture = 120 * 1000;
                            break;
                        case HARD:
                            millisInFuture = 45 * 1000;
                            break;
                    }
                    break;

                case 5:
                    switch (AllLevels.getTime()) {
                        case EASY:
                            millisInFuture = 540 * 1000;
                            break;
                        case MEDIUM:
                            millisInFuture = 360 * 1000;
                            break;
                        case HARD:
                            millisInFuture = 180 * 1000;
                            break;
                    }
                    break;
            }
        }

        //converting the milis in minutes and seconds and showing them in the timer view
        timerView.setText(getString(R.string.timer_format,
                TimeUnit.MILLISECONDS.toMinutes(millisInFuture),
                TimeUnit.MILLISECONDS.toSeconds(millisInFuture) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisInFuture))));

        if (!Settings.getForTimeOn()) {
            timeText.setText("");
            timerView.setText("");
        }


        timer = new CountDownTimer(millisInFuture, 1000) {

            public void onTick(long millisUntilFinished) {
                timerView.setText(getString(R.string.timer_format,
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                //keeping track of seconds and minutes passed
                timePassed++;
                Puzzle.setMinutesPassed(timePassed / 60);
                Puzzle.setSecondsPassed(timePassed % 60);
            }

            public void onFinish() {

                //"time's up" sound
                global.playSound(soundPool, soundIdLose);

                //"main menu" button
                View.OnClickListener mainMenu = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        global.playSound(soundPool, soundIdButton);
                        backToMain(game);
                    }
                };

                //"try again" button
                View.OnClickListener tryAgain = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        global.playSound(soundPool, soundIdButton);
                        Global.setCurrentImageRealTag(Global.getCurrentImageTag());
                        restartActivity(game);
                    }
                };

                //"change level" button
                View.OnClickListener changeLevel = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        global.playSound(soundPool, soundIdButton);
                        back(game);
                    }
                };

                //creating a "time's up" dialog
                global.createDialog(false, R.string.timeUp_dialog_title,
                                    getString(R.string.timeUp_dialog_text),
                                    R.string.main_menu, mainMenu,
                                    R.string.change_level, changeLevel,
                                    R.string.try_again, tryAgain);

            }

        };

        game_board.setAdapter(new ImageAdapter(this, game_board, boardColumns, moves_number, timer, this, soundPool, global));

    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavStatBar();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stopping the timer when exiting the activity
        timer.cancel();
    }


    //entering fullscreen
    public void hideNavStatBar() {
        View decorView = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }



    //backToMain, restartActivity and back methods for programmatical use

    public void backToMain(Game game) {
        game.finish();
        Intent back = new Intent(game, MainMenu.class);
        back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        game.startActivity(back);
    }

    public void restartActivity(Game game) {
        game.finish();
        Intent restart = new Intent(game, Game.class);
        game.startActivity(restart);
        overridePendingTransition(0,0);
    }

    public void back(Game game) {
        game.finish();
    }



    //backToMain, restartActivity and back methods to use in the graphical designer

    public void backToMain(View v) {
        global.playSound(soundPool, soundIdButton);
        game.finish();
        Intent back = new Intent(game, MainMenu.class);
        back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        game.startActivity(back);
    }

    public void restartActivity(View v) {
        global.playSound(soundPool, soundIdButton);
        Global.setCurrentImageRealTag(Global.getCurrentImageTag());
        timer.cancel();
        game.finish();
        Intent restart = new Intent(game, Game.class);
        game.startActivity(restart);
        overridePendingTransition(0,0);
    }

    public void back(View v) {
        global.playSound(soundPool, soundIdButton);
        finish();
    }


    public void info(View v) {
        global.playSound(soundPool, soundIdButton);
        global.createDialog(true, 0,
                            getString(R.string.info_text),
                            R.string.close_dialog, null,
                            0, null,
                            0, null);
    }


    private void applyDarkTheme() {
        Button button1 = findViewById(R.id.backToNG);
        button1.setTextColor(getResources().getColor(R.color.white));
        button1.setBackgroundResource(R.drawable.mybutton_dark);

        Button button2 = findViewById(R.id.tryAgain);
        button2.setTextColor(getResources().getColor(R.color.white));
        button2.setBackgroundResource(R.drawable.mybutton_dark);
    }


    private void applyLightTheme() {
        Button button1 = findViewById(R.id.backToNG);
        button1.setTextColor(getResources().getColor(R.color.colorAccent));
        button1.setBackgroundResource(R.drawable.mybutton_light);

        Button button2 = findViewById(R.id.tryAgain);
        button2.setTextColor(getResources().getColor(R.color.colorAccent));
        button2.setBackgroundResource(R.drawable.mybutton_light);
    }

}