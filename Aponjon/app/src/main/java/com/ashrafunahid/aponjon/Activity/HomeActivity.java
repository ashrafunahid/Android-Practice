package com.ashrafunahid.aponjon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ashrafunahid.aponjon.R;
import com.ashrafunahid.aponjon.Adapter.UserAdapter;
import com.ashrafunahid.aponjon.ModelClass.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    UserAdapter adapter;
    FirebaseDatabase database;
    ArrayList<Users> usersArrayList;
    ImageView img_logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
        } else {
            usersArrayList = new ArrayList<>();

            DatabaseReference reference = database.getReference().child("user");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Users users = dataSnapshot.getValue(Users.class);
                        usersArrayList.add(users);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            img_logOut = (ImageView) findViewById(R.id.img_logOut);
            mainUserRecyclerView = (RecyclerView) findViewById(R.id.mainUserRecyclerView);
            mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new UserAdapter(HomeActivity.this, usersArrayList);
            mainUserRecyclerView.setAdapter(adapter);

            img_logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("Logout")
                            .setMessage("Are you sure you want to Logout?")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    auth.signOut();
                                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

//                Dialog dialog = new Dialog(HomeActivity.this, R.style.Dialog);
//                dialog.setContentView(R.layout.dialog_layout);
//
//                TextView yesBtn, noBtn;
//                yesBtn = (TextView) findViewById(R.id.yesBtn);
//                noBtn = (TextView) findViewById(R.id.noBtn);
//
//                yesBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        FirebaseAuth.getInstance().signOut();
//                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                    }
//                });
//
//                noBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
                }
            });
        }


    }
}