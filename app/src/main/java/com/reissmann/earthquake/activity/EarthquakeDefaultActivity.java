package com.reissmann.earthquake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reissmann.earthquake.ClickListenerInterface;
import com.reissmann.earthquake.EarthquakeAdapter;
import com.reissmann.earthquake.R;
import com.reissmann.earthquake.RecyclerViewTouchListener;
import com.reissmann.earthquake.model.EarthquakeDataObject;
import com.reissmann.earthquake.model.Feature;
import com.reissmann.earthquake.service.GetEarthquakeDataASyncService;
import com.reissmann.earthquake.service.Json2JavaMapperService;

import org.json.JSONObject;

import java.util.List;

public class EarthquakeDefaultActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONObject result;
    private Toolbar mToolbar;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_default_activity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHideOnContentScrollEnabled(true);

        GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();

        try{
            result =  async.execute(this.getString(R.string.api_uri)).get();

            Json2JavaMapperService earthquakeMapper = new Json2JavaMapperService(result);
            final EarthquakeDataObject earthquakeDataObject = earthquakeMapper.getEarthquakeDataObject();
            List<Feature> earthquakeList = earthquakeDataObject.getFeatures();

            mRecyclerView = (RecyclerView) findViewById(R.id.rvEarthquake);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new EarthquakeAdapter(this, earthquakeList);
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this,
                    mRecyclerView, new ClickListenerInterface() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well
                    Intent item_intent = new Intent(EarthquakeDefaultActivity.this, EarthquakeDetailActivity.class);
                    item_intent.putExtra("EarthquakeItem", earthquakeDataObject.getFeatures().get(position));
                    startActivity(item_intent);
                }

                @Override
                public void onLongClick(View view, int position) {
                    Toast.makeText(EarthquakeDefaultActivity.this, "Long press on position: " + position,
                            Toast.LENGTH_LONG).show();
                }
            }));

        } catch (Exception e){
            Log.println(Log.ERROR,"",e.toString());
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.navigation, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.
        MenuItem menuRefresh = menu.add(0, R.id.menu_refresh, 0, R.string.menu_refresh);
        menuRefresh.setIcon(R.drawable.ic_refresh_white_24px);

        // Need to use MenuItemCompat methods to call any action item related methods
        MenuItemCompat.setShowAsAction(menuRefresh, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.menu_refresh:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
