package com.unlim.plantsatlas.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.unlim.plantsatlas.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ListViewAdapterImage extends BaseAdapter {

    private List<String> plantAddPhotos;
    private Context context;
    private LayoutInflater inflater;

    public ListViewAdapterImage(Context context, List<String> photos) {
        this.context = context;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.plantAddPhotos = photos;
    }

    @Override
    public int getCount() {
        return plantAddPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return plantAddPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.simple_list_image, null);
        }

        String photo = plantAddPhotos.get(position);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageListImage);
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("photos/" + photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable image = Drawable.createFromStream(inputStream, null);

        imageView.setImageDrawable(image);
        view.setTag(photo);

        return view;
    }
}
