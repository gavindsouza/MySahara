package com.sfit.sahara.mysahara;

import android.app.Notification;
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
   NotificationHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_fifth_page_home);
        helper = new NotificationHelper(this);
        Button notif = findViewById(R.id.notif);
        Button logout = (Button) findViewById(R.id.log_out);
        TextView text = (TextView) findViewById(R.id.online_status);
        Integer online = 1;

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor edit = data.edit();
                    edit.clear();
                    edit.commit();
                    startActivity(new Intent(junior_fifth_page_home.this, first_page.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Something Happened", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (online == 1)
            text.setText("Current Status: online");
        else
            text.setText("Current Status: offline");

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.notificationtext);
                String title = getString(R.string.notificationtitle);
                Notification.Builder builder = helper.getsfitchannelnotification(title,text);
                helper.getManager().notify(0,builder.build());
            }
        });

    }





    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application?");
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
