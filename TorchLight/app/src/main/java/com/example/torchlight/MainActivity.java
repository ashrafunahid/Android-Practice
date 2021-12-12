package com.example.torchlight;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {

    ToggleButton TorchButton;
    Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TorchButton = (ToggleButton) findViewById(R.id.tourch);

        TorchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    TorchButton.setBackgroundResource(R.drawable.ic_power_on);
                    camera = Camera.open();
                    Camera.Parameters p = camera.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();
                }
                else{
                    TorchButton.setBackgroundResource(R.drawable.ic_power_off);
                    camera.stopPreview();
                    camera.release();
                }
            }
        });

    }
}