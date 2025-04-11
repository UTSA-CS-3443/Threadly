package edu.utsa.threadly.module.Database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private static DatabaseClient instance;
    private final ClosetDatabase database;

    private DatabaseClient(Context context) {
        database = Room.databaseBuilder(context, ClosetDatabase.class, "app_database").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public ClosetDatabase getDatabase() {
        return database;
    }
}