package com.ashrafunahid.retrofittutorial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashrafunahid.retrofittutorial.BottomNavFragments.DashboardFragment;
import com.ashrafunahid.retrofittutorial.BottomNavFragments.ProfileFragment;
import com.ashrafunahid.retrofittutorial.BottomNavFragments.UsersFragment;
import com.ashrafunahid.retrofittutorial.R;
import com.ashrafunahid.retrofittutorial.ResponseModel.DeleteAccountResponse;
import com.ashrafunahid.retrofittutorial.RetrofitClient;
import com.ashrafunahid.retrofittutorial.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());
        
        sharedPrefManager = new SharedPrefManager(getApplicationContext());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        if (item.getItemId() == R.id.dashboard) {
            fragment = new DashboardFragment();
        } else if (item.getItemId() == R.id.users) {
            fragment = new UsersFragment();
        } else if (item.getItemId() == R.id.profile) {
            fragment = new ProfileFragment();
        }

        if (fragment != null) {
            loadFragment(fragment);
        }

        return true;
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout){
            logout();
        }
        else if(item.getItemId() == R.id.delete_account){
            deleteAccount();
        }
        
        return super.onOptionsItemSelected(item);
        
    }

    private void deleteAccount() {

        Call<DeleteAccountResponse> call = RetrofitClient.getInstance().getApi().deleteUserAccount(sharedPrefManager.getUser().getId());

        call.enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response) {

                if(response.isSuccessful()) {

                    if(response.body().getError().equals("200")){

                        logout();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    else {

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

                else {

                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void logout() {
        sharedPrefManager.logout();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "You're logged out!", Toast.LENGTH_SHORT).show();
    }
}