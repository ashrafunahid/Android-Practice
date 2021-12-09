package com.example.imagefromurl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);

        String url = "https://scontent.fdac24-1.fna.fbcdn.net/v/t31.18172-8/12339539_1051452574886560_5922055887321468039_o.jpg?_nc_cat=102&ccb=1-5&_nc_sid=174925&_nc_ohc=k03AaPr4SrgAX__I3Wq&_nc_ht=scontent.fdac24-1.fna&oh=c1e7470fd091673fd1055dabebfd42dd&oe=61D600B6";

        Picasso.get().load(url).into(imageView);

    }
}