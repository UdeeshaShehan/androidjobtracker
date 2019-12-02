package com.placetracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.placetracker.R;
import com.placetracker.domain.Location;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class LocationAdapter  extends ArrayAdapter<Location> {
    public LocationAdapter(@NonNull Context context, ArrayList<Location> locations) {
        super(context,0, locations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Location location = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_location, parent, false);
        }
        // Lookup view for data population
        TextView longitude = (TextView) convertView.findViewById(R.id.listlongitude);
        longitude.setTextColor(Color.BLACK);
        TextView latitude = (TextView) convertView.findViewById(R.id.listlatitude);
        latitude.setTextColor(Color.BLACK);
        // Populate the data into the template view using the data object
        longitude.setText("Longitude : " + location.getLongitude());
        latitude.setText("Latitude : " + location.getLatitude());
        // Return the completed view to render on screen
        return convertView;
    }
}
