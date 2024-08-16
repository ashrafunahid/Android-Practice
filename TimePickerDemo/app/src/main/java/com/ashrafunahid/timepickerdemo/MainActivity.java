package com.ashrafunahid.timepickerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.ashrafunahid.timepickerdemo.Fragments.DatePickerFragment;
import com.ashrafunahid.timepickerdemo.Fragments.TimePickerFragment;
import com.ashrafunahid.timepickerdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TimePickerFragment.OnTimeSelectListener, DatePickerFragment.OnDateSelectListener {

    ActivityMainBinding binding;
    TextView selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectedTime = findViewById(R.id.selected_time);

        binding.btnPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "Pick a Time");

            }
        });

        binding.btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "Pick a Date");

            }
        });

    }

    @Override
    public void getTime(String time) {
        binding.selectedTime.setText("The Picked Time is: " + time);
    }

    @Override
    public void getDate(String date) {
        binding.selectedDate.setText("The Picked Date is: " + date);
    }
}