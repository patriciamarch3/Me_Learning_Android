package com.example.actionbarvideo2018;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Second Activity");
        actionBar.setSubtitle("Welcome to -1 floor");
        // enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
