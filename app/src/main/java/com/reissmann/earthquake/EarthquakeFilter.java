package com.reissmann.earthquake;

import android.widget.Filter;

import com.reissmann.earthquake.model.Feature;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeFilter extends Filter {

    private EarthquakeAdapter earthquakeAdapter;
    private List<Feature> earthquakeList = new ArrayList<>();
    private List<Feature> earthquakeListOriginal = new ArrayList<>();
    private List<Feature> filteredEarthquakeList = new ArrayList<>();

    public EarthquakeFilter(EarthquakeAdapter earthquakeAdapter, List<Feature> earthquakeList) {
        super();
        this.earthquakeAdapter = earthquakeAdapter;
        this.earthquakeList.addAll(earthquakeList);
        this.earthquakeListOriginal.addAll(earthquakeList);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredEarthquakeList.clear();
        earthquakeList.clear();
        earthquakeList.addAll(earthquakeListOriginal);
        FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            filteredEarthquakeList.addAll(earthquakeList);
        } else {
            for (Feature earthquake : earthquakeList) {
                if (earthquake.getProperties().getMag() > Double.parseDouble(constraint.toString())) {
                    filteredEarthquakeList.add(earthquake);
                }
            }
        }

        results.values = filteredEarthquakeList;
        results.count = filteredEarthquakeList.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        earthquakeAdapter.earthquakeList.clear();
        earthquakeAdapter.earthquakeList.addAll((ArrayList<Feature>) results.values);
        earthquakeAdapter.notifyDataSetChanged();
    }
}
