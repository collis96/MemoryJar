package com.arcticumi.memoryjar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static ApplicationDatabase applicationDatabase;

    private Button newMemory;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private BottomNavigationView botNav;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        applicationDatabase = Room.databaseBuilder(getApplicationContext(), ApplicationDatabase.class,"postdb").allowMainThreadQueries().build();
        List<Memory> memories = applicationDatabase.dao().getPosts();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new LayoutMarginDecoration(1, 20));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MemoryListAdapter(memories);
        recyclerView.setAdapter(mAdapter);

        newMemory = findViewById(R.id.btnNew);
        newMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newMem = new Intent(MainActivity.this, NewMemActivity.class);
                startActivity(newMem);
            }
        });


        botNav = findViewById(R.id.bottom_nav);
        Menu menu = botNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        botNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        break;
                    case R.id.nav_random:
                        Intent intent1 = new Intent(MainActivity.this, RandomActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_calendar:
                        Intent intent2 = new Intent(MainActivity.this, CalendarActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        });

    }

    public class MemoryListAdapter extends RecyclerView.Adapter<MemoryListAdapter.MemoryHolder> {

        private List<Memory> memories;

        public MemoryListAdapter(List<Memory> mMemories) {
            memories = mMemories;
        }

        @NonNull
        @Override
        public MemoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.container, parent, false);
            MemoryHolder vh = new MemoryHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MemoryHolder holder, int position) {
            Memory memory = memories.get(position);
            holder.setTitle(memory.getMemoryTitle());
            holder.setDescription(memory.getMemoryDescription());
            holder.setListeners(memory, position);
        }

        @Override
        public int getItemCount() {
            return memories.size();
        }

        public class MemoryHolder extends RecyclerView.ViewHolder {

            View mView;
            private Button editMemory;
            private Button deleteMemory;

            public MemoryHolder(View v){
                super(v);
                mView = v;
                editMemory = mView.findViewById(R.id.btnEdit);
                deleteMemory = mView.findViewById(R.id.btnDelete);
            }

            public void setTitle(String title){
                TextView post_title = mView.findViewById(R.id.tvContainerTitle);
                post_title.setText(title);
            }

            public void setDescription(String desc){
                TextView post_desc = mView.findViewById(R.id.tvContainerDescription);
                post_desc.setText(desc);
            }

            public void setListeners(final Memory memory1, final int position){

                editMemory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editMem = new Intent( MainActivity.this, EditMemActivity.class);
                        editMem.putExtra("int_value", memory1.getMemoryId());
                        startActivity(editMem);
                    }
                });

                deleteMemory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.applicationDatabase.dao().delete(memory1);
                        notifyItemRemoved(position);
                    }
                });
            }
        }
    }
}
