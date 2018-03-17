package com.sfit.sahara.mysahara;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class junior_second_page_signup extends Activity {
    FirebaseFirestore db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_signup);

        db=FirebaseFirestore.getInstance();

        final EditText fname = (EditText) findViewById(R.id.etFname);
        final EditText lname = (EditText) findViewById(R.id.etLname);
        final EditText contact = (EditText) findViewById(R.id.etContact);
        final EditText username = (EditText) findViewById(R.id.etUsername);
        final EditText password = (EditText) findViewById(R.id.etPass);
        final EditText confirm = (EditText) findViewById(R.id.etConfirmPass);
        final Button signup =(Button) findViewById(R.id.btnSignup);

        signup.setEnabled(false);

        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                final String pass = password.getText().toString();
                final String confirm_pass = confirm.getText().toString();
                final String f_name = fname.getText().toString();
                final String l_name = lname.getText().toString();
                final String contact_num = contact.getText().toString();
                final String user = username.getText().toString();

                if (pass.equals(confirm_pass)) { /*&&(!f_name.isEmpty())&&(!contact_num.isEmpty())&&(!user.isEmpty())&&(!pass.isEmpty())&&(!confirm_pass.isEmpty())*/
                    signup.setEnabled(true);
                    signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> m = new HashMap<>();
                            m.put("First Name", f_name);
                            m.put("Last Name", l_name);
                            m.put("Contact", contact_num);
                            m.put("Username", user);
                            m.put("Password", pass);
                            m.put("Senior First Name","" );
                            m.put("Senior Last Name","" );
                            m.put("Code", "");
                            db.collection("users").document(user).set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast success = Toast.makeText(getApplicationContext(),"Account Created, Login now",Toast.LENGTH_LONG);
                                    success.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
                                    success.show();
                                    SharedPreferences userdata = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = userdata.edit();
                                    edit.putString("Username",user);
                                    edit.putString("Password",pass);
                                    edit.putString("Contact",contact_num);
                                    edit.putString("First Name",f_name);
                                    edit.putString("Last Name",l_name);
                                    edit.putString("Senior First Name","xabsttc");
                                    edit.putString("Senior Last Name","");
                                    edit.commit();
                                    startActivity(new Intent(junior_second_page_signup.this,junior_second_page_login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast failure = Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_SHORT);
                                    failure.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
                                    failure.show();
                                }
                            });
                        }
                    });
                }
                else
                    Toast.makeText(junior_second_page_signup.this, "Please enter same password in both the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}