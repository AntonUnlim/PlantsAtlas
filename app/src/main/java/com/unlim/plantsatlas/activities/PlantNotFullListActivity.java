package com.unlim.plantsatlas.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.adapters.ListViewAdapterImageWithTitle;
import com.unlim.plantsatlas.adapters.ListViewAdapterWithFilter;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Category;
import com.unlim.plantsatlas.main.EndangeredList;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Plant;
import com.unlim.plantsatlas.main.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlantNotFullListActivity extends AppCompatActivity {

    private TextView titleTextView;
    private ImageView imageView;
    private EditText searchEditText;
    private ListView plantsListView;
    private Object tagFromIntent;
    private List<Listable> listOfPlants;
    private ListViewAdapterWithFilter adapterText;
    private ListViewAdapterImageWithTitle adapterImage;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_not_full_list);
        findAllViews();
        getInfoFromIntent();
        setImage();
        addTextWatcher();

        listOfPlants = Database.getNotFullPlants();
        adapterText = new ListViewAdapterWithFilter(this, listOfPlants);
        adapterImage = new ListViewAdapterImageWithTitle(this, listOfPlants);

        plantsListView.setAdapter(adapterImage);
        plantsListView.setOnItemClickListener(getItemClickListener());
        aSwitch.setOnCheckedChangeListener(switchListener());
    }

    private CompoundButton.OnCheckedChangeListener switchListener() {
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchEditText.setVisibility(View.VISIBLE);
                    plantsListView.setAdapter(adapterText);
                }
                if (!isChecked) {
                    searchEditText.setVisibility(View.INVISIBLE);
                    plantsListView.setAdapter(adapterImage);
                }
            }
        };
        return listener;
    }

    private AdapterView.OnItemClickListener getItemClickListener() {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getTag() instanceof Plant) {
                    startPlantActivity((Plant) view.getTag());
                }
            }
        };
        return listener;
    }

    private void startPlantActivity(Plant plant) {
        Intent intent;
        intent = new Intent(PlantNotFullListActivity.this, PlantActivity.class);
        intent.putExtra(Const.INTENT_PLANT_ID, plant.getId());
        startActivity(intent);
    }

    private void setImage() {
        if (tagFromIntent instanceof Family || tagFromIntent instanceof EndangeredList || tagFromIntent instanceof FlowerColor) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            imageView.setLayoutParams(params);
        } else {
            Category category = (Category)tagFromIntent;
            InputStream inputStream = null;
            try {
                inputStream = getAssets().open("section_images/" + category.getImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable image = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(image);
        }
    }

    private void addTextWatcher() {
        TextWatcher searchWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fillListOfPlants(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
        searchEditText.addTextChangedListener(searchWatcher);
    }

    private void getInfoFromIntent() {
        tagFromIntent = getIntent().getSerializableExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS);
        if (tagFromIntent instanceof Family) {
            titleTextView.setText(((Family)tagFromIntent).getName());
        } else if (tagFromIntent instanceof LifeForm) {
            titleTextView.setText(((LifeForm)tagFromIntent).getName());
        } else if (tagFromIntent instanceof Habitat) {
            titleTextView.setText(((Habitat)tagFromIntent).getName());
        } else if (tagFromIntent instanceof Value) {
            titleTextView.setText(((Value)tagFromIntent).getName());
        } else if (tagFromIntent instanceof EndangeredList) {
            titleTextView.setText(((EndangeredList)tagFromIntent).getName());
        } else if (tagFromIntent instanceof FlowerColor) {
            titleTextView.setText("Цвет " + ((FlowerColor)tagFromIntent).getName());
        }
        listOfPlants = Database.getNotFullPlants();
    }

    private void findAllViews() {
        titleTextView = (TextView)findViewById(R.id.plantNotFullListTitle);
        imageView = (ImageView)findViewById(R.id.plantNotFullListImage);
        searchEditText = (EditText)findViewById(R.id.plantNotFullListSearchText);
        plantsListView = (ListView)findViewById(R.id.plantNotFullListView);
        aSwitch = (Switch)findViewById(R.id.plantNotFullListSwitch);
    }

    private void fillListOfPlants(CharSequence searchString) {
        adapterText.getFilter().filter(searchString);
        plantsListView.setAdapter(adapterText);
    }
}
