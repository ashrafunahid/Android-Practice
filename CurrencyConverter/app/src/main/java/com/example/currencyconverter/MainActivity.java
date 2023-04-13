package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public void convertAmount(View view){

        TextView currency_info = (TextView) findViewById(R.id.currency_info);
        EditText currency_input = (EditText) findViewById(R.id.currency_input);
//        Log.i("Length: ", String.valueOf(currency_input.length()));
        if(currency_input.getText().toString().length() > 0){
            double input_amount = Double.parseDouble(currency_input.getText().toString());
            if(currency_info.getText().toString() == getResources().getString(R.string.usd)){
                double converted_amount = input_amount * 0.92;
                Toast.makeText(MainActivity.this, "EUR: " + String.format("%.2f", converted_amount) + " €", Toast.LENGTH_SHORT).show();
            }else{
                double converted_amount = input_amount * 1.08;
                Toast.makeText(MainActivity.this, "USD: " + String.format("%.2f", converted_amount) + " $", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Input value cannot be null", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void switchCurrency(View view){
        ImageView currency_image = (ImageView) findViewById(R.id.currency_image);
        TextView currency_info = (TextView) findViewById(R.id.currency_info);
        Button toggle_currency = (Button) findViewById(R.id.toggle_currency);

        if(currency_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.first).getConstantState()){
            currency_image.setImageResource(R.drawable.second);
        }else {
            currency_image.setImageResource(R.drawable.first);
        }

        if(currency_info.getText().toString() == getResources().getString(R.string.usd)){
            currency_info.setText(R.string.eur);
        }else {
            currency_info.setText(R.string.usd);
        }

        if(toggle_currency.getText().toString() == getResources().getString(R.string.eur_to_usd)){
            toggle_currency.setText(R.string.usd_to_eur);
        }else{
            toggle_currency.setText(R.string.eur_to_usd);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}