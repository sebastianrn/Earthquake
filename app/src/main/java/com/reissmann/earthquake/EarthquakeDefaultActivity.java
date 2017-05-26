package com.reissmann.earthquake;

import com.reissmann.earthquake.model.EarthquakeDataObject;
import com.reissmann.earthquake.model.Feature;
import com.reissmann.earthquake.service.*;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

public class EarthquakeDefaultActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONObject result;
    private Toolbar mToolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    //new GetEarthquakeDataASyncService(mTextMessage).execute(uri);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    //new GetEarthquakeDataASyncService(mTextMessage).execute(uri);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_default_activity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();

        try{
            result =  async.execute(this.getString(R.string.api_uri)).get();

            Json2JavaMapperService earthquakeMapper = new Json2JavaMapperService(result);
            EarthquakeDataObject earthquakeDataObject = earthquakeMapper.getEarthquakeDataObject();
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

            mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                    mRecyclerView, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well
                    Toast.makeText(EarthquakeDefaultActivity.this, "Single Click on position: " + position,
                            Toast.LENGTH_SHORT).show();
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
}
