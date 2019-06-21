package com.unlim.plantsatlas.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;

import java.io.IOException;
import java.io.InputStream;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        String fileName = getIntent().getStringExtra(Const.INTENT_PHOTO);
        ImageView bigImage = (ImageView)findViewById(R.id.bigImage);
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("photos/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable image = Drawable.createFromStream(inputStream, null);
        bigImage.setImageDrawable(image);
    }
}
