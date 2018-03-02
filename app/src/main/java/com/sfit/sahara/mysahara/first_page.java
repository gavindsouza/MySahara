package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class first_page extends AppCompatActivity {

    Button btnAdd;
    EditText etAddCode;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        btnAdd = findViewById(R.id.btnAdd);
        etAddCode = findViewById(R.id.etAddCode);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(first_page.this,details.class);
                startActivity(i);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = etAddCode.getText().toString();

                //check if code is matching with code in database
                if (true){
                    Intent i = new Intent(first_page.this,third_page_senior.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(first_page.this, "Please enter the correct code", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
