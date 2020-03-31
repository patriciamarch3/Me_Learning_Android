package com.example.challengeroomapi.activities;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.fragments.OnActionListener;
import com.example.challengeroomapi.repositories.BooksViewModel;
import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.uihelpers.TopToast;

public class DetailActivity extends MenuActivity implements OnActionListener {
    private BooksViewModel booksViewModel;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_detail_activity));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
            TopToast.create(this, getString(R.string.message_error, e.getMessage()));
        }
    }

    @Override
    public void onDeleteClicked() {
        try{
            booksViewModel.delete(book);
            setReply(RESULT_OK, getString(R.string.message_delete, bookTostring(book)));
        } catch (Exception e) {
            setReply(RESULT_CANCELED, e.getMessage());
        }
    }

    @Override
    public boolean onApplyClicked(String newTitle, String newAuthor) {
        if (newTitle.isEmpty() || newAuthor.isEmpty()) {
            TopToast.create(this, getString(R.string.message_enter_all_fields));
            return true;
        } else {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            try{
                booksViewModel.update(book);
                booksViewModel.setEditClicked(false);
                setReply(RESULT_OK, getString(R.string.message_update, bookTostring(book)), false);
                return false;
            } catch (Exception e) {
                setReply(RESULT_CANCELED, e.getMessage());
                return true;
            }
        }
    }

    private void setReply(int resultCode, String message) {
        setReply(resultCode, message, true);
    }

    private void setReply(int resultCode, String message, boolean finish) {
        String key, value;
        if (resultCode == RESULT_OK) {
            key = "success";
            value = getString(R.string.message_success, message);
        } else {
            key = "error";
            value = getString(R.string.message_error, message);
        }
        if (finish) {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(key, value);
            setResult(resultCode, replyIntent);
            DetailActivity.this.finish();
        } else {
            TopToast.create(DetailActivity.this, value);
            setResult(resultCode);
        }
    }
}
