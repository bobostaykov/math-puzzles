//
package com.example.boris.mathpuzzles;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Game extends AppCompatActivity {

    private GridView game_board;
    private int boardColumns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        hideNavStatBar();

        game_board = findViewById(R.id.game_board);

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

        game_board.setAdapter(new ImageAdapter(this, game_board, boardColumns));
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

}
