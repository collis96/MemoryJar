package com.arcticumi.memoryjar;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditMemActivity extends AppCompatActivity {

    public static final String MEMORY_ID = "memory_id";
    private EditText etTitle;
    private EditText etDescription;
    private Bundle bundle;
    private int memoryId;
    private ImageView ivImgPreview;
    private Button editPhoto, editVideo, editMusic, update;
    private Image imgUrl;
    private Memory thisMemory;
    private static final String TAG = "EditMemActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memory);
        bundle = getIntent().getExtras();
        if (bundle != null){
            memoryId = bundle.getInt("int_value");
        }
        thisMemory = MainActivity.applicationDatabase.dao().findById(memoryId);
        etTitle = findViewById(R.id.etNewMemAddTitle);
        etDescription = findViewById(R.id.etNewMemAddDesc);
        ivImgPreview = findViewById(R.id.ivNewMemImgPreview);
        editPhoto = findViewById(R.id.btnNewMemAddImg);
        editVideo = findViewById(R.id.btnNewMemAddVideo);
        editMusic = findViewById(R.id.btnNewMemAddMusic);
        update = findViewById(R.id.btnNewMemPost);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMemory(thisMemory);
                Toast.makeText(EditMemActivity.this, "Memory Updated", Toast.LENGTH_LONG).show();
            }
        });
        etTitle.setText(thisMemory.getMemoryTitle());
        etDescription.setText(thisMemory.getMemoryDescription());
    }

    public void editMemory(Memory memory){
        String title = etTitle.getText().toString();
        String desc = etDescription.getText().toString();
        memory.setMemoryTitle(title);
        memory.setMemoryDescription(desc);
        MainActivity.applicationDatabase.dao().updateMemory(memory);
    }

    public void cancelEdit(){

    }

    public Memory findMemory(int id){
        return MainActivity.applicationDatabase.dao().findById(id);
    }
}
