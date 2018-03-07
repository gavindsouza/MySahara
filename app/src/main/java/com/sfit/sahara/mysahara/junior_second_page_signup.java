package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class junior_second_page_signup extends Activity {
    EditText etFname, etLname, etContact, etUsername, etPass, etConfirmPass;
    Button btnSignup =(Button) findViewById(R.id.btnSignup);
    FirebaseFirestore db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_signup);

        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etContact = findViewById(R.id.etContact);
        etUsername = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);

       if((etFname!=null)&&(etContact!=null)&&(etUsername!=null)&&(etPass!=null)&&(etConfirmPass!=null))
            btnSignup.setEnabled(true);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> m=new HashMap<>();
                m.put("First Name",etFname);
                m.put("Last Name",etLname);
                m.put("First Name",etFname);

                db.collection("sahara").document("users").set(m);
            }
        });

    }
}