package com.arcticumi.memoryjar;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_new_memory);
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
                addMemory();
                Toast.makeText(NewMemActivity.this, "New Memory Added!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addMemory(){
        String title = etTitle.getText().toString();
        String desc = etDescription.getText().toString();
        Memory memory = new Memory();
        memory.setMemoryTitle(title);
        memory.setMemoryDescription(desc);
        MainActivity.applicationDatabase.dao().addMem(memory);
        etTitle.setText("");
        etDescription.setText("");
    }

}
