package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class junior_second_page_login extends Activity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_login);

        Button login = (Button)findViewById(R.id.login);
        Button signup = (Button) findViewById(R.id.signup);
        EditText username=(EditText)findViewById(R.id.username);
        EditText password=(EditText)findViewById(R.id.password);
        final String user=username.getText().toString();
        final String pass=password.getText().toString();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(junior_second_page_login.this,junior_second_page_signup.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db=FirebaseFirestore.getInstance();
                //db.collection("sahara").document("users").collection(user).document("Data").getFirestore();
                startActivity(new Intent(junior_second_page_login.this,junior_second_page_home.class));
            }
        });
    }
}
