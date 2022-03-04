package com.ashrafunahid.aponjon.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.aponjon.R;
import com.ashrafunahid.aponjon.ModelClass.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {

    TextView register_login;
    CircleImageView profile_image;
    EditText reg_name, reg_email, reg_password, reg_cpassword;
    Button reg_register;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Uri imageUri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String imageURI;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_password = (EditText) findViewById(R.id.reg_password);
        reg_cpassword = (EditText) findViewById(R.id.reg_cpasswrod);
        reg_register = (Button) findViewById(R.id.reg_register);
        register_login = (TextView) findViewById(R.id.register_login);

        reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String name = reg_name.getText().toString();
                String email = reg_email.getText().toString();
                String password = reg_password.getText().toString();
                String cpassword = reg_cpassword.getText().toString();
                String status = "Hey there I'm using aponjon";

                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                    finish();
                }

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    progressDialog.dismiss();
                    reg_email.setError("Invalid Email Address");
                    Toast.makeText(RegistrationActivity.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(cpassword)) {
                    progressDialog.dismiss();
                    reg_cpassword.setError("Not similar to password");
                    Toast.makeText(RegistrationActivity.this, "Both password should be same", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Password should be minimum 6 character length", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
                                StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());

                                if (imageUri != null) {
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                            if (task.isSuccessful()) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageURI = uri.toString();
                                                        Users users = new Users(auth.getUid(), name, email, imageURI, status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                                                } else {
                                                                    Toast.makeText(RegistrationActivity.this, "Error in registration", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    String status = "Hey there I'm using aponjon";
                                    imageURI = "https://firebasestorage.googleapis.com/v0/b/aponjon-59768.appspot.com/o/profile.png?alt=media&token=1491aaf2-10ab-4372-b183-2f6d7ecb945c";
                                    Users users = new Users(auth.getUid(), name, email, imageURI, status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                            } else {
                                                Toast.makeText(RegistrationActivity.this, "Error in registration", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a profile photo"), 10);
            }
        });

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                imageUri = data.getData();
                profile_image.setImageURI(imageUri);
            }
        }
    }
}