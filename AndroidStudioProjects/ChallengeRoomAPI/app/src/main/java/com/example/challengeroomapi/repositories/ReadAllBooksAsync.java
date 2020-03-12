package com.example.challengeroomapi.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.room.BookDAO;
import com.example.challengeroomapi.room.ConnectionToDB;

import java.util.List;

public class ReadAllBooksAsync extends AsyncTask<Void, Void, List<Book>> {
    private AsyncTaskCallback<List<Book>> callback;
    private Exception exception;
    private BookDAO bookDAO;

    public ReadAllBooksAsync(Context context, AsyncTaskCallback<List<Book>> callback) {
        this.callback = callback;
        exception = null;
        bookDAO = ConnectionToDB.getInstance(context).getDatabase().getBookDAO();
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        List<Book> bookList = null;
        try {
            bookList = bookDAO.getAllBooks();
        } catch (Exception e) {
            exception = e;
        }
        return bookList;
    }

    @Override
    protected void onPostExecute(List<Book> bookList) {
        super.onPostExecute(bookList);

        if (callback != null) {
            if (exception == null) {
                callback.handleResponse(bookList);
            } else {
                callback.handleFault(exception);
            }
        }
    }
}
