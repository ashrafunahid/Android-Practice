package com.example.makeanemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText my_mail_to, my_mail_subject, my_mail_body;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_mail_to = (EditText) findViewById(R.id.mail_to);
        my_mail_subject = (EditText) findViewById(R.id.mail_subject);
        my_mail_body = (EditText) findViewById(R.id.mail_body);

        sendBtn = (Button) findViewById(R.id.send_button);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

    }

    protected void sendMail(){
        String recipientList = my_mail_to.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = my_mail_subject.getText().toString();
        String message = my_mail_body.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Select an app"));
    }
}