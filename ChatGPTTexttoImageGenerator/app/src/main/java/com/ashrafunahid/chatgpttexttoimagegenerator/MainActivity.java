package com.ashrafunahid.chatgpttexttoimagegenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    Button generateButton;
    ProgressBar progressBar;
    ImageView outputImage;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();

        inputText = (EditText) findViewById(R.id.input_text);
        generateButton = (Button) findViewById(R.id.generate_btn);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        outputImage = (ImageView) findViewById(R.id.output_image);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = inputText.getText().toString().trim();
                if(text.isEmpty()) {
                    inputText.setError("Please write something");
                    inputText.requestFocus();
                    return;
                }

                generateImage(text);

            }
        });

    }

    private void generateImage(String text) {

        showProgressBar(true);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("prompt", text);
            jsonBody.put("size", "512x512");
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .header("Authorization", "Bearer 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")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this, "Failed to generate response", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i("API Response", response.body().string());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String errorCode = jsonObject.getString("error");
                    Log.i("API Response", errorCode);
                    String imageUrl = jsonObject.getJSONArray("data").getJSONObject(0).getString("url");
//                    if(!errorCode.isEmpty()) {
//                        Toast.makeText(MainActivity.this, errorCode, Toast.LENGTH_SHORT).show();
//                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.get().load(imageUrl).into(outputImage);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    showProgressBar(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showProgressBar(boolean b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(b) {
                    progressBar.setVisibility(View.VISIBLE);
                    generateButton.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    generateButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}