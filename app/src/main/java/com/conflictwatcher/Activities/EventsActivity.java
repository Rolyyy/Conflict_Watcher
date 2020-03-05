package com.conflictwatcher.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.conflictwatcher.Other.CSVReader;
import com.conflictwatcher.R;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;


    ListView myList;
    ArrayList eventsList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setupNav(savedInstanceState);

        //CSV Start
        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader(EventsActivity.this, "2020_02_syria.csv");
        try {
            rows = csvReader.readCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title, event, snippet;
        for (int i = 0; i < rows.size(); i++) {
            title = "Date :  " + rows.get(i)[4];
            event = "Event :  " + rows.get(i)[8];
            snippet = rows.get(i)[27];
            eventsList.add(title + "\n" + event + "\n" + "\n" + snippet);
        }



        myList = findViewById(R.id.eventsListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.activity_listview, R.id.listText1, eventsList);
        myList.setAdapter(arrayAdapter);


    }

    private void setupNav(Bundle savedInstanceState) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.layout_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_events);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_map:
                startActivity(new Intent(EventsActivity.this, MapActivity.class));
                finish();

                break;


            case R.id.nav_events:
               // startActivity(new Intent(EventsActivity.this, EventsActivity.class));
              //  finish();

                break;
            case R.id.nav_info:
                startActivity(new Intent(EventsActivity.this, ConflictsActivity.class));
                finish();

                break;

            case R.id.nav_profile:
                startActivity(new Intent(EventsActivity.this, ProfileActivity.class));
                finish();


                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
