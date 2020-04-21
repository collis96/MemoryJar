package com.arcticumi.memoryjar;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@androidx.room.Database(entities = {Memory.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract Dao dao();
}
