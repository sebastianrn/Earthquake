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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reissmann.earthquake.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthquakeDetailMapFragment extends Fragment {

    public EarthquakeDetailMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.earthquake_details_map_fragment, container, false);
        TextView place = ((TextView) view.findViewById(R.id.place));

        String place_arg = getArguments().getString("place");
        place.setText(place_arg);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        Log.println(Log.ERROR,"", "");

    }
}
