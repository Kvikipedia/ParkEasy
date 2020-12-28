package com.example.parkeazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ParkingPlacesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter parkingAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_places);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        String city_name = extras.getString("cityName");
        String date = extras.getString("date");
        String time = extras.getString("time");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        // Get all the parking lots in the city
        final ArrayList<DatabaseTables.ParkingLot> parkingLots = databaseHelper.getAllParkingLot(city_name);
        databaseHelper.close();

        button = (Button) findViewById(R.id.parkinglots_toolbar_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkingPlacesActivity.this, MyReservationsActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.parkingLots_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        parkingAdapter = new parkingAdapter(this, parkingLots, email, city_name, date, time);
        recyclerView.setAdapter(parkingAdapter);

    }
}