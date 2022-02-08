package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Host extends AppCompatActivity {

    List<String> allGuest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        findViewById(R.id.cameraHost).setOnClickListener(this::openCamera);
    }

    private void sendRequestToInviteGuest(){

    }

    private void sendToDB(){
        //Send List<String> allGuest
    }

    private void updateAllGuest(){
        clearAllGuest();
        //for appGuest() from DB
    }

    private void clearAllGuest(){
        ((LinearLayout)findViewById(R.id.layoutGuests)).removeAllViews();
    }

    private void appGuest(String strName, String strBuilding, String strRoom){
        LayoutInflater ltInflater = getLayoutInflater();
        View item = ltInflater.inflate(R.layout.guests_layout, ((LinearLayout)findViewById(R.id.layoutGuests)), false);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String strTime = df.format(new Date());

        ((TextView)item.findViewById(R.id.nameLastName)).setText(strName);
        ((TextView)item.findViewById(R.id.timeIn)).setText(strTime);

        ((LinearLayout)findViewById(R.id.layoutGuests)).addView(item);
    }

    private void appGuestByStr(String nameBuildingRoom){
        nameBuildingRoom = decryptQR(nameBuildingRoom);

        allGuest.add(nameBuildingRoom);

        StringBuilder tName = new StringBuilder();
        StringBuilder tBuilding = new StringBuilder();
        StringBuilder tRoom = new StringBuilder();
        int c = 0;
        for (int i = 0; i < nameBuildingRoom.length(); i++){
            if (nameBuildingRoom.charAt(i) == '/') c++;
            if (c == 0) tName.append(nameBuildingRoom.charAt(i));
            if (c == 1) tBuilding.append(nameBuildingRoom.charAt(i));
            if (c == 2) tRoom.append(nameBuildingRoom.charAt(i));
        }

        if (c == 3){
            appGuest(tName.toString(), tBuilding.toString(), tRoom.toString());
        } else {
            sendRequestToInviteGuest();
        }
    }

    private void openCamera(View view) {
        startActivityForResult(new Intent(this, Camera.class), 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        String nameBuildingRoom = data.getStringExtra("nameBuildingRoom");
        appGuestByStr(nameBuildingRoom);
    }

    @NotNull
    private String decryptQR(String s) {
        StringBuilder str = new StringBuilder(s);

        for (int i = 0; i < str.length(); ++i) {
            if (i % 2 == 0 && str.length() % 2 == 0 || i % 2 != 0 && str.length() % 2 != 0) {
                str.delete(i, i + 1);
            }
        }

        for (int i = 0; i < str.length(); ++i) {
            int t = str.charAt(i) ^ 33;
            char buf = (char)t;
            str.setCharAt(i, buf);
        }

        return str.toString();
    }

}