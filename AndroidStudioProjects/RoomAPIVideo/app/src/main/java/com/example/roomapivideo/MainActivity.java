package com.example.roomapivideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomapivideo.room.ConnectToDB;
import com.example.roomapivideo.room.Contact;
import com.example.roomapivideo.room.ContactDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etID, etPhone, etName;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    TextView tvResult;

    ContactDAO contactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etID = findViewById(R.id.etID);
        etPhone = findViewById(R.id.etPhone);
        etName = findViewById(R.id.etName);
        tvResult = findViewById(R.id.tvResult);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        contactDAO = ConnectToDB.getInstance(getApplicationContext()).getDb().getContactDAO();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etID.getText().toString();
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Contact contact = new Contact();
                    contact.setID(Long.parseLong(id.trim()));
                    contact.setName(name.trim());
                    contact.setPhone(phone.trim());
                    try {
                        contactDAO.insert(contact);
                        Toast.makeText(MainActivity.this, "New contact added!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Contact> contacts = contactDAO.getAllContacts();
                    StringBuilder result = new StringBuilder();
                    for (Contact contact : contacts) {
                        result.append(contact.getName());
                        result.append(", ");
                        result.append(contact.getPhone());
                        result.append(", ");
                        result.append(contact.getID());
                        result.append("\n");
                    }
                    tvResult.setText(result);
                    Toast.makeText(MainActivity.this, "All data retrieved!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etID.getText().toString();
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Must enter ID! Name and phone number is optional.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Contact contact = contactDAO.getContactByID(Long.parseLong(id.trim()));
                        if (!name.isEmpty()) {
                            contact.setName(name.trim());
                            contactDAO.update(contact);
                            Toast.makeText(MainActivity.this, "name is updated!", Toast.LENGTH_SHORT).show();
                        }
                        if (!phone.isEmpty()) {
                            contact.setPhone(phone.trim());
                            contactDAO.update(contact);
                            Toast.makeText(MainActivity.this, "phone number is updated!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etID.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Must enter ID!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        contactDAO.delete(Long.parseLong(id.trim()));
                        Toast.makeText(MainActivity.this, "Successfully deleted!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
