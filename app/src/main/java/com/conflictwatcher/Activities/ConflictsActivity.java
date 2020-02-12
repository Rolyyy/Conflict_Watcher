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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.conflictwatcher.Conflicts.ConflictAfghan;
import com.conflictwatcher.Conflicts.ConflictIraq;
import com.conflictwatcher.Conflicts.ConflictKurdTurk;
import com.conflictwatcher.Conflicts.ConflictLibya;
import com.conflictwatcher.Conflicts.ConflictMexico;
import com.conflictwatcher.Conflicts.ConflictSomali;
import com.conflictwatcher.Conflicts.ConflictSyria;
import com.conflictwatcher.Conflicts.ConflictYemen;
import com.conflictwatcher.R;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class ConflictsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflicts);
        setupNav(savedInstanceState);


        Button syriaButton = findViewById(R.id.act_conflict_syria);
        syriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictSyria.class));
            }
        });

        Button yemenButton = findViewById(R.id.act_conflict_yemen);
        yemenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictYemen.class));
            }
        });

        Button iraqButton = findViewById(R.id.act_conflict_iraq);
        iraqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictIraq.class));
            }
        });

        Button afghanButton = findViewById(R.id.act_conflict_afghan);
        afghanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictAfghan.class));
            }
        });

        Button libyaButton = findViewById(R.id.act_conflict_libya);
        libyaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictLibya.class));
            }
        });

        Button somaliButton = findViewById(R.id.act_conflict_somali);
        somaliButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictSomali.class));
            }
        });

        Button kurdturkButton = findViewById(R.id.act_conflict_kurd_turk);
        kurdturkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictKurdTurk.class));
            }
        });

        Button mexicoButton = findViewById(R.id.act_conflict_mexico);
        mexicoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConflictsActivity.this, ConflictMexico.class));
            }
        });


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
            navigationView.setCheckedItem(R.id.nav_info);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_map:
                startActivity(new Intent(ConflictsActivity.this, MapActivity.class));
                finish();

                break;


            case R.id.nav_events:
                startActivity(new Intent(ConflictsActivity.this, EventsActivity.class));
                finish();

                break;
            case R.id.nav_info:
               //current activity

                break;

            case R.id.nav_profile:
                startActivity(new Intent(ConflictsActivity.this, ProfileActivity.class));
                finish();


                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
