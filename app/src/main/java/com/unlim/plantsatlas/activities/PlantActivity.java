package com.unlim.plantsatlas.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.main.Plant;

import java.io.IOException;
import java.io.InputStream;

public class PlantActivity extends AppCompatActivity {

    private TextView tvPlantRusName, tvPlantLatName, tvPlantAuthor, tvPlantFamily, tvPlantText;
    private ImageView ivMainPhoto;
    private Plant plant;
    private LinearLayout lvAdditionalPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        findAllViews();

        int idPlant = getIntent().getIntExtra(Const.INTENT_PLANT_ID, -1);
        plant = Database.getPlantById(idPlant);

        tvPlantRusName.setText(plant.getRusName());
        tvPlantLatName.setText(plant.getLatName());
        tvPlantAuthor.setText(plant.getAuthor());
        tvPlantFamily.setText("Семейство " + plant.getFamily());
        tvPlantText.setText(plant.getText());
        setImage();
        setAdditionalPhotos();
        ivMainPhoto.setOnClickListener(onImageClickListener());
    }

    private View.OnClickListener onImageClickListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantActivity.this, PhotoActivity.class);
                intent.putExtra(Const.INTENT_PHOTO, v.getTag().toString());
                startActivity(intent);
            }
        };
        return onClickListener;
    }

    private void setAdditionalPhotos() {
        if(plant.getListOfAdditionalPhotos().size() > 0) {
            for(String fileName: plant.getListOfAdditionalPhotos()) {
                ImageView imageView = new ImageView(this);
                //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                imageView.setPadding(10, 10, 10, 5);
                //imageView.setLayoutParams(params);
                InputStream inputStream = null;
                try {
                    inputStream = getAssets().open("photos/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable image = Drawable.createFromStream(inputStream, null);
                imageView.setImageDrawable(image);
                lvAdditionalPhotos.addView(imageView);
            }
        }
    }

    private void findAllViews() {
        tvPlantRusName = (TextView)findViewById(R.id.plantTitleRus);
        tvPlantLatName = (TextView)findViewById(R.id.plantTitleLat);
        tvPlantAuthor = (TextView)findViewById(R.id.plantTitleAuthor);
        tvPlantFamily = (TextView)findViewById(R.id.plantFamily);
        tvPlantText = (TextView)findViewById(R.id.plantText);
        ivMainPhoto = (ImageView)findViewById(R.id.plantMainImage);
        lvAdditionalPhotos = (LinearLayout)findViewById(R.id.plantAdditionalImages);
    }

    private void setImage() {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("photos/" + plant.getMainPhotoFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable image = Drawable.createFromStream(inputStream, null);
        ivMainPhoto.setTag(plant.getMainPhotoFileName());
        ivMainPhoto.setImageDrawable(image);
    }


}
