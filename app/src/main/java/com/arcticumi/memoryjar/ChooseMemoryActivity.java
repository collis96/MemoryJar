package com.arcticumi.memoryjar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.List;

// This class is responsible for handling a user adding a new memory to the database
public class ChooseMemoryActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter newAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button cancel;
    private Bundle bundle;
    private String memoryNumericDate;
    private List<Memory> memories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_memory);

        // Assign views to variables
        cancel = findViewById(R.id.btnChooseCancel);
        recyclerView = findViewById(R.id.recyclerView);

        // Pass memoryDateNumeric from previous Android activity
        bundle = getIntent().getExtras();
        if (bundle != null){
            memoryNumericDate = bundle.getString("date_value");
        }

        // Retrieve memories by memory date
        memories = MainActivity.applicationDatabase.dao().getByDate(memoryNumericDate);

        // Populate the recyclerView with memory entries
        populateRecyclerView();
        // Set listeners
        setListeners();

    }

    public void refreshMemories(){
        newAdapter = new MemoryListAdapter(this, memories);
        recyclerView.setAdapter(newAdapter);
    }

    private void populateRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new LayoutMarginDecoration(1, 20));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MemoryListAdapter(ChooseMemoryActivity.this, memories);
        recyclerView.setAdapter(mAdapter);
    }

    private void setListeners(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(ChooseMemoryActivity.this, MainActivity.class);
                startActivity(cancel);
            }
        });
    }
}
