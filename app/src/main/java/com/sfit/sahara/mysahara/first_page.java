package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

public class first_page extends AppCompatActivity {

    EditText etAddCode;
    Button btnSubmit;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        Button btnlogin = findViewById(R.id.btnlogin);
        Button btnsignup = findViewById(R.id.btnsignup);
        etAddCode = findViewById(R.id.etAddCode);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(first_page.this,junior_second_page_signup.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(first_page.this,junior_second_page_login.class);
                startActivity(i);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String code = etAddCode.getText().toString();
                //final int flag;
                CollectionReference usersRef = db.collection("users");
                //check if code is matching with code in database)
                usersRef.whereEqualTo("Code", code).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String sfname = document.getString("Senior First Name");
                                String slname = document.getString("Senior Last Name");
                                String contact = document.getString("Contact");
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                Intent i = new Intent(first_page.this,senior_first_page.class);
                                i.putExtra("sfname",sfname);
                                i.putExtra("slname",slname);
                                i.putExtra("contact",contact);
                                startActivity(i);
                                break;
                            }
                        } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        });

    }
}
