package com.ashrafunahid.eshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashrafunahid.eshop.MainHomeActivity;
import com.ashrafunahid.eshop.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    LinearLayout Login, Registration;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        Login = (LinearLayout) findViewById(R.id.main_login_button);
        Registration = (LinearLayout) findViewById(R.id.main_register_button);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            Toast.makeText(this, "You are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }
}