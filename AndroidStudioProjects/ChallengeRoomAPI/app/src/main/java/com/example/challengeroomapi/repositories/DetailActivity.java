package com.example.challengeroomapi.repositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.room.Book;

public class DetailActivity extends AppCompatActivity {
    EditText etISBN, etTitle, etAuthor;
    Button btnDelete, btnEdit, btnApply;
    long ISBN;
    Book book;
    BooksViewModel booksViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        etISBN = findViewById(R.id.etISBN);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnApply = findViewById(R.id.btnApply);
        btnApply.setVisibility(View.GONE);

        ISBN = getIntent().getLongExtra("isbn", 0);
        etISBN.setText(Long.toString(ISBN));

        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        booksViewModel.getBookByISBN(ISBN).observe(this, new Observer<Book>() {
            @Override
            public void onChanged(Book foundBook) {
                if (foundBook != null) {
                    book = foundBook;
                    etTitle.setText(book.getTitle());
                    etAuthor.setText(book.getAuthor());
                }
            }
        });

        etISBN.setEnabled(false);
        etTitle.setEnabled(false);
        etAuthor.setEnabled(false);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteBookAsync(DetailActivity.this.getApplicationContext(), new AsyncTaskCallback<Book>() {
                    @Override
                    public void handleResponse(Book response) {
//                        Toast.makeText(DetailActivity.this,
//                                response.getTitle() + " by " + response.getAuthor() + " deleted successfully!",
//                                Toast.LENGTH_SHORT).show();
                        replyIntent.putExtra("success", response.getTitle() + " by " + response.getAuthor() + " deleted successfully!");
                        setResult(RESULT_OK, replyIntent);
                        DetailActivity.this.finish();
                    }

                    @Override
                    public void handleFault(Exception e) {
//                        Toast.makeText(DetailActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        replyIntent.putExtra("error", "Error! " + e.getMessage());
                        setResult(RESULT_CANCELED, replyIntent);
                        DetailActivity.this.finish();
                    }
                }).execute(ISBN);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle.setEnabled(true);
                etAuthor.setEnabled(true);
                btnApply.setVisibility(View.VISIBLE);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = etTitle.getText().toString();
                String newAuthor = etAuthor.getText().toString();
                if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Please enter ALL fields!", Toast.LENGTH_SHORT).show();
                } else {
                    book.setTitle(newTitle.trim());
                    book.setAuthor(newAuthor.trim());
                    new UpdateBookAsync(DetailActivity.this.getApplicationContext(), new AsyncTaskCallback<Book>() {
                        @Override
                        public void handleResponse(Book response) {
//                            Toast.makeText(DetailActivity.this,
//                                    response.getTitle() + " by " + response.getAuthor() + " updated successfully!",
//                                    Toast.LENGTH_SHORT).show();
                            replyIntent.putExtra("success", response.getTitle() + " by " + response.getAuthor() + " updated successfully!");
                            setResult(RESULT_OK, replyIntent);
                            DetailActivity.this.finish();
                        }

                        @Override
                        public void handleFault(Exception e) {
//                            Toast.makeText(DetailActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            replyIntent.putExtra("error", "Error! " + e.getMessage());
                            setResult(RESULT_CANCELED, replyIntent);
                            DetailActivity.this.finish();
                        }
                    }).execute(book);
                }
            }
        });
    }
}
