package com.example.earthquake;

import com.example.earthquake.model.EarthquakeDataObject;
import com.example.earthquake.model.Feature;
import com.example.earthquake.service.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class EarthquakeDefaultActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONObject result;

    String uri = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    new GetEarthquakeDataASyncService(mTextMessage).execute(uri);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    new GetEarthquakeDataASyncService(mTextMessage).execute(uri);
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
        setContentView(R.layout.activity_earthquake_default);

        GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();
        try{
            result =  async.execute(uri).get();

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

            /*mTextMessage = (TextView) findViewById(R.id.message);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/

        } catch (Exception e){
            Log.println(Log.ERROR,"",e.toString());
        }

    }

}
