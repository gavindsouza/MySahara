package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class junior_second_page__signup extends Activity {
    EditText etFname, etLname, etContact, etUsername, etPass, etConfirmPass;
    Button btnSignup;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page__signup);

        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etContact = findViewById(R.id.etContact);
        etUsername = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}