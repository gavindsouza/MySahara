package com.sfit.sahara.mysahara;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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
        Button logout = findViewById(R.id.senior_log_out);

        try {
            final SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
            final String contact =data.getString("Contact",null);

            imgbtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    startActivity(i.setData(Uri.parse("tel:" + contact)));
                }
            });

            imgbtnMess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("sms:" + contact));
                    i.putExtra("sms_body", "please help me | sent from MySahara");
                    startActivity(i);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor edit = data.edit();
                    edit.clear();
                    edit.commit();
                    startActivity(new Intent(senior_second_page.this,first_page.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            });
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Something Happened",Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(senior_second_page.this);
        builder.setMessage("Do you really want to exit this application?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                //System.exit(0);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();
    }
}
