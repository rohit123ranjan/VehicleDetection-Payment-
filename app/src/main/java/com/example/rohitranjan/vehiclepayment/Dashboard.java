package com.example.rohitranjan.vehiclepayment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

public class Dashboard extends AppCompatActivity {
    String id,email;
    public static final String ITEM_EMAIL = "itememail1";
    public static final String ITEM_ID = "itemid1";
    public static final String ID_PRIVATE = "ID_PRIVATE";
    public static final String KEY_PRIVATE = "KEY_PRIVATE";
    public static final String EMAIL_PRIVATE = "EMAIL_PRIVATE";
    private SharedPreferences idPrivate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        id = i.getStringExtra(LoginActivity.ITEM_ID);
        email = i.getStringExtra(LoginActivity.ITEM_EMAIL);
        idPrivate= getSharedPreferences(ID_PRIVATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor idEdit = idPrivate.edit();
        idEdit.putString(KEY_PRIVATE, id);
        idEdit.putString(EMAIL_PRIVATE, email);
        idEdit.apply();

        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();
        ((TextView) findViewById(R.id.home_click)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,Dashboard.class);
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.myOrder_click)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, EditProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Dashboard.ITEM_ID, id);
                intent.putExtra(Dashboard.ITEM_EMAIL, email);
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.logOut)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.ID_PRIVATE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Dashboard.this,LoginActivity.class));
            }
        });
    }

    public void profilePage(View view){
        Intent intent = new Intent(Dashboard.this, Profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Dashboard.ITEM_ID, id);
        intent.putExtra(Dashboard.ITEM_EMAIL, email);
        startActivity(intent);
    }

    public void mapsActivity(View view){
        Intent intent = new Intent(Dashboard.this,MapsActivity.class);
        startActivity(intent);
    }
}
