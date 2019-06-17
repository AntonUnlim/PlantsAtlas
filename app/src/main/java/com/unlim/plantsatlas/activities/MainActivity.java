package com.unlim.plantsatlas.activities;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.DatabeseHelper;
import com.unlim.plantsatlas.main.Family;
import com.unlim.plantsatlas.main.Habitat;
import com.unlim.plantsatlas.main.LifeForm;
import com.unlim.plantsatlas.main.Value;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabeseHelper databeseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateDatabase();

        // test
        SQLiteDatabase db;

        try {
            db = databeseHelper.getReadableDatabase();
        } catch (SQLException e) {
            throw e;
        }

        Database.fillMainData(db);

        ListView testListView = (ListView)findViewById(R.id.testList);
        List<Value> list = Database.getValues();

        ArrayAdapter<String> testAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        testListView.setAdapter(testAdapter);
    }

    private void updateDatabase() {
        databeseHelper = new DatabeseHelper(getApplicationContext());
        try {
            databeseHelper.updateDatabase();
        } catch (IOException e) {
            throw new Error("Unable to update database");
        }
    }
}
