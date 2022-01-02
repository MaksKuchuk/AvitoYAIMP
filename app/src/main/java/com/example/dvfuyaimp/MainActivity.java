package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.enterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterBTN(view);
            }
        });
        findViewById(R.id.restorePasswordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restorePasswordBTN(view);
            }
        });

    }

    public void enterBTN(View view){
        Toast.makeText(getApplicationContext(),"Enter", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainScreen.class));
    }

    public void restorePasswordBTN(View view){
        Toast.makeText(getApplicationContext(),"Restore password", Toast.LENGTH_SHORT).show();
    }

}