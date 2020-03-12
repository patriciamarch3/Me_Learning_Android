package com.example.textfilevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    EditText etFirstName, etLastName;
    Button btnAdd, btnSave;
    TextView tvData;

    ArrayList<Person> persons;
    final String FILE_PATH = "persons.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        tvData = findViewById(R.id.tvData);
        persons = new ArrayList<>();
        loadDataFromFile();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                if (firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill ALL fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Person person = new Person(firstName.trim(), lastName.trim());
                    persons.add(person);

                    setTextToTextView();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // gets connection to the file
                    FileOutputStream file = openFileOutput(FILE_PATH, MODE_PRIVATE);
                    // write to the corresponding file
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);

                    for (Person person : persons) {
                        outputStreamWriter.write(person.toString());
                    }

                    outputStreamWriter.flush();
                    file.close();

                    Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadDataFromFile() {
        persons.clear();

        String line;
        File file = getApplicationContext().getFileStreamPath(FILE_PATH);
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "No data currently in the database!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILE_PATH)));
                while ((line = reader.readLine()) != null) {
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");
                        Person person = new Person(tokenizer.nextToken(), tokenizer.nextToken());
                        persons.add(person);
                }
                reader.close();

                setTextToTextView();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setTextToTextView() {
        String text = "";
        for (Person person : persons) {
            text += person.toString();
        }
        tvData.setText(text);
    }
}
