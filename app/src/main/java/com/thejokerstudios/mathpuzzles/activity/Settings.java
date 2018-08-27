//everything ok
package com.thejokerstudios.mathpuzzles.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.thejokerstudios.mathpuzzles.R;
import com.thejokerstudios.mathpuzzles.help.Global;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    private Global global = new Global();
    private static boolean sound_on = true, forTime_on = true;
    private SoundPool soundPool;
    private int buttonSoundID = 0;
    private boolean isLangSpinnerTouched = false, isThemeSpinnerTouched = false;


    private static class MySpinnerAdapter extends ArrayAdapter<String> {
        Typeface font = Typeface.create("casual", Typeface.NORMAL);

        private MySpinnerAdapter(Context context, int resource, String[] items) {
            super(context, resource, items);
        }

        //setting font and color to the spinner and its dropdown menu text

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

        Switch sound_switch = findViewById(R.id.sound_switch);
        if (sound_on) {
            sound_switch.setChecked(true);
            buttonSoundID = soundPool.load(this, R.raw.button_click_1, 1);
        }
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                global.playSound(soundPool, buttonSoundID);
                if (isChecked) {
                    sound_on = true;
                    Toast.makeText(Settings.this, R.string.toast_sound_on, Toast.LENGTH_SHORT).show();
                    buttonSoundID = soundPool.load(Settings.this, R.raw.button_click_1, 1);
                } else {
                    sound_on = false;
                    Toast.makeText(Settings.this, R.string.toast_sound_off, Toast.LENGTH_SHORT).show();
                    buttonSoundID = 0;
                }
                updateSavedSettings();
                MainMenu.setSoundSettingChanged();
            }
        });

        Switch time_switch = findViewById(R.id.time_switch);
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
                updateSavedSettings();
            }
        });


        Spinner languageSpinner = findViewById(R.id.language_spinner);

        String en = getString(R.string.en_lang);
        String de = getString(R.string.de_lang);
        String bg = getString(R.string.bg_lang);
        String[] languages;

        //sorting the labels, depending on the current locale set
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
                //prevents the first option from being selected constantly
                if (!isLangSpinnerTouched) return;
                global.playSound(soundPool, buttonSoundID);

                if (parent.getItemAtPosition(position).equals(getString(R.string.en_lang)) && !getResources().getConfiguration().locale.toString().equalsIgnoreCase("en_gb"))
                    setLocale("en_gb");

                if (parent.getItemAtPosition(position).equals(getString(R.string.de_lang)) && !getResources().getConfiguration().locale.toString().equals("de"))
                    setLocale("de");

                if (parent.getItemAtPosition(position).equals(getString(R.string.bg_lang)) && !getResources().getConfiguration().locale.toString().equals("bg"))
                    setLocale("bg");

                Toast.makeText(Settings.this, getString(R.string.toast_language_changed), Toast.LENGTH_SHORT).show();
                MainMenu.setLocaleChanged();
                updateSavedSettings();
                restartActivity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });


        Spinner buttonThemeSpinner = findViewById(R.id.button_theme_spinner);

        String dark = getString(R.string.button_theme_dark);
        String light = getString(R.string.button_theme_light);
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
                //prevents the first option from being selected constantly
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
                updateSavedSettings();
                restartActivity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        setLocale(Global.getLocale());

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


    //setting new locale
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res;
        res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        Global.setLocale(lang);
    }


    //when "Back" button is pressed
    public void backToMain(View v) {
        global.playSound(soundPool, buttonSoundID);
        finish();
    }


    private void restartActivity() {
        finish();
        Intent refresh = new Intent(this, Settings.class);
        startActivity(refresh);
        overridePendingTransition(0, 0);
    }


    public void applyDarkTheme() {
        Button button1 = findViewById(R.id.sett_back_btn);
        button1.setTextColor(getResources().getColor(R.color.white));
        button1.setBackgroundResource(R.drawable.mybutton_dark);
    }


    public void applyLightTheme() {
        Button button1 = findViewById(R.id.sett_back_btn);
        button1.setTextColor(getResources().getColor(R.color.colorAccent));
        button1.setBackgroundResource(R.drawable.mybutton_light);
    }


    //configuring the settings saved from the last time the app was used
    private void updateSavedSettings() {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_prefs_file_name), Context.MODE_PRIVATE).edit();
        editor.putString(getString(R.string.key_locale), Global.getLocale());
        editor.putBoolean(getString(R.string.key_sound), sound_on);
        editor.putBoolean(getString(R.string.key_time), forTime_on);
        editor.putBoolean(getString(R.string.key_theme), Global.getButtonThemeDark());
        editor.apply();
    }


    public static boolean getSoundOn() {
        return sound_on;
    }


    public void setSoundOn(boolean on) {
        sound_on = on;
    }


    public static boolean getForTimeOn() {
        return forTime_on;
    }


    public void setForTimeOn(boolean on) {
        forTime_on = on;
    }

}
