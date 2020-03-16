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

import java.util.Locale;

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
        etISBN.setText(String.format(Locale.getDefault(), "%d", ISBN));

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
                        setReply(RESULT_OK, response.toString() + " deleted");
                    }

                    @Override
                    public void handleFault(Exception e) {
                        setReply(RESULT_CANCELED, e.getMessage());
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
                            setReply(RESULT_OK, response.toString() + " updated");
                        }

                        @Override
                        public void handleFault(Exception e) {
                            setReply(RESULT_CANCELED, e.getMessage());
                        }
                    }).execute(book);
                }
            }
        });
    }

    private void setReply(int resultCode, String message) {
        Intent replyIntent = new Intent();
        String key, value;
        if (resultCode == RESULT_OK) {
            key = "success";
            value = message + " successfully!";
        } else {
            key = "error";
            value = "Error: " + message;
        }
        replyIntent.putExtra(key, value);
        setResult(resultCode, replyIntent);
        DetailActivity.this.finish();
    }
}
