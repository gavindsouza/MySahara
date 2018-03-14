package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class senior_second_page extends AppCompatActivity {

    ImageButton imgbtnCall;
    ImageButton imgbtnMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_second_page);

        imgbtnCall = findViewById(R.id.imgbtnCall);
        imgbtnMess = findViewById(R.id.imgbtnMess);

        Intent intent = getIntent();
        final String contact = intent.getStringExtra("contact");

        imgbtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        imgbtnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:" + contact));
                i.putExtra("sms_body","please help me");
                startActivity(i);
            }
        });
    }
}
