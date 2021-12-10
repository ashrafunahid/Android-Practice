package com.example.vollylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button MyButton;
    TextView MyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyButton = (Button) findViewById(R.id.btn);
        MyText = (TextView) findViewById(R.id.text);

        MyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleString();
            }
        });
    }

    public void SimpleString(){
//        Using normal process of request queue
//        RequestQueue queue = Volley.newRequestQueue(this);

//        Customized process of request queue
//        RequestQueue MyRequestQueue;
//
//        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
//        Network network = new BasicNetwork(new HurlStack());
//
//        MyRequestQueue = new RequestQueue(cache, network);
//        MyRequestQueue.start();

//        Using Singleton pattern
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        String url = "http://192.168.0.107/welcome.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyText.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                MyText.setText("Server Not Found");
            }
        });
//        Using normal process of request queue
//        queue.add(stringRequest);

//        Customized process of request queue
//        MyRequestQueue.add(stringRequest);


//        Using Singleton pattern
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}