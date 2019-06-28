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
import com.unlim.plantsatlas.main.EndangeredList;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Section;
import com.unlim.plantsatlas.main.Value;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private ListView categoryListView;
    private EditText searchEditText;
    private TextView titleTextView;
    private ListViewAdapterWithFilter adapter;
    private Section selectedSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        categoryListView = (ListView)findViewById(R.id.category_list);
        searchEditText = (EditText)findViewById(R.id.category_search_text);
        titleTextView = (TextView)findViewById(R.id.category_title);

        selectedSection = (Section)getIntent().getSerializableExtra(Const.INTENT_SECTION);
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
        searchEditText.addTextChangedListener(searchWatcher);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Database.setNotFullPlants(view.getTag());
                startNewActivity(view.getTag());
            }
        });
    }

    protected void onResume(){
        super.onResume();
        getCategory();
        fillListOfCategories("");
        searchEditText.setText("");
    }

    private void getCategory() {
        switch (selectedSection.getId()) {
            case 3:
                List<Listable> families = Database.getFamilies();
                adapter = new ListViewAdapterWithFilter(this, families);
                titleTextView.setText(selectedSection.getName());
                break;
            case 4:
                List<Listable> lifeForms = Database.getLifeForms();
                adapter = new ListViewAdapterWithFilter(this, lifeForms);
                titleTextView.setText(selectedSection.getName());
                break;
            case 5:
                List<Listable> values = Database.getValues();
                adapter = new ListViewAdapterWithFilter(this, values);
                titleTextView.setText(selectedSection.getName());
                break;
            case 6:
                List<Listable> habitats = Database.getHabitats();
                adapter = new ListViewAdapterWithFilter(this, habitats);
                titleTextView.setText(selectedSection.getName());
                break;
            case 7:
                List<Listable> endangeredList = Database.getEndangeredLists();
                adapter = new ListViewAdapterWithFilter(this, endangeredList);
                titleTextView.setText(selectedSection.getName());
                break;
            case 8:
                List<Listable> flowerColors = Database.getFlowerColors();
                adapter = new ListViewAdapterWithFilter(this, flowerColors, true);
                titleTextView.setText(selectedSection.getName());
                break;
            default:
                adapter = null;
        }
    }

    private void fillListOfCategories(CharSequence searchString) {
        adapter.getFilter().filter(searchString);
        categoryListView.setAdapter(adapter);
    }

    private void startNewActivity(Object tag) {
        Intent intent;
        intent = new Intent(CategoryListActivity.this, PlantNotFullListActivity.class);

        if (tag instanceof Family) {
            Family family = (Family)tag;
            intent.putExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS, family);
        } else if (tag instanceof LifeForm) {
            LifeForm lifeForm = (LifeForm)tag;
            intent.putExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS, lifeForm);
        } else if (tag instanceof Habitat) {
            Habitat habitat = (Habitat)tag;
            intent.putExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS, habitat);
        } else if (tag instanceof Value) {
            Value value = (Value)tag;
            intent.putExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS, value);
        } else if (tag instanceof EndangeredList) {
            EndangeredList endangeredList = (EndangeredList)tag;
            intent.putExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS, endangeredList);
        } else if (tag instanceof FlowerColor) {
            FlowerColor flowerColor = (FlowerColor) tag;
            intent.putExtra(Const.INTENT_CATEGORY_LIST_FOR_PLANTS, flowerColor);
        }
        startActivity(intent);
    }
}
