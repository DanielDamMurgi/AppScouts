package com.scouts.appscouts.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scouts.appscouts.R;

public class GeneralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        getSupportActionBar().setTitle("General");
    }
}
