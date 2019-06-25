package com.unlim.plantsatlas.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Plant;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ListViewAdapterImageWithTitle extends BaseAdapter {
    private List<Listable> plants;
    private Context context;
    private LayoutInflater inflater;

    public ListViewAdapterImageWithTitle(Context context, List<Listable> plants) {
        this.context = context;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.plants = plants;
    }

    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Object getItem(int position) {
        return plants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.simple_list_image_with_title, null);
        }

        Plant plant = (Plant)plants.get(position);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageListImageWithTitle);
        TextView textView = (TextView)view.findViewById(R.id.imageListTitleWithTitle);

        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("photos/" + plant.getMainPhotoFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable image = Drawable.createFromStream(inputStream, null);

        imageView.setImageDrawable(image);
        textView.setText(plant.getRusName());
        view.setTag(plant);

        return view;
    }
}
