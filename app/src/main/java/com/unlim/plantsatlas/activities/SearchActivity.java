package com.unlim.plantsatlas.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.adapters.ListViewAdapterWithFilter;
import com.unlim.plantsatlas.adapters.SpinnerAdapter;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Plant;
import com.unlim.plantsatlas.main.Value;
import com.unlim.plantsatlas.main.YesNo;

import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private EditText searchString;
    private Button btnSearch;
    private ListView searchListView;
    private List<Listable> plants;
    private ListViewAdapterWithFilter adapter;
    private TextView textViewFoundAmount;
    private Spinner
            spinnerFamily,
            spinnerLifeForm,
            spinnerValue,
            spinnerHabitat,
            spinnerFlowerColor,
            spinnerEndangeredListSar,
            spinnerEndangeredListRF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findAllViews();
        fillSpinners();
        btnSearch.setOnClickListener(onSearchButtonClickListener());
        searchListView.setOnItemClickListener(onItemClickListener());
        searchString.requestFocus();
    }

    private void fillSpinners() {
        SpinnerAdapter adapterFamily = new SpinnerAdapter(this, Database.getFamilies());
        spinnerFamily.setAdapter(adapterFamily);
        SpinnerAdapter adapterLifeForm = new SpinnerAdapter(this, Database.getLifeForms());
        spinnerLifeForm.setAdapter(adapterLifeForm);
        SpinnerAdapter adapterValue = new SpinnerAdapter(this, Database.getValues());
        spinnerValue.setAdapter(adapterValue);
        SpinnerAdapter adapterHabitat = new SpinnerAdapter(this, Database.getHabitats());
        spinnerHabitat.setAdapter(adapterHabitat);
        SpinnerAdapter adapterFlowerColor = new SpinnerAdapter(this, Database.getFlowerColors());
        spinnerFlowerColor.setAdapter(adapterFlowerColor);
        SpinnerAdapter adapterEndangeredList = new SpinnerAdapter(this, Database.getYesNoList());
        spinnerEndangeredListSar.setAdapter(adapterEndangeredList);
        spinnerEndangeredListRF.setAdapter(adapterEndangeredList);

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
                plants = Database.searchPlants(
                        searchString.getText().toString(),
                        (Family)spinnerFamily.getSelectedItem(),
                        (LifeForm)spinnerLifeForm.getSelectedItem(),
                        (Value)spinnerValue.getSelectedItem(),
                        (Habitat)spinnerHabitat.getSelectedItem(),
                        (FlowerColor)spinnerFlowerColor.getSelectedItem(),
                        (YesNo)spinnerEndangeredListSar.getSelectedItem(),
                        (YesNo)spinnerEndangeredListRF.getSelectedItem()
                        );
                adapter = new ListViewAdapterWithFilter(SearchActivity.this, plants);
                searchListView.setAdapter(adapter);
                InputMethodManager inputMethodManager = (InputMethodManager)SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(SearchActivity.this.getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                textViewFoundAmount.setText(Const.getPlantsAmountString(plants.size()));
            }
        };
        return listener;
    }

    private void findAllViews() {
        searchString = (EditText)findViewById(R.id.searchStringSearchActivity);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        searchListView = (ListView)findViewById(R.id.searchListView);
        textViewFoundAmount = (TextView)findViewById(R.id.searchAmountFound);
        spinnerFamily = (Spinner)findViewById(R.id.searchSpinnerFamily);
        spinnerLifeForm = (Spinner)findViewById(R.id.searchSpinnerLifeForm);
        spinnerValue = (Spinner)findViewById(R.id.searchSpinnerValue);
        spinnerHabitat = (Spinner)findViewById(R.id.searchSpinnerHabitat);
        spinnerFlowerColor = (Spinner)findViewById(R.id.searchSpinnerFlowerColor);
        spinnerEndangeredListSar = (Spinner)findViewById(R.id.searchSpinnerEndangeredListSar);
        spinnerEndangeredListRF = (Spinner)findViewById(R.id.searchSpinnerEndangeredListRF);
    }
}
