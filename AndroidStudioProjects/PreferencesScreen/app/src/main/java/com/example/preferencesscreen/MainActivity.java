package com.example.preferencesscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Main");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean autoSync = prefs.getBoolean("perform_sync", true);
        String syncInterval = prefs.getString("sync_interval", "30");
        String fullName = prefs.getString("full_name", "");
        String email = prefs.getString("email_address", "");

        Toast.makeText(this, "AutoSync? " + autoSync + "\n" +
                    "Sync Interval: " + syncInterval + "\n" +
                    "Full Name: " + fullName + "\n" +
                    "Email: " + email,
                Toast.LENGTH_LONG).show();

        // add my own data to preferences
        prefs.edit().putBoolean("should_be", true).commit();
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
                Toast.makeText(this, "setting is clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Settings.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
