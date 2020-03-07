package com.arcticumi.memoryjar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button newBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private BottomNavigationView botNav;
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = Room.databaseBuilder(getApplicationContext(),Database.class,"postdb").allowMainThreadQueries().build();
        List<Post> posts = database.dao().getPosts();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(posts);
        recyclerView.setAdapter(mAdapter);

        newBtn = findViewById(R.id.btnNew);
        newBtn.setOnClickListener(new View.OnClickListener() {
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<Post> posts;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            View mView;
            public MyViewHolder(View v){
                super(v);
                mView = v;
            }

            public void setTitle(String title){
                TextView post_title = mView.findViewById(R.id.tvContainerTitle);
                post_title.setText(title);
            }

            public void setDescription(String desc){
                TextView post_desc = mView.findViewById(R.id.tvContainerDescription);
                post_desc.setText(desc);
            }
        }

        public MyAdapter(List<Post> mPosts) {
            posts = mPosts;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.container, parent, false);
            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post post = posts.get(position);
            holder.setTitle(post.getTitle());
            holder.setDescription(post.getDescription());
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }
}
