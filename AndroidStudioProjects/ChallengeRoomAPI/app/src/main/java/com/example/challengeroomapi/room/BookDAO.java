package com.example.challengeroomapi.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM Book WHERE id = :ISBN")
    void delete(long ISBN);

    @Query("DELETE FROM BOOK")
    void deleteAll();

    @Query("SELECT * FROM Book WHERE id = :ISBN")
    LiveData<Book> getBookByISBN(long ISBN);

    @Query("SELECT * FROM Book ORDER BY title, author")
    LiveData<List<Book>> getAllBooks();
}
