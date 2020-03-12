package com.example.challengeroomapi.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.challengeroomapi.room.Book;
import com.example.challengeroomapi.room.BookDAO;
import com.example.challengeroomapi.room.ConnectionToDB;

public class ReadBookByISBNAsync extends AsyncTask<Long, Void, Book> {
    private AsyncTaskCallback<Book> callback;
    private Exception exception;
    private BookDAO bookDAO;

    public ReadBookByISBNAsync(Context context, AsyncTaskCallback<Book> callback) {
        this.callback = callback;
        exception = null;
        bookDAO = ConnectionToDB.getInstance(context).getDatabase().getBookDAO();
    }

    @Override
    protected Book doInBackground(Long... longs) {
        Book book = null;
        try {
            book = bookDAO.getBookByISBN(longs[0]);
            if (book == null) {
                throw new Exception("Book already exists in the database!");
            }
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
