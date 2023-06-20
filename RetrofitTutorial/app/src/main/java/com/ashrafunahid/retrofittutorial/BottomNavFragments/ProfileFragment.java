package com.ashrafunahid.retrofittutorial.BottomNavFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ashrafunahid.retrofittutorial.R;
import com.ashrafunahid.retrofittutorial.ResponseModel.UpdateUserPasswordResponse;
import com.ashrafunahid.retrofittutorial.ResponseModel.UpdateUserProfileResponse;
import com.ashrafunahid.retrofittutorial.RetrofitClient;
import com.ashrafunahid.retrofittutorial.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    EditText userName, userEmail, oldPassword, newPassword;
    Button updateProfileBtn, updatePasswordBtn;

    int id;
    String email;

    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        For update Profile
        userName = (EditText) view.findViewById(R.id.update_user_name);
        userEmail = (EditText) view.findViewById(R.id.update_user_email);
        updateProfileBtn = (Button) view.findViewById(R.id.updateUserInfoBtn);

//        For update Password
        oldPassword = (EditText) view.findViewById(R.id.old_password);
        newPassword = (EditText) view.findViewById(R.id.new_password);
        updatePasswordBtn = (Button) view.findViewById(R.id.updateUserPasswordBtn);

//        Getting user ID and email from Shared Preferences
        sharedPrefManager = new SharedPrefManager(getActivity());
        id = sharedPrefManager.getUser().getId();
        email = sharedPrefManager.getUser().getEmail();

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserPassword();
            }
        });


        return view;
    }

    private void updateUserPassword() {

        String currentPassword = oldPassword.getText().toString();
        String updatePassword = newPassword.getText().toString();

        if(currentPassword.isEmpty()) {
            oldPassword.requestFocus();
            oldPassword.setError("Please enter your old password");
            return;
        }

        if(updatePassword.isEmpty()) {
            newPassword.requestFocus();
            newPassword.setError("Please enter a new password");
            return;
        }

        Call<UpdateUserPasswordResponse> call = RetrofitClient.getInstance().getApi().updateUserPassword(email, currentPassword, updatePassword);

        call.enqueue(new Callback<UpdateUserPasswordResponse>() {
            @Override
            public void onResponse(Call<UpdateUserPasswordResponse> call, Response<UpdateUserPasswordResponse> response) {

                if(response.isSuccessful()) {

                    if(response.body().getError().equals("200")){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateUserPasswordResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUserProfile() {

        String username = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();

        if (username.isEmpty()) {
            userName.requestFocus();
            userName.setError("Please enter you Full Name");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.requestFocus();
            userEmail.setError("Please enter a valid email address");
            return;
        }

        if (email.isEmpty()) {
            userEmail.requestFocus();
            userEmail.setError("Please enter your email address");
            return;
        }

        Call<UpdateUserProfileResponse> call = RetrofitClient.getInstance().getApi().updateUserProfile(id, username, email);

        call.enqueue(new Callback<UpdateUserProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateUserProfileResponse> call, Response<UpdateUserProfileResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getError().equals("200")) {

                        sharedPrefManager.saveUser(response.body().getUser());
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<UpdateUserProfileResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}