package com.reissmann.earthquake.service;

import android.util.Log;

import com.reissmann.earthquake.model.EarthquakeDataObject;
import com.fasterxml.jackson.databind.*;

import org.json.JSONObject;

/**
 * Created by sebas on 20.05.2017.
 */

public class Json2JavaMapperService {
    private ObjectMapper mapper = new ObjectMapper();
    private JSONObject jsonObject;

    public Json2JavaMapperService(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public EarthquakeDataObject getEarthquakeDataObject(){
        EarthquakeDataObject earthquakeDataObject = new EarthquakeDataObject();

        try{
            earthquakeDataObject = mapper.readValue(jsonObject.toString(), EarthquakeDataObject.class);
        } catch (Exception e) {
            Log.println(Log.ERROR,"",e.toString());
        }
        return earthquakeDataObject;
    }
}
