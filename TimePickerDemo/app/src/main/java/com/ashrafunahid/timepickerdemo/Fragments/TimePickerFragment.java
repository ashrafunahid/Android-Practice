package com.ashrafunahid.timepickerdemo.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ashrafunahid.timepickerdemo.MainActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public interface OnTimeSelectListener {
        void getTime(String time);
    }
    private OnTimeSelectListener onTimeSelectListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), TimePickerDialog.THEME_HOLO_DARK, this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        onTimeSelectListener.getTime(timePicker.getHour() + ":" + timePicker.getMinute());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onTimeSelectListener = (OnTimeSelectListener) getActivity();
        } catch (Exception e) {
            Log.e("TAG", "onAttach: " + e.getMessage());
        }
    }

}
