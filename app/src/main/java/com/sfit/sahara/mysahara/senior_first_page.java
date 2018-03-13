package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class senior_first_page extends AppCompatActivity {
    TextView tvHiOldie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senior_first_page);
        tvHiOldie = findViewById(R.id.tvHiOldie);

        //fetch name of old man from database and display it
        Intent i = getIntent();
        String sfname = i.getStringExtra("sfname");
        String slname = i.getStringExtra("slname");


        tvHiOldie.setText("HI\n" + sfname + "\n"+ slname);
        //make button for call and sos
    }
}
