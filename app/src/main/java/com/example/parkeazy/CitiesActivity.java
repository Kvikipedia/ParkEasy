package com.example.parkeazy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class CitiesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter cityAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");

        //Create array of cities
        final ArrayList<City> cities = new ArrayList<City>();
        cities.add(new City("Skopje", 9, R.drawable.city_skopje));
        cities.add(new City("Bitola", 8, R.drawable.city_bitola));
        cities.add(new City("Kumanovo", 8, R.drawable.city_kumanovo));
        cities.add(new City("Prilep", 8, R.drawable.city_prilep));
        cities.add(new City("Tetovo", 7, R.drawable.city_tetovo));
        cities.add(new City("Ohrid", 7, R.drawable.city_ohrid));
        cities.add(new City("Veles", 7, R.drawable.city_veles));
        cities.add(new City("Strumica", 7, R.drawable.city_strumica));

        button = (Button) findViewById(R.id.cities_toolbar_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(CitiesActivity.this, MyReservationsActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
        });

        recyclerView = findViewById(R.id.cities_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cityAdapter = new cityAdapter(this, cities, email);
        recyclerView.setAdapter(cityAdapter);

    }
}