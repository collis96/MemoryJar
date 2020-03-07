package com.arcticumi.memoryjar;

import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewMemActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private ImageView ivImgPreview;
    private Button addPhoto, addVideo, addMusic, post;
    private Image imgUrl;
    private static final String TAG = "NewMemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);

        etTitle = findViewById(R.id.etNewMemAddTitle);
        etDescription = findViewById(R.id.etNewMemAddDesc);
        ivImgPreview = findViewById(R.id.ivNewMemImgPreview);

        addPhoto = findViewById(R.id.btnNewMemAddImg);
        addVideo = findViewById(R.id.btnNewMemAddVideo);
        addMusic = findViewById(R.id.btnNewMemAddMusic);

        post = findViewById(R.id.btnNewMemPost);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToDevice();
                Toast.makeText(NewMemActivity.this, "New Memory Added!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void writeToDevice(){
        String title = etTitle.getText().toString();
        String desc = etDescription.getText().toString();
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(desc);
        MainActivity.database.dao().addPost(post);
        etTitle.setText("");
        etDescription.setText("");
    }

}
