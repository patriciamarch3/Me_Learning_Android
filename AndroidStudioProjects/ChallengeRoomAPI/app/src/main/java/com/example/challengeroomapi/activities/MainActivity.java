package com.example.challengeroomapi.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.fragments.BookAdapter;
import com.example.challengeroomapi.uihelpers.TopToast;

public class MainActivity extends BaseActivity implements BookAdapter.ItemClicked {
    private final int BOOK_DETAIL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_main_activity));
            actionBar.setIcon(R.drawable.library);
            actionBar.setDisplayShowHomeEnabled(true);
        }
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
                    TopToast.create(MainActivity.this, data.getStringExtra("error"));
                }
            } else {
                if (data != null) {
                    TopToast.create(MainActivity.this, data.getStringExtra("success"));
                }
            }
        }
    }
}
