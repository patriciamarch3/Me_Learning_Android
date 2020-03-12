package com.example.customedittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView etFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = (AutoCompleteTextView) findViewById(R.id.etFirstName);

        ArrayList<String> names = new ArrayList<>();
        names.add("Mark");
        names.add("Renjun");
        names.add("Jeno");
        names.add("Haechan");
        names.add("Jaemin");
        names.add("Chenle");
        names.add("Jisung");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_design_autocomplete , names);

        // initial auto-complete after the 1st character
        etFirstName.setThreshold(1);
        etFirstName.setAdapter(adapter);
    }
}
