package com.unlim.plantsatlas.activities;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.adapters.ListViewAdapterWithFilter;
import com.unlim.plantsatlas.data.Const;
import com.unlim.plantsatlas.data.Database;
import com.unlim.plantsatlas.data.DatabaseHelper;
import com.unlim.plantsatlas.data.Listable;
import com.unlim.plantsatlas.main.Section;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listOfSectionsView;
    private List<Listable> listOfSections;
    private SQLiteDatabase db;
    private boolean isLatNames = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateDatabase();
        db = getDatabase();
        try {
            Database.fillMainData(db);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        fillListOfSections();
        listOfSectionsView.setOnItemClickListener(getItemClickListener());

    }

    private AdapterView.OnItemClickListener getItemClickListener() {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    isLatNames = true;
                }
                else {
                    isLatNames = false;
                }

                try {
                    startNewActivity(view.getTag());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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
        ListViewAdapterWithFilter adapter = new ListViewAdapterWithFilter(this, listOfSections);
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

    private void startNewActivity(Object tag) throws ClassNotFoundException {
        Intent intent;
        intent = new Intent(MainActivity.this, ((Section)tag).getClassToStart());
        intent.putExtra(Const.INTENT_IS_LAT_NAMES, isLatNames);
        intent.putExtra(Const.INTENT_SECTION, (Section)tag);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
