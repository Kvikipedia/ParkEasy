package com.example.parkeazy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class ReservationAdditionalFragment extends Fragment {

    Double latitude, longitude;
    String email, parkingLot, date, time, city, data;
    ImageView qrImage;

    public ReservationAdditionalFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation_additional, container, false);

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra("email");
        city = intent.getStringExtra("city");
        parkingLot = intent.getStringExtra("parkingLot");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");

        qrImage = (ImageView) v.findViewById(R.id.reservation_fragment_qr);
        data = email + city + parkingLot + date + time;
        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 20);
        Bitmap bitmap = qrgEncoder.getBitmap();
        qrImage.setImageBitmap(bitmap);
        return v;
    }
}