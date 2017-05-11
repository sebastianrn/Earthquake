package com.example.earthquake;

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
import java.util.logging.Logger;

/**
 * Created by sebas on 15.04.2017.
 */

class ASyncRetrieveEarthquakeDataTask extends AsyncTask <String, Void, JSONObject> {

    private TextView textView;

    public ASyncRetrieveEarthquakeDataTask(TextView textView) {
        this.textView = textView;
    }

    public ASyncRetrieveEarthquakeDataTask() {}

    @Override
    protected JSONObject doInBackground(String... url){
        return loadEarthquakeData(url[0]);
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(JSONObject result){
        JSONObject test = result;
        textView.setText(result.toString());
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

            //JSONObject main = result.getJSONObject("main");
            //weather = String.valueOf(main.getDouble("temp"));

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
