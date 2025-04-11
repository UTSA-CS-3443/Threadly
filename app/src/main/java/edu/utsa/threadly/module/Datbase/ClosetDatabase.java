package edu.utsa.threadly.module.Datbase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Closet.class, Outfit.class, ClothingItem.class}, version = 1)

public abstract class ClosetDatabase extends RoomDatabase {
    public abstract ClosetDao closetDao();
}
