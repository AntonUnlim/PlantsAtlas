package com.unlim.plantsatlas.activities;

import android.content.Intent;
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
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Category;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Plant;
import com.unlim.plantsatlas.main.Section;
import com.unlim.plantsatlas.main.Value;

import java.util.Arrays;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private ListView categoryList;
    private EditText editTextSearch;
    private TextView title;
    private ListViewAdapterWithFilter adapter;
    private int categoryFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        categoryList = (ListView)findViewById(R.id.category_list);
        editTextSearch = (EditText)findViewById(R.id.category_search_text);
        title = (TextView)findViewById(R.id.category_title);

        categoryFromIntent = getIntent().getIntExtra(Const.INTENT_CATEGORY_LIST, -1);
        TextWatcher searchWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fillListOfCategories(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
        editTextSearch.addTextChangedListener(searchWatcher);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Database.setNotFullPlants(view.getTag());
                startNewActivity();
                /*Toast t = Toast.makeText(CategoryListActivity.this, selectedPlant.getName(), Toast.LENGTH_SHORT);
                t.show();*/
            }
        });
    }

    protected void onResume(){
        super.onResume();
        getCategory();
        fillListOfCategories("");
        editTextSearch.setText("");
    }

    private void getCategory() {
        switch (categoryFromIntent) {
            case 3:
                List<Listable> families = Database.getFamilies();
                adapter = new ListViewAdapterWithFilter(this, families);
                title.setText("Семейства");
                break;
            case 4:
                List<Listable> lifeForms = Database.getLifeForms();
                adapter = new ListViewAdapterWithFilter(this, lifeForms);
                title.setText("Жизненные формы");
                break;
            case 5:
                List<Listable> values = Database.getValues();
                adapter = new ListViewAdapterWithFilter(this, values);
                title.setText("Значение");
                break;
            case 6:
                List<Listable> habitats = Database.getHabitats();
                adapter = new ListViewAdapterWithFilter(this, habitats);
                title.setText("Местообитание");
                break;
            case 7:
                List<Listable> endangeredList = Database.getEndangeredLists();
                adapter = new ListViewAdapterWithFilter(this, endangeredList);
                title.setText("Красная книга");
                break;
            case 8:
                List<Listable> flowerColors = Database.getFlowerColors();
                adapter = new ListViewAdapterWithFilter(this, flowerColors);
                title.setText("Цветки");
                break;
            default:
                adapter = null;
        }
    }

    private void fillListOfCategories(CharSequence searchString) {
        adapter.getFilter().filter(searchString);
        categoryList.setAdapter(adapter);
    }

    private void startNewActivity() {
        Intent intent;
        intent = new Intent(CategoryListActivity.this, PlantNamesActivity.class);
        intent.putExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS, "notFullList");
        startActivity(intent);
    }
}
