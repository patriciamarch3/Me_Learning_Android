package com.example.recyclerviewactionbarchallenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Book> books;
    final static int ADD_BOOK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books = new ArrayList<>();
        books.add(new Book("Scfi", "The Three-Body Problem", "Cixin Liu"));
        books.add(new Book("Romance", "Twilight", "Stephanie"));
        books.add(new Book("Drama", "What is Love?", "Twice"));
        books.add(new Book("Scfi", "Superhuman", "NCT127"));
        books.add(new Book("Drama", "Fake Love", "BTS"));
        books.add(new Book("Romance", "My First and Last", "NCT Dream"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List of Books");

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        myAdapter = new BookAdapter(this, books);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addBook:
//                Toast.makeText(MainActivity.this, "Add a book!", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(this, AddBook.class), ADD_BOOK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_BOOK) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra("title");
                String author = data.getStringExtra("author");
                String genre = data.getStringExtra("genre");

                books.add(new Book(genre, title, author));
                myAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Data not received!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
