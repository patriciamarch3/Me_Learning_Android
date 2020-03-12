package com.example.asynctasktemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asynctasktemplate.R;
import com.example.asynctasktemplate.db_operations.AsyncTaskCallback;
import com.example.asynctasktemplate.db_operations.RunSomethingInTheBackground;

public class MainActivity extends AppCompatActivity {
    Button btnDO;
    TextView tvResult;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDO = findViewById(R.id.btnDO);
        tvResult = findViewById(R.id.tvResult);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnDO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                // attach the only application itself with the RunSomethingInTheBackground
                new RunSomethingInTheBackground(MainActivity.this.getApplicationContext(), new AsyncTaskCallback() {
                    @Override
                    public void handleResponse(String object) {
                        tvResult.setText(object);
                        Toast.makeText(MainActivity.this, "Progress done!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void handleFault(Exception e) {
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }).execute();
            }
        });
    }
}
