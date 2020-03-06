package com.arcticumi.memoryjar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalendarActivity extends AppCompatActivity {

    private BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        botNav = findViewById(R.id.bottom_nav);
        Menu menu = botNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        botNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(CalendarActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_random:
                        Intent intent2 = new Intent(CalendarActivity.this, RandomActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_calendar:
                        break;
                }
            }
        });
    }

}
