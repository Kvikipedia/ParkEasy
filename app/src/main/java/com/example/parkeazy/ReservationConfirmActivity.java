package com.example.parkeazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReservationConfirmActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirm);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        String city = extras.getString("city");
        String parkingLot = extras.getString("parkingLot");
        String date = extras.getString("date");
        String time = extras.getString("time");
        double latitude = extras.getDouble("latitude");
        double longitude = extras.getDouble("longitude");

        btn = (Button) findViewById(R.id.reservation_confirmation_toolbar_button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationConfirmActivity.this, MyReservationsActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });



    }

}