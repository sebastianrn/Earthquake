package com.reissmann.earthquake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.widget.TextView;

import com.reissmann.earthquake.R;
import com.reissmann.earthquake.model.EarthquakeDataObject;
import com.reissmann.earthquake.model.Feature;

import java.util.List;

/**
 * Created by sebas on 27.05.2017.
 */

public class EarthquakeDetailActivity extends AppCompatActivity {

    private String testmessage;

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_details);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Feature earthquakeItem = intent.getParcelableExtra("EarthquakeItem");
        testmessage = earthquakeItem.getProperties().getPlace();

        String message = intent.getStringExtra(EarthquakeDefaultActivity.ACCOUNT_SERVICE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(testmessage);
    }
}
