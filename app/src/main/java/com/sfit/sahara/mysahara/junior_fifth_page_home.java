package com.sfit.sahara.mysahara;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

        Button notif = findViewById(R.id.notif);
        Button logout = (Button) findViewById(R.id.log_out);
        TextView text = (TextView) findViewById(R.id.online_status);
        Boolean online = true;

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

        if (online)
            text.setText("Current Status: online");
        else
            text.setText("Current Status: offline");

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification.Builder builder = new Notification.Builder(junior_fifth_page_home.this);
                Intent intent = new Intent(getApplicationContext(),junior_fifth_page_home.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(junior_fifth_page_home.this, 01, intent,0);
                builder.setContentIntent(pendingIntent);
                builder.setDefaults(Notification.DEFAULT_ALL);
                builder.setContentTitle("User has left geofence");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentText("check on your loved one");
                builder.setAutoCancel(true);
                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(001, builder.build());
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
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();
    }

}
