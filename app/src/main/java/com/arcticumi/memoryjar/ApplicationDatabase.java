package com.arcticumi.memoryjar;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Memory.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract Dao dao();
}
