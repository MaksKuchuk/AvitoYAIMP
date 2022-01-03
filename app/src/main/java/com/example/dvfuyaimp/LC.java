package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_c);
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
        overridePendingTransition(R.anim.anim_lc_to_main_out, R.anim.anim_lc_to_main);
    }
}