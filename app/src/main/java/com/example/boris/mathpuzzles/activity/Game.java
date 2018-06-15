package com.example.boris.mathpuzzles.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.datatype.AllLevels;
import com.example.boris.mathpuzzles.datatype.Level;
import com.example.boris.mathpuzzles.help.Global;
import com.example.boris.mathpuzzles.main.ImageAdapter;

import java.util.concurrent.TimeUnit;

public class Game extends AppCompatActivity {

    private GridView game_board;
    private TextView moves_number;
    private TextView timerView;
    private CountDownTimer timer;
    private Game game = this;
    private int boardColumns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        hideNavStatBar();

        Global.setGameContext(this);

        game_board = findViewById(R.id.game_board);
        moves_number = findViewById(R.id.moves_number);
        timerView = findViewById(R.id.timer);

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

        //if boardColumns == 3 and time level == hard
        int milisInFuture = 20*1000;

        if (boardColumns == 4) milisInFuture *= 2;
        if (boardColumns == 5) milisInFuture *= 4;

        if (AllLevels.getTime() == Level.MEDIUM) milisInFuture *= 1.5;
        if (AllLevels.getTime() == Level.EASY) milisInFuture *= 2;

        timerView.setText(getString(R.string.timer_format,
                TimeUnit.MILLISECONDS.toMinutes(milisInFuture),
                TimeUnit.MILLISECONDS.toSeconds(milisInFuture) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milisInFuture))));

        timer = new CountDownTimer(milisInFuture, 1000) {

            public void onTick(long millisUntilFinished) {
                timerView.setText(getString(R.string.timer_format,
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {

                final DialogInterface.OnClickListener mainMenu = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backToMain(game);
                    }
                };

                DialogInterface.OnClickListener tryAgain = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartActivity(game);
                    }
                };

                Global.createDialog(R.string.timeUp_dialog_title,
                            getString(R.string.timeUp_dialog_text),
                            R.string.main_menu, mainMenu,
                            -1, null,
                            R.string.try_again, tryAgain);

            }

        };

        game_board.setAdapter(new ImageAdapter(this, game_board, boardColumns, moves_number, timerView, timer, this));

    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavStatBar();
    }


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


    public void backToMain(Game game) {
        game.finish();
        Intent back = new Intent(game, MainActivity.class);
        back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        game.startActivity(back);
    }


    public void backToMain(View v) {
        game.finish();
        Intent back = new Intent(game, MainActivity.class);
        back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        game.startActivity(back);
    }


    public void restartActivity(Game game) {
        game.finish();
        Intent restart = new Intent(game, Game.class);
        game.startActivity(restart);
        overridePendingTransition(0,0);
    }


    public void restartActivity(View v) {
        game.finish();
        Intent restart = new Intent(game, Game.class);
        game.startActivity(restart);
        overridePendingTransition(0,0);
    }



    public void back(Game game) {
        game.finish();
    }


    //TODO: add buttons to game activity - back to main, new game
    //TODO: add sounds
    //TODO: save info to database (Firebase)
    //TODO: fine tune UI
    //TODO: create the math problems (images)
    //TODO: create the app icon
    //TODO: final inspection - remove unused methods, review warnings and write comments
}
