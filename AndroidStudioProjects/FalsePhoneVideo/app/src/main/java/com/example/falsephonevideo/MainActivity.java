package com.example.falsephonevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tvMessage);

        Uri data = getIntent().getData();
        if (data != null) {
            tvMessage.setText(data.toString().trim());
        } else {
            tvMessage.setText("No info");
        }


    }
}
