package com.example.challengeroomapi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.room.Book;

public class BaseActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;
    protected static final int RESULT_BACK = 1; // result code for pressing Android back button

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences.registerOnSharedPreferenceChangeListener(this);
        PreferenceController.changeSettings(preferences, this);
        editor = preferences.edit();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        Context context = PreferenceController.changeSettings(preferences, newBase);
        super.attachBaseContext(new ContextWrapper(context));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // try to add a preference in shared preference to show that the setting is changed
        if (key.equals("themeColor") || key.equals("language")) {
            editor.putBoolean("isChanged", true);
            editor.apply();
            this.recreate();
        }
    }

    public String bookTostring(Book book) {
        return getString(R.string.tostring_book, book.getTitle(), book.getAuthor());
    }
}
