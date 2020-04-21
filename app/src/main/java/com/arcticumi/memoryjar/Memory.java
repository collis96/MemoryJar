package com.arcticumi.memoryjar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "memories")
public class Memory {

    @PrimaryKey(autoGenerate = true)
    private int memoryId;

    @ColumnInfo(name = "memory_title")
    private String memoryTitle;

    @ColumnInfo(name = "memory_description")
    private String memoryDescription;

    @ColumnInfo(name = "memory_date")
    private Date memoryDate;

    @ColumnInfo(name = "memory_img_url")
    private String memoryImgUrl;

    @ColumnInfo(name = "memory_video_url")
    private String memoryVideoUrl;

    @ColumnInfo(name = "memory_music_url")
    private String memoryMusicUrl;

    @ColumnInfo(name = "memory_is_favourite")
    private int memoryIsFavourite;

    @ColumnInfo(name = "memory_category")
    private String memoryCategory;

    @ColumnInfo(name = "memory_rating")
    private int memoryRating;

    public Date getMemoryDate() {
        return memoryDate;
    }

    public void setMemoryDate(Date memoryDate) {
        this.memoryDate = memoryDate;
    }

    public String getMemoryVideoUrl() {
        return memoryVideoUrl;
    }

    public void setMemoryVideoUrl(String memoryVideoUrl) {
        this.memoryVideoUrl = memoryVideoUrl;
    }

    public String getMemoryMusicUrl() {
        return memoryMusicUrl;
    }

    public void setMemoryMusicUrl(String memoryMusicUrl) {
        this.memoryMusicUrl = memoryMusicUrl;
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

    public String getMemoryImgUrl() {
        return memoryImgUrl;
    }

    public void setMemoryImgUrl(String memoryImgUrl) {
        this.memoryImgUrl = memoryImgUrl;
    }
}
