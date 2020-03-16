package com.example.challengeroomapi.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.challengeroomapi.room.Book;

import java.util.List;

public class BooksViewModel extends AndroidViewModel {
    private BookRepository repository;
    private LiveData<List<Book>> books;

    public BooksViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        books = repository.getBooks();
    }

    public void insert(Book book) throws Exception {
        repository.insert(book);
    }

    public void update(Book book) throws Exception {
        repository.update(book);
    }

    public void delete(Book book) throws Exception {
        repository.delete(book);
    }

    public void delete(long ISBN) throws Exception {
        repository.delete(ISBN);
    }

    public LiveData<Book> getBookByISBN(long ISBN) throws Exception {
        return repository.getBookByISBN(ISBN);
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }
}