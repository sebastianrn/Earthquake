package com.reissmann.earthquake.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.reissmann.earthquake.Utils;
import com.reissmann.earthquake.model.Feature;

import static android.content.ContentValues.TAG;

public class EarthquakeDetailMapFragment extends Fragment implements OnMapReadyCallback {

    private TextView placeTextView;
    private TextView depthTextView;
    private TextView timeTextView;
    private TextView magnitudeTextView;

    private MapView mMapView;
    private Utils utils = new Utils();

    private String place;
    private String magnitude;
    private String time;
    private String depth;
    private double latitude;
    private double longitude;

    public EarthquakeDetailMapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.earthquake_details_map_fragment, container, false);
        setBundleValues(view, savedInstanceState);

        return view;
    }

    private void setBundleValues(View view, Bundle savedInstanceState) {
        Feature earthquakeItem = (Feature) getArguments().getParcelable("earthquakeItem");

        assert earthquakeItem != null;
        this.longitude = earthquakeItem.getGeometry().getCoordinates().get(0);
        this.latitude = earthquakeItem.getGeometry().getCoordinates().get(1);

        this.place = earthquakeItem.getProperties().getPlace();
        this.depth = utils.getFormattedDepth(earthquakeItem.getGeometry().getCoordinates().get(2));
        this.time = utils.getFormattedDate(earthquakeItem.getProperties().getTime()).toString();
        this.magnitude = utils.getFormattedMagnitude(earthquakeItem.getProperties().getMag());

        placeTextView = (TextView) view.findViewById(R.id.place);
        depthTextView = (TextView) view.findViewById(R.id.depth);
        timeTextView = (TextView) view.findViewById(R.id.time);
        magnitudeTextView = (TextView) view.findViewById(R.id.magnitude);

        placeTextView.setText(place);
        depthTextView.setText(depth);
        timeTextView.setText(time);
        magnitudeTextView.setText(magnitude);

        mMapView = (MapView) view.findViewById(R.id.map_view_fragment);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        Log.println(Log.ERROR, "", "");

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
