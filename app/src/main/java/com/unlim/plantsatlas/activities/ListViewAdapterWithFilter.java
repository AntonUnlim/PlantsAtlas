package com.unlim.plantsatlas.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Listable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterWithFilter extends BaseAdapter implements Filterable {
    private Context context;
    private LayoutInflater inflater;
    private List<Listable> unfilteredList;
    private List<Listable> filteredList;
    private CustomFilter filter;

    public ListViewAdapterWithFilter(Context context, List<Listable> listToFilter) {
        this.context = context;
        this.unfilteredList = listToFilter;
        filteredList = listToFilter;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }

    @Override
    public int getCount() {
        return unfilteredList.size();
    }

    @Override
    public Listable getItem(int position) {
        return unfilteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.simple_list_item, parent, false);
        }

        Object object = getItem(position);

        TextView textViewName = (TextView)view.findViewById(R.id.item_name);
        textViewName.setText(object.toString());
        view.setTag(object);

        return view;
    }

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0) {
                List<Listable> filteredList = new ArrayList<>();
                for(int i = 0; i < ListViewAdapterWithFilter.this.filteredList.size(); i++) {
                    if(ListViewAdapterWithFilter.this.filteredList.get(i).getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        Listable tmpObject = null;
                        try {
                            tmpObject = ListViewAdapterWithFilter.this.filteredList.get(i).clone();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        filteredList.add(tmpObject);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            } else {
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            unfilteredList = (ArrayList<Listable>)results.values;
            notifyDataSetChanged();
        }
    }
}
