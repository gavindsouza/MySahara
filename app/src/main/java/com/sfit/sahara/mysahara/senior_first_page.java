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
        final String contact = i.getStringExtra("contact");

        tvHiOldie.setText("HI\n" + sfname + "\n"+ slname);
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(senior_first_page.this, senior_second_page.class);
                intent.putExtra("contact",contact);
                startActivity(intent);
                finish();
            }
        }).start();


    }
}
