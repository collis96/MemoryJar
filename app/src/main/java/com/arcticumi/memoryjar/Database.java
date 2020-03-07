package com.arcticumi.memoryjar;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Post.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract Dao dao();
}
