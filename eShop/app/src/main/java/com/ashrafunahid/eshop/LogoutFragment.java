package com.ashrafunahid.eshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ashrafunahid.eshop.activities.LoginActivity;
import com.ashrafunahid.eshop.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogoutFragment extends Fragment {

    FirebaseAuth auth;
    Button noBtn, yesBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        auth = FirebaseAuth.getInstance();
        noBtn = root.findViewById(R.id.not_logout);
        yesBtn = root.findViewById(R.id.yes_logout);

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainHomeActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}