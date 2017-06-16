package com.reissmann.earthquake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
    private EarthquakeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JSONObject result;
    private Toolbar mToolbar;
    private RelativeLayout defaultActivityLayout;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_default_activity);

        defaultActivityLayout = (RelativeLayout) findViewById(R.id.defaultActivityLayout);

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHideOnContentScrollEnabled(true);

        GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();
        try{
            final List<Feature> earthquakeList = getDataAsync(async);

            mRecyclerView = (RecyclerView) findViewById(R.id.rvEarthquake);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new EarthquakeAdapter(this, earthquakeList);
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this,
                    mRecyclerView, new ClickListenerInterface() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well
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
            Log.println(Log.ERROR, "", e.toString());
        }
    }

    private List<Feature> getDataAsync(GetEarthquakeDataASyncService async) throws InterruptedException, java.util.concurrent.ExecutionException {
        result = async.execute(this.getString(R.string.api_uri)).get();

        Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(2000);

        //((ImageView)findViewById(R.id.menu_refresh)).setAnimation(animation);

        Json2JavaMapperService earthquakeMapper = new Json2JavaMapperService(result);
        EarthquakeDataObject earthquakeDataObject = earthquakeMapper.getEarthquakeDataObject();
        List<Feature> earthquakeList = earthquakeDataObject.getFeatures();

        //((ImageView)findViewById(R.id.menu_refresh)).clearAnimation();
        Snackbar snackbar = Snackbar
                .make(defaultActivityLayout, R.string.snackbar_refresh_complete, Snackbar.LENGTH_LONG);
        snackbar.show();

        return earthquakeList;
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
                GetEarthquakeDataASyncService async = new GetEarthquakeDataASyncService();
                try {
                    getDataAsync(async);
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
}
