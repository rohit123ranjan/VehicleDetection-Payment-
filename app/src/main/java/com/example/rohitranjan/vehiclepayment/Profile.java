package com.example.rohitranjan.vehiclepayment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    String userN,emailN,carN,phoneN;
    private SharedPreferences idPrivate;
    TextView nameP,emailP,carP,phoneP;

    DatabaseReference rootRef, demoRef;
    FirebaseAuth mAuth;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        nameP = findViewById(R.id.nameP);
        emailP = findViewById(R.id.emailP);
        carP = findViewById(R.id.carP);
        phoneP = findViewById(R.id.phone);

        Intent i = getIntent();
        id = i.getStringExtra(Dashboard.ITEM_ID);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference("userInfo");

        //database reference pointing to demo node
        demoRef = rootRef.child(id);

        demoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pojoProfile value = dataSnapshot.getValue(pojoProfile.class);
                if(value != null) {
                    Toast.makeText(Profile.this, value.getUserName(), Toast.LENGTH_SHORT).show();
                    nameP.setText(value.getUserName());
                    emailP.setText(value.getUserEmail());
                    carP.setText(value.getUserCar());
                    phoneP.setText(value.getUserPhone());
                }else{
                    Toast.makeText(Profile.this, "NO Data found!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        /*idPrivate = getSharedPreferences(LoginActivity.ID_PRIVATE, Context.MODE_PRIVATE);
        userN = idPrivate.getString(LoginActivity.USER_PRIVATE, "NA");
        emailN = idPrivate.getString(LoginActivity.EMAIL_PRIVATE, "NA1");
        carN = idPrivate.getString(LoginActivity.VALUE_PRIVATE, "NA2");

        nameP.setText(userN);
        emailP.setText(emailN);
        carP.setText(carN);*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
