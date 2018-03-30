package com.sfit.sahara.mysahara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class junior_second_page_signup extends AppCompatActivity {
    FirebaseFirestore db;
    private EditText fname, lname, contact, username, password, confirm;
    Button signup;
    private String f_name, l_name, contact_num, user, pass, confirm_pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_second_page_signup);

        db = FirebaseFirestore.getInstance();

        fname = findViewById(R.id.etFname);
        lname = findViewById(R.id.etLname);
        contact = findViewById(R.id.etContact);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPass);
        confirm = findViewById(R.id.etConfirmPass);
        signup = findViewById(R.id.btnSignup);

        signup.setEnabled(false);
        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    String pass = password.getText().toString();
                    String confirm_pass = confirm.getText().toString();
                    if (confirm_pass.equals(pass))
                        signup.setEnabled(true);
                }catch (Exception e){}
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register(){
        initialize();
        if (!validate()){
            Toast.makeText(this, "Signup has failed", Toast.LENGTH_SHORT).show();
        }
        else {
            onSignupSuccess();
        }
    }

    public void initialize(){
        f_name = fname.getText().toString().trim();
        l_name = lname.getText().toString().trim();
        contact_num = contact.getText().toString().trim();
        user = username.getText().toString().trim();
        pass = password.getText().toString().trim();
        confirm_pass = confirm.getText().toString().trim();
    }

    public boolean validate(){
        boolean valid = true;
        if (f_name.isEmpty()){
            fname.setError("Please enter valid first name");
            valid=false;
        }
        if (l_name.isEmpty()){
            lname.setError("Please enter valid last name");
            valid=false;
        }
        if (contact_num.isEmpty() || contact_num.length()!=10){
            contact.setError("Please enter a valid mobile number");
            valid=false;
        }
        if (user.isEmpty()){
            username.setError("Please enter a username");
            valid=false;
        }
        if (pass.isEmpty()){
            password.setError("Please enter a password");
            valid=false;
        }
        if (confirm_pass.isEmpty()){
            confirm.setError("Please enter a password");
            valid=false;
        }
        if (!(pass.equals(confirm_pass))){
            confirm.setError("please enter the same passwords at both places");
            valid=false;
        }

        return valid;
    }

    public void onSignupSuccess(){
        Map<String, Object> m = new HashMap<>();
        m.put("First Name", f_name);
        m.put("Last Name", l_name);
        m.put("Contact", contact_num);
        m.put("Username", user);
        m.put("Password", pass);
        m.put("Senior First Name","xabsttc" );
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
                edit.apply();
                startActivity(new Intent(junior_second_page_signup.this,junior_third_page_details.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
        /*signup.setEnabled(false);

        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String pass = password.getText().toString();
                String confirm_pass = confirm.getText().toString();
                String f_name = fname.getText().toString();
                String l_name = lname.getText().toString();
                String contact_num = contact.getText().toString();
                String user = username.getText().toString();

                if (confirm_pass.equals(pass)) {
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
*/

}