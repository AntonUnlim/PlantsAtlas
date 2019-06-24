package com.unlim.plantsatlas.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.unlim.plantsatlas.R;
import com.unlim.plantsatlas.data.Const;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tvAboutText = (TextView)findViewById(R.id.tvAboutText);
        tvAboutText.setText(Const.getTextAbout());
    }
}
