package com.arcticumi.memoryjar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memories")
public class Memory {

    @PrimaryKey(autoGenerate = true)
    private int memoryId;

    @ColumnInfo(name = "memory_title")
    private String memoryTitle;

    @ColumnInfo(name = "memory_description")
    private String memoryDescription;

    @ColumnInfo(name = "memory_date")
    private String memoryDate;

    @ColumnInfo(name = "memory_date_numeric")
    private String memoryDateNumeric;

    @ColumnInfo(name = "memory_media_uri")
    private String memoryMediaUri;

    @ColumnInfo(name = "memory_is_favourite")
    private int memoryIsFavourite;

    @ColumnInfo(name = "memory_category")
    private String memoryCategory;

    @ColumnInfo(name = "memory_rating")
    private int memoryRating;

    public String getMemoryDateNumeric() {
        return memoryDateNumeric;
    }

    public void setMemoryDateNumeric(String memoryDateNumeric) {
        this.memoryDateNumeric = memoryDateNumeric;
    }

    public String getMemoryDate() {
        return memoryDate;
    }

    public void setMemoryDate(String memoryDate) {
        this.memoryDate = memoryDate;
    }

    public int getMemoryIsFavourite() {
        return memoryIsFavourite;
    }

    public void setMemoryIsFavourite(int memoryIsFavourite) {
        this.memoryIsFavourite = memoryIsFavourite;
    }

    public String getMemoryCategory() {
        return memoryCategory;
    }

    public void setMemoryCategory(String memoryCategory) {
        this.memoryCategory = memoryCategory;
    }

    public int getMemoryRating() {
        return memoryRating;
    }

    public void setMemoryRating(int memoryRating) {
        this.memoryRating = memoryRating;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public String getMemoryTitle() {
        return memoryTitle;
    }

    public void setMemoryTitle(String memoryTitle) {
        this.memoryTitle = memoryTitle;
    }

    public String getMemoryDescription() {
        return memoryDescription;
    }

    public void setMemoryDescription(String memoryDescription) {
        this.memoryDescription = memoryDescription;
    }

    public String getMemoryMediaUri() {
        return memoryMediaUri;
    }

    public void setMemoryMediaUri(String memoryMediaUri) {
        this.memoryMediaUri = memoryMediaUri;
    }
}
