package com.example.recyclerviewactionbarchallenge;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBook extends AppCompatActivity {
    EditText etTitle, etAuthor, etGenre;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add a New Book");
        actionBar.setDisplayHomeAsUpEnabled(true);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etGenre = findViewById(R.id.etGenre);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitle.getText() == null || etAuthor.getText() == null || etGenre.getText() == null) {
                    Toast.makeText(AddBook.this, "Please Fill ALL fields!", Toast.LENGTH_SHORT).show();
                } else {
                    String title = etTitle.getText().toString().trim();
                    String author = etAuthor.getText().toString().trim();
                    String genre = etGenre.getText().toString().trim();

                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("author", author);
                    intent.putExtra("genre", genre);
                    setResult(RESULT_OK, intent);
                    AddBook.this.finish();
                }
            }
        });
    }
}
