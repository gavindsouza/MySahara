package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class junior_second_page_login extends Activity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_login);

        final Button login = (Button)findViewById(R.id.login);
        final EditText username=(EditText)findViewById(R.id.or);
        final EditText password=(EditText)findViewById(R.id.password);
        login.setEnabled(false);

        password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if ((!username.getText().toString().isEmpty()) && (!password.getText().toString().isEmpty()))
                        login.setEnabled(true);
                }
            });
                    //.setEnabled(true);

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
                                    Toast.makeText(getApplicationContext(),"Incorrect Password",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No such user",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "get failed with "+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
