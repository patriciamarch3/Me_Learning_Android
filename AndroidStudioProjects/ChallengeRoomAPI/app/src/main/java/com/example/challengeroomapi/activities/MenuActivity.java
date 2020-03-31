package com.example.challengeroomapi.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.uihelpers.TopToast;

public class MenuActivity extends BaseActivity {
    private final int CHANGE_SETTING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                        TopToast.create(this, getString(R.string.message_setting_change_success));
                    }
                }
            } else {
                TopToast.create(this, getString(R.string.message_setting_change_fail));
            }
        }
    }
}
