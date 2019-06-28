package com.unlim.plantsatlas.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.adapters.ListViewAdapterImage;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.main.Plant;

import java.io.IOException;
import java.io.InputStream;

public class PlantActivity extends AppCompatActivity {

    private TextView tvPlantRusName, tvPlantLatName, tvPlantAuthor, tvPlantFamily, tvPlantText;
    private ImageView ivMainPhoto;
    private Plant plant;
    private ListView lvAdditionalPhotos;

    private TextView tvTabCategory, tvTabDescription;
    private NestedScrollView nsvCategory, nsvDescription;

    private TextView tableLifeForm, tableValue, tableValueTitle, tableHabitat, tableHabitatTitle,
            tableRedBookSar, tableRedBookRF, tableFlowerColor;

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
        setAdditionalPhotos();
        setImage();

        ivMainPhoto.setOnClickListener(onImageClickListener());

        tvTabCategory.setOnClickListener(onTabCategoryClickListener());
        tvTabDescription.setOnClickListener(onTabDescriptionClickListener());

        tableLifeForm.setText(fillLifeForm());
        tableValue.setText(fillValues());
        tableHabitat.setText(fillHabitats());
        tableRedBookSar.setText(plant.isEndangeredListSaratov() ? "Да" : "Нет");
        tableRedBookRF.setText(plant.isEndangeredListRussia() ? "Да" : "Нет");
        tableFlowerColor.setText(fillFlowerColor());
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

    private AdapterView.OnItemClickListener onItemClickListener() {
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlantActivity.this, PhotoActivity.class);
                intent.putExtra(Const.INTENT_PHOTO, view.getTag().toString());
                startActivity(intent);
            }
        };
        return onItemClickListener;
    }

    private void setAdditionalPhotos() {
        if(plant.getListOfAdditionalPhotos().size() > 0) {
            ListViewAdapterImage imageAdapter = new ListViewAdapterImage(PlantActivity.this, plant.getListOfAdditionalPhotos());
            lvAdditionalPhotos.setAdapter(imageAdapter);
            lvAdditionalPhotos.setOnItemClickListener(onItemClickListener());
        }
    }

    private void findAllViews() {
        tvPlantRusName = (TextView)findViewById(R.id.plantTitleRus);
        tvPlantLatName = (TextView)findViewById(R.id.plantTitleLat);
        tvPlantAuthor = (TextView)findViewById(R.id.plantTitleAuthor);
        tvPlantFamily = (TextView)findViewById(R.id.plantFamily);
        tvPlantText = (TextView)findViewById(R.id.plantText);
        ivMainPhoto = (ImageView)findViewById(R.id.plantMainImage);
        lvAdditionalPhotos = (ListView)findViewById(R.id.plantAdditionalImages);

        tvTabCategory = (TextView)findViewById(R.id.tabCategory);
        tvTabDescription = (TextView)findViewById(R.id.tabDescription);
        nsvCategory = (NestedScrollView)findViewById(R.id.tabScreenCategory);
        nsvDescription = (NestedScrollView)findViewById(R.id.tabScreenDescription);

        tableLifeForm = (TextView)findViewById(R.id.tableLifeForm);
        tableValue = (TextView)findViewById(R.id.tableValue);
        tableValueTitle = (TextView)findViewById(R.id.tableValueTitle);
        tableHabitat = (TextView)findViewById(R.id.tableHabitat);
        tableHabitatTitle = (TextView)findViewById(R.id.tableHabitatTitle);
        tableRedBookSar = (TextView)findViewById(R.id.tableRedBookSar);
        tableRedBookRF = (TextView)findViewById(R.id.tableRedBookRF);
        tableFlowerColor = (TextView)findViewById(R.id.tableFlowerColor);
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

    private View.OnClickListener onTabCategoryClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTabCategory.setBackground(null);
                tvTabDescription.setBackground(getResources().getDrawable(R.drawable.tab_shape, null));
                nsvCategory.setVisibility(View.VISIBLE);
                nsvDescription.setVisibility(View.INVISIBLE);
            }
        };
        return listener;
    }
    private View.OnClickListener onTabDescriptionClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTabCategory.setBackground(getResources().getDrawable(R.drawable.tab_shape, null));
                tvTabDescription.setBackground(null);
                nsvCategory.setVisibility(View.INVISIBLE);
                nsvDescription.setVisibility(View.VISIBLE);
            }
        };
        return listener;
    }

    private String fillLifeForm() {
        if (plant.getLifeForm() != null) {
            return plant.getLifeForm().getName();
        } else {
            return "Нет";
        }
    }

    private String fillValues() {
        String result = "";
        int size = plant.getValues().size();
        if(size > 0) {
            for(int i = 0; i < size; i++) {
                result += plant.getValues().get(i) + ((i == size-1) ? "" : "\n");
            }
        } else {
            result = "Нет";
        }
        tableValueTitle.setLines((size==0)?1:size);
        return result;
    }

    private String fillHabitats() {
        String result = "";
        int size = plant.getHabitats().size();
        if(size > 0) {
            for(int i = 0; i < size; i++) {
                result += plant.getHabitats().get(i) + ((i == size-1) ? "" : "\n");
            }
        } else {
            result = "Нет";
        }
        tableHabitatTitle.setLines((size==0)?1:size);
        return result;
    }

    private String fillFlowerColor() {
        if (plant.getFlowerColor() != null) {
            return plant.getFlowerColor().getName();
        } else {
            return "Нет";
        }
    }

}
