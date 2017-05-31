package com.reissmann.earthquake.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reissmann.earthquake.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthquakeDetailMapFragment extends Fragment implements OnMapReadyCallback {
    private double latitude;
    private double longitude;

    public EarthquakeDetailMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.earthquake_details_map_fragment, container, false);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng targetLocation = new LatLng(latitude, longitude);

        map.addMarker(new MarkerOptions()
                .position(targetLocation)
                .title("Marker"));

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(targetLocation)
                .zoom(6)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
