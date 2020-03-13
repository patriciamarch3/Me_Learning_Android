package com.example.challengeroomapi.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.room.ConnectionToDB;

import java.util.List;

public class BooksViewModel extends AndroidViewModel {
    private LiveData<List<Book>> books;

    public BooksViewModel(@NonNull Application application) {
        super(application);

        books = ConnectionToDB.getInstance(getApplication()).getDatabase().getBookDAO().getAllBooksLive();
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }
}
