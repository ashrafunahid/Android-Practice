package com.ashrafunahid.filesystemdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button create, delete, read, file_list, create_dir, read_dir;
    ImageView imageView;
    String FILE_NAME = "newtextfile";
    String IMAGE_NAME = "tree";
    String DIR_NAME = "MyDirectory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);
        textView = (TextView) findViewById(R.id.text_view);
        imageView = (ImageView) findViewById(R.id.imageView);
        create = (Button) findViewById(R.id.create);
        delete = (Button) findViewById(R.id.delete);
        read = (Button) findViewById(R.id.read);
        file_list = (Button) findViewById(R.id.file_list);
        create_dir = (Button) findViewById(R.id.create_dir);
        read_dir = (Button) findViewById(R.id.read_dir);

        String filePath = getFilesDir().getAbsolutePath();
        textView.setText(filePath + "/");

        create.setOnClickListener(this::createFile);
        read.setOnClickListener(this::readFile);
        file_list.setOnClickListener(this::fileList);
        delete.setOnClickListener(this::deleteFile);
        create_dir.setOnClickListener(this::createDirectory);
        read_dir.setOnClickListener(this::readDirectory);
    }

    private void readDirectory(View view) {

        File path = getDir(DIR_NAME, MODE_PRIVATE);
        File file = new File(path, "abc.txt");

        if(file.exists()) {
            Toast.makeText(getApplicationContext(), "File Found", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void createDirectory(View view) {

        File path = getDir(DIR_NAME, MODE_PRIVATE);
        File file = new File(path, "abc.txt");

        String data = "This is a text file with some dummy characters.";
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteFile(View view) {

        boolean deleteSuccess = deleteFile(IMAGE_NAME);

        Toast.makeText(getApplicationContext(), IMAGE_NAME.toUpperCase() + " Deleted " + deleteSuccess, Toast.LENGTH_SHORT).show();

    }

    private void fileList(View view) {

        String[] fileList = fileList();

        for (String fileName:fileList){
            textView.append("\n" + fileName);
        }

    }

    private void readFile(View view) {

//        Rading Textual file
//        StringBuilder stringBuilder = new StringBuilder();
//
//        InputStream inputStream = null;
//
//        try {
//            inputStream = openFileInput(FILE_NAME);
//
//            int read;
//
//            while((read = inputStream.read())!= -1){
//                stringBuilder.append((char) read);
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        textView.setText(stringBuilder.toString());

//        Reading Binary File

        Bitmap bitmap = null;
        InputStream inputStream = null;

        try {
            inputStream = openFileInput(IMAGE_NAME);

            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        imageView.setImageBitmap(bitmap);
    }

    private void createFile(View view) {

//        Writing Texual File
//        String data = editText.getText().toString();
//
//        FileOutputStream fileOutputStream = null;
//
//        try {
//            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
//            fileOutputStream.write(data.getBytes());
//            fileOutputStream.flush();
//            textView.setText("File Written.");
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        finally {
//            if(fileOutputStream != null){
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }

//        Writing binary file
        Bitmap data = getImage();

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(IMAGE_NAME, MODE_PRIVATE);

            data.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);

            textView.setText("Image Written.");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Bitmap getImage(){

        Bitmap image = null;

        try {
            InputStream inputStream = getAssets().open("tree.jpg");
            image = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }
}