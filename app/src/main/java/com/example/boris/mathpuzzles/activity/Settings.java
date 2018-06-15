package com.example.boris.mathpuzzles.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.boris.mathpuzzles.R;
import com.example.boris.mathpuzzles.help.Global;

import java.util.Locale;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private MainActivity main = new MainActivity();
    private Spinner spinner;
    private Locale locale;
    private boolean spinnerOpened = false;
    String en;
    String de;
    String bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hideNavStatBar();

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
        spinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getItemAtPosition(position).equals(getString(R.string.en_lang)) && !getResources().getConfiguration().locale.toString().equalsIgnoreCase("en_gb")) {
            System.err.println(getResources().getConfiguration().locale);
            Toast.makeText(parent.getContext(), "Language changed to English", Toast.LENGTH_SHORT).show();
            setLocale("en_gb");
            MainActivity.setLocaleChanged();
        }

        if (parent.getItemAtPosition(position).equals(getString(R.string.de_lang)) && !getResources().getConfiguration().locale.toString().equals("de")) {
            Toast.makeText(parent.getContext(), "Sprache auf Deutsch gesetzt", Toast.LENGTH_SHORT).show();
            setLocale("de");
            MainActivity.setLocaleChanged();
        }

        if (parent.getItemAtPosition(position).equals(getString(R.string.bg_lang)) && !getResources().getConfiguration().locale.toString().equals("bg")) {
            Toast.makeText(parent.getContext(), "Избран език български", Toast.LENGTH_SHORT).show();
            setLocale("bg");
            MainActivity.setLocaleChanged();
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
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
        finish();
    }
}
