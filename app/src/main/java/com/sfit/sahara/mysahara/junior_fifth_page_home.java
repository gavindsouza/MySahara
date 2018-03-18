package com.sfit.sahara.mysahara;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class junior_fifth_page_home extends AppCompatActivity {
    //public double slat=0,slong=0,elat=0,elong=0;
    public float loc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_fifth_page_home);

        Button notif = findViewById(R.id.notif);
        Button logout = (Button) findViewById(R.id.log_out);
        TextView text = (TextView) findViewById(R.id.online_status);
        Boolean online = true;
        final FusedLocationProviderClient locate= LocationServices.getFusedLocationProviderClient(this);
        FirebaseFirestore db=FirebaseFirestore.getInstance();


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

        try{
            final SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
            final String username =data.getString("Username",null);

            db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        final double current_latitude = document.getDouble("Current Latitude");
                        final double current_longitude = document.getDouble("Current Longitude");
                        final double home_latitude = document.getDouble("Home Longitude");
                        final double home_longitude = document.getDouble("Home Latitude");

                        Toast.makeText(getApplicationContext(), (String.valueOf(current_latitude)+String.valueOf(current_longitude)),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), (String.valueOf(home_latitude)+String.valueOf(home_longitude)),Toast.LENGTH_LONG).show();

                        Location locationA = new Location("A");
                        locationA.setLatitude(current_latitude);
                        locationA.setLongitude(current_longitude);
                        Location locationB = new Location("B");
                        locationB.setLatitude(home_latitude);
                        locationB.setLongitude(home_longitude);
                        loc= locationB.distanceTo(locationA) /1000000;
                    }

                    //if(loc>50)    //50 is the geofence
                    Toast.makeText(getApplicationContext(), "Person is "+(String.valueOf(loc))+" away from set point",Toast.LENGTH_LONG).show();
                }
            });

            //Location.distanceBetween(slat, slong, elat, elong);
        }catch (Exception e){}
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
