package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class junior_second_page_signup extends Activity {
    FirebaseFirestore db;
    final EditText fname = (EditText) findViewById(R.id.etFname);
    final EditText lname = (EditText) findViewById(R.id.etLname);
    final EditText contact = (EditText) findViewById(R.id.etContact);
    final EditText username = (EditText) findViewById(R.id.etUsername);
    final EditText password = (EditText) findViewById(R.id.etPass);
    final EditText confirm = (EditText) findViewById(R.id.etConfirmPass);
    final Button signup =(Button) findViewById(R.id.btnSignup);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_signup);

        final String f_name = fname.getText().toString();
        final String l_name = lname.getText().toString();
        final String contact_num = contact.getText().toString();
        final String user = username.getText().toString();
        final String pass = password.getText().toString();
        final String confirm_pass = confirm.getText().toString();

        signup.setEnabled(false);
        //if((f_name!=null)&&(contact_num!=null)&&(username!=null)&&(password!=null)&&(confirm!=null))

        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                    if(pass.equals(confirm_pass))
                        signup.setEnabled(true);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> m=new HashMap<>();
                m.put("First Name",f_name);
                m.put("Last Name",l_name);
                m.put("Contact",contact_num);
                m.put("Username",user);
                m.put("Password",pass);
                db.collection("sahara").document("users").set(m);
            }
        });
    }
}