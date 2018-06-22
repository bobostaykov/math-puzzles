package com.example.boris.mathpuzzles.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.help.Global;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    private Global global = new Global();
    private Spinner languageSpinner, buttonThemeSpinner;
    private Locale locale;
    private Switch sound_switch, time_switch;
    private boolean spinnerOpened = false;
    private static boolean sound_on = true, forTime_on = true;
    private String en, de, bg, dark, light;
    private int boardColumns;
    private SoundPool soundPool;
    private int buttonSoundID = 0;
    private boolean isLangSpinnerTouched = false, isThemeSpinnerTouched = false;


    private static class MySpinnerAdapter extends ArrayAdapter<String> {
        Typeface font = Typeface.create("casual", Typeface.NORMAL);

        private MySpinnerAdapter(Context context, int resource, String[] items) {
            super(context, resource, items);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(font);
            view.setTextColor(Global.getGlobalResources().getColor(R.color.colorSettingsText));
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(font);
            view.setTextColor(Global.getGlobalResources().getColor(R.color.colorSettingsText));
            return view;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hideNavStatBar();

        if (Global.getButtonThemeDark()) applyDarkTheme();
        else applyLightTheme();

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
                    Toast.makeText(Settings.this, R.string.toast_sound_on, Toast.LENGTH_SHORT).show();
                    buttonSoundID = soundPool.load(Settings.this, R.raw.button_click_1, 1);
                } else {
                    sound_on = false;
                    Toast.makeText(Settings.this, R.string.toast_sound_off, Toast.LENGTH_SHORT).show();
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
                if (isChecked) {
                    forTime_on = true;
                    Toast.makeText(Settings.this, R.string.toast_time_on, Toast.LENGTH_SHORT).show();
                } else {
                    forTime_on = false;
                    Toast.makeText(Settings.this, R.string.toast_time_off, Toast.LENGTH_SHORT).show();
                }
            }
        });

        languageSpinner = findViewById(R.id.language_spinner);

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

        MySpinnerAdapter langAdapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages);
        languageSpinner.setAdapter(langAdapter);

        languageSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                isLangSpinnerTouched = true;
                return false;
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isLangSpinnerTouched) return;
                global.playSound(soundPool, buttonSoundID);

                if (parent.getItemAtPosition(position).equals(getString(R.string.en_lang)) && !getResources().getConfiguration().locale.toString().equalsIgnoreCase("en_gb")) {
                    Toast.makeText(parent.getContext(), "Language changed to English", Toast.LENGTH_SHORT).show();
                    setLocale("en_gb");
                }

                if (parent.getItemAtPosition(position).equals(getString(R.string.de_lang)) && !getResources().getConfiguration().locale.toString().equals("de")) {
                    Toast.makeText(parent.getContext(), "Sprache auf Deutsch gesetzt", Toast.LENGTH_SHORT).show();
                    setLocale("de");
                }

                if (parent.getItemAtPosition(position).equals(getString(R.string.bg_lang)) && !getResources().getConfiguration().locale.toString().equals("bg")) {
                    Toast.makeText(parent.getContext(), "Избран език български", Toast.LENGTH_SHORT).show();
                    setLocale("bg");
                }
                MainMenu.setLocaleChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });


        buttonThemeSpinner = findViewById(R.id.button_theme_spinner);

        dark = getString(R.string.button_theme_dark);
        light = getString(R.string.button_theme_light);
        String[] themes;

        if (Global.getButtonThemeDark()) themes = new String[]{dark, light};
        else themes = new String[]{light, dark};

        MySpinnerAdapter themeAdapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, themes);
        buttonThemeSpinner.setAdapter(themeAdapter);

        buttonThemeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                isThemeSpinnerTouched = true;
                return false;
            }
        });

        buttonThemeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isThemeSpinnerTouched) return;
                global.playSound(soundPool, buttonSoundID);

                if (parent.getItemAtPosition(position).equals(getString(R.string.button_theme_dark))) {
                    Global.setButtonThemeDark(true);
                    Toast.makeText(parent.getContext(), R.string.toast_theme_dark, Toast.LENGTH_SHORT).show();
                }

                if (parent.getItemAtPosition(position).equals(getString(R.string.button_theme_light))) {
                    Global.setButtonThemeDark(false);
                    Toast.makeText(parent.getContext(), R.string.toast_theme_light, Toast.LENGTH_SHORT).show();
                }

                MainMenu.setThemeChanged();
                restartActivity();
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
        restartActivity();
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


    private void restartActivity() {
        finish();
        Intent refresh = new Intent(this, Settings.class);
        startActivity(refresh);
        overridePendingTransition(0,0);
    }


    private void applyDarkTheme() {
        Button button1 = findViewById(R.id.sett_back_btn);
        button1.setTextColor(getResources().getColor(R.color.white));
        button1.setBackgroundResource(R.drawable.mybutton_dark);
    }


    private void applyLightTheme() {
        Button button1 = findViewById(R.id.sett_back_btn);
        button1.setTextColor(getResources().getColor(R.color.colorAccent));
        button1.setBackgroundResource(R.drawable.mybutton_light);
    }

}
