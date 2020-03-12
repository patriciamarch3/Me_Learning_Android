package com.example.explicitintentvideo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etName;
    Button btnAct2;
    Button btnAct3;
    TextView tvResult;

    final int ACTIVITY3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnAct2 = findViewById(R.id.btnAct2);
        btnAct3 = findViewById(R.id.btnAct3);
        tvResult = findViewById(R.id.tvResult);

        btnAct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // param1: current context; param2: the context I want to go. (full name + .class)
                    Intent intent = new Intent(MainActivity.this,
                            com.example.explicitintentvideo.Activity2.class);
                    // pass data using the intent by letting the intent carry the data (key-value pair)
                    intent.putExtra("name", name);
                    // execute the intent
                    startActivity(intent);
                }

            }
        });

        btnAct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.explicitintentvideo.Activity3.class);
                startActivityForResult(intent, ACTIVITY3); // the result will fall back to onActivityResult function
            }
        });
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String text = "Data from activity3 will be displayed here";
        if (requestCode == ACTIVITY3) {
            if (resultCode == RESULT_OK) {
                String surname = data.getStringExtra("surname");
                if (surname.isEmpty()) {
                    text = "No surname entered!";
                } else {
                    text = "Your surname is " + surname;
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "No data received from activity 3!", Toast.LENGTH_SHORT).show();
            }
            tvResult.setText(text);
        }
    }
}
