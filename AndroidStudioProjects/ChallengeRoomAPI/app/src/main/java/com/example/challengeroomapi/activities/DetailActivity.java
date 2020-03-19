package com.example.challengeroomapi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.fragments.OnActionListener;
import com.example.challengeroomapi.repositories.BooksViewModel;
import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.uihelpers.TopToast;

public class DetailActivity extends AppCompatActivity implements OnActionListener {
    private BooksViewModel booksViewModel;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        long ISBN = getIntent().getLongExtra("isbn", -1);
        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        try {
            booksViewModel.getBookByISBN(ISBN).observe(this, (Book foundBook) -> {
                if (foundBook != null) {
                    book = foundBook;
                    booksViewModel.setBookForDetail(book);
                }
            });
        } catch (Exception e) {
            TopToast.create(this, "ERROR! " + e.getMessage());
        }
    }

    @Override
    public void onDeleteClicked() {
        try{
            booksViewModel.delete(book);
            setReply(RESULT_OK, book.toString() + " DELETED");
        } catch (Exception e) {
            setReply(RESULT_CANCELED, e.getMessage());
        }
    }

    @Override
    public void onApplyClicked(String newTitle, String newAuthor) {
        if (newTitle.isEmpty() || newAuthor.isEmpty()) {
            TopToast.create(this, "Please enter ALL fields!");
        } else {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            try{
                booksViewModel.update(book);
                booksViewModel.setEditClicked(false);
                setReply(RESULT_OK, book.toString() + " UPDATED", false);
            } catch (Exception e) {
                setReply(RESULT_CANCELED, e.getMessage());
            }
        }
    }

    private void setReply(int resultCode, String message) {
        setReply(resultCode, message, true);
    }

    private void setReply(int resultCode, String message, boolean finish) {
        Intent replyIntent = new Intent();
        String key, value;
        if (resultCode == RESULT_OK) {
            key = "success";
            value = message + " successfully!";
        } else {
            key = "error";
            value = "ERROR! " + message;
        }
        replyIntent.putExtra(key, value);
        setResult(resultCode, replyIntent);
        if (finish) {
            DetailActivity.this.finish();
        }
    }
}
