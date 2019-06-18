package com.unlim.plantsatlas.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Plant;
import com.unlim.plantsatlas.main.Value;

import java.util.Arrays;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private ListView categoryList;
    private EditText editTextSearch;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        categoryList = (ListView)findViewById(R.id.category_list);
        editTextSearch = (EditText)findViewById(R.id.category_search_text);
        title = (TextView)findViewById(R.id.category_title);

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
    }

    protected void onResume(){
        super.onResume();
        fillListOfCategories("");
        editTextSearch.setText("");
    }

    private void fillListOfCategories(CharSequence searchString) {
        String categoryFromIntent = getIntent().getStringExtra(Const.INTENT_CATEGORY_LIST);
        ArrayAdapter<String> adapter;
        switch (categoryFromIntent) {
            case "Family":
                List<Family> families = Database.getFamilies();
                adapter = new ArrayAdapter(this, R.layout.simple_list_item, R.id.item_name, families);
                title.setText("Семейства");
                break;
            case "LifeForm":
                List<LifeForm> lifeForms = Database.getLifeForms();
                adapter = new ArrayAdapter(this, R.layout.simple_list_item, R.id.item_name, lifeForms);
                title.setText("Жизненные формы");
                break;
            case "Value":
                List<Value> values = Database.getValues();
                adapter = new ArrayAdapter(this, R.layout.simple_list_item, R.id.item_name, values);
                title.setText("Значение");
                break;
            case "Habitat":
                List<Habitat> habitats = Database.getHabitats();
                adapter = new ArrayAdapter(this, R.layout.simple_list_item, R.id.item_name, habitats);
                title.setText("Местообитание");
                break;
            case "FlowerColor":
                List<FlowerColor> flowerColors = Database.getFlowerColors();
                adapter = new ArrayAdapter(this, R.layout.simple_list_item, R.id.item_name, flowerColors);
                title.setText("Цветки");
                break;
            default:
                adapter = null;
        }
        adapter.getFilter().filter(searchString);
        categoryList.setAdapter(adapter);
    }
}
