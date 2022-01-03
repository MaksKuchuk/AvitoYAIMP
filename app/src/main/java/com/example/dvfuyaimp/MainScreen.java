package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        findViewById(R.id.MonBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonBTN(view);
            }
        });
        findViewById(R.id.TueBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TueBTN(view);
            }
        });
        findViewById(R.id.WedBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WedBTN(view);
            }
        });
        findViewById(R.id.ThuBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThuBTN(view);
            }
        });
        findViewById(R.id.FriBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriBTN(view);
            }
        });
        findViewById(R.id.SatBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SatBTN(view);
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingBTN(view);
            }
        });
        findViewById(R.id.LC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LcBTN(view);
            }
        });
    }

    public void MonBTN(View view){
        Toast.makeText(getApplicationContext(),"Monday", Toast.LENGTH_SHORT).show();
    }
    public void TueBTN(View view){
        Toast.makeText(getApplicationContext(),"Tuesday", Toast.LENGTH_SHORT).show();
    }
    public void WedBTN(View view){
        Toast.makeText(getApplicationContext(),"Wednesday", Toast.LENGTH_SHORT).show();
    }
    public void ThuBTN(View view){
        Toast.makeText(getApplicationContext(),"Thursday", Toast.LENGTH_SHORT).show();
    }
    public void FriBTN(View view){
        Toast.makeText(getApplicationContext(),"Friday", Toast.LENGTH_SHORT).show();
    }
    public void SatBTN(View view){
        Toast.makeText(getApplicationContext(),"Saturday", Toast.LENGTH_SHORT).show();
    }

    public void settingBTN(View view){
        Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, Settings.class));
        overridePendingTransition(R.anim.anim_main_to_settings_out, R.anim.anim_main_to_settings);
    }
    public void LcBTN(View view){
        Toast.makeText(getApplicationContext(),"LC", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LC.class));
        overridePendingTransition(R.anim.anim_main_to_lc_out, R.anim.anim_main_to_lc);
    }
}