package com.unlim.plantsatlas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.adapters.ListViewAdapterWithFilter;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Plant;

import java.util.Collections;
import java.util.List;

public class PlantNamesActivity extends AppCompatActivity {

    private ListView plantsListView;
    private EditText searchEditText;
    private TextView titleTextView;
    private List<Listable> plants;
    private boolean isLatNames = false;
    private ListViewAdapterWithFilter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_names);

        plantsListView = (ListView)findViewById(R.id.plant_names_list);
        searchEditText = (EditText)findViewById(R.id.plant_names_search_text);
        titleTextView = (TextView)findViewById(R.id.plant_names_title);

        plants = Database.getPlants();

        isLatNames = getIntent().getBooleanExtra(Const.INTENT_IS_LAT_NAMES, false);
        titleTextView.setText((isLatNames)?"Латинские названия":"Русские названия");

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

        plantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startPlantActivity((Plant)view.getTag());
            }
        });
    }

    protected void onResume(){
        super.onResume();
        if (isLatNames) {
            Collections.sort(plants, Plant.sortByLatName());
        } else {
            Collections.sort(plants, Plant.sortByRusName());
        }
        adapter = new ListViewAdapterWithFilter(this, plants, isLatNames);
        fillListOfPlants("");
        searchEditText.setText("");
    }

    private void fillListOfPlants(CharSequence searchString) {
        adapter.getFilter().filter(searchString);
        plantsListView.setAdapter(adapter);
    }

    private void startPlantActivity(Plant plant) {
        Intent intent;
        intent = new Intent(PlantNamesActivity.this, PlantActivity.class);
        intent.putExtra(Const.INTENT_PLANT_ID, plant.getId());
        startActivity(intent);
    }

}
