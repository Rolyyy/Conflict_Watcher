package com.conflictwatcher.Other;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.conflictwatcher.R;



//This Class is used for the custom InfoWindow when a data point is tapped
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private final View mWindow;

    //Constructor
    public CustomInfoWindowAdapter(Context mContext) {

        //Set the custom xml infowindow to be used for layout
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_infowindow, null);
    }

    //This method sets the text to the view
    private void renderWindowText(Marker marker, View view){

        String snippet = marker.getSnippet();
        TextView tvSnippet = view.findViewById(R.id.info_window_text); //the text which is sent from MapActivity


        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }
    }


    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}

