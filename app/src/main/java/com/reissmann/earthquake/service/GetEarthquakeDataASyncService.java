package com.reissmann.earthquake.service;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sebas on 15.04.2017.
 */

public class GetEarthquakeDataASyncService extends AsyncTask <String, Void, JSONObject> {

    private TextView textView;

    public GetEarthquakeDataASyncService(TextView textView) {
        this.textView = textView;
    }

    public GetEarthquakeDataASyncService() {}

    @Override
    protected JSONObject doInBackground(String... url){
        return loadEarthquakeData(url[0]);
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(JSONObject result){

    }

    private JSONObject loadEarthquakeData(String uri) {
        JSONObject result = new JSONObject();

        try {
            URL url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            result = new JSONObject(builder.toString());

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
