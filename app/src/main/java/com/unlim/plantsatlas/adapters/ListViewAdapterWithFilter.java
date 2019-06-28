package com.unlim.plantsatlas.adapters;

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
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Plant;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapterWithFilter extends BaseAdapter implements Filterable {
    private Context context;
    private LayoutInflater inflater;
    private List<Listable> unfilteredList;
    private List<Listable> filteredList;
    private CustomFilter filter;
    private boolean flag = false;

    public ListViewAdapterWithFilter(Context context, List<Listable> listToFilter) {
        this.context = context;
        this.unfilteredList = listToFilter;
        filteredList = listToFilter;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }

    public ListViewAdapterWithFilter(Context context, List<Listable> listToFilter, boolean flag) {
        this(context, listToFilter);
        this.flag = flag;
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
        if(flag && object instanceof Plant) {
            textViewName.setText(((Plant)object).getLatName());
        } else {
            textViewName.setText(object.toString());
        }
        view.setTag(object);

        if(flag && object instanceof FlowerColor) {
            view.setBackgroundColor(((FlowerColor)object).getIntColor());
        }
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
                    if(flag && ListViewAdapterWithFilter.this.filteredList.get(i) instanceof Plant) {
                        // filter by lat name (for plants only)
                        if (((Plant)ListViewAdapterWithFilter.this.filteredList.get(i)).getLatName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                            Listable tmpObject = null;
                            try {
                                tmpObject = ListViewAdapterWithFilter.this.filteredList.get(i).clone();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            filteredList.add(tmpObject);
                        }
                    } else {
                        // filter by rus name
                        if (ListViewAdapterWithFilter.this.filteredList.get(i).getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                            Listable tmpObject = null;
                            try {
                                tmpObject = ListViewAdapterWithFilter.this.filteredList.get(i).clone();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            filteredList.add(tmpObject);
                        }
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
