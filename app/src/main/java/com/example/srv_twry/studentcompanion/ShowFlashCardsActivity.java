package com.example.srv_twry.studentcompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
*  This activity shows the flash cards associated with a certain flash card topic.
* */

public class ShowFlashCardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_flash_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
