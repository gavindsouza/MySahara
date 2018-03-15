package com.sfit.sahara.mysahara;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class junior_fifth_page_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_fifth_page_home);
        Button exit = (Button) findViewById(R.id.log_out);
        TextView text = (TextView) findViewById(R.id.online_status);
        Integer online = 1;
        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //onBackPressed();
                AlertDialog.Builder builder = new AlertDialog.Builder(junior_fifth_page_home.this);
                builder.setMessage("Do you want to exit this application?");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor edit = data.edit();
                            edit.clear();
                            edit.commit();
                            //startActivity(new Intent(senior_second_page.this, first_page.class));
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Something Happened",Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        System.exit(0);
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

        });
        if (online==1)
            text.setText("Current Status: online");
        else
            text.setText("Current Status: offline");
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
                    //SharedPreferences.Editor edit = data.edit();
                    //edit.clear();
                    //edit.commit();
                    startActivity(new Intent(junior_fifth_page_home.this, first_page.class));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Something Happened",Toast.LENGTH_SHORT).show();
                }
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
