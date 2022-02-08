package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Guest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        findViewById(R.id.cameraGuest).setOnClickListener(this::openCamera);

        String name = "Maksim Kuchuk", building = "8.2", room = "467";
        makeGuest(name, building, room);
    }

    private void openCamera(View view){
        startActivity(new Intent(this, Camera.class));
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




}