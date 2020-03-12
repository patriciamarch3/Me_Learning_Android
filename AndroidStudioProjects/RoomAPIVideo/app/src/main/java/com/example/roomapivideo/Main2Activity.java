package com.example.roomapivideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomapivideo.repositories.AsyncTaskCallBack;
import com.example.roomapivideo.repositories.DeleteContactAsync;
import com.example.roomapivideo.repositories.FindContactByIdAsync;
import com.example.roomapivideo.repositories.InsertContactAsync;
import com.example.roomapivideo.repositories.ReadAllContactsAsync;
import com.example.roomapivideo.repositories.UpdateContactAsync;
import com.example.roomapivideo.room.ConnectToDB;
import com.example.roomapivideo.room.Contact;
import com.example.roomapivideo.room.ContactDAO;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
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
                    Toast.makeText(Main2Activity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Contact contact = new Contact();
                    contact.setID(Long.parseLong(id.trim()));
                    contact.setName(name.trim());
                    contact.setPhone(phone.trim());
                    try {
                        new InsertContactAsync(Main2Activity.this.getApplicationContext(), new AsyncTaskCallBack<Contact>() {
                            @Override
                            public void handleResponse(Contact response) {
                                Toast.makeText(Main2Activity.this,
                                        response.getName() + " successfully added to database!",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleFault(Exception e) {
                                Toast.makeText(Main2Activity.this,"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).execute(contact);
                        Toast.makeText(Main2Activity.this, "New contact added!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Main2Activity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ReadAllContactsAsync(Main2Activity.this.getApplicationContext(), new AsyncTaskCallBack<List<Contact>>() {
                        @Override
                        public void handleResponse(List<Contact> response) {
                            StringBuilder result = new StringBuilder();
                            for (Contact contact : response) {
                                result.append(contact.getName());
                                result.append(", ");
                                result.append(contact.getPhone());
                                result.append(", ");
                                result.append(contact.getID());
                                result.append("\n");
                            }
                            tvResult.setText(result);
                            Toast.makeText(Main2Activity.this, "All data retrieved!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(Exception e) {
                            Toast.makeText(Main2Activity.this,"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                    Toast.makeText(Main2Activity.this, "New contact added!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Main2Activity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Main2Activity.this, "Must enter ID! Name and phone number is optional.", Toast.LENGTH_SHORT).show();
                } else {
                    new FindContactByIdAsync(Main2Activity.this.getApplicationContext(), new AsyncTaskCallBack<Contact>() {
                        @Override
                        public void handleResponse(Contact response) {
                            if (!name.isEmpty()) {
                                response.setName(name.trim());
                            }
                            if (!phone.isEmpty()) {
                                response.setPhone(phone.trim());
                            }
                            new UpdateContactAsync(Main2Activity.this.getApplicationContext(), new AsyncTaskCallBack<Contact>() {
                                @Override
                                public void handleResponse(Contact response) {
                                    Toast.makeText(Main2Activity.this,
                                            response.getName() + " successfully updated!",
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(Exception e) {
                                    Toast.makeText(Main2Activity.this,"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }).execute(response);
                        }

                        @Override
                        public void handleFault(Exception e) {
                            Toast.makeText(Main2Activity.this,"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).execute(Long.parseLong(id.trim()));
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etID.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(Main2Activity.this, "Must enter ID!", Toast.LENGTH_SHORT).show();
                } else {
                    new FindContactByIdAsync(Main2Activity.this.getApplicationContext(), new AsyncTaskCallBack<Contact>() {
                        @Override
                        public void handleResponse(Contact response) {
                            new DeleteContactAsync(Main2Activity.this.getApplicationContext(), new AsyncTaskCallBack<Contact>() {
                                @Override
                                public void handleResponse(Contact response) {
                                    Toast.makeText(Main2Activity.this,
                                            response.getName() + " successfully deleted!",
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(Exception e) {
                                    Toast.makeText(Main2Activity.this,"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }).execute(response);
                        }

                        @Override
                        public void handleFault(Exception e) {
                            Toast.makeText(Main2Activity.this,"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).execute(Long.parseLong(id.trim()));
                }
            }
        });
    }
}
