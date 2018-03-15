package com.sfit.sahara.mysahara;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;
import static android.content.SharedPreferences.*;

public class first_page extends AppCompatActivity {

    EditText etAddCode;
    Button btnSubmit;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        final SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);

        Button btnlogin = findViewById(R.id.btnlogin);
        Button btnsignup = findViewById(R.id.btnsignup);
        etAddCode = findViewById(R.id.etAddCode);
        btnSubmit = findViewById(R.id.btnSubmit);

        try {
            Toast.makeText(getApplicationContext(), data.getString("Code", null), Toast.LENGTH_LONG).show();
            if (!data.getString("Code", null).isEmpty()||data.getString("Code", null)=="")
                startActivity(new Intent(first_page.this, senior_second_page.class));
        } catch (Exception e) {

            btnsignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(first_page.this, junior_second_page_signup.class));
                }
            });

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(first_page.this, junior_second_page_login.class));
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(first_page.this, "This might take a while", Toast.LENGTH_SHORT).show();
                    final String code = etAddCode.getText().toString();
                    final Editor edit = data.edit();
                    //final int flag;
                    CollectionReference usersRef = db.collection("users");
                    //check if code is matching with code in database)
                    usersRef.whereEqualTo("Code", code).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    String sfname = document.getString("Senior First Name");
                                    String slname = document.getString("Senior Last Name");
                                    String contact = document.getString("Contact");
                                    edit.putString("Username", document.getString("Username"));
                                    edit.putString("Password", document.getString("Password"));
                                    edit.putString("Code",document.getString("Code"));
                                    edit.putString("Contact", contact);
                                    edit.putString("Senior First Name", sfname);
                                    edit.putString("Senior Last Name", slname);
                                    edit.commit();
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    Intent i = new Intent(first_page.this, senior_first_page.class);
                                    //i.putExtra("sfname",sfname);
                                    //i.putExtra("slname",slname);
                                    //i.putExtra("contact",contact);
                                    startActivity(i);
                                    //finish();
                                    break;
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(first_page.this, "Please enter the correct code", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
        if ((ActivityCompat.checkSelfPermission(first_page.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)||(ActivityCompat.checkSelfPermission(first_page.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)||(ActivityCompat.checkSelfPermission(first_page.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(first_page.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }
    }

    /*public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    SharedPreferences data = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor edit = data.edit();
                    edit.clear();
                    edit.commit();
                    //startActivity(new Intent(senior_second_page.this, first_page.class));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Something Happened",Toast.LENGTH_SHORT).show();
                }
                finish();
                System.exit(0);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();
    }
*/
}

