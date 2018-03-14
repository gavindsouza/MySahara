package com.sfit.sahara.mysahara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;



public class junior_third_page_details extends AppCompatActivity {
    private final int REQUEST_CODE_PLACEPICKER = 1;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String code,locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_third_page_details);
        final Button generate=findViewById(R.id.gen_code);
        Button confirm=findViewById(R.id.confirm);
        final EditText fname=findViewById(R.id.fname);
        final EditText lname=findViewById(R.id.lname);
        Button go = findViewById(R.id.show_loc);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = Integer.toString(code_gen());
                generate.setText(code);
                try {
                    final String f_name=fname.getText().toString();
                    final String l_name=lname.getText().toString();
                    SharedPreferences user = getSharedPreferences("UserData", MODE_PRIVATE);
                    String username = user.getString("Username", null);
                    Map<String, Object> m = new HashMap<>();
                    m.put("Code", code);
                    m.put("Senior First Name",f_name);
                    m.put("Senior Last Name",l_name);
                    db.collection("users").document(username).update(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Name and Code UPDATED", Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e){}
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(junior_third_page_details.this,junior_fifth_page_home.class));
            }
        });
    }////

    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = builder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(this,data);

        String name = placeSelected.getName().toString();
        String address = placeSelected.getAddress().toString();

        TextView enterCurrentLocation =  findViewById(R.id.txt);
        enterCurrentLocation.setText(name + ", " + address);
    }

    public int code_gen(){
        final int random = new Random().nextInt(9999) + 20;
        db.collection("users").whereEqualTo("Code", random).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    code_gen();
            }
        });
        return random;
    }
}
