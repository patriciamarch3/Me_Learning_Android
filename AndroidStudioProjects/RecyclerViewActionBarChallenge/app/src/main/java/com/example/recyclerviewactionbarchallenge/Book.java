package com.example.recyclerviewactionbarchallenge;

public class Book {
    private String genre;
    private String title;
    private String author;

    public Book(String genre, String title, String author) {
        this.genre = genre;
        this.title = title;
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
