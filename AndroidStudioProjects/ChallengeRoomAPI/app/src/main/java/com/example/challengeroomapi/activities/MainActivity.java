package com.example.challengeroomapi.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.repositories.BooksViewModel;
import com.example.challengeroomapi.room.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookAdapter.ItemClicked {
    List<Book> books;
    final int BOOK_DETAIL = 1;

    EditText etISBN, etTitle, etAuthor;
    Button btnCreate;
    BooksViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books = new ArrayList<>();
        etISBN = findViewById(R.id.etISBN);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        btnCreate = findViewById(R.id.btnCreate);

        viewModel = new ViewModelProvider(this).get(BooksViewModel.class);

        btnCreate.setOnClickListener((View v) -> {
                String ISBNString = etISBN.getText().toString();
                String title = etTitle.getText().toString();
                String author = etAuthor.getText().toString();

                if (ISBNString.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill ALL fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Book book = new Book(Long.parseLong(ISBNString), title, author);
                    try{
                        viewModel.insert(book);
                        etISBN.getText().clear();
                        etTitle.getText().clear();
                        etAuthor.getText().clear();
                        Toast.makeText(MainActivity.this, book.toString() + " ADDED successfully!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "ERROR! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    @Override
    public void onClickItem(long ISBN) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        // only pass the primary key and the other activity queries for the object
        // rather than query for the object here and pass each of the object members,
        // because what if there are hundreds of members or the members contain objects
        intent.putExtra("isbn", ISBN);
        startActivityForResult(intent, BOOK_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BOOK_DETAIL) {
            if (resultCode != RESULT_OK) {
                if (data != null) {
                    Toast.makeText(MainActivity.this, data.getStringExtra("error"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, data.getStringExtra("success"), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
