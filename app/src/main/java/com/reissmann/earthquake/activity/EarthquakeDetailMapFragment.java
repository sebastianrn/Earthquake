package com.reissmann.earthquake.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reissmann.earthquake.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthquakeDetailMapFragment extends Fragment implements OnMapReadyCallback {

    private TextView placeTextView;
    MapView mMapView;

    private String place;
    private double latitude;
    private double longitude;

    public EarthquakeDetailMapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.earthquake_details_map_fragment, container, false);

        placeTextView = ((TextView) view.findViewById(R.id.place));
        mMapView = (MapView) view.findViewById(R.id.map_view_fragment);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        setBundleValues(savedInstanceState);
        placeTextView.setText(place);

        return view;
    }

    private void setBundleValues(Bundle bundle){
        this.place = getArguments().getString("place");
        this.latitude = getArguments().getDouble("latitude");
        this.longitude = getArguments().getDouble("longitude");
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        Log.println(Log.ERROR,"", "");

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

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException e) {
                Log.e(TAG, "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }
}
