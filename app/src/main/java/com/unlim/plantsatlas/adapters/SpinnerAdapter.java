package com.unlim.plantsatlas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Listable;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Listable> list;

    private SpinnerAdapter(Context context) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public SpinnerAdapter(Context context, List<Listable> list) {
        this(context);
        this.list = copyListAndAddNullItem(list);
    }

    public SpinnerAdapter(Context context, String[] list) {
        this(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.spinner_simple_item, parent, false);
        Listable object = (Listable)getItem(position);
        TextView textView = (TextView)view.findViewById(R.id.spinnerItemTextView);
        if (object == null) {
            textView.setText("Не важно");
        } else {
            textView.setText(object.getName());
        }
        view.setTag(object);
        return view;
    }

    private List<Listable> copyListAndAddNullItem(List<Listable> listableList) {
        List<Listable> returnList = new ArrayList<>();
        returnList.add(null);
        returnList.addAll(listableList);
        return returnList;
    }
}
