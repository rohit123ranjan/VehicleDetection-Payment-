package com.example.rohitranjan.vehiclepayment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    String id,email,user_name,user_phone,user_carno;
    EditText userName,userPhone,carNo;
    public static final String FB_DATABASE_PATH = "userInfo";
    public static final String ITEM_EMAIL = "itememail";
    public static final String ITEM_ID = "itemid";
    public static final String ITEM_NAME = "itemname";
    public static final String ITEM_PHONE = "itemphone";
    public static final String ITEM_CARNO = "itemcar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.phoneText);
        carNo = findViewById(R.id.carNumber);


        Intent i = getIntent();
        id = i.getStringExtra(Dashboard.ITEM_ID);
        email = i.getStringExtra(Dashboard.ITEM_EMAIL);

        Toast.makeText(EditProfile.this,id+" this "+email , Toast.LENGTH_SHORT).show();
    }


    public void saveButton(View view) {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH).child(id);
        user_name = userName.getText().toString();
        user_phone = userPhone.getText().toString();
        user_carno = carNo.getText().toString();

        Toast.makeText(this, email + id + user_name + user_phone + user_carno, Toast.LENGTH_LONG).show();
        pojoProfile profile = new pojoProfile(
                email,
                id,
                user_name,
                user_phone,
                user_carno
        );
        mDatabaseRef.setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(EditProfile.this, Dashboard.class);
                    intent.putExtra(EditProfile.ITEM_ID, id);
                    intent.putExtra(EditProfile.ITEM_EMAIL, email);
                    intent.putExtra(EditProfile.ITEM_NAME, user_name);
                    intent.putExtra(EditProfile.ITEM_CARNO, user_carno);
                    intent.putExtra(EditProfile.ITEM_PHONE, user_phone);
                    Toast.makeText(EditProfile.this, user_name, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "try again", Toast.LENGTH_SHORT).show();
                }
                /*Toast.makeText(LoginActivity.this,"Successful!" , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ITEM_ID, userId);
                intent.putExtra(ITEM_EMAIL, email);
                startActivity(intent);*/
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
