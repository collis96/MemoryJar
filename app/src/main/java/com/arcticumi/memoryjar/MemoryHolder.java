package com.arcticumi.memoryjar;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

// MemoryHolder class extends the RecyclerView.ViewHolder to hold information
// inside memory objects
public class MemoryHolder extends RecyclerView.ViewHolder {

    private MemoryListAdapter memoryListAdapter;
    View mView;
    private Button editMemory;
    private Button deleteMemory;
    private VideoView post_media_vid;
    private LinearLayout container;
    private ImageView post_favourite;

    public MemoryHolder(MemoryListAdapter memoryListAdapter, View v){
        super(v);
        this.memoryListAdapter = memoryListAdapter;
        mView = v;
        editMemory = mView.findViewById(R.id.btnEdit);
        deleteMemory = mView.findViewById(R.id.btnDelete);
        post_media_vid = mView.findViewById(R.id.vvContainerVideo);
        container = mView.findViewById(R.id.llContainer);
        post_favourite = mView.findViewById(R.id.ivContainerFavourite);
    }

    public void setTitle(String title){
        TextView post_title = mView.findViewById(R.id.tvContainerTitle);
        post_title.setText(title);
    }

    public void setDescription(String desc){
        TextView post_desc = mView.findViewById(R.id.tvContainerDescription);
        post_desc.setText(desc);
    }

    public void setCategory(String cat){
        TextView post_cat = mView.findViewById(R.id.tvContainerCategory);
        post_cat.setText(cat);
    }

    public void setDate(String date){
        TextView post_date = mView.findViewById(R.id.tvContainerDate);
        post_date.setText(date);
    }

    public void setMedia(String mediaUriString){
        ImageView post_media_img = mView.findViewById(R.id.ivContainerImage);
        if(mediaUriString != null){
            Uri mediaUri = Uri.parse(mediaUriString);
            // Check media uri type
            if(CheckMediaType.check(memoryListAdapter.getContext(), mediaUri) == "jpg"){
                post_media_img.setVisibility(View.VISIBLE);
                post_media_vid.setVisibility(View.GONE);
                post_media_img.setImageURI(mediaUri);
            } else if (CheckMediaType.check(memoryListAdapter.getContext(), mediaUri) == "mp4"){
                post_media_img.setVisibility(View.GONE);
                post_media_vid.setVisibility(View.VISIBLE);
                post_media_vid.setVideoURI(mediaUri);
                post_media_vid.seekTo(1);
            } else {
                Toast.makeText(memoryListAdapter.getContext(), "Incorrect File Type, accepted are JPG and MP4.", Toast.LENGTH_LONG).show();
                post_media_img.setVisibility(View.GONE);
                post_media_vid.setVisibility(View.GONE);
            }
        } else {
            post_media_img.setVisibility(View.GONE);
            post_media_vid.setVisibility(View.GONE);
        }
    }

    public void setFavourite(int isFavourite){
        // Check if memory is a favourite then assign appropriate resource
        if(isFavourite==0){
            post_favourite.setImageResource(R.drawable.outline_star_border_black_48dp);
        } else {
            post_favourite.setImageResource(R.drawable.baseline_star_black_48dp);
        }
    }

    public void setListeners(final Memory memory1, final int position){
        editMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editMem = new Intent(memoryListAdapter.getContext(), EditMemActivity.class);
                editMem.putExtra("int_value", memory1.getMemoryId());
                memoryListAdapter.getContext().startActivity(editMem);
            }
        });
        deleteMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.applicationDatabase.dao().delete(memory1);
                MainActivity.setFilterSelection(0);
                memoryListAdapter.notifyItemRemoved(position);
            }
        });
        post_media_vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!post_media_vid.isPlaying()){
                    post_media_vid.start();
                } else if (post_media_vid.isPlaying() && post_media_vid.canPause()){
                    post_media_vid.pause();
                } else {
                    post_media_vid.resume();
                }
            }
        });
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memoryIntent = new Intent(memoryListAdapter.getContext(), MemoryActivity.class);
                memoryIntent.putExtra("int_value", memory1.getMemoryId());
                memoryListAdapter.getContext().startActivity(memoryIntent);
            }
        });
        post_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memory1.getMemoryIsFavourite()==0){
                    memory1.setMemoryIsFavourite(1);
                    MainActivity.applicationDatabase.dao().updateMemory(memory1);
                    post_favourite.setImageResource(R.drawable.baseline_star_black_48dp);
                } else {
                    memory1.setMemoryIsFavourite(0);
                    MainActivity.applicationDatabase.dao().updateMemory(memory1);
                    post_favourite.setImageResource(R.drawable.outline_star_border_black_48dp);
                }
            }
        });
    }
}
