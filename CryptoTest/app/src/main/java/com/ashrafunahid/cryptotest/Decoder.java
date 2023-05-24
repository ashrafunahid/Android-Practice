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

public class Decoder extends AppCompatActivity {

    EditText editTextDcr;
    TextView dcrTv;
    ClipboardManager cbmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        editTextDcr = (EditText) findViewById(R.id.editTextDcr);
        dcrTv = (TextView) findViewById(R.id.dcrTv);
        cbmd = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

    }

    public void decryptText(View view){
        String encryptedText = editTextDcr.getText().toString();
        String decryptedData = Decode.dec(encryptedText);
        dcrTv.setText(decryptedData);
    }

    public void copyTextDcr(View view){

        String txt = dcrTv.getText().toString().trim();
        if(!txt.isEmpty()){
            ClipData data = ClipData.newPlainText("text", txt);
            cbmd.setPrimaryClip(data);
            Toast.makeText(this, "Decrypted data copied", Toast.LENGTH_SHORT).show();
        }
    }
}