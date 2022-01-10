package com.example.dvfuyaimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Host extends AppCompatActivity {

    long randomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        findViewById(R.id.cameraHost).setOnClickListener(this::openCamera);
        findViewById(R.id.createQR).setOnClickListener(this::openQRcode);
        findViewById(R.id.closeQR).setOnClickListener(this::closeQR);

        appGuest("10:01:2022", "Kuchuk Maksim", "15:03");
        appGuest("10:01:2022", "Babak Ivan", "15:34");
        appGuest("10:01:2022", "Bolotaev Roman", "17:07");
    }

    private void appGuest(String strDay, String strName, String strTime){
        LayoutInflater ltInflater = getLayoutInflater();
        View item = ltInflater.inflate(R.layout.guests_layout, ((LinearLayout)findViewById(R.id.layoutGuests)), false);

        ((TextView)item.findViewById(R.id.dayMonth)).setText(strDay);
        ((TextView)item.findViewById(R.id.nameLastName)).setText(strName);
        ((TextView)item.findViewById(R.id.timeIn)).setText(strTime);

        ((LinearLayout)findViewById(R.id.layoutGuests)).addView(item);
    }

    private void openCamera(View view) {
        startActivity(new Intent(this, Camera.class));
    }
    private void openQRcode(View view){
        findViewById(R.id.QRBTNlayout).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollLayoutGuests).setVisibility(View.INVISIBLE);
        findViewById(R.id.Guests).setVisibility(View.INVISIBLE);
        randomKey = (long)(-2000000000 + Math.random() * 2000000000 + Math.random() * 2000000000);
        createQRcode(Long.toString(randomKey));
    }
    private void closeQR(View view){
        findViewById(R.id.QRBTNlayout).setVisibility(View.GONE);
        findViewById(R.id.scrollLayoutGuests).setVisibility(View.VISIBLE);
        findViewById(R.id.Guests).setVisibility(View.VISIBLE);
    }
    private void createQRcode(String strQR){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(strQR, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ((ImageView)findViewById(R.id.QRCode)).setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}