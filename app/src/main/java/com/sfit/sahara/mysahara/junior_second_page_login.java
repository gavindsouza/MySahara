package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class junior_second_page_login extends Activity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_login);

        Button login = (Button)findViewById(R.id.login);
        Button signup = (Button) findViewById(R.id.signup);
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(junior_second_page_login.this,junior_second_page_signup.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user=username.getText().toString();
                final String pass=password.getText().toString();
                DocumentReference ref =db.collection("users").document(user);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                if(document.get("Password").equals(pass)) {
                                    startActivity(new Intent(junior_second_page_login.this, junior_second_page_home.class));
                                }else
                                    Log.d(TAG,"Incorrect Password");
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }
}
