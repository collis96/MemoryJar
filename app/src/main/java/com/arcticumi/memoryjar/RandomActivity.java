package com.arcticumi.memoryjar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RandomActivity extends AppCompatActivity {

    private Button newBtn;
    private BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        botNav = findViewById(R.id.bottom_nav);
        Menu menu = botNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        botNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(RandomActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_random:
                        break;
                    case R.id.nav_calendar:
                        Intent intent2 = new Intent(RandomActivity.this, CalendarActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        });

    }

}
