package com.unlim.plantsatlas.activities;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.DatabaseHelper;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listOfSectionsView;
    private List<String> listOfSections;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateDatabase();
        db = getDatabase();
        Database.fillMainData(db);
        fillListOfSections();
        listOfSectionsView.setOnItemClickListener(getItemClickListener());
    }

    private AdapterView.OnItemClickListener getItemClickListener() {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startNewActivity(position);
            }
        };
        return listener;
    }

    private SQLiteDatabase getDatabase() {
        try {
            return databaseHelper.getReadableDatabase();
        } catch (SQLException e) {
            throw e;
        }
    }

    private void fillListOfSections() {
        listOfSectionsView = (ListView)findViewById(R.id.listOfSections);
        listOfSections = Database.getSections();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfSections);
        listOfSectionsView.setAdapter(adapter);
    }

    private void updateDatabase() {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        try {
            databaseHelper.updateDatabase();
        } catch (IOException e) {
            throw new Error("Unable to update database");
        }
    }

    private void startNewActivity(int section) {
        Intent intent;
        switch (section) {
            case 0:
                intent = new Intent(MainActivity.this, PlantNamesActivity.class);
                intent.putExtra(Const.INTENT_IS_RUS_NAMES, true);
                break;
            case 1:
                intent = new Intent(MainActivity.this, PlantNamesActivity.class);
                intent.putExtra(Const.INTENT_IS_RUS_NAMES, false);
                break;
            case 2:
                intent = new Intent(MainActivity.this, CategoryListActivity.class);
                intent.putExtra(Const.INTENT_CATEGORY_LIST, "Family");
                break;
            case 3:
                intent = new Intent(MainActivity.this, CategoryListActivity.class);
                intent.putExtra(Const.INTENT_CATEGORY_LIST, "LifeForm");
                break;
            case 4:
                intent = new Intent(MainActivity.this, CategoryListActivity.class);
                intent.putExtra(Const.INTENT_CATEGORY_LIST, "Value");
                break;
            case 5:
                intent = new Intent(MainActivity.this, CategoryListActivity.class);
                intent.putExtra(Const.INTENT_CATEGORY_LIST, "Habitat");
                break;
            case 6: // red book
                intent = new Intent();
                //intent.putExtra(Const.INTENT_CATEGORY_LIST, "Family");
                break;
            case 7:
                intent = new Intent(MainActivity.this, CategoryListActivity.class);
                intent.putExtra(Const.INTENT_CATEGORY_LIST, "FlowerColor");
                break;
            case 8: // search
                intent = new Intent();
                //intent.putExtra(Const.INTENT_CATEGORY_LIST, "Family");
                break;
            case 9: // about
                intent = new Intent();
                //intent.putExtra(Const.INTENT_CATEGORY_LIST, "Family");
                break;
            default:
                intent = new Intent();
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
