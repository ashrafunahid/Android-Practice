package com.ashrafunahid.cryptotest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Encoder extends AppCompatActivity {

    EditText editText;
    TextView encTv;
    ClipboardManager cbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoder);

        editText = (EditText) findViewById(R.id.editText);
        encTv = (TextView) findViewById(R.id.encTv);
        cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

    }

    public void encryptText(View view) {
        String userInputData = editText.getText().toString();
        Log.i("info", userInputData);
        String encryptedData = Encode.enc(userInputData);
        encTv.setText(encryptedData);
    }

    public void copyText(View view) {

        String txt = encTv.getText().toString().trim();

        if (!txt.isEmpty()) {
            ClipData data = ClipData.newPlainText("text", txt);
            cbm.setPrimaryClip(data);

            Toast.makeText(this, "Encrypted Data Copied", Toast.LENGTH_SHORT);
        }
    }
}