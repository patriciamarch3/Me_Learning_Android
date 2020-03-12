package com.example.roomapivideo.room;

import android.content.Context;

import androidx.room.Room;

public class ConnectToDB {
    private static ConnectToDB instance;
    private AppDatabase db;

    private ConnectToDB(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "db_contacts").build();
    }

    public static ConnectToDB getInstance(Context context) {
        synchronized (ConnectToDB.class) {
            if (instance == null) {
                instance = new ConnectToDB(context);
            }
            return instance;
        }
    }

    public AppDatabase getDb() {
        return db;
    }
}
