package com.unlim.plantsatlas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.adapters.ListViewAdapterWithFilter;
import com.unlim.plantsatlas.adapters.SpinnerAdapter;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.FlowerColor;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Plant;

import java.util.List;

public class DefinerActivity extends AppCompatActivity {

    private Spinner spinnerFlowerColor, spinnerLifeForm, spinnerHabitat;
    private Button btnDefiner;
    private ListView definerListView;
    private TextView textViewAmountFound;
    private List<Listable> definedPlants;
    private ListViewAdapterWithFilter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definer);
        findAllViews();
        fillSpinners();
        btnDefiner.setOnClickListener(onButtonClickListener());
        definerListView.setOnItemClickListener(onItemClickListener());
    }

    private View.OnClickListener onButtonClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillResultListView();
            }
        };
        return listener;
    }

    private void fillSpinners() {
        SpinnerAdapter adapterFlowerColor = new SpinnerAdapter(this, Database.getFlowerColors());
        spinnerFlowerColor.setAdapter(adapterFlowerColor);
        SpinnerAdapter adapterLifeForm = new SpinnerAdapter(this, Database.getLifeForms());
        spinnerLifeForm.setAdapter(adapterLifeForm);
        SpinnerAdapter adapterHabitat = new SpinnerAdapter(this, Database.getHabitats());
        spinnerHabitat.setAdapter(adapterHabitat);
    }

    private void findAllViews() {
        spinnerFlowerColor = (Spinner)findViewById(R.id.spinnerFlowerColor);
        spinnerLifeForm = (Spinner)findViewById(R.id.spinnerLifeForm);
        spinnerHabitat = (Spinner)findViewById(R.id.spinnerHabitat);
        btnDefiner = (Button)findViewById(R.id.btnDefiner);
        definerListView = (ListView)findViewById(R.id.definerListView);
        textViewAmountFound = (TextView)findViewById(R.id.definerAmountFound);
    }

    private void fillResultListView() {
        definedPlants = Database.definePlants(
                (FlowerColor)spinnerFlowerColor.getSelectedItem(),
                (LifeForm)spinnerLifeForm.getSelectedItem(),
                (Habitat)spinnerHabitat.getSelectedItem());
        textViewAmountFound.setText(Const.getPlantsAmountString(definedPlants.size()));
        adapter = new ListViewAdapterWithFilter(DefinerActivity.this, definedPlants);
        definerListView.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DefinerActivity.this, PlantActivity.class);
                intent.putExtra(Const.INTENT_PLANT_ID, ((Plant)view.getTag()).getId());
                startActivity(intent);
            }
        };
        return listener;
    }

}
