package com.example.srv_twry.studentcompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreatePDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);
        setTitle(getResources().getString(R.string.create_pdf));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
