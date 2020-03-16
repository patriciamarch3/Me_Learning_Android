package com.example.challengeroomapi.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.challengeroomapi.room.AppDatabase;
import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.room.BookDAO;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

class BookRepository {
    private BookDAO bookDAO;
    private LiveData<List<Book>> books;

    BookRepository(Application application) {
        bookDAO = AppDatabase.getInstance(application).getBookDAO();
        books = bookDAO.getAllBooks();
    }

    // use submit function that accepts callable task that would throw exception to handle error on the UI
    void insert(Book book) throws Exception {
        Callable<Book> task = () -> {
            bookDAO.insert(book);
            return book;
        };
        Future future = AppDatabase.databaseExecutor.submit(task);
        future.get();
    }

    void update(Book book) throws Exception {
        Callable<Book> task = () -> {
            bookDAO.update(book);
            return book;
        };
        Future future = AppDatabase.databaseExecutor.submit(task);
        future.get();
    }

    void delete(Book book) throws Exception {
        Callable<Book> task = () -> {
            bookDAO.delete(book);
            return book;
        };
        Future future = AppDatabase.databaseExecutor.submit(task);
        future.get();
    }

    void delete(long ISBN) throws Exception {
        Callable<Long> task = () -> {
            bookDAO.delete(ISBN);
            return ISBN;
        };
        Future future = AppDatabase.databaseExecutor.submit(task);
        future.get();
    }

    LiveData<Book> getBookByISBN(long ISBN) throws Exception {
        Callable<LiveData<Book>> task = () -> bookDAO.getBookByISBN(ISBN);
        Future<LiveData<Book>> future = AppDatabase.databaseExecutor.submit(task);
        return future.get();
    }

    LiveData<List<Book>> getBooks() {
        return books;
    }
}
