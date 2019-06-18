package com.unlim.plantsatlas.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.main.Plant;

import java.util.ArrayList;
import java.util.List;

public class PlantNameAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private LayoutInflater inflater;
    private List<Plant> plants;
    private List<Plant> plantFilterList;
    private boolean isRusNames;
    private NameFilter filter;

    public PlantNameAdapter(Context context, List<Plant> plants, boolean isRusNames) {
        this.context = context;
        this.plants = plants;
        plantFilterList = plants;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isRusNames = isRusNames;
        getFilter();
    }

    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Plant getItem(int position) {
        return plants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Plant getPlant(int position) {
        return (getItem(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.simple_list_item, parent, false);
        }

        Plant plant = getPlant(position);

        TextView textViewName = (TextView)view.findViewById(R.id.item_name);
        textViewName.setText((isRusNames)?plant.getRusName():plant.getLatName());
        view.setTag(plant);

        return view;
    }

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new NameFilter();
        }
        return filter;
    }

    private class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0) {
                List<Plant> filteredList = new ArrayList<>();
                for(int i = 0; i < plantFilterList.size(); i++) {
                    if(plantFilterList.get(i).getRusName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        Plant tmpPlant = new Plant(plantFilterList.get(i).getRusName());
                        filteredList.add(tmpPlant);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            } else {
                results.count = plantFilterList.size();
                results.values = plantFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            plants = (ArrayList<Plant>)results.values;
            notifyDataSetChanged();
        }
    }
}

