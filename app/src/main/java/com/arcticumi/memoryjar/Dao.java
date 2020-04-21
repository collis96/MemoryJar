package com.arcticumi.memoryjar;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    public void addMem(Memory memory);

    @Query("SELECT * FROM memories")
    public List<Memory> getPosts();

    @Query("SELECT * FROM memories WHERE memoryId LIKE :id")
    public Memory findById(int id);

    @Update
    public void updateMemory(Memory memory);

    @Delete
    public void delete(Memory memory);

}
