package com.arcticumi.memoryjar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewMemActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etCategory;
    private ImageView ivImgPreview, ivNoMediaSelected;
    private VideoView vvVideoPreview;
    private Button addMedia, post, cancel;
    private String mediaUri;
    private static final int MEDIA_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memory);

        // Assign views to variables
        etTitle = findViewById(R.id.etNewMemAddTitle);
        etDescription = findViewById(R.id.etNewMemAddDesc);
        etCategory = findViewById(R.id.etCategory);
        ivImgPreview = findViewById(R.id.ivNewMemImgPreview);
        ivNoMediaSelected = findViewById(R.id.ivNoMediaSelected);
        vvVideoPreview = findViewById(R.id.vVNewMemVideoPreview);
        addMedia = findViewById(R.id.btnNewMemAddMedia);
        post = findViewById(R.id.btnNewMemPost);
        cancel = findViewById(R.id.btnMemoryCancel);

        // Set listeners
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
            mediaUri = data.getData().toString();
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

    private void addMemory(){
        String title = etTitle.getText().toString();
        String desc = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance().format(calendar.getTime());
        String numericDate = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        Memory memory = new Memory();
        memory.setMemoryTitle(title);
        memory.setMemoryDescription(desc);
        memory.setMemoryCategory(category);
        memory.setMemoryDate(date);
        memory.setMemoryDateNumeric(numericDate);
        memory.setMemoryMediaUri(mediaUri);
        memory.setMemoryIsFavourite(0);
        MainActivity.applicationDatabase.dao().addMem(memory);
        Intent done = new Intent(NewMemActivity.this, MainActivity.class);
        startActivity(done);
    }

    private void selectMedia(){
        Intent imgIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imgIntent.setType("image/* video/*");
        startActivityForResult(imgIntent, MEDIA_PICK_CODE);
    }

    private void cancelNewMem(){
        Intent newCancel = new Intent(NewMemActivity.this, MainActivity.class);
        startActivity(newCancel);
    }

    private void setListeners(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNewMem();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemory();
                Toast.makeText(NewMemActivity.this, "New Memory Added!", Toast.LENGTH_LONG).show();
            }
        });
        addMedia.setOnClickListener(new View.OnClickListener() {
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
        vvVideoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vvVideoPreview.isPlaying()){
                    vvVideoPreview.start();
                } else if (vvVideoPreview.isPlaying() && vvVideoPreview.canPause()){
                    vvVideoPreview.pause();
                } else {
                    vvVideoPreview.resume();
                }
            }
        });
    }

}
