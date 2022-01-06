package com.example.dvfuyaimp;

import androidx.annotation.RequiresApi;
import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import server_connection.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RequiresApi(api = Build.VERSION_CODES.O)
public class MainScreen extends AppCompatActivity {

    final int TransitionWeekButtonsTime = 100;

    View LastWeekBTN;
    LocalDateTime startOfWeek;
    DateTimeFormatter fServ = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    DateTimeFormatter fDate = DateTimeFormatter.ofPattern("HH:mm");
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        findViewById(R.id.MonBTN).setOnClickListener(this::DayBTN);
        findViewById(R.id.TueBTN).setOnClickListener(this::DayBTN);
        findViewById(R.id.WedBTN).setOnClickListener(this::DayBTN);
        findViewById(R.id.ThuBTN).setOnClickListener(this::DayBTN);
        findViewById(R.id.FriBTN).setOnClickListener(this::DayBTN);
        findViewById(R.id.SatBTN).setOnClickListener(this::DayBTN);

        findViewById(R.id.settings).setOnClickListener(this::SettingBTN);
        findViewById(R.id.LC).setOnClickListener(this::LcBTN);

        findViewById(R.id.PrevBTN).setOnClickListener(this::WeekBTN);
        findViewById(R.id.NextBTN).setOnClickListener(this::WeekBTN);

        setStartDay();
    }

    public void DayBTN(View view){
        if (LastWeekBTN != view || flag == 1) {
            if (flag == 0) {
                ((TransitionDrawable) LastWeekBTN.getBackground()).reverseTransition(TransitionWeekButtonsTime);
                ((TransitionDrawable) view.getBackground()).startTransition(TransitionWeekButtonsTime);
                LastWeekBTN = view;
            }
            deleteAllViewInScrollView();


            addLesson(startOfWeek.plusDays(dayOfWeekByButton(view)).format(fServ), "" + startOfWeek.plusDays(dayOfWeekByButton(view)+1).format(fServ), ""+dayOfWeekByButton(view), ""+startOfWeek.plusDays(dayOfWeekByButton(view)).getDayOfWeek());

            Schedule[] schedule = DataAccess.GetSchedule("select * from Lessons where time>='"+startOfWeek.plusDays(dayOfWeekByButton(view)).format(fServ)+"' and time<'"+startOfWeek.plusDays(dayOfWeekByButton(view) + 1).format(fServ)+"' order by time asc");

            for (Schedule s : schedule) {
                addLesson(s.Lesson, s.Teacher, s.GetLDC(fDate), s.Room);
            }
        }
    }

    public void SettingBTN(View view){
        Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, Settings.class));
        overridePendingTransition(R.anim.anim_main_to_settings_out, R.anim.anim_main_to_settings);
    }
    public void LcBTN(View view){
        Toast.makeText(getApplicationContext(),"LC", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LC.class));
        overridePendingTransition(R.anim.anim_main_to_lc_out, R.anim.anim_main_to_lc);
    }
    public void WeekBTN(View view){
        if (view.getId() == R.id.PrevBTN) {
            startOfWeek = startOfWeek.minusDays(7);
            if (LastWeekBTN.getId() == R.id.SatBTN) {
                flag = 1;
            }
            DayBTN(findViewById(R.id.SatBTN));
        } else {
            startOfWeek = startOfWeek.plusDays(7);
            if (LastWeekBTN.getId() == R.id.MonBTN) {
                flag = 1;
            }
            DayBTN(findViewById(R.id.MonBTN));
        }
        flag = 0;
    }

    private void setStartDay(){
        LastWeekBTN = findViewById(idByDayOfWeek() == R.id.MonBTN ? R.id.TueBTN : R.id.MonBTN);
        ((TransitionDrawable)findViewById(R.id.MonBTN).getBackground()).startTransition(0);

        startOfWeek = LocalDateTime.now().toLocalDate().atStartOfDay().with(DayOfWeek.MONDAY);
        DayBTN(findViewById(idByDayOfWeek()));
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

    private void deleteAllViewInScrollView(){
        ((LinearLayout)findViewById(R.id.subjectScrollLayout)).removeAllViews();
    }

    private int idByDayOfWeek() {
        String day = String.valueOf(LocalDateTime.now().getDayOfWeek());
        switch (day){
            case "TUESDAY":
                return R.id.TueBTN;
            case "WEDNESDAY":
                return R.id.WedBTN;
            case "THURSDAY":
                return R.id.ThuBTN;
            case "FRIDAY":
                return R.id.FriBTN;
            case "SATURDAY":
                return R.id.SatBTN;
            case "MONDAY":
            default:
                return R.id.MonBTN;
        }
    }

    @SuppressLint("NonConstantResourceId")
    private int dayOfWeekByButton(View view) {
        switch (view.getId()) {
            case R.id.TueBTN:
                return 1;
            case R.id.WedBTN:
                return 2;
            case R.id.ThuBTN:
                return 3;
            case R.id.FriBTN:
                return 4;
            case R.id.SatBTN:
                return 5;
            case R.id.MonBTN:
            default:
                return 0;
        }
    }
}