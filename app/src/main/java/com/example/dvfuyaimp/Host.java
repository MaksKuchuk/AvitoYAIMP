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
    int bracketsUP1 = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        findViewById(R.id.cameraHost).setOnClickListener(this::openCamera);
    }

    private void sendRequestToInviteGuest(String requestCode){
        sendToDB(decryptRequestCode(requestCode));
    }

    private void sendToDB(String requestCode){
        //Send List<String> allGuest
    }

    private boolean isSuccess(){
        //BD returns an answer success or fail
        return true;
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
            Toast.makeText(this, "Invalid QR-code", Toast.LENGTH_SHORT).show();
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
        nameBuildingRoom = decryptQR(nameBuildingRoom);

        boolean isGuest = false;
        for (int i = 0; i < bracketsUP1; i++)
            if (nameBuildingRoom.charAt(i) != '!') {
                isGuest = true;
                break;
            }
        for (int i = nameBuildingRoom.length() - 1; i >= nameBuildingRoom.length() - bracketsUP1; i--)
            if (nameBuildingRoom.charAt(i) != '!') {
                isGuest = true;
                break;
            }

        if (isGuest) {
            appGuestByStr(nameBuildingRoom);
        } else {
            sendRequestToInviteGuest(nameBuildingRoom);
        }

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

    @NotNull
    private String decryptRequestCode(String requestCode) {
        requestCode = decryptQR(requestCode);
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < requestCode.length(); i++){
            if (i >= bracketsUP1 && i < requestCode.length() - bracketsUP1){
                temp.append(requestCode.charAt(i));
            }
        }

        return temp.toString();
    }

}