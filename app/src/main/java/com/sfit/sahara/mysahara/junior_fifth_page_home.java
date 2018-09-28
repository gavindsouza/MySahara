package com.sfit.sahara.mysahara;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class junior_fifth_page_home extends AppCompatActivity implements OnMapReadyCallback {
    int g;//variable for setting geofence distance
    FusedLocationProviderClient locate;
    LocationRequest mLocationRequest;
    boolean mRequestingLocationUpdates = true;
    LocationCallback mLocationCallback;
    public double current_latitude,current_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_fifth_page_home);

        locate = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100000);
        mLocationRequest.setFastestInterval(50000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Button logout = findViewById(R.id.log_out);

       final FirebaseFirestore db=FirebaseFirestore.getInstance();
       final WebView web = findViewById(R.id.webview);

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

      try {
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        final SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
                        final String username = data.getString("Username", null);
                        final String codes = data.getString("Code", null);

                        db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    final GeoPoint home = document.getGeoPoint("Home");
                                    final GeoPoint current = document.getGeoPoint("Current");
                                    final Location curr = new Location("A");
                                    final Location hom = new Location("B");
                                    final String ge = document.getString("Geofence");
                                    try {

                                        current_latitude = current.getLatitude();
                                        current_longitude = current.getLongitude();
                                        double home_latitude = home.getLatitude();
                                        double home_longitude = home.getLongitude();

                                        curr.setLongitude(current_longitude);
                                        curr.setLatitude(current_latitude);
                                        hom.setLatitude(home_latitude);
                                        hom.setLongitude(home_longitude);
                                        g = Integer.parseInt(ge);


                                        final Button address = findViewById(R.id.address);
                                        address.setEnabled(true);
                                        //address.setText("Latitude:"+Double.toString(current_latitude)+"\nLongitude:"+Double.toString(current_longitude));

                                        address.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.ENGLISH,"geo:%f,%f",current_latitude,current_longitude))));
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query="+Double.toString(current_latitude)+","+Double.toString(current_longitude))));
                                            }
                                        });
                                        WebSettings setting = web.getSettings();
                                        setting.setJavaScriptEnabled(true);
                                        web.loadUrl("https://www.google.com/maps/search/?api=1&query="+Double.toString(current_latitude)+","+Double.toString(current_longitude));
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Add generated Code in Senior's Side", Toast.LENGTH_LONG).show();
                                    }
                                    TextView code = findViewById(R.id.code);
                                    code.setText(new StringBuilder().append("Code:").append(codes).toString());

                                    Toast.makeText(getApplicationContext(), "Person is " + (curr.distanceTo(hom)) + " metres away from set point", Toast.LENGTH_LONG).show();
                                    if (curr.distanceTo(hom) > g) {
                                        Notification.Builder builder = new Notification.Builder(junior_fifth_page_home.this);
                                        Intent intent = new Intent(getApplicationContext(), junior_fifth_page_home.class);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(junior_fifth_page_home.this, 01, intent, 0);
                                        builder.setContentIntent(pendingIntent);
                                        builder.setDefaults(Notification.DEFAULT_ALL);
                                        builder.setContentTitle("User has left geofence\nThey are "+curr.distanceTo(hom)+" metres from home");
                                        builder.setSmallIcon(R.mipmap.ic_launcher);
                                        builder.setContentText("check on your loved one");
                                        builder.setAutoCancel(true);
                                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                        notificationManager.notify(001, builder.build());
                                    }
                                }
                            }
                        });
                    }
                }
            };
        }catch (Exception e){} /*outermost try ends here*/


    } //oncreate ends here

    @Override
    protected void onResume() {
        super.onResume();
        mRequestingLocationUpdates = true;
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRequestingLocationUpdates = false;
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        locate.removeLocationUpdates(mLocationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locate.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
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

    @Override
    public void onMapReady(GoogleMap map) {
        //map.addMarker(new MarkerOptions().position(new LatLng(21, 72)).title("Marker"));
        LatLng latLng = new LatLng(21.11,72.11);
        map.addMarker(new MarkerOptions().position(latLng)).isVisible();

        float zoomLevel = 16.0f; //This goes up to 21

        map.addMarker(new MarkerOptions().position(new LatLng(current_latitude, current_longitude)).title("Marker"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(junior_fifth_page_home.this);
    }
}
