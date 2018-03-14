package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class junior_second_page_addmember extends AppCompatActivity {

    Button btnAdd;
    Button btnTrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_second_page_addmember);

        btnAdd = findViewById(R.id.btnAdd);
        btnTrack = findViewById(R.id.btnTrack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(junior_second_page_addmember.this,junior_second_page_home.class);
                startActivity(i);
            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take junior to senior tracking page
            }
        });
    }
}
