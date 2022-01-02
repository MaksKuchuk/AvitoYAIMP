package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        Toast.makeText(getApplicationContext(), Long.toString(hash(pass)), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainScreen.class));
    }

    public void restorePasswordBTN(View view){
        Toast.makeText(getApplicationContext(), "Restore password", Toast.LENGTH_SHORT).show();
    }

}