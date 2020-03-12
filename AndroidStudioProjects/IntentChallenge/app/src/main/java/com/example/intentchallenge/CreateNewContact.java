package com.example.intentchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateNewContact extends AppCompatActivity implements View.OnClickListener {
    EditText etName;
    EditText etPhone;
    EditText etWebsite;
    EditText etLocation;
    ImageView ivSatisfied;
    ImageView ivNeutral;
    ImageView ivUnsatisfied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etWebsite = findViewById(R.id.etWebsite);
        etLocation = findViewById(R.id.etLocation);
        ivSatisfied = findViewById(R.id.ivSatisfied);
        ivNeutral = findViewById(R.id.ivNeutral);
        ivUnsatisfied = findViewById(R.id.ivUnsatisfied);

        ivSatisfied.setOnClickListener(this);
        ivNeutral.setOnClickListener(this);
        ivUnsatisfied.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String website = etWebsite.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || website.isEmpty() || location.isEmpty()) {
            Toast.makeText(CreateNewContact.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            // 1: satisfied; 0: neutral; -1: unsatisfied
            int attitude = 0;
            if (v.getId() == R.id.ivSatisfied) {
                attitude = 1;
            } else if (v.getId() == R.id.ivNeutral) {
                attitude = 0;
            } else {
                attitude = -1;
            }
            intent.putExtra("attitude", attitude);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("website", website);
            intent.putExtra("location", location);
            setResult(RESULT_OK, intent);
            CreateNewContact.this.finish();
        }
    }
}