package com.arcticumi.memoryjar;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    public void addPost(Post post);

    @Query("select * from posts")
    public List<Post> getPosts();

}
