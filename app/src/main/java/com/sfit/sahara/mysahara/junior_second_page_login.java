package com.sfit.sahara.mysahara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class junior_second_page_login extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_login);

        final Button login = (Button)findViewById(R.id.login);
        final EditText username=(EditText)findViewById(R.id.or);
        final EditText password=(EditText)findViewById(R.id.password);
        login.setEnabled(false);
        try {
            SharedPreferences user = getSharedPreferences("UserData", MODE_PRIVATE);
            String um = user.getString("Username", null);
            String pass = user.getString("Password", null);
            if (!um.isEmpty()) {
                startActivity(new Intent(junior_second_page_login.this, junior_second_page_after_login_signup.class));
                finish();
            }
                //Toast.makeText(getApplicationContext(), um + pass, Toast.LENGTH_LONG).show();
        }catch (Exception e){}

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

                                    SharedPreferences userdata = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = userdata.edit();
                                    edit.putString("Username",document.getString("Username"));
                                    edit.putString("Password",document.getString("Password"));
                                    edit.putString("Contact",document.getString("Contact"));
                                    edit.putString("Senior First Name",document.getString("Senior First Name"));
                                    edit.putString("Senior Last Name",document.getString("Senior Last Name"));
                                    edit.putString("Code",document.getString("Code"));
                                    edit.commit();
                                    //String a =userdata.getString("Username",null);
                                    //Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(junior_second_page_login.this, junior_second_page_after_login_signup.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    finish();
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
