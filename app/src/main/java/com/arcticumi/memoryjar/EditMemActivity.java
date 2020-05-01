package com.arcticumi.memoryjar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditMemActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etCategory;
    private Bundle bundle;
    private int memoryId;
    private ImageView ivImgPreview, ivNoMediaSelected;
    private VideoView vvVideoPreview;
    private Button editMedia, update, cancel;
    private Uri mediaUri;
    private Memory thisMemory;
    private String mediaUriString;
    private static final int MEDIA_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memory);

        // Assign views to variables
        etTitle = findViewById(R.id.etNewMemAddTitle);
        etDescription = findViewById(R.id.etNewMemAddDesc);
        etCategory = findViewById(R.id.etCategory);
        ivImgPreview = findViewById(R.id.ivNewMemImgPreview);
        ivNoMediaSelected = findViewById(R.id.ivNoMediaSelected);
        vvVideoPreview = findViewById(R.id.vVNewMemVideoPreview);
        editMedia = findViewById(R.id.btnNewMemAddMedia);
        update = findViewById(R.id.btnNewMemPost);
        cancel = findViewById(R.id.btnMemoryCancel);

        // Pass memory ID from previous Android activity
        bundle = getIntent().getExtras();
        if (bundle != null){
            memoryId = bundle.getInt("int_value");
        }

        // Find memory information by ID
        thisMemory = MainActivity.applicationDatabase.dao().findById(memoryId);

        // Populate views with memory information
        populateViews();
        // Set OnClickListeners
        setListeners();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                    selectMedia();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MEDIA_PICK_CODE) {
            mediaUriString = data.getData().toString();
            if(CheckMediaType.check(getApplicationContext(),data.getData()) == "jpg"){
                ivImgPreview.setVisibility(View.VISIBLE);
                ivNoMediaSelected.setVisibility(View.INVISIBLE);
                vvVideoPreview.setVisibility(View.INVISIBLE);
                ivImgPreview.setImageURI(data.getData());
            } else if(CheckMediaType.check(getApplicationContext(), data.getData()) == "mp4"){
                vvVideoPreview.setVisibility(View.VISIBLE);
                ivImgPreview.setVisibility(View.INVISIBLE);
                ivNoMediaSelected.setVisibility(View.INVISIBLE);
                vvVideoPreview.setVideoURI(data.getData());
                vvVideoPreview.requestFocus();
                vvVideoPreview.start();
            } else {
                ivNoMediaSelected.setVisibility(View.VISIBLE);
                vvVideoPreview.setVisibility(View.INVISIBLE);
                ivImgPreview.setVisibility(View.INVISIBLE);

            }
        }
    }

    private void selectMedia(){
        Intent imgIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imgIntent.setType("image/* video/*");
        startActivityForResult(imgIntent, MEDIA_PICK_CODE);
    }

    private void editMemory(Memory memory){
        String title = etTitle.getText().toString();
        String desc = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        memory.setMemoryTitle(title);
        memory.setMemoryDescription(desc);
        memory.setMemoryCategory(category);
        memory.setMemoryMediaUri(mediaUriString);
        MainActivity.applicationDatabase.dao().updateMemory(memory);
        Intent done = new Intent(EditMemActivity.this, MainActivity.class);
        startActivity(done);
    }

    private void cancelEdit(){
        Intent cancel = new Intent(EditMemActivity.this, MainActivity.class);
        startActivity(cancel);
    }

    private void setListeners(){
        editMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking the runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        // Request permission
                        String[] perm = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(perm, PERMISSION_CODE);
                    } else {
                        selectMedia();
                    }
                }
                else {
                    // Operating system is older than Marshmallow
                    selectMedia();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMemory(thisMemory);
                Toast.makeText(EditMemActivity.this, "Memory Updated", Toast.LENGTH_LONG).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEdit();
            }
        });
    }

    private void populateViews(){
        etTitle.setText(thisMemory.getMemoryTitle());
        etDescription.setText(thisMemory.getMemoryDescription());
        etCategory.setText(thisMemory.getMemoryCategory());
        // Check mediaUri type
        if(mediaUriString!=null){
            mediaUri = Uri.parse(thisMemory.getMemoryMediaUri());
            mediaUriString = thisMemory.getMemoryMediaUri();
            if(CheckMediaType.check(getApplicationContext(), mediaUri) == "jpg"){
                ivImgPreview.setVisibility(View.VISIBLE);
                vvVideoPreview.setVisibility(View.INVISIBLE);
                ivNoMediaSelected.setVisibility(View.INVISIBLE);
                ivImgPreview.setImageURI(mediaUri);
            } else if(CheckMediaType.check(getApplicationContext(), mediaUri) == "mp4") {
                ivImgPreview.setVisibility(View.INVISIBLE);
                vvVideoPreview.setVisibility(View.VISIBLE);
                ivNoMediaSelected.setVisibility(View.INVISIBLE);
                vvVideoPreview.setVideoURI(mediaUri);
            } else {
                Toast.makeText(EditMemActivity.this, "Incorrect media type, JPG and MP4 accepted.", Toast.LENGTH_LONG).show();
                ivImgPreview.setVisibility(View.INVISIBLE);
                vvVideoPreview.setVisibility(View.INVISIBLE);
                ivNoMediaSelected.setVisibility(View.VISIBLE);
            }
        } else {
            ivImgPreview.setVisibility(View.INVISIBLE);
            vvVideoPreview.setVisibility(View.INVISIBLE);
            ivNoMediaSelected.setVisibility(View.VISIBLE);
        }
    }

}
