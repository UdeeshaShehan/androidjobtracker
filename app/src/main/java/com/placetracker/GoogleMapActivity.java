package com.placetracker;

import android.os.Bundle;

import com.placetracker.utility.CurrentJob;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.fragment.app.FragmentActivity;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng first = null;
        LatLng second = null;
        if (CurrentJob.getInstance().getPlaceSelfie().getFirstLocation().getLatitude() != 0.0 ) {
            first = new LatLng(CurrentJob.getInstance().getPlaceSelfie().getFirstLocation().
                    getLatitude(), CurrentJob.getInstance().getPlaceSelfie().getFirstLocation().getLongitude());
        } else {
            first = new LatLng(0, 0);
        }
        if (CurrentJob.getInstance().getPlaceSelfie().getLastLocation().getLatitude() != 0.0 ) {
            second = new LatLng(CurrentJob.getInstance().getPlaceSelfie().getLastLocation().getLatitude(),
                    CurrentJob.getInstance().getPlaceSelfie().getLastLocation().getLongitude());
        } else {
            second = new LatLng(0, 0);
        }

        mMap.addMarker(new MarkerOptions().position(first).title("First Selfie place"));
        mMap.addMarker(new MarkerOptions().position(second).title("Last Selfie place"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(first));

    }
}
