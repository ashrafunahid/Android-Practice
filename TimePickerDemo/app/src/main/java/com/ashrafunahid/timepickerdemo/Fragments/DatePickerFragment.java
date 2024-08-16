package com.ashrafunahid.timepickerdemo.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface OnDateSelectListener {
        void getDate(String date);
    }
    OnDateSelectListener onDateSelectListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        onDateSelectListener.getDate(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" +datePicker.getYear());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onDateSelectListener = (OnDateSelectListener) getActivity();
        } catch (Exception e) {
            Log.e("TAG", "onAttach: " + e.getMessage());
        }
    }
}
