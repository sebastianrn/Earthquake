package com.reissmann.earthquake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.reissmann.earthquake.R;
import com.reissmann.earthquake.model.Feature;


public class EarthquakeDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static FragmentManager fragmentManager;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        // Get the Intent default activity
        Intent intent = getIntent();
        Feature earthquakeItem = intent.getParcelableExtra("EarthquakeItem");

        //Create the bundle which is passed to the detail fragment
        Bundle args = new Bundle();
        args.putParcelable("earthquakeItem", earthquakeItem);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.navigation, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.
        MenuItem menuRefresh = menu.add(0, R.id.menu_refresh, 0, R.string.menu_refresh);
        //locationItem.setIcon(R.drawable.ic_action_location);

        // Need to use MenuItemCompat methods to call any action item related methods
        //MenuItemCompat.setShowAsAction(menuRefresh, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }


}
