package com.example.challengeroomapi.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String themeColor = preferences.getString("themeColor", "green");
        String language = preferences.getString("language", "english");
        switch (themeColor) {
            case "red":
                setTheme(R.style.RedTheme);
                break;

            case "blue":
                setTheme(R.style.BlueTheme);
                break;

            default:
                setTheme(R.style.GreenTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Book");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
    public boolean onApplyClicked(String newTitle, String newAuthor) {
        if (newTitle.isEmpty() || newAuthor.isEmpty()) {
            TopToast.create(this, "Please enter ALL fields!");
            return true;
        } else {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            try{
                booksViewModel.update(book);
                booksViewModel.setEditClicked(false);
                setReply(RESULT_OK, book.toString() + " UPDATED", false);
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
            value = message + " successfully!";
        } else {
            key = "error";
            value = "ERROR! " + message;
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
