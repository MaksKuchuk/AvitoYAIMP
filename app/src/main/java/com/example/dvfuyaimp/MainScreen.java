package com.example.dvfuyaimp;

import androidx.annotation.RequiresApi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import server_connection.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.O)
public class MainScreen extends AppCompatActivity {

    final int TransitionWeekButtonsTime = 100;
    final int TransitionLessonTime = 500;

    View LastWeekBTN;
    View LastItemTeacherDescription = null;
    LocalDateTime startOfWeek;
    DateTimeFormatter fServ = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    DateTimeFormatter fDate = DateTimeFormatter.ofPattern("HH:mm");
    List<List<Schedule>> weekSchedule = new ArrayList<>();
    int flag = 0;
    String weekDates = "";

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

            List<Schedule> daySchedule = weekSchedule.get(dayOfWeekByButton(view));

            for (Schedule s : daySchedule) {
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
        ((LinearLayout)findViewById(R.id.subjectScrollLayout)).animate().alpha(0f).setDuration(TransitionLessonTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int ID;
                if (view.getId() == R.id.PrevBTN) {
                    startOfWeek = startOfWeek.minusWeeks(1);
                    ID = R.id.SatBTN;
                } else {
                    startOfWeek = startOfWeek.plusWeeks(1);
                    ID = R.id.MonBTN;
                }
                getWeekSchedule();
                if (LastWeekBTN.getId() == ID) {
                    flag = 1;
                }
                weekDates = startOfWeek.format(DateTimeFormatter.ofPattern("dd-MM")) + " — " + startOfWeek.plusDays(6).format(DateTimeFormatter.ofPattern("dd-MM"));
                // после этого можно обновлять дату

                DayBTN(findViewById(ID));
                flag = 0;

                ((LinearLayout)findViewById(R.id.subjectScrollLayout)).animate().alpha(1f).setDuration(TransitionLessonTime).setListener(null);
            }
        });
    }

    private void setStartDay(){
        LastWeekBTN = findViewById(R.id.SunBTN);
        ((TransitionDrawable)findViewById(R.id.MonBTN).getBackground()).startTransition(0);

        for (int i = 0; i < 7; ++i) {
            weekSchedule.add(new ArrayList<>());
        }

        startOfWeek = LocalDateTime.now().toLocalDate().atStartOfDay().with(DayOfWeek.MONDAY);
        int ID = idByDayOfWeek();
        if (ID == R.id.SunBTN) {
            startOfWeek = startOfWeek.plusWeeks(1);
            ID = R.id.MonBTN;
        }
        weekDates = startOfWeek.format(DateTimeFormatter.ofPattern("dd-MM")) + " — " + startOfWeek.plusDays(6).format(DateTimeFormatter.ofPattern("dd-MM"));
        getWeekSchedule();
        DayBTN(findViewById(ID));
    }


    private void getWeekSchedule() {
        for (List<Schedule> schedules : weekSchedule) {
            if (schedules != null)
                schedules.clear();
        }
        Schedule[] querySchedule = DataAccess.GetSchedule("select * from Lessons where time>='"+startOfWeek.format(fServ)+"' and time<'"+startOfWeek.plusWeeks(1).format(fServ)+"' order by time asc");
        for (Schedule schedules : querySchedule) {
            weekSchedule.get((int) ChronoUnit.DAYS.between(startOfWeek, LocalDateTime.parse(schedules.Time))).add(schedules);
        }
    }


    private void addLesson(String strLesson, String strTeacher, String strTime, String strRoom){
        LayoutInflater ltInflater = getLayoutInflater();
        View item = ltInflater.inflate(R.layout.lesson_layout, ((LinearLayout)findViewById(R.id.subjectScrollLayout)), false);

        ((TextView)item.findViewById(R.id.lesson)).setText(strLesson);
        ((TextView)item.findViewById(R.id.teacher)).setText(strTeacher);
        ((TextView)item.findViewById(R.id.time)).setText(strTime);
        ((TextView)item.findViewById(R.id.room)).setText(strRoom);

        item.setOnClickListener(this::openTeacherDescriptionEvent);

        ((LinearLayout)findViewById(R.id.subjectScrollLayout)).addView(item);
    }

    private void addEvent(String strEvent, String strDescription, String strTimeEvent, String strPlace){
        LayoutInflater ltInflater = getLayoutInflater();
        View item = ltInflater.inflate(R.layout.event_layout, ((LinearLayout)findViewById(R.id.subjectScrollLayout)), false);

        ((TextView)item.findViewById(R.id.event)).setText(strEvent);
        ((TextView)item.findViewById(R.id.description)).setText(strDescription);
        ((TextView)item.findViewById(R.id.timeEvent)).setText(strTimeEvent);
        ((TextView)item.findViewById(R.id.place)).setText(strPlace);

        item.setOnClickListener(this::openTeacherDescriptionEvent);

        ((LinearLayout)findViewById(R.id.subjectScrollLayout)).addView(item);
    }

    private void openTeacherDescriptionEvent(View item){
        if (item == LastItemTeacherDescription){
            closeItem(LastItemTeacherDescription);
            LastItemTeacherDescription = null;
            return;
        }
        if (LastItemTeacherDescription == null){
            openItem(item);
            LastItemTeacherDescription = item;
        }
        closeItem(LastItemTeacherDescription);
        openItem(item);
        LastItemTeacherDescription = item;
    }

    private void openItem(View item){
        if (item.getId() == R.id.lessonWrapper){
            item.findViewById(R.id.teacher).setVisibility(View.VISIBLE);
            item.findViewById(R.id.lessonArrow).setVisibility(View.VISIBLE);
        } else if (item.getId() == R.id.eventWrapper){
            item.findViewById(R.id.description).setVisibility(View.VISIBLE);
        }
    }
    private void closeItem(View item){
        if (item.getId() == R.id.lessonWrapper){
            item.findViewById(R.id.teacher).setVisibility(View.GONE);
            item.findViewById(R.id.lessonArrow).setVisibility(View.GONE);
        } else if (item.getId() == R.id.eventWrapper){
            item.findViewById(R.id.description).setVisibility(View.GONE);
        }
    }

    private void deleteAllViewInScrollView(){
        ((LinearLayout)findViewById(R.id.subjectScrollLayout)).removeAllViews();
    }

    private int idByDayOfWeek() {
        String day = String.valueOf(LocalDateTime.now().getDayOfWeek());
        switch (day){
            case "MONDAY":
                return R.id.MonBTN;
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
            default:
                return R.id.SunBTN;
        }
    }

    @SuppressLint("NonConstantResourceId")
    private int dayOfWeekByButton(View view) {
        switch (view.getId()) {
            case R.id.MonBTN:
                return 0;
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
            default:
                return 6;
        }
    }
}