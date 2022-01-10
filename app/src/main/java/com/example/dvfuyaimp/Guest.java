package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Guest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        findViewById(R.id.cameraGuest).setOnClickListener(this::openCamera);
    }

    private void openCamera(View view){
        startActivity(new Intent(this, Camera.class));
    }
}