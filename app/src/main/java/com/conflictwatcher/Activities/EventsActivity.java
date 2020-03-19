package com.conflictwatcher.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.conflictwatcher.Other.CSVReader;
import com.conflictwatcher.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;


    ListView myList;
    public ArrayList eventsList = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setupNav(savedInstanceState);

        filterButton();
        TextView location_text = findViewById(R.id.events_location_text);
        location_text.setText("Syrian Civil War");
        setupCSV("2020_02_syria.csv");

    }


    public void filterButton() {
        Button filter_button = findViewById(R.id.events_button_filter);
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog();




            }
        });
    }

    public void filterDialog(){

        //Creating an instance of AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(EventsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_filter, null);  //Custom layout used for the dialog
        mBuilder.setView(mView);

         Button syria_button = mView.findViewById(R.id.filter_syria);
        syria_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Syrian Civil War");

                setupCSV("2020_02_syria.csv");

            }
        });

         Button afghan_button = mView.findViewById(R.id.filter_afghan);
        afghan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Afghanistan Conflict");

                setupCSV("2020_02_afghan.csv");

            }
        });


        Button yemen_button = mView.findViewById(R.id.filter_yemen);
        yemen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Yemeni Crisis");

                setupCSV("2020_02_yemen.csv");

            }
        });

        Button iraq_button = mView.findViewById(R.id.filter_iraq);
        iraq_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Iraq Conflict");

                setupCSV("2020_02_iraq.csv");

            }
        });

        Button kurdturk_button = mView.findViewById(R.id.filter_kurdturk);
        kurdturk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Kurdish-Turkish Conflict");

                setupCSV("2020_02_kurdturk.csv");

            }
        });

        Button libya_button = mView.findViewById(R.id.filter_libya);
        libya_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Libyan Civil War");

                setupCSV("2020_02_libya.csv");

            }
        });

        Button mexico_button = mView.findViewById(R.id.filter_mexico);
        mexico_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Mexican Drug War");

                setupCSV("2020_02_mexico.csv");

            }
        });


        Button somalia_button = mView.findViewById(R.id.filter_somali);
        somalia_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventsList.clear();
                myList.setAdapter(null);

                TextView location_text = findViewById(R.id.events_location_text);
                location_text.setText("Somali Civil War");

                setupCSV("2020_02_somalia.csv");

            }
        });







        //Create and display the dialog
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        //Button with onClickListener which closes the dialog:
        Button close_dialog = mView.findViewById(R.id.filter_dialog_close);

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });



    }


    public void setupCSV(String filename){



        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader(EventsActivity.this, filename);
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
