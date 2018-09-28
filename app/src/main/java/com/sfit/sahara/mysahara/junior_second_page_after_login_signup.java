package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class junior_second_page_after_login_signup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences user = getSharedPreferences("UserData", MODE_PRIVATE);
        //Toast.makeText(getApplicationContext(),user.getString("Username",null),Toast.LENGTH_LONG).show();
        try {
            String temp= user.getString("Senior First Name", null);
            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_LONG).show();
            assert temp != null;
            if (temp.equals("xabsttc"))
                startActivity(new Intent(junior_second_page_after_login_signup.this, junior_third_page_details.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            else {
                startActivity(new Intent(junior_second_page_after_login_signup.this, junior_fifth_page_home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"What just happened? Whatever happened at 'junior_second_page_after_login_signup.java' line 21",Toast.LENGTH_LONG).show();
        }
    }
}
