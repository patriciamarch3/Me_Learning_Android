package com.example.challengeroomapi.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.uihelpers.TopToast;

public class BaseActivity extends AppCompatActivity {
    private final int CHANGE_SETTING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        PreferenceController.changeSettings(preferences,this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivityForResult(settingsIntent, CHANGE_SETTING);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_SETTING) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    boolean settingChanged = data.getBooleanExtra("isChanged", false);
                    if (settingChanged) {
                        this.recreate();
                        TopToast.create(this, "Settings changed successfully!");
                    }
                }
            } else {
                TopToast.create(this, "Settings changed failed!");
            }
        }
    }
}
