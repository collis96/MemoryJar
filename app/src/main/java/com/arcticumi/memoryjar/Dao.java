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
    public List<Memory> getMemories();

    @Query("SELECT * FROM memories WHERE memoryId LIKE :id")
    public Memory findById(int id);

    @Query("SELECT * FROM memories ORDER BY memory_date_numeric ASC")
    public List<Memory> getOldest();

    @Query("SELECT * FROM memories ORDER BY memory_date_numeric DESC")
    public List<Memory> getNewest();

    @Query("SELECT * FROM memories WHERE memory_category LIKE :cat")
    public List<Memory> getByCategory(String cat);

    @Query("SELECT * FROM memories ORDER BY RANDOM() LIMIT 1")
    public Memory getRandom();

    @Query("SELECT * FROM memories WHERE memory_is_favourite = 1")
    public List<Memory> getFavourites();

    @Query("SELECT * FROM memories WHERE memory_date_numeric LIKE :date")
    public List<Memory> getByDate(String date);

    @Update
    public void updateMemory(Memory memory);

    @Delete
    public void delete(Memory memory);

}
