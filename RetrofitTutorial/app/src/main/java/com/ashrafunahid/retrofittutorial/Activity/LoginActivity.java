package com.ashrafunahid.retrofittutorial.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.retrofittutorial.R;
import com.ashrafunahid.retrofittutorial.ResponseModel.LoginResponse;
import com.ashrafunahid.retrofittutorial.RetrofitClient;
import com.ashrafunahid.retrofittutorial.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginBtn;
    TextView registerBtn;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Hide action bar
        getSupportActionBar().hide();

//        Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        registerBtn = (TextView) findViewById(R.id.register_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    private void userLogin() {

        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if(email.isEmpty())
        {
            loginEmail.requestFocus();
            loginEmail.setError("Please enter your email address");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            loginEmail.requestFocus();
            loginEmail.setError("Please enter a valid email address");
            return;
        }
        if(password.isEmpty())
        {
            loginPassword.requestFocus();
            loginPassword.setError("Please enter your password");
            return;
        }

        Call<LoginResponse> request = RetrofitClient.getInstance().getApi().login(email, password);

        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();

                if(response.isSuccessful())
                {

                    if(loginResponse.getError().equals("200")){

                        sharedPrefManager.saveUser(loginResponse.getUser());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }

                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sharedPrefManager.isLoggedIn()) {

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }

}