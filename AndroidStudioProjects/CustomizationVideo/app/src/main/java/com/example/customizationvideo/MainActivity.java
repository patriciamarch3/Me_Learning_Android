package com.example.customizationvideo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView atvName;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Floating Hint Input Box, Autocomplete Hint, Custom Button, Custom Toast");

        atvName = findViewById(R.id.atvName);

        ArrayList<String> names = new ArrayList<>();
        names.add("James");
        names.add("Jack");
        names.add("Jaehyun");
        names.add("Jeno");
        names.add("Jaemin");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, names);
        atvName.setThreshold(1);
        atvName.setAdapter(myAdapter);

        showToast("Hello World!");

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Button clicked!");
            }
        });
    }

    public void showToast(String message) {
        View toastView = getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.linearLayout));
        TextView tvMessage = toastView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

//        Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();

        Toast toast = new Toast(MainActivity.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0,0);
        toast.show();
    }
}
