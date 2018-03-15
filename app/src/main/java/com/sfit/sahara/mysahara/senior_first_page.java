package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.content.SharedPreferences;
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
        SharedPreferences data=getSharedPreferences("UserData",MODE_PRIVATE);

        final String sfname = data.getString("Senior First Name",null);
        final String slname = data.getString("Senior Last Name",null);
        final String contact = data.getString("Contact",null);

        tvHiOldie.setText("HI\n" + sfname + "\n"+ slname);
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(senior_first_page.this, senior_second_page.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("contact",contact);
                startActivity(intent);
                finish();
            }
        }).start();
        finish();
    }
}
