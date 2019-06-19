package com.unlim.plantsatlas.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Plant;

import java.util.List;

public class PlantNamesActivity extends AppCompatActivity {

    private ListView plantNamesList;
    private EditText editTextSearch;
    private TextView title;
    private List<Listable> plants;
    private boolean isRusNames;
    private boolean isFullPlantList;
    private ListViewAdapterWithFilter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_names);

        plantNamesList = (ListView)findViewById(R.id.plant_names_list);
        editTextSearch = (EditText)findViewById(R.id.plant_names_search_text);
        title = (TextView)findViewById(R.id.plant_names_title);

        String fromIntent = getIntent().getStringExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS);
        isFullPlantList = (fromIntent == null || fromIntent.equals(""));

        fillPlants();

        isRusNames = getIntent().getBooleanExtra(Const.INTENT_IS_RUS_NAMES, true);
        title.setText((isRusNames)?"Русские названия":"Латинские названия");

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
        editTextSearch.addTextChangedListener(searchWatcher);

        plantNamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant selectedPlant = (Plant)view.getTag();
                Toast t = Toast.makeText(PlantNamesActivity.this, selectedPlant.getName(), Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    protected void onResume(){
        super.onResume();
        adapter = new ListViewAdapterWithFilter(this, plants);
        fillListOfPlants("");
        editTextSearch.setText("");
    }

    private void fillListOfPlants(CharSequence searchString) {
        adapter.getFilter().filter(searchString);
        plantNamesList.setAdapter(adapter);
    }

    private void fillPlants() {
        if (isFullPlantList) {
            plants = Database.getPlants();
        } else {
            plants = Database.getNotFullPlants();
        }
    }
}
