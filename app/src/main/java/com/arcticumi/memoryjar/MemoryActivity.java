package com.arcticumi.memoryjar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

public class MemoryActivity extends Activity {

    private Bundle bundle;
    private int memoryId;
    private Memory thisMemory;
    private TextView tvTitle, tvCategory, tvDate, tvDesc;
    private ImageView ivMemoryImage;
    private VideoView vvMemoryVideo;
    private Button btnEdit, btnCancel;
    private Uri mediaUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_view);
        
        // Assign views to variables
        tvTitle = findViewById(R.id.tvMemoryTitle);
        tvCategory = findViewById(R.id.tvMemoryCategory);
        tvDate = findViewById(R.id.tvMemoryDate);
        tvDesc = findViewById(R.id.tvMemoryDesc);
        ivMemoryImage = findViewById(R.id.ivMemoryImage);
        vvMemoryVideo = findViewById(R.id.vvMemoryVideo);
        btnEdit = findViewById(R.id.btnMemoryEdit);
        btnCancel = findViewById(R.id.btnMemoryCancel);

        // Pass memory ID from previous Android activity
        bundle = getIntent().getExtras();
        if (bundle != null){
            memoryId = bundle.getInt("int_value");
        }

        // Find memory information by ID
        thisMemory = MainActivity.applicationDatabase.dao().findById(memoryId);
        
        // Populate views with memory information
        populateViews();

        // Set onClickListeners
        setListeners();
    }

    private void setListeners(){
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editMem = new Intent( MemoryActivity.this, EditMemActivity.class);
                editMem.putExtra("int_value", thisMemory.getMemoryId());
                startActivity(editMem);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(MemoryActivity.this, MainActivity.class);
                startActivity(cancel);
            }
        });
        vvMemoryVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vvMemoryVideo.isPlaying()){
                    vvMemoryVideo.start();
                } else if (vvMemoryVideo.isPlaying() && vvMemoryVideo.canPause()){
                    vvMemoryVideo.pause();
                } else {
                    vvMemoryVideo.resume();
                }
            }
        });
    }

    private void populateViews(){
        tvTitle.setText(thisMemory.getMemoryTitle());
        tvDesc.setText(thisMemory.getMemoryDescription());
        tvCategory.setText(thisMemory.getMemoryCategory());
        tvDate.setText(thisMemory.getMemoryDate());
        // Check mediaUri type
        if(thisMemory.getMemoryMediaUri()!=null){
            mediaUri = Uri.parse(thisMemory.getMemoryMediaUri());
            if(CheckMediaType.check(getApplicationContext(), mediaUri) == "jpg"){
                ivMemoryImage.setVisibility(View.VISIBLE);
                vvMemoryVideo.setVisibility(View.INVISIBLE);
                ivMemoryImage.setImageURI(mediaUri);
            } else if(CheckMediaType.check(getApplicationContext(), mediaUri) == "mp4") {
                ivMemoryImage.setVisibility(View.INVISIBLE);
                vvMemoryVideo.setVisibility(View.VISIBLE);
                vvMemoryVideo.setVideoURI(mediaUri);
            } else {
                Toast.makeText(MemoryActivity.this, "Incorrect media type, JPG and MP4 accepted.", Toast.LENGTH_LONG).show();
                ivMemoryImage.setVisibility(View.INVISIBLE);
                vvMemoryVideo.setVisibility(View.INVISIBLE);
            }
        } else {
            ivMemoryImage.setVisibility(View.INVISIBLE);
            vvMemoryVideo.setVisibility(View.INVISIBLE);
        }
    }

}
