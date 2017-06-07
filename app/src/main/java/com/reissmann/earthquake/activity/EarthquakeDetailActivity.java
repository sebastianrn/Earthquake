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

public class EarthquakeDetailActivity extends FragmentActivity {

    private static FragmentManager fragmentManager;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_details);

        // Get the Intent default activity
        Intent intent = getIntent();
        Feature earthquakeItem = intent.getParcelableExtra("EarthquakeItem");

        //Create the bundle which is passed to the detail fragment
        Bundle args = new Bundle();
        args.putString("place", earthquakeItem.getProperties().getPlace());
        args.putDouble("latitude", earthquakeItem.getGeometry().getCoordinates().get(1));
        args.putDouble("longitude", earthquakeItem.getGeometry().getCoordinates().get(0));

        //Create instance of fragment
        EarthquakeDetailMapFragment mapDetails = new EarthquakeDetailMapFragment();
        mapDetails.setArguments(args);

        //replace the framelayout with the fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.details_frame_layout, mapDetails)
                .commit();
    }



}
