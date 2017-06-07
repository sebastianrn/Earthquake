package com.reissmann.earthquake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reissmann.earthquake.R;
import com.reissmann.earthquake.model.Feature;

import java.util.List;

/**
 * Created by sebas on 27.05.2017.
 */

public class EarthquakeDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private String testmessage;
    private double latitude;
    private double longitude;
    private static FragmentManager fragmentManager;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_details);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Feature earthquakeItem = intent.getParcelableExtra("EarthquakeItem");
        latitude = earthquakeItem.getGeometry().getCoordinates().get(1);
        longitude = earthquakeItem.getGeometry().getCoordinates().get(0);
        testmessage = earthquakeItem.getProperties().getPlace();

        fragmentManager = getSupportFragmentManager();

        EarthquakeDetailMapFragment mapDetails = new EarthquakeDetailMapFragment();
        Bundle args = new Bundle();
        args.putString("place", testmessage);
        mapDetails.setArguments(args);
        fragmentManager.beginTransaction().add(mapDetails, "tag").commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
