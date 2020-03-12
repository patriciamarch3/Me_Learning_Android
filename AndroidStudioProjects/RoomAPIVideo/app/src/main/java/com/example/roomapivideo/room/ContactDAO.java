package com.example.roomapivideo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM Contact WHERE id = :id")
    void delete(long id);

    @Update
    void update(Contact contact);

    @Query("SELECT * FROM Contact ORDER BY name")
    List<Contact> getAllContacts();

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getContactByID(long id);
}
