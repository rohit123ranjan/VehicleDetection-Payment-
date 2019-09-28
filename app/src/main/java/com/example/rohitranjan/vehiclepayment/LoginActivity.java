package com.example.rohitranjan.vehiclepayment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    String user_id,user_name,user_email,user_carno;
    public static final String ITEM_EMAIL = "itememail";
    public static final String ITEM_ID = "itemid";
    public static final String ID_PRIVATE = "ID_PRIVATE";
    public static final String KEY_PRIVATE = "KEY_PRIVATE";
    public static final String USER_PRIVATE = "USER_PRIVATE";
    public static final String EMAIL_PRIVATE = "EMAIL_PRIVATE";
    public static final String VALUE_PRIVATE = "VALUE_PRIVATE";
    private SharedPreferences idPrivate;
    DatabaseReference databaseReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.emailLogin);
        editTextPassword = findViewById(R.id.passLogin);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.signUp_activity).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");



        idPrivate= getSharedPreferences(ID_PRIVATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor idEdit = idPrivate.edit();
        idEdit.putString(KEY_PRIVATE, user_id);
        idEdit.putString(USER_PRIVATE, user_name);
        idEdit.putString(EMAIL_PRIVATE, user_email);
        idEdit.putString(VALUE_PRIVATE, user_carno);
        idEdit.apply();

    }

    private void userLogin(){
        final String email = editTextEmail.getText().toString().trim();
        String password= editTextPassword.getText().toString().trim();

        if (email.isEmpty()||(!Patterns.EMAIL_ADDRESS.matcher(email).matches())||password.isEmpty()||(password.length()<6)){
            if(email.isEmpty()){
                editTextEmail.setError("Email is Required");
                editTextEmail.requestFocus();
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please enter a valid email");
                editTextEmail.requestFocus();

            }

            if (password.isEmpty()){
                editTextPassword.setError("Password is Required");
                editTextPassword.requestFocus();

            }

            if (password.length()<6){
                editTextPassword.setError("Minimum length of password should be 6");
                editTextPassword.requestFocus();

            }
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        if(editTextEmail.getText().toString().equals("admin@gmail.com")){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()){

                        FirebaseUser user1 = mAuth.getCurrentUser();
                        if (user1 != null) {
                            userId = user1.getUid();
                        }

                        User user = new User(
                                email,
                                userId
                        );
                        databaseReference.child(userId)
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    finish();
                                    Toast.makeText(LoginActivity.this,"Successful!" , Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                    intent.putExtra(ITEM_ID, userId);
                                    intent.putExtra(ITEM_EMAIL, email);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        FirebaseUser user1 = mAuth.getCurrentUser();
                        if (user1 != null) {
                            userId = user1.getUid();
                        }

                        User user = new User(
                                email,
                                userId
                        );
                        databaseReference.child(userId)
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                                Toast.makeText(LoginActivity.this,"Successful!" , Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(ITEM_ID, userId);
                                intent.putExtra(ITEM_EMAIL, email);

                                startActivity(intent);
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        /*if(user != null){
            if(user.getEmail().equals("admin@gmail.com")){
                finish();
                startActivity(new Intent(this, Dashboard.class));
            }
            else{
                finish();
                startActivity(new Intent(this, Dashboard.class));
            }
        }*/
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUp_activity:
                finish();
                startActivity(new Intent(this, SignupActivity.class));
                break;

            case R.id.loginButton:
                userLogin();
                break;
        }
    }


}
