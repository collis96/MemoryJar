package com.arcticumi.memoryjar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemoryListAdapter extends RecyclerView.Adapter<MemoryHolder> {

    private Context context;
    private List<Memory> memories;

    public MemoryListAdapter(Context context, List<Memory> mMemories) {
        this.context = context;
        memories = mMemories;
    }

    @NonNull
    @Override
    public MemoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.container, parent, false);
        MemoryHolder vh = new MemoryHolder(this, view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryHolder holder, int position) {
        // Populate RecyclerView with memory information
        Memory memory = memories.get(position);
        holder.setTitle(memory.getMemoryTitle());
        holder.setDescription(memory.getMemoryDescription());
        holder.setCategory(memory.getMemoryCategory());
        holder.setDate(memory.getMemoryDate());
        holder.setMedia(memory.getMemoryMediaUri());
        holder.setFavourite(memory.getMemoryIsFavourite());
        holder.setListeners(memory, position);

    }

    @Override
    public int getItemCount() {
        return memories.size();
    }

    public Context getContext() {
        return context;
    }

}
