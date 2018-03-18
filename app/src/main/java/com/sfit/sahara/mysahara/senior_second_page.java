package com.sfit.sahara.mysahara;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.security.Permission;
import java.util.HashMap;
import java.util.Map;

public class senior_second_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_second_page);
        ImageButton imgbtnCall = findViewById(R.id.imgbtnCall);
        ImageButton imgbtnMess = findViewById(R.id.imgbtnMess);
        Button logout = findViewById(R.id.senior_log_out);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FusedLocationProviderClient locate = LocationServices.getFusedLocationProviderClient(this);
        try {
            final SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
            final String contact = data.getString("Contact", null);
            final String sfname = data.getString("Senior First Name", null);
            final String slname = data.getString("Senior Last Name", null);
            final String user = data.getString("Username", null);
            Toast.makeText(getApplicationContext(), "Hi " + sfname + " " + slname, Toast.LENGTH_LONG);

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
                    startActivity(new Intent(senior_second_page.this, first_page.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            });

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locate.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Map<String, Object> m = new HashMap<>();
                        //m.put("Current Latitude",location.getLatitude());
                        //m.put("Current Longitude",location.getLongitude());
                        m.put("Current", new GeoPoint(location.getLatitude(), location.getLongitude()));
                        db.collection("users").document(user).update(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "location is updated", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
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
