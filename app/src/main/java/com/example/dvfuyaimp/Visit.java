package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Visit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);

        findViewById(R.id.host).setOnClickListener(this::hostBTN);
        findViewById(R.id.guest).setOnClickListener(this::guestBTN);
    }

    private void hostBTN(View view){
        startActivity(new Intent(this, Host.class));
    }

    private void guestBTN(View view){
        startActivity(new Intent(this, Guest.class));
    }


}