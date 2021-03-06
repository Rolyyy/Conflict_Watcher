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
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.conflictwatcher.MyItem;
import com.conflictwatcher.Other.CSVReader;
import com.conflictwatcher.Other.CustomInfoWindowAdapter;
import com.conflictwatcher.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnPolygonClickListener {

    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;

    private GeoJsonLayer layer_syria;
    private GeoJsonLayer layer_yemen;
    private GeoJsonLayer layer_afghan;
    private GeoJsonLayer layer_iraq;
    private GeoJsonLayer layer_somali;
    private GeoJsonLayer layer_libya;
    private GeoJsonLayer layer_mexico;

    private ClusterManager<MyItem> mClusterManager;



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
                startActivity(new Intent(MapActivity.this, EventsActivity.class));
                finish();

                break;
            case R.id.nav_info:
                startActivity(new Intent(MapActivity.this, ConflictsActivity.class));
                finish();

                break;

            case R.id.nav_profile:
                startActivity(new Intent(MapActivity.this, ProfileActivity.class));
                finish();


                break;

            case R.id.nav_acknowledgments:
                startActivity(new Intent(MapActivity.this, AcknowledgeActivity.class));

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Called when the Map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Disables navigation button:
        googleMap.getUiSettings().setMapToolbarEnabled(false);


        LatLng stdView = new LatLng(24.367114, 44.472656);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stdView, 4f));



        try {
            layer_syria = new GeoJsonLayer(googleMap, R.raw.syria_geo, getApplicationContext());
            layer_yemen = new GeoJsonLayer(googleMap, R.raw.yemen_geo, getApplicationContext());
            layer_afghan = new GeoJsonLayer(googleMap, R.raw.afghan_geo, getApplicationContext());
            layer_iraq = new GeoJsonLayer(googleMap, R.raw.iraq_geo, getApplicationContext());
            layer_somali = new GeoJsonLayer(googleMap, R.raw.somali_geo, getApplicationContext());
            layer_libya = new GeoJsonLayer(googleMap, R.raw.libya_geo, getApplicationContext());
            layer_mexico = new GeoJsonLayer(googleMap, R.raw.mexico_geo, getApplicationContext());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }



        setupPolygon(googleMap, layer_afghan);
        setupPolygon(googleMap, layer_iraq);
        setupPolygon(googleMap, layer_somali);
        setupPolygon(googleMap, layer_libya);
        setupPolygon(googleMap, layer_mexico);
        setupPolygon(googleMap, layer_syria);
        setupPolygon(googleMap, layer_yemen);





        final SharedPreferences sharedPref2 = MapActivity.this.getPreferences(Context.MODE_PRIVATE);
        boolean isDataPointsChecked = sharedPref2.getBoolean("checkbox2", false);

        /*
        if (isDataPointsChecked) {
            setupDataPoints(googleMap, 1, "201910syria.csv");
        }
        */

        mClusterManager = new ClusterManager<>(this, googleMap);
        setupDataPoints(googleMap,1,"2020_02_yemen.csv");
        setupDataPoints(googleMap,1,"2020_02_syria.csv");
        setupDataPoints(googleMap,1,"2020_02_afghan.csv");
        setupDataPoints(googleMap,1,"2020_02_kurdturk.csv");
        setupDataPoints(googleMap,1,"2020_02_libya.csv");
        setupDataPoints(googleMap,1,"2020_02_mexico.csv");
        setupDataPoints(googleMap,1,"2020_02_somalia.csv");



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

        String text1 = polygon.getId();

    }

    public void setMapStyle(GoogleMap googleMap) {

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

    public void layersButton(final GoogleMap googleMap) {
        Button layers_button = findViewById(R.id.layers_btn);
        layers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layersDialog(googleMap);
            }
        });
    }

    public void layersDialog(final GoogleMap googleMap) {

        //Creating an instance of AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MapActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_layers, null);  //Custom layout used for the dialog
        mBuilder.setView(mView);



        final SharedPreferences sharedPref2 = MapActivity.this.getPreferences(Context.MODE_PRIVATE);
        boolean isDataPointsChecked = sharedPref2.getBoolean("checkbox2", false);

        final CheckBox checkbox_points = mView.findViewById(R.id.checkbox_points_syria);

        checkbox_points.setChecked(isDataPointsChecked);
        checkbox_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updates checkbox state
                SharedPreferences.Editor editor = sharedPref2.edit();
                editor.putBoolean("checkbox2", ((CheckBox) view).isChecked());
                editor.commit();
                if (checkbox_points.isChecked()) {
                    Log.d("checkbox_check", "Checkbox 2 set to TRUE!");
                    //setupDataPoints(googleMap, 1, "201910syria.csv");


                } else {
                    Log.d("checkbox_check", "Checkbox 2 set to FALSE!");
                    //setupDataPoints(googleMap, 0, "201910syria.csv");

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


    public void setupPolygon(GoogleMap googleMap, GeoJsonLayer layer_choice) {


        int color_fill = 0x4d165ac7;

        // GeoJsonLayer layer = new GeoJsonLayer(googleMap, R.raw.syria_geo, getApplicationContext());

        GeoJsonPolygonStyle style = layer_choice.getDefaultPolygonStyle();
        style.setFillColor(color_fill);
        style.setStrokeColor(color_fill);
        style.setStrokeWidth(1F);

        layer_choice.addLayerToMap();








    }


    public void setupDataPoints(GoogleMap googleMap, int value, String file) {

        /*
        String choice;
        if (name == "syria"){
            choice = "mClusterManagerSyria";
        }
        if (name == "yemen"){
            choice = "mClusterManagerYemen";
        }
        */
        Log.d("setupMethodStart", String.valueOf(value));
        if (value == 1) {

            //CSV Start
            List<String[]> rows = new ArrayList<>();
            CSVReader csvReader = new CSVReader(MapActivity.this, file);
            try {
                rows = csvReader.readCSV();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_marker_blue);




            googleMap.setOnCameraIdleListener(mClusterManager);
            googleMap.setOnMarkerClickListener(mClusterManager);

            googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this)); //Set the info window to the custom one created

            double myLat, myLong;
            String title, snippet;
            /// !!!
            /// IF my database format changes, this loop has to be reformatted!
            for (int i = 0; i < rows.size(); i++) {
                myLat = Double.valueOf(rows.get(i)[22]);
                myLong = Double.valueOf(rows.get(i)[23]);
                title = "Date :  " + rows.get(i)[4] + "\n" + "Event :  " + rows.get(i)[8];
                snippet = rows.get(i)[27];
                // participants ="Actors :  " + rows.get(i)[9] + "\n" + "and :  " + rows.get(i)[12];


                //googleMap.addMarker(new MarkerOptions().position(new LatLng(myLat, myLong)).icon(icon).title("title")).setSnippet(data_info);

                MyItem infoWindowItem = new MyItem(myLat, myLong, title, snippet);
                mClusterManager.addItem(infoWindowItem);
            }

            //googleMap.setOnInfoWindowClickListener(mClusterManager);
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(MapActivity.this, "InfoWindowTapped", Toast.LENGTH_LONG).show();
                    //This is called if an infowindow for a specific event is tapped... Might be utilized for a functionality?
                }
            });


            double currentLat = googleMap.getCameraPosition().target.latitude;
            double currentLong = googleMap.getCameraPosition().target.longitude;
            float currentZoom = googleMap.getCameraPosition().zoom;
            LatLng stdView = new LatLng(currentLat, currentLong);

            //updates view with current camera coordinates and zoom level, as otherwise clusters wouldn't appear until user manually changes zoom level
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stdView, currentZoom));

            //when a cluster is clicked, nothing is done:
            mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
                @Override
                public boolean onClusterClick(Cluster<MyItem> cluster) {
                    return true;
                }

            });


        } else {
            googleMap.clear(); //Might need to change this, as this also removes all polygons on map...
            mClusterManager.clearItems();
            //clearItems removes all polygons... remove this, as selectable polygon dialog window is not needed





        }

    }




}

