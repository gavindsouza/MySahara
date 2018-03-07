package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class junior_second_page__signup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page__signup);
        Button sback = (Button) findViewById(R.id.back);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(junior_second_page__signup.this,junior_second_page_login.class));
            }
        });
    }

}