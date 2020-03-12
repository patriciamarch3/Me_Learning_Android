package com.example.challengeroomapi.room;

import android.content.Context;

import androidx.room.Room;

public class ConnectionToDB {
    private static ConnectionToDB instance;
    private AppDatabase database;

    private ConnectionToDB(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "db_books").build();
    }

    public static ConnectionToDB getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectionToDB(context);
        }
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
