package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button yourButton = (Button) findViewById(R.id.buttons_id);
        yourButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    db=FirebaseFirestore.getInstance();
                    Map<String, Object> m = new HashMap<>();
                    m.put("First Name", "f_name");
                    m.put("Last Name", "l_name");
                    m.put("Contact", "contact_num");
                    m.put("Username", "user");
                    m.put("Password", "pass");

                    Map<String, Object> city = new HashMap<>();
                    city.put("name", "Los Angeles");
                    city.put("state", "CA");
                    city.put("country", "USA");

                    try {
                        db.collection("sahara").document("users").set(m);
                    db.collection("cities").document("LA")
                            .set(city)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });
                    }
                    catch (Exception e){
                        yourButton.setText("Failed");
                    }
                }
            });
    }
}

