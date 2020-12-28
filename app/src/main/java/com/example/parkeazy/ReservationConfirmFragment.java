package com.example.parkeazy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class ReservationConfirmFragment extends Fragment {

    TextView successTxtView, cityTxtView, parkingLotTxtView, dateTxtView, timeTxtView;
    String email, parkingLot, date, time, city;
    Button navigation;
    Double latitude, longitude;

    public ReservationConfirmFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation_confirm, container, false);

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra("email");
        city = intent.getStringExtra("city");
        parkingLot = intent.getStringExtra("parkingLot");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        latitude = intent.getDoubleExtra("latitude", 1.0);
        longitude = intent.getDoubleExtra("longitutde", 1.0);

        successTxtView = v.findViewById(R.id.reservation_fragment_success);
        successTxtView.setText("SUCCESSFUL RESERVATION!");

        cityTxtView = v.findViewById(R.id.reservation_fragment_city);
        cityTxtView.setText("City: " + city);

        parkingLotTxtView = v.findViewById(R.id.reservation_fragment_parkinglot);
        parkingLotTxtView.setText("Parking: " + parkingLot);

        dateTxtView = v.findViewById(R.id.reservation_fragment_date);
        dateTxtView.setText("Date: " + date);

        timeTxtView = v.findViewById(R.id.reservation_fragment_time);
        timeTxtView.setText("Time: " + time);
        navigation = (Button) v.findViewById(R.id.reservation_fragment_navigation);
        navigation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + latitude +"," + longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        return v;
    }

}