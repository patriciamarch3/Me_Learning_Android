package com.example.challengeroomapi.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.fragments.BookAdapter;
import com.example.challengeroomapi.uihelpers.TopToast;

public class MainActivity extends AppCompatActivity implements BookAdapter.ItemClicked {
    final int BOOK_DETAIL = 1;
    final int CHANGE_SETTING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        PreferenceController.changeSettings(preferences,this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Book Database");
        actionBar.setIcon(R.drawable.library);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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
    public void onClickItem(long ISBN) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        // only pass the primary key and the other activity queries for the object
        // rather than query for the object here and pass each of the object members,
        // because what if there are hundreds of members or the members contain objects
        intent.putExtra("isbn", ISBN);
        startActivityForResult(intent, BOOK_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BOOK_DETAIL) {
            if (resultCode != RESULT_OK) {
                if (data != null) {
                    TopToast.create(MainActivity.this, data.getStringExtra("error"));
                }
            } else {
                if (data != null) {
                    TopToast.create(MainActivity.this, data.getStringExtra("success"));
                }
            }
        } else if (requestCode == CHANGE_SETTING) {
            if (resultCode == RESULT_OK) {
                TopToast.create(MainActivity.this, "Settings changed successfully!");
            } else {
                TopToast.create(MainActivity.this, "Settings changed failed!");
            }
        }
    }
}
