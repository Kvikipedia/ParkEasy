package com.example.parkeazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MyReservationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter reservationAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        // Get all the parking lots in the city
        final ArrayList<DatabaseTables.Reservation> reservations = databaseHelper.getAllReservation(email);
        databaseHelper.close();

        recyclerView = findViewById(R.id.my_reservations_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reservationAdapter = new reservationAdapter(this, reservations, email);
        recyclerView.setAdapter(reservationAdapter);
    }
}