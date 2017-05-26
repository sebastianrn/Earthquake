package com.reissmann.earthquake;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reissmann.earthquake.model.Feature;

import java.util.List;

/**
 * Created by se_re on 11.05.2017.
 */

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {
    private List<Feature> earthquakesList;
    private Context context;
    private Utils utils = new Utils();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView itemCardView;
        public TextView placeTextView;
        public TextView magnitudeTextView;
        public TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemCardView = (CardView)itemView.findViewById(R.id.itemCardView);
            placeTextView = (TextView)itemView.findViewById(R.id.textViewPlace);
            magnitudeTextView = (TextView)itemView.findViewById(R.id.textViewMagnitude);
            dateTextView = (TextView)itemView.findViewById(R.id.textViewDate);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EarthquakeAdapter(Context context, List<Feature> earthquakesList) {
        this.earthquakesList = earthquakesList;
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EarthquakeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View earthquakeView = inflater.inflate(R.layout.earthquake_default_item, parent,false);

        ViewHolder vh = new ViewHolder(earthquakeView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feature earthquake = earthquakesList.get(position);

        TextView textViewPlace = holder.placeTextView;
        textViewPlace.setText(earthquake.getProperties().getPlace());

        TextView textViewMag = holder.magnitudeTextView;
        textViewMag.setText(earthquake.getProperties().getMag().toString());

        TextView textViewDate = holder.dateTextView;
        textViewDate.setText(utils.getFormattedDate(earthquake.getProperties().getTime()).toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return earthquakesList.size();
    }
}