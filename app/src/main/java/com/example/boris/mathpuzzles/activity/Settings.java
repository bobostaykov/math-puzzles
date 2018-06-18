package com.example.boris.mathpuzzles.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.help.Global;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    private Global global = new Global();
    private Spinner spinner;
    private Locale locale;
    private Switch sound_switch, time_switch;
    private boolean spinnerOpened = false;
    private static boolean sound_on = true, forTime_on = true;
    private String en;
    private String de;
    private String bg;
    private int boardColumns;
    private SoundPool soundPool;
    private int buttonSoundID = 0;
    private boolean isSpinnerTouched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hideNavStatBar();

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();

        sound_switch = findViewById(R.id.sound_switch);
        if (sound_on) {
            sound_switch.setChecked(true);
            buttonSoundID = soundPool.load(this, R.raw.button_click_1, 1);
        }
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                global.playSound(soundPool, buttonSoundID);
                MainMenu.setSoundSettingChanged();
                if (isChecked) {
                    sound_on = true;
                    buttonSoundID = soundPool.load(Settings.this, R.raw.button_click_1, 1);
                } else {
                    sound_on = false;
                    buttonSoundID = 0;
                }
            }
        });

        time_switch = findViewById(R.id.time_switch);
        time_switch.setChecked(forTime_on);
        time_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                global.playSound(soundPool, buttonSoundID);
                forTime_on = isChecked;
            }
        });

        spinner = findViewById(R.id.language_spinner);

        en = getString(R.string.en_lang);
        de = getString(R.string.de_lang);
        bg = getString(R.string.bg_lang);
        String[] languages;

        switch (getResources().getConfiguration().locale.toString()) {
            case "de":
                languages = new String[]{de, bg, en};
                break;
            case "bg":
                languages = new String[]{bg, en, de};
                break;
            default:
                languages = new String[]{en, de, bg};
                break;
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        spinner.setAdapter(adapter);

        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                isSpinnerTouched = true;
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isSpinnerTouched) return;
                global.playSound(soundPool, buttonSoundID);

                if (parent.getItemAtPosition(position).equals(getString(R.string.en_lang)) && !getResources().getConfiguration().locale.toString().equalsIgnoreCase("en_gb")) {
                    Toast.makeText(parent.getContext(), "Language changed to English", Toast.LENGTH_SHORT).show();
                    setLocale("en_gb");
                    MainMenu.setLocaleChanged();
                }

                if (parent.getItemAtPosition(position).equals(getString(R.string.de_lang)) && !getResources().getConfiguration().locale.toString().equals("de")) {
                    Toast.makeText(parent.getContext(), "Sprache auf Deutsch gesetzt", Toast.LENGTH_SHORT).show();
                    setLocale("de");
                    MainMenu.setLocaleChanged();
                }

                if (parent.getItemAtPosition(position).equals(getString(R.string.bg_lang)) && !getResources().getConfiguration().locale.toString().equals("bg")) {
                    Toast.makeText(parent.getContext(), "Избран език български", Toast.LENGTH_SHORT).show();
                    setLocale("bg");
                    MainMenu.setLocaleChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

    }


    //setting new locale
    public void setLocale(String lang) {
        locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        Global.setLocale(lang);

        //restarting activity so the changes take effect here as well
        finish();
        Intent refresh = new Intent(this, Settings.class);
        startActivity(refresh);
        overridePendingTransition(0,0);
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


    //when "Back" button is pressed
    public void backToMain(View v) {
        global.playSound(soundPool, buttonSoundID);
        finish();
    }


    public static boolean getSoundOn() {
        return sound_on;
    }


    public static boolean getForTimeOn() {
        return forTime_on;
    }
}
