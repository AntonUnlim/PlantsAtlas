package com.unlim.plantsatlas.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.main.Plant;

import java.util.Arrays;
import java.util.List;

public class PlantNamesActivity extends AppCompatActivity {

    private ListView plantNamesList;
    private EditText editTextSearch;
    private TextView title;
    private List<Plant> plants;
    private boolean isRusNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_names);

        plantNamesList = (ListView)findViewById(R.id.plant_names_list);
        editTextSearch = (EditText)findViewById(R.id.plant_names_search_text);
        title = (TextView)findViewById(R.id.plant_names_title);
        plants = Database.getPlants();

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
                Toast t = Toast.makeText(PlantNamesActivity.this, (isRusNames)?selectedPlant.getRusName():selectedPlant.getLatName(), Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    protected void onResume(){
        super.onResume();
        fillListOfPlants("");
        editTextSearch.setText("");
    }

    private void fillListOfPlants(CharSequence searchString) {
        isRusNames = getIntent().getBooleanExtra(Const.INTENT_IS_RUS_NAMES, true);
        title.setText((isRusNames)?"Русские названия":"Латинские названия");
        String[] plantNames = new String[plants.size()];
        for (int i = 0; i < plants.size(); i++) {
            plantNames[i] = (isRusNames) ? plants.get(i).getRusName() : plants.get(i).getLatName();
        }
        Arrays.sort(plantNames);
        PlantNameAdapter adapter = new PlantNameAdapter(this, plants, isRusNames);
        adapter.getFilter().filter(searchString);
        plantNamesList.setAdapter(adapter);


    }
}
