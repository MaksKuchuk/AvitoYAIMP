package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {

    final int TransitionWeekButtonsTime = 100;

    View LastWeekBTN;

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

        setStartDay(findViewById(R.id.MonBTN));
        addLesson("YAIMP", "Sush", "15:10", "D820");
        addLesson("VAISD", "Klenin", "11:50", "G464");
        addEvent("contest", "15:10", "Building A");
    }

    public void MonBTN(View view){
        if (LastWeekBTN != view){
            ((TransitionDrawable)LastWeekBTN.getBackground()).reverseTransition(TransitionWeekButtonsTime);
            ((TransitionDrawable)view.getBackground()).startTransition(TransitionWeekButtonsTime);
            LastWeekBTN = view;
        }
    }
    public void TueBTN(View view){
        if (LastWeekBTN != view){
            ((TransitionDrawable)LastWeekBTN.getBackground()).reverseTransition(TransitionWeekButtonsTime);
            ((TransitionDrawable)view.getBackground()).startTransition(TransitionWeekButtonsTime);
            LastWeekBTN = view;
        }
    }
    public void WedBTN(View view){
        if (LastWeekBTN != view){
            ((TransitionDrawable)LastWeekBTN.getBackground()).reverseTransition(TransitionWeekButtonsTime);
            ((TransitionDrawable)view.getBackground()).startTransition(TransitionWeekButtonsTime);
            LastWeekBTN = view;
        }
    }
    public void ThuBTN(View view){
        if (LastWeekBTN != view){
            ((TransitionDrawable)LastWeekBTN.getBackground()).reverseTransition(TransitionWeekButtonsTime);
            ((TransitionDrawable)view.getBackground()).startTransition(TransitionWeekButtonsTime);
            LastWeekBTN = view;
        }
    }
    public void FriBTN(View view){
        if (LastWeekBTN != view){
            ((TransitionDrawable)LastWeekBTN.getBackground()).reverseTransition(TransitionWeekButtonsTime);
            ((TransitionDrawable)view.getBackground()).startTransition(TransitionWeekButtonsTime);
            LastWeekBTN = view;
        }
    }
    public void SatBTN(View view){
        if (LastWeekBTN != view){
            ((TransitionDrawable)LastWeekBTN.getBackground()).reverseTransition(TransitionWeekButtonsTime);
            ((TransitionDrawable)view.getBackground()).startTransition(TransitionWeekButtonsTime);
            LastWeekBTN = view;
        }
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

    private void setStartDay(View view){
        LastWeekBTN = findViewById(R.id.MonBTN);
        ((TransitionDrawable)view.getBackground()).startTransition(0);
    }

    private void addLesson(String strLesson, String strTeacher, String strTime, String strRoom){
        LayoutInflater ltInflater = getLayoutInflater();
        View item = ltInflater.inflate(R.layout.lesson_layout, ((LinearLayout)findViewById(R.id.subjectScrollLayout)), false);

        ((TextView)item.findViewById(R.id.lesson)).setText(strLesson);
        ((TextView)item.findViewById(R.id.teacher)).setText(strTeacher);
        ((TextView)item.findViewById(R.id.time)).setText(strTime);
        ((TextView)item.findViewById(R.id.room)).setText(strRoom);

        ((LinearLayout)findViewById(R.id.subjectScrollLayout)).addView(item);
    }

    private void addEvent(String strEvent, String strTimeEvent, String strPlace){
        LayoutInflater ltInflater = getLayoutInflater();
        View item = ltInflater.inflate(R.layout.event_layout, ((LinearLayout)findViewById(R.id.subjectScrollLayout)), false);

        ((TextView)item.findViewById(R.id.event)).setText(strEvent);
        ((TextView)item.findViewById(R.id.timeEvent)).setText(strTimeEvent);
        ((TextView)item.findViewById(R.id.place)).setText(strPlace);

        ((LinearLayout)findViewById(R.id.subjectScrollLayout)).addView(item);
    }
}