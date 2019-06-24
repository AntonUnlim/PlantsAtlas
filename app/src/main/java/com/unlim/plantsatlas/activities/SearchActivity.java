package com.unlim.plantsatlas.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Plant;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchString;
    private Button btnSearch;
    private ListView searchListView;
    private List<Listable> plants;
    private ListViewAdapterWithFilter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findAllViews();

        btnSearch.setOnClickListener(onSearchButtonClickListener());
        searchListView.setOnItemClickListener(onItemClickListener());
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, PlantActivity.class);
                intent.putExtra(Const.INTENT_PLANT_ID, ((Plant)view.getTag()).getId());
                startActivity(intent);
            }
        };
        return listener;
    }

    private View.OnClickListener onSearchButtonClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchString.getText().length() > 0) {
                    plants = Database.searchPlants(searchString.getText().toString());
                    adapter = new ListViewAdapterWithFilter(SearchActivity.this, plants);
                    searchListView.setAdapter(adapter);
                    InputMethodManager inputMethodManager = (InputMethodManager)SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        };
        return listener;
    }

    private void findAllViews() {
        searchString = (EditText)findViewById(R.id.searchStringSearchActivity);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        searchListView = (ListView)findViewById(R.id.searchListView);
    }
}
