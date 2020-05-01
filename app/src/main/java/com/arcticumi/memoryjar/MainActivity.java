package com.arcticumi.memoryjar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static ApplicationDatabase applicationDatabase;
    private Button newMemory;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter newAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private BottomNavigationView botNav;
    private static Spinner filter;
    private List<Memory> memories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Assign views to variables
        applicationDatabase = Room.databaseBuilder(getApplicationContext(), ApplicationDatabase.class,"postdb").allowMainThreadQueries().build();
        memories = applicationDatabase.dao().getNewest();
        recyclerView = findViewById(R.id.recyclerView);
        newMemory = findViewById(R.id.btnNew);
        filter = findViewById(R.id.spnFilter);
        botNav = findViewById(R.id.bottom_nav);

        // Populate views
        populateRecyclerView();
        populateFilter();
        populateBottomNav();

        // Set listeners
        setListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMemories(memories);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getItemAtPosition(position).toString())
        {
            case "Newest":
                memories = applicationDatabase.dao().getNewest();
                refreshMemories(memories);
                break;
            case "Oldest":
                memories = applicationDatabase.dao().getOldest();
                refreshMemories(memories);
                break;
            case "Favourites":
                memories = applicationDatabase.dao().getFavourites();
                refreshMemories(memories);
                break;
            case "By Category":
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Category");
                alert.setMessage("Enter a category");
                final EditText input = new EditText(this);
                alert.setView(input);
                alert.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        memories = applicationDatabase.dao().getByCategory(input.getText().toString());
                        refreshMemories(memories);
                    }
                });
                alert.show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void refreshMemories(List<Memory> memories){
        newAdapter = new MemoryListAdapter(this, memories);
        recyclerView.setAdapter(newAdapter);
    }

    private void setListeners(){
        newMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newMem = new Intent(MainActivity.this, NewMemActivity.class);
                startActivity(newMem);
            }
        });
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        break;
                    case R.id.nav_random:
                        Intent randomIntent = new Intent(MainActivity.this, MemoryActivity.class);
                        Memory randomMem = applicationDatabase.dao().getRandom();
                        randomIntent.putExtra("int_value", randomMem.getMemoryId());
                        startActivity(randomIntent);
                        break;
                    case R.id.nav_calendar:
                        Intent intent2 = new Intent(MainActivity.this, CalendarActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
    }

    public static void setFilterSelection(int number) {
        filter.setSelection(number);
    }

    private void populateFilter(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);
        filter.setOnItemSelectedListener(this);
    }

    private void populateRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new LayoutMarginDecoration(1, 20));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MemoryListAdapter(this, memories);
        recyclerView.setAdapter(mAdapter);
    }

    private void populateBottomNav(){
        Menu menu = botNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }


}
