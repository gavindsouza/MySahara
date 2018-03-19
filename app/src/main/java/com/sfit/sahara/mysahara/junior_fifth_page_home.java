package com.sfit.sahara.mysahara;

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
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class junior_fifth_page_home extends AppCompatActivity {
    public float loc=0; //variable for setting geofence distance
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_fifth_page_home);

        Button notif = findViewById(R.id.notif);
        Button logout = findViewById(R.id.log_out);

        final FusedLocationProviderClient locate= LocationServices.getFusedLocationProviderClient(this);
        FirebaseFirestore db=FirebaseFirestore.getInstance();


        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor edit = data.edit();
                    edit.clear();
                    edit.apply();
                    startActivity(new Intent(junior_fifth_page_home.this, first_page.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Something Happened", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                        GeoPoint home= document.getGeoPoint("Home");
                        GeoPoint current = document.getGeoPoint("Current");
                        Location curr=new Location("A");
                        Location hom=new Location("B");

                        double current_latitude = current.getLatitude();
                        double current_longitude = current.getLongitude();
                        double home_latitude = home.getLatitude();
                        double home_longitude = home.getLongitude();

                        curr.setLongitude(current_longitude);
                        curr.setLatitude(current_latitude);
                        hom.setLatitude(home_latitude);
                        hom.setLongitude(home_longitude);

                        Toast.makeText(getApplicationContext(), "Person is "+(curr.distanceTo(hom))+" metres away from set point",Toast.LENGTH_LONG).show();
                    }
                }
            });
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
