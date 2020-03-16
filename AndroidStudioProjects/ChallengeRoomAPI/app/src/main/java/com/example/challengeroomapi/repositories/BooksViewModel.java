package com.example.challengeroomapi.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.challengeroomapi.room.AppDatabase;
import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.room.BookDAO;

import java.util.List;

public class BooksViewModel extends AndroidViewModel {
    private LiveData<List<Book>> books;
    private BookDAO bookDAO;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        bookDAO = AppDatabase.getInstance(application).getBookDAO();
        books = bookDAO.getAllBooksLive();
    }

    LiveData<Book> getBookByISBN(long ISBN) {
        return bookDAO.getBookByISBNLive(ISBN);
    }

    LiveData<List<Book>> getBooks() {
        return books;
    }
}
