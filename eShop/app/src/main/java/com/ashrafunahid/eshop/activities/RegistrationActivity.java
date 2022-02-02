package com.ashrafunahid.eshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.eshop.MainHomeActivity;
import com.ashrafunahid.eshop.R;
import com.ashrafunahid.eshop.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    Button SignUp;
    TextView SignIn;
    EditText RegName, RegEmail, RegPassword;
//    ProgressBar progressBar;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

//        progressBar = (ProgressBar) findViewById(R.id.progessBar);
//        progressBar.setVisibility(View.GONE);

        SignUp = (Button) findViewById(R.id.sign_up);
        SignIn = (TextView) findViewById(R.id.sign_in);
        RegName = (EditText) findViewById(R.id.reg_name);
        RegEmail = (EditText) findViewById(R.id.reg_email);
        RegPassword = (EditText) findViewById(R.id.reg_password);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, MainHomeActivity.class));
            finish();
        }

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
//                progressBar.setVisibility(View.VISIBLE);
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });


    }

    private void createUser() {
        String userName = RegName.getText().toString();
        String userEmail = RegEmail.getText().toString();
        String userPassword = RegPassword.getText().toString();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, MainHomeActivity.class));
//            Toast.makeText(this, "You are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Name is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 8) {
            Toast.makeText(this, "Password must be minimum 8 character long.", Toast.LENGTH_SHORT).show();
            return;
        }

//        Create New user

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            UserModel userModel = new UserModel(userName, userEmail, userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
//                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(RegistrationActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainHomeActivity.class));
                            finish();
                        } else {
//                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}