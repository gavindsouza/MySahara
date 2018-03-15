package com.sfit.sahara.mysahara;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.Permission;

public class senior_second_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_second_page);


        ImageButton imgbtnCall = findViewById(R.id.imgbtnCall);
        ImageButton imgbtnMess = findViewById(R.id.imgbtnMess);
        Button logout=findViewById(R.id.senior_log_out);

        Intent intent = getIntent();
        final String contact = intent.getStringExtra("contact");

        imgbtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + contact));
                if (ActivityCompat.checkSelfPermission(senior_second_page.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(senior_second_page.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor edit = data.edit();
                    edit.clear();
                    edit.commit();
                    startActivity(new Intent(senior_second_page.this, first_page.class));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Something Happened",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
