//comments ok
package com.example.boris.mathpuzzles;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

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

    private boolean easy_board_btn_chosen = false;
    private boolean medium_board_btn_chosen = false;
    private boolean hard_board_btn_chosen = false;
    private boolean easy_time_btn_chosen = false;
    private boolean medium_time_btn_chosen = false;
    private boolean hard_time_btn_chosen = false;
    private boolean easy_math_btn_chosen = false;
    private boolean medium_math_btn_chosen = false;
    private boolean hard_math_btn_chosen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        hideNavStatBar();

        easy_board_btn = findViewById(R.id.easy_board_btn);
        medium_board_btn = findViewById(R.id.medium_board_btn);
        hard_board_btn = findViewById(R.id.hard_board_btn);
        easy_time_btn = findViewById(R.id.easy_time_btn);
        medium_time_btn = findViewById(R.id.medium_time_btn);
        hard_time_btn = findViewById(R.id.hard_time_btn);
        easy_math_btn = findViewById(R.id.easy_math_btn);
        medium_math_btn = findViewById(R.id.medium_math_btn);
        hard_math_btn = findViewById(R.id.hard_math_btn);
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


    //when "Back" button has been pressed
    public void backToMainActivity(View v) {
        finish();
    }


    //level easy for board size chosen
    public void easyBoard(View v) {

        if (medium_board_btn_chosen) {
            medium_board_btn.setBackgroundResource(android.R.drawable.btn_default);
            medium_board_btn_chosen = false;
        }

        if (hard_board_btn_chosen) {
            hard_board_btn.setBackgroundResource(android.R.drawable.btn_default);
            hard_board_btn_chosen = false;
        }


        easy_board_btn.setBackground(createButtonBorder());
        easy_board_btn_chosen = true;
    }


    //level medium for board size chosen
    public void mediumBoard(View v) {

        if (easy_board_btn_chosen) {
            easy_board_btn.setBackgroundResource(android.R.drawable.btn_default);
            easy_board_btn_chosen = false;
        }

        if (hard_board_btn_chosen) {
            hard_board_btn.setBackgroundResource(android.R.drawable.btn_default);
            hard_board_btn_chosen = false;
        }

        medium_board_btn.setBackground(createButtonBorder());
        medium_board_btn_chosen = true;
    }


    //level hard for board size chosen
    public void hardBoard(View v) {

        if (easy_board_btn_chosen) {
            easy_board_btn.setBackgroundResource(android.R.drawable.btn_default);
            easy_board_btn_chosen = false;
        }

        if (medium_board_btn_chosen) {
            medium_board_btn.setBackgroundResource(android.R.drawable.btn_default);
            medium_board_btn_chosen = false;
        }

        hard_board_btn.setBackground(createButtonBorder());
        hard_board_btn_chosen = true;
    }


    //level easy for time chosen
    public void easyTime(View v) {

        if (medium_time_btn_chosen) {
            medium_time_btn.setBackgroundResource(android.R.drawable.btn_default);
            medium_time_btn_chosen = false;
        }

        if (hard_time_btn_chosen) {
            hard_time_btn.setBackgroundResource(android.R.drawable.btn_default);
            hard_time_btn_chosen = false;
        }

        easy_time_btn.setBackground(createButtonBorder());
        easy_time_btn_chosen = true;
    }


    //level medium for time chosen
    public void mediumTime(View v) {

        if (easy_time_btn_chosen) {
            easy_time_btn.setBackgroundResource(android.R.drawable.btn_default);
            easy_time_btn_chosen = false;
        }

        if (hard_time_btn_chosen) {
            hard_time_btn.setBackgroundResource(android.R.drawable.btn_default);
            hard_time_btn_chosen = false;
        }

        medium_time_btn.setBackground(createButtonBorder());
        medium_time_btn_chosen = true;
    }


    //level hard for time chosen
    public void hardTime(View v) {

        if (easy_time_btn_chosen) {
            easy_time_btn.setBackgroundResource(android.R.drawable.btn_default);
            easy_time_btn_chosen = false;
        }

        if (medium_time_btn_chosen) {
            medium_time_btn.setBackgroundResource(android.R.drawable.btn_default);
            medium_time_btn_chosen = false;
        }

        hard_time_btn.setBackground(createButtonBorder());
        hard_time_btn_chosen = true;
    }


    //level easy for math problems chosen
    public void easyMath(View v) {

        if (medium_math_btn_chosen) {
            medium_math_btn.setBackgroundResource(android.R.drawable.btn_default);
            medium_math_btn_chosen = false;
        }

        if (hard_math_btn_chosen) {
            hard_math_btn.setBackgroundResource(android.R.drawable.btn_default);
            hard_math_btn_chosen = false;
        }

        easy_math_btn.setBackground(createButtonBorder());
        easy_math_btn_chosen = true;
    }


    //level medium for math problems chosen
    public void mediumMath(View v) {

        if (easy_math_btn_chosen) {
            easy_math_btn.setBackgroundResource(android.R.drawable.btn_default);
            easy_math_btn_chosen = false;
        }

        if (hard_math_btn_chosen) {
            hard_math_btn.setBackgroundResource(android.R.drawable.btn_default);
            hard_math_btn_chosen = false;
        }

        medium_math_btn.setBackground(createButtonBorder());
        medium_math_btn_chosen = true;
    }


    //level hard for math problems chosen
    public void hardMath(View v) {

        if (easy_math_btn_chosen) {
            easy_math_btn.setBackgroundResource(android.R.drawable.btn_default);
            easy_math_btn_chosen = false;
        }

        if (medium_math_btn_chosen) {
            medium_math_btn.setBackgroundResource(android.R.drawable.btn_default);
            medium_math_btn_chosen = false;
        }

        hard_math_btn.setBackground(createButtonBorder());
        hard_math_btn_chosen = true;
    }


    //setting border to the button to be visible fro the user what level has been chosen
    private Drawable createButtonBorder() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(5, Color.MAGENTA);
        drawable.setColor(Color.WHITE);
        return drawable;
    }


    //when "Start" button has been pressed
    public void switchToGameActivity(View v) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

}







