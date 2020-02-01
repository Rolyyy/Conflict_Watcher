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

import com.conflictwatcher.Activities.LoginReg.LoginActivity;
import com.conflictwatcher.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;
    private Button btnLogout;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        String userEmail = firebaseUser.getEmail();
        String userName = firebaseUser.getDisplayName();


        TextView profileEmail = findViewById(R.id.profile_email);
        if(userEmail!=null){
            profileEmail.setText(userEmail);
        }
        TextView profileName = findViewById(R.id.profile_name);
        if (userName!=null){
            profileName.setText(userName);
        }

        btnSettings = findViewById(R.id.settings_btn);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
            }
        });


        btnLogout = findViewById(R.id.logout_btn);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

        setupNav(savedInstanceState);
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
            navigationView.setCheckedItem(R.id.nav_profile);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_map:
                startActivity(new Intent(ProfileActivity.this, MapActivity.class));
                finish();
                break;
            case R.id.nav_events:
                startActivity(new Intent(ProfileActivity.this, EventsActivity.class));
                finish();

                break;
            case R.id.nav_info:
                startActivity(new Intent(ProfileActivity.this, ConflictsActivity.class));
                finish();

                break;

            case R.id.nav_profile:

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
