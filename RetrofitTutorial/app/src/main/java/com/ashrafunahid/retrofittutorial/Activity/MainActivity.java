package com.ashrafunahid.retrofittutorial.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.retrofittutorial.R;
import com.ashrafunahid.retrofittutorial.ResponseModel.RegisterResponse;
import com.ashrafunahid.retrofittutorial.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    EditText fullName, emailAddress, userPassword;
    Button registerBtn;
    TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Hide action bar
        getSupportActionBar().hide();
//
//        Hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fullName = (EditText) findViewById(R.id.full_name);
        emailAddress = (EditText) findViewById(R.id.email_address);
        userPassword  = (EditText) findViewById(R.id.password);
        registerBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (TextView) findViewById(R.id.login_btn);
        
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {

        String username = fullName.getText().toString();
        String email = emailAddress.getText().toString();
        String password = userPassword.getText().toString();

        if(username.isEmpty())
        {
            fullName.requestFocus();
            fullName.setError("Please enter your full name");
            return;
        }
        if(email.isEmpty())
        {
            emailAddress.requestFocus();
            emailAddress.setError("Please enter your email address");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailAddress.requestFocus();
            emailAddress.setError("Please enter a valid email address");
            return;
        }
        if(password.isEmpty())
        {
            userPassword.requestFocus();
            userPassword.setError("Please enter a new password");
            return;
        }
        if (password.length() < 4)
        {
            userPassword.requestFocus();
            userPassword.setError("Password must be at least 4 characters");
            return;
        }

        Call<RegisterResponse> request = RetrofitClient
                .getInstance()
                .getApi()
                .register(username, email, password);

        request.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful())
                {
                    Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}