package com.ibssbd.gobeautify.BottomNavigationsFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.ibssbd.gobeautify.R;
import com.ibssbd.gobeautify.Utils.CommonUtil;

public class ProfileFragment extends Fragment {

    ConstraintLayout showInfo, editInfo;
    ImageView profilePicture, hideEditButton, showEditButton, pickCalendar;

    EditText editDOB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        showInfo = (ConstraintLayout) view.findViewById(R.id.show_info);
        editInfo = (ConstraintLayout) view.findViewById(R.id.edit_info);
        profilePicture = (ImageView) view.findViewById(R.id.profile_picture);
        hideEditButton = (ImageView) view.findViewById(R.id.hide_edit_button);
        showEditButton = (ImageView) view.findViewById(R.id.show_edit_button);
        pickCalendar = (ImageView) view.findViewById(R.id.pick_calendar);
        editDOB = (EditText) view.findViewById(R.id.edit_dob);

        hideEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo.setVisibility(View.VISIBLE);
                editInfo.setVisibility(View.GONE);
            }
        });

        showEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo.setVisibility(View.GONE);
                editInfo.setVisibility(View.VISIBLE);
            }
        });

        pickCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        return view;
    }

    private void pickDate(){

        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editDOB.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(month+1) + "-" + String.valueOf(year));
            }
        }, 1990, 7, 15);
        dialog.show();
    }

}