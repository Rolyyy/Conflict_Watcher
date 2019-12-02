package com.conflictwatcher.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.conflictwatcher.Other.CSVReader;
import com.conflictwatcher.Other.CustomInfoWindowAdapter;
import com.conflictwatcher.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnPolygonClickListener {

    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;

    private GeoJsonLayer layer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Setting up the Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        auth = FirebaseAuth.getInstance();


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
            navigationView.setCheckedItem(R.id.nav_map);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_map:


                break;
            case R.id.nav_events:
                Toast.makeText(this, "Events", Toast.LENGTH_LONG).show();

                break;
            case R.id.nav_info:
                Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show();

                break;

            case R.id.nav_profile:
                startActivity(new Intent(MapActivity.this, ProfileActivity.class));
                finish();


                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Called when the Map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {


        LatLng stdView = new LatLng(34.849875, 38.869629);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stdView, 6f));

        try {
            layer = new GeoJsonLayer(googleMap, R.raw.syria_geo, getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(layer==null){

        }

        //Checks state of layers checkbox.. if it is checked, map polygon is added
        final SharedPreferences sharedPref = MapActivity.this.getPreferences(Context.MODE_PRIVATE);
        boolean isMyValueChecked = sharedPref.getBoolean("checkbox", false);

        if(isMyValueChecked){
            setupPolygon(googleMap, 1);
        }









        setMapStyle(googleMap);

        layersButton(googleMap);
        //setupDataPoints(googleMap);
        //setupPolygon(googleMap);





        //Test map polygon functionality:
        int color_purple = 0x4d165ac7;
        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions().clickable(true).add(
                        new LatLng(51.645, -0.138),
                        new LatLng(51.368, 0.1448),
                        new LatLng(51.483, -0.5046)));
        polygon1.setFillColor(color_purple);
        polygon1.setStrokeWidth(0f);
        polygon1.isClickable();
        googleMap.setOnPolygonClickListener(this);

    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        Toast.makeText(MapActivity.this, "Polygon Clicked!", Toast.LENGTH_SHORT).show();

    }

    public void setMapStyle(GoogleMap googleMap){

        try {
            //Use of a .json file in order to change the style of the map
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("TAG", "Style parsing failed.");

            }
        } catch (Resources.NotFoundException e) {
            Log.e("TAG", "Can't find style. Error: ", e);
        }

    }

    public void layersButton(final GoogleMap googleMap){
        Button layers_button = findViewById(R.id.layers_btn);
        layers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layersDialog(googleMap);
            }
        });
    }

    public void layersDialog(final GoogleMap googleMap){

        //Creating an instance of AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MapActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_layers, null);  //Custom layout used for the dialog
        mBuilder.setView(mView);

        //Used to read state of checkbox
        final SharedPreferences sharedPref = MapActivity.this.getPreferences(Context.MODE_PRIVATE);
        boolean isPolygonChecked = sharedPref.getBoolean("checkbox", false);


        final CheckBox checkbox_polygon = mView.findViewById(R.id.dialog_layers_checkbox_polygon);

        checkbox_polygon.setChecked(isPolygonChecked);
        checkbox_polygon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updates checkbox state
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("checkbox", ((CheckBox) view).isChecked());
                editor.commit();
                if(checkbox_polygon.isChecked()){
                    Log.d("checkbox_check", "Checkbox 1 set to TRUE!");
                    setupPolygon(googleMap, 1);


                }else{
                    Log.d("checkbox_check", "Checkbox 1 set to FALSE!");
                    setupPolygon(googleMap, 0);

                }
            }
        });

        if(checkbox_polygon.isChecked()){
            Log.d("checkbox_check", "Checkbox 1 Checked!");
        }else{
            Log.d("checkbox_check", "Checkbox 1 UnChecked!");
        }




        final SharedPreferences sharedPref2 = MapActivity.this.getPreferences(Context.MODE_PRIVATE);
        boolean isDataPointsChecked = sharedPref2.getBoolean("checkbox2", false);

        final CheckBox checkbox_points = mView.findViewById(R.id.dialog_layers_checkbox_points);

        checkbox_points.setChecked(isDataPointsChecked);
        checkbox_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updates checkbox state
                SharedPreferences.Editor editor = sharedPref2.edit();
                editor.putBoolean("checkbox2", ((CheckBox) view).isChecked());
                editor.commit();
                if(checkbox_points.isChecked()){
                    Log.d("checkbox_check", "Checkbox 2 set to TRUE!");
                    setupDataPoints(googleMap, 1);


                }else{
                    Log.d("checkbox_check", "Checkbox 2 set to FALSE!");
                    setupDataPoints(googleMap, 0);

                }
            }
        });




        //Create and display the dialog
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        //Button with onClickListener which closes the dialog:
        Button close_dialog = mView.findViewById(R.id.confirm_layers_dialog);

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }



    public void setupPolygon(GoogleMap googleMap, int value) {


        int color_fill = 0x4d165ac7;

            // GeoJsonLayer layer = new GeoJsonLayer(googleMap, R.raw.syria_geo, getApplicationContext());



            GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
            style.setFillColor(color_fill);
            style.setStrokeColor(color_fill);
            style.setStrokeWidth(1F);

                if(value==1) {
                    layer.addLayerToMap();
                    Log.d("checkbox_check", "LAYER ADDED");

                }
                else if(value==0){
                    layer.removeLayerFromMap();
                    Log.d("checkbox_check", "LAYER REMOVED");

                }




    }


    public void setupDataPoints(GoogleMap googleMap, int value) {

        Log.d("setupMethodStart", String.valueOf(value));
        if(value==1) {

            //CSV Start
            List<String[]> rows = new ArrayList<>();
            CSVReader csvReader = new CSVReader(MapActivity.this, "201910syria.csv");
            try {
                rows = csvReader.readCSV();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_marker);

            googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this)); //Set the info window to the custom one created

            /// !!!
            /// IF my database format changes, this loop has to be reformatted!
            double myLat;
            double myLong;
            String data_info;

            for (int i = 0; i < rows.size(); i++) {
                myLat = Double.valueOf(rows.get(i)[22]);
                myLong = Double.valueOf(rows.get(i)[23]);
                data_info = "Date: " + rows.get(i)[4] + "  Event: " + rows.get(i)[8];

                Log.d("mydatainfo", data_info);
                googleMap.addMarker(new MarkerOptions().position(new LatLng(myLat, myLong)).icon(icon).title("title")).setSnippet(data_info);

            }
        }
        else{
            googleMap.clear();
            final SharedPreferences sharedPref = MapActivity.this.getPreferences(Context.MODE_PRIVATE);
            boolean isMyValueChecked = sharedPref.getBoolean("checkbox", false);

            if(isMyValueChecked){
                setupPolygon(googleMap, 1);
            }

        }

    }
}