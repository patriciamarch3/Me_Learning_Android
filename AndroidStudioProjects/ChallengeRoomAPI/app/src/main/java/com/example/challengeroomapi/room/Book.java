package com.example.challengeroomapi.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Book {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long ISBN;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String author;

    @Ignore
    public Book() {
        super();
    }

    public Book(long ISBN, String title, String author) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
    }

    @Ignore
    public Book(Book book) {
        ISBN = book.getISBN();
        title = book.getTitle();
        author = book.getAuthor();
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "\"" + title + "\"" + " by " + author;
    }
}
