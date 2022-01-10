package com.example.dvfuyaimp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for connection with server
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        findViewById(R.id.schedule).setOnClickListener(this::toSchedule);
        findViewById(R.id.visit).setOnClickListener(this::toVisit);
    }

    private void toSchedule(View view){
        startActivity(new Intent(this, MainScreen.class));
    }

    private void toVisit(View view){
        startActivity(new Intent(this, Visit.class));
    }




}