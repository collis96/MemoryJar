package com.arcticumi.memoryjar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// The following imports are open source found here:
// https://github.com/Applandeo/Material-Calendar-View
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
// ====================================================

import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private BottomNavigationView botNav;
    private CalendarView calendar;
    private List<Memory> memories;
    private List<EventDay> eventDays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Assign views to variables
        calendar = findViewById(R.id.cvCalendar);
        botNav = findViewById(R.id.bottom_nav);

        // Get all memories
        memories = MainActivity.applicationDatabase.dao().getMemories();

        // Assign memories to dates in the calendar
        populateCalendar();

        // Populate bottom nav menu
        populateBottomNav();

        // Set listeners
        setListeners();

    }

    private void populateCalendar(){
        for(Memory memory : memories){
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(dateFormat.parse(memory.getMemoryDateNumeric()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            EventDay eventDay = new EventDay(calendar, R.drawable.memory_jar_logo);
            eventDays.add(eventDay);
        }
        calendar.setEvents(eventDays);
    }

    private void populateBottomNav(){
        Menu menu = botNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }

    private void setListeners(){
        calendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar temp = eventDay.getCalendar();
                String numericDate = new SimpleDateFormat("yyyyMMdd").format(temp.getTime());
                List<Memory> memListTemp = MainActivity.applicationDatabase.dao().getByDate(numericDate);
                if(memListTemp.size()==1){
                    // If one memory is present on this date, open a dedicated view for this memory
                    Intent intent1 = new Intent(CalendarActivity.this, MemoryActivity.class);
                    intent1.putExtra("int_value", memListTemp.get(0).getMemoryId());
                    startActivity(intent1);
                } else if (memListTemp.size()>1) {
                    // If more than one memory is present on this date, open a dedicated layout to view these memories
                    Intent intent2 = new Intent(CalendarActivity.this, ChooseMemoryActivity.class);
                    intent2.putExtra("date_value", numericDate);
                    startActivity(intent2);
                } else {
                    Toast.makeText(CalendarActivity.this, "No memory on this day.", Toast.LENGTH_LONG).show();
                }
            }
        });
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent homeIntent = new Intent(CalendarActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.nav_random:
                        Intent randomIntent = new Intent(CalendarActivity.this, MemoryActivity.class);
                        Memory randomMem = MainActivity.applicationDatabase.dao().getRandom();
                        randomIntent.putExtra("int_value", randomMem.getMemoryId());
                        startActivity(randomIntent);
                        break;
                    case R.id.nav_calendar:
                        break;
                }
                return true;
            }
        });
    }

}
