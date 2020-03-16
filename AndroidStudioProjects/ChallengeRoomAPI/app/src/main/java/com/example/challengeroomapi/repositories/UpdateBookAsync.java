package com.example.challengeroomapi.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.challengeroomapi.room.AppDatabase;
import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.room.BookDAO;

public class UpdateBookAsync extends AsyncTask<Book, Void, Book> {
    private AsyncTaskCallback<Book> callback;
    private Exception exception;
    private BookDAO bookDAO;

    public UpdateBookAsync(Context context, AsyncTaskCallback<Book> callback) {
        this.callback = callback;
        exception = null;
        bookDAO = AppDatabase.getInstance(context).getBookDAO();
    }

    @Override
    protected Book doInBackground(Book... books) {
        Book book = books[0];
        try {
            bookDAO.update(book);
        } catch (Exception e) {
            exception = e;
        }

        return book;
    }

    @Override
    protected void onPostExecute(Book book) {
        super.onPostExecute(book);

        if (callback != null) {
            if (exception == null) {
                callback.handleResponse(book);
            } else {
                callback.handleFault(exception);
            }
        }
    }
}
