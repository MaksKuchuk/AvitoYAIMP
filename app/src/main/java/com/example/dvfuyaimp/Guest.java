package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.jetbrains.annotations.NotNull;

public class Guest extends AppCompatActivity {
    int bracketsUP1 = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        findViewById(R.id.cameraGuest).setOnClickListener(this::openCamera);

        String name = "Ivan Ivanov", building = "7.4", room = "1234";
        makeGuest(name, building, room);
    }

    private void sendRequestToBdToLeave(String requestCode){

    }

    private boolean isSuccess(){
        //BD returns an answer success or fail
        return true;
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
        nameBuildingRoom = decryptRequestCode(nameBuildingRoom);

        boolean isRight = true;
        for (int i = 0; i < bracketsUP1; i++)
            if (nameBuildingRoom.charAt(i) != '!') {
                isRight = false;
                break;
            }
        for (int i = nameBuildingRoom.length() - 1; i >= nameBuildingRoom.length() - bracketsUP1; i--)
            if (nameBuildingRoom.charAt(i) != '!') {
                isRight = false;
                break;
            }

        if (isRight){
            sendRequestToBdToLeave(nameBuildingRoom);
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    private void createQRcode(String strQR){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        strQR = encryptQR(strQR);

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(strQR, BarcodeFormat.QR_CODE,1000,1000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ((ImageView)findViewById(R.id.QRCode)).setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void makeGuest(String name, String building, String room){
        ((TextView)findViewById(R.id.nameOfGuest)).setText(name);
        ((TextView)findViewById(R.id.buildingnum)).setText(("Building: " + building));
        ((TextView)findViewById(R.id.roomNum)).setText(("Room: " + room));

        createQRcode((name + '/' + building + '/' + room + '/'));
    }

    private String encryptQR(String s) {
        StringBuffer str = new StringBuffer(s);

        for (int i = 0; i < str.length(); ++i) {
            if (i % 2 == 0) {
                int buf = str.charAt(i) - 42;
                str.insert(i, (char)buf);
            }
            else {
                int t = str.charAt(i) ^ 33;
                char buf = (char)t;
                str.setCharAt(i, buf);
            }
        }

        return str.toString();
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