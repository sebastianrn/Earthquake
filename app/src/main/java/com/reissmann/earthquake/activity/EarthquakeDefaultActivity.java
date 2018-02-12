package com.reissmann.earthquake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.reissmann.earthquake.ClickListenerInterface;
import com.reissmann.earthquake.EarthquakeAdapter;
import com.reissmann.earthquake.R;
import com.reissmann.earthquake.RecyclerViewTouchListener;
import com.reissmann.earthquake.rawDataModel.EarthquakeDataObject;
import com.reissmann.earthquake.rawDataModel.Feature;
import com.reissmann.earthquake.service.GetEarthquakeDataASyncService;
import com.reissmann.earthquake.service.Json2JavaMapperService;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EarthquakeDefaultActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "Error_Earthquake";
    private EarthquakeAdapter mAdapter;
    private SeekBar seekbarMagnitude;
    private RelativeLayout defaultActivityLayout;
    private TextView textViewMagInfo;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_default_activity);

        defaultActivityLayout = (RelativeLayout) findViewById(R.id.defaultActivityLayout);
        seekbarMagnitude = (SeekBar) findViewById(R.id.seekBarMagnitude);
        textViewMagInfo = (TextView) findViewById(R.id.textViewMagnitudeInfo);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(this);

        seekbarMagnitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                textViewMagInfo.setText(getString(R.string.magnitudefilterMessage, seekbarMagnitude.getProgress()));
                mAdapter.getFilter().filter(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewMagInfo.setText(getString(R.string.magnitudefilterMessage, seekbarMagnitude.getProgress()));
                mAdapter.getFilter().filter(String.valueOf(seekBar.getProgress()));
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();
        try{
            final List<Feature> earthquakeList = getDataAsync(async);

            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvEarthquake);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new EarthquakeAdapter(this, earthquakeList);
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this,
                    mRecyclerView, new ClickListenerInterface() {
                @Override
                public void onClick(View view, final int position) {
                    Intent item_intent = new Intent(EarthquakeDefaultActivity.this, EarthquakeDetailActivity.class);
                    item_intent.putExtra("EarthquakeItem", earthquakeList.get(position));
                    startActivity(item_intent);
                }

                @Override
                public void onLongClick(View view, int position) {
                    Toast.makeText(EarthquakeDefaultActivity.this, "Long press on position: " + position,
                            Toast.LENGTH_LONG).show();
                }
            }));
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    private List<Feature> getDataAsync(GetEarthquakeDataASyncService async) throws InterruptedException, java.util.concurrent.ExecutionException {
        JSONObject result = async.execute(this.getString(R.string.api_uri)).get();

        Json2JavaMapperService earthquakeMapper = new Json2JavaMapperService(result);
        EarthquakeDataObject earthquakeDataObject = earthquakeMapper.getEarthquakeDataObject();

        return earthquakeDataObject.getFeatures();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        MenuItem menuRefresh = menu.add(0, R.id.menu_refresh, 0, R.string.menu_refresh);
        menuRefresh.setIcon(R.drawable.ic_refresh_white_24px);
        MenuItemCompat.setShowAsAction(menuRefresh, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                return true;
            case R.id.menu_refresh:
                GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();
                try {
                    Animation animation = new RotateAnimation(0.0f, 360.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setRepeatCount(-1);
                    animation.setDuration(2000);

                    mAdapter.earthquakeList.clear();
                    mAdapter.earthquakeList.addAll(getDataAsync(async));
                    mAdapter.notifyDataSetChanged();

                    Snackbar snackbar = Snackbar
                            .make(defaultActivityLayout, R.string.snackbar_refresh_complete, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (Exception e) {
                    Log.println(Log.ERROR, "", e.toString());
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        final GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mAdapter.earthquakeList.clear();
                    mAdapter.earthquakeList.addAll(getDataAsync(async));
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } catch (InterruptedException | ExecutionException e) {
                    Log.e(TAG, "onRefresh: Error during swipe to refresh", e);
                }
            }
        }, 2000);

        Snackbar snackbar = Snackbar
                .make(defaultActivityLayout, R.string.snackbar_refresh_complete, Snackbar.LENGTH_LONG);
        snackbar.show();

    }
}
