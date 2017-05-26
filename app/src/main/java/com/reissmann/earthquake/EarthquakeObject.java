package com.reissmann.earthquake;

/**
 * Created by se_re on 16.05.2017.
 */

public class EarthquakeObject {
    private String place;
    private Double magnitude;

    public EarthquakeObject(){
        place = "Testplace";
        magnitude = 8.4;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }
}
