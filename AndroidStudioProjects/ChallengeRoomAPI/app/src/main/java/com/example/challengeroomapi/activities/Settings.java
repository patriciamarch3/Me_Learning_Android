package com.example.challengeroomapi.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.uihelpers.TopToast;

public class Settings extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences.registerOnSharedPreferenceChangeListener(this);
        String themeColor = preferences.getString("themeColor", "green");
        String language = preferences.getString("language", "english");
        switch (themeColor) {
            case "red":
                setTheme(R.style.RedTheme);
                break;

            case "blue":
                setTheme(R.style.BlueTheme);
                break;

            default:
                setTheme(R.style.GreenTheme);
        }

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Settings");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

//        TopToast.create(this, "setting clicked!");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("themeColor")) {
            this.recreate();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.setting, rootKey);
        }
    }
}
