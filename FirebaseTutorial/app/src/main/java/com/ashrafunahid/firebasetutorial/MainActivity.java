package com.ashrafunahid.firebasetutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.firebasetutorial.Models.UploadModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    Button chooseFileButton, uploadButton;
    EditText fileName;
    ImageView imagePreview;
    ProgressBar progressBar;
    TextView showUploadButton;
    int PICK_IMAGE_REQUEST_CODE = 100;
    Uri imageUri;
//    String imageDownloadUrl;
    StorageReference firebaseStorageReference;
    DatabaseReference firebaseDatabaseReference;
    StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseFileButton = (Button) findViewById(R.id.choose_file_btn);
        uploadButton = (Button) findViewById(R.id.upload_btn);
        fileName = (EditText) findViewById(R.id.file_name);
        imagePreview = (ImageView) findViewById(R.id.image_preview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        showUploadButton = (TextView) findViewById(R.id.show_upload_btn);

//        Creating Firebase Storage and Database Reference with instance
        firebaseStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTask != null && storageTask.isInProgress()) {
                    Toast.makeText(MainActivity.this, "Previous task is in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        showUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowUploads.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            if (requestCode == PICK_IMAGE_REQUEST_CODE) {

                imageUri = data.getData();
                Picasso.get().load(imageUri).into(imagePreview);

            }

        }

    }

    //    This function will be work for getting the image extension
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {

        if (imageUri != null) {

            StorageReference fileReference = firebaseStorageReference.child(
                    System.currentTimeMillis() + "." + getFileExtension(imageUri)
            );

            storageTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            String fileNameUrl = taskSnapshot.getStorage().getName();
                            firebaseStorageReference.child(fileNameUrl).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String imageDownloadUrl = task.getResult().toString();
                                    Log.i("Nahid", fileNameUrl);
                                    Log.i("Nahid", imageDownloadUrl);
                                    UploadModel uploadModel = new UploadModel(fileName.getText().toString().trim(), imageDownloadUrl);

                                    String uploadId = firebaseDatabaseReference.push().getKey();
                                    firebaseDatabaseReference.child(uploadId).setValue(uploadModel);

                                    Picasso.get()
                                                    .load(imageDownloadUrl)
                                                            .into(imagePreview);
                                    Toast.makeText(MainActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);

                        }
                    });

        } else {

            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();

        }
    }
}