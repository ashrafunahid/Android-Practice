package com.ashrafunahid.alertdialogdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button SingleButton, DoubleButton, TripleButton, CustomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingleButton = (Button) findViewById(R.id.single_button);
        DoubleButton = (Button) findViewById(R.id.double_button);
        TripleButton = (Button) findViewById(R.id.triple_button);
        CustomDialog = (Button) findViewById(R.id.custom_dialog);

//        Showing AlertDialog with Single Button
        SingleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                alertDialog.setTitle(R.string.single_button);
                alertDialog.setIcon(R.drawable.baseline_error_24);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Got it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Got it! Button Clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

            }
        });


//        Showing AlertDialog with Double Button
        DoubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);

                alertBuilder.setTitle(R.string.double_button);
                alertBuilder.setIcon(R.drawable.baseline_error_24);
                alertBuilder.setCancelable(false);
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You Clicked Yes Button", Toast.LENGTH_SHORT).show();
                    }
                });
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You Clicked No Button", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

            }
        });


//        Showing AlertDialog with Triple Button
        TripleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);

                alertBuilder.setTitle(R.string.triple_button);
                alertBuilder.setIcon(R.drawable.baseline_error_24);
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You Clicked Yes Button", Toast.LENGTH_SHORT).show();
                    }
                });
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You Clicked No Button", Toast.LENGTH_SHORT).show();
                    }
                });
                alertBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You Clicked Cancel Button", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

            }
        });

//        Showing a Custom Dialog
        CustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCancelable(false);

                Button GotItButton = (Button) dialog.findViewById(R.id.got_it_btn);
                GotItButton.setBackgroundColor(Color.BLUE);
                GotItButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Custom Dialog Box Closed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }
}