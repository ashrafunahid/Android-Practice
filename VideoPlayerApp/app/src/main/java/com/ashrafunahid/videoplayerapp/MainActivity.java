package com.ashrafunahid.videoplayerapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    VideoView videoViewLocal, videoViewUrl;
    MediaController mediaControllerLocal, mediaControllerUrl;
    Uri uri = Uri.parse("https://static.videezy.com/system/resources/previews/000/002/231/original/5226496.mp4");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoViewLocal = findViewById(R.id.video_view_local);
        videoViewUrl = findViewById(R.id.video_view_url);

        mediaControllerLocal = new MediaController(this);
        mediaControllerUrl= new MediaController(this);

        // Play Video from raw folder
        videoViewLocal.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.mountains);
        mediaControllerLocal.setAnchorView(videoViewLocal);
        videoViewLocal.setMediaController(mediaControllerLocal);

        // Play Video from URL
        videoViewUrl.setVideoURI(uri);
        mediaControllerUrl.setAnchorView(videoViewUrl);
        videoViewUrl.setMediaController(mediaControllerUrl);
//        videoViewUrl.start();

    }
}