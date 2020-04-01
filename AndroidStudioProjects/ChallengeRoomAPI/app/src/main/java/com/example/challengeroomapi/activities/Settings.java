package com.example.challengeroomapi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.preference.PreferenceFragmentCompat;

import com.example.challengeroomapi.R;

public class Settings extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_settings_activity));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                safeFinishActivity(RESULT_OK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        safeFinishActivity(RESULT_BACK);
    }

    private void safeFinishActivity(int result_code) {
        boolean isChanged = preferences.getBoolean("isChanged", false);
        Intent replyIntent = new Intent();
        replyIntent.putExtra("isChanged", isChanged);
        setResult(result_code, replyIntent);
        this.finish();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.setting, rootKey);
        }
    }
}
