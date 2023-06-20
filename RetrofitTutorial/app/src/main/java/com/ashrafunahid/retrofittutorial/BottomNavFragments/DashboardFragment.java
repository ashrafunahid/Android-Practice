package com.ashrafunahid.retrofittutorial.BottomNavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashrafunahid.retrofittutorial.R;
import com.ashrafunahid.retrofittutorial.SharedPrefManager;

public class DashboardFragment extends Fragment {

    TextView userName, userEmail;
    SharedPrefManager sharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        userName = (TextView) view.findViewById(R.id.user_name);
        userEmail = (TextView) view.findViewById(R.id.user_email);

        sharedPrefManager = new SharedPrefManager(getActivity());

        userName.setText("Hello! " + sharedPrefManager.getUser().getUsername());
        userEmail.setText(sharedPrefManager.getUser().getEmail());

        return view;
    }
}