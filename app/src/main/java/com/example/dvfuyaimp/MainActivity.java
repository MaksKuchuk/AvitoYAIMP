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

    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for connection with server
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        remember = (CheckBox)findViewById(R.id.password_rem);

        findViewById(R.id.enterButton).setOnClickListener(this::enterBTN);
        findViewById(R.id.restorePasswordButton).setOnClickListener(this::restorePasswordBTN);
        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private long hash(String pass){
        long left = 4633;
        long right = 3427;
        long res = 460193893;
        int len = pass.length();

        for (int i = 0; i < len; i++) {
            if (i % 2 == 0){ left *= pass.charAt(i); } else { right *= pass.charAt(i); }
            res *= (left * right) + 3583;
        }
        for (int i = 0; i < len; i += 3) {
            if (i % 2 == 1){ left ^= pass.charAt(i); } else { right *= pass.charAt(i); }
            res *= (left ^ right) + 2909;
        }
        for (int i = 1; i < len; i++) {
            if (i % 2 == 1){ left *= pass.charAt(i); } else { right /= pass.charAt(i); }
            res ^= (left * right) + 1229;
        }
        for (int i = 0; i < len; i += 3) {
            if (i % 2 == 0){ left ^= pass.charAt(i); } else { right *= pass.charAt(i); }
            res *= (left ^ right) + 5477;
        }
        for (int i = 1; i < len; i++) {
            if (i % 2 == 0){ left /= pass.charAt(i); } else { right *= pass.charAt(i); }
            res ^= (left * right) + 2383;
        }

        return res;
    }

    public void enterBTN(View view){
        String pass = ((EditText)findViewById(R.id.PasswordEdit)).getText().toString().trim();
        String login = ((EditText)findViewById(R.id.EmailEdit)).getText().toString().trim();

        Toast.makeText(getApplicationContext(), Long.toString(hash(pass)), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainScreen.class));
    }

    public void restorePasswordBTN(View view){
        Toast.makeText(getApplicationContext(), "Restore password", Toast.LENGTH_SHORT).show();
    }

}