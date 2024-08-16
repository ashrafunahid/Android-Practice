package com.ashrafunahid.musicplayerapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ashrafunahid.musicplayerapplication.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    // Media Player
    MediaPlayer mediaPlayer;
    // Handlers
    Handler handler = new Handler();
    // Variables
    double startTime = 0;
    double endTime = 0;
    int forwardTime = 10000;
    int backwardTime = 10000;
    static int oneTimeOnly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.playButton.setVisibility(View.VISIBLE);
        binding.pauseButton.setVisibility(View.INVISIBLE);

        mediaPlayer = MediaPlayer.create(this, R.raw.astronaut);
        binding.timeSeekBar.setClickable(false);

        binding.musicTitle.setText(getResources().getIdentifier("astronaut", "raw", getPackageName()));

        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic();
                binding.playButton.setVisibility(View.GONE);
                binding.pauseButton.setVisibility(View.VISIBLE);
            }
        });

        binding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                binding.playButton.setVisibility(View.VISIBLE);
                binding.pauseButton.setVisibility(View.GONE);
            }
        });

        binding.forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempTime = (int) startTime;
                if ((tempTime + forwardTime) <= endTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    // ToDo throw a message to user
                }
            }
        });

        binding.rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempTime = (int) startTime;
                if ((tempTime - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    // ToDo throw a message to user
                }
            }
        });

    }

    private void playMusic() {
        mediaPlayer.start();

        endTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            binding.timeSeekBar.setMax((int) endTime);
            oneTimeOnly = 1;
        }

        binding.timeLeftText.setText(String.format(
                "%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) endTime),
                TimeUnit.MILLISECONDS.toSeconds((long) endTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) endTime))));

        binding.timeSeekBar.setProgress((int) startTime);
        handler.postDelayed(updateSongTime, 100);
    }

    private Runnable updateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            binding.timeLeftText.setText(String.format(
                    "%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))
            ));

            binding.timeSeekBar.setProgress((int) startTime);
            handler.postDelayed(this, 100);
        }
    };
}