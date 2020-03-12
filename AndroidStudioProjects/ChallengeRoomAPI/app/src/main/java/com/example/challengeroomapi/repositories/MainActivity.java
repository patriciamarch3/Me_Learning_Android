package com.example.challengeroomapi.repositories;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.room.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookAdapter.ItemClicked {
    List<Book> books;
    final int BOOK_DETAIL = 1;

    EditText etISBN, etTitle, etAuthor;
    Button btnCreate;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books = new ArrayList<>();
        etISBN = findViewById(R.id.etISBN);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        btnCreate = findViewById(R.id.btnCreate);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        updateRecyclerView();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ISBNString = etISBN.getText().toString();
                String title = etTitle.getText().toString();
                String author = etAuthor.getText().toString();

                if (ISBNString.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill ALL fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Book book = new Book(Long.parseLong(ISBNString), title, author);
                    new InsertBookAsync(MainActivity.this.getApplicationContext(), new AsyncTaskCallback<Book>() {
                        @Override
                        public void handleResponse(Book response) {
                            updateRecyclerView();
                            Toast.makeText(MainActivity.this,
                                    response.getTitle() + " by " + response.getAuthor() + "added successfully!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(Exception e) {
                            Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).execute(book);
                }
            }
        });
    }

    private void updateRecyclerView() {
        new ReadAllBooksAsync(this.getApplicationContext(), new AsyncTaskCallback<List<Book>>() {
            @Override
            public void handleResponse(List<Book> response) {
                if (response.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
                books = response;
                myAdapter = new BookAdapter(MainActivity.this, books);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void handleFault(Exception e) {
                Toast.makeText(MainActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).execute();
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
            if (resultCode == RESULT_OK) {
                updateRecyclerView();
            } else {
                Toast.makeText(MainActivity.this, "Error occurs in the activity!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
