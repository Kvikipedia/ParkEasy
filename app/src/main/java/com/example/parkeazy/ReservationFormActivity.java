package com.example.parkeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ReservationFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView city_img;
    TextView date_picker, city_desc, city_name;
    DatePickerDialog datePickerDialog;
    int year, month, day;
    Calendar calendar;
    Spinner spinner;
    Button button, tlb_button;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        String cityName = extras.getString("cityName");
        int cityParkingLots = extras.getInt("cityParkingLots");
        int cityImage = extras.getInt("cityImage");

        city_name = (TextView) findViewById(R.id.reservation_cityName);
        city_name.setText(cityName);

        city_img = (ImageView) findViewById(R.id.reservation_cityImage);
        city_img.setImageResource(cityImage);

        city_desc = (TextView) findViewById(R.id.reservation_cityParkingLots);
        String temp = "Parking lots: " + cityParkingLots;
        city_desc.setText(temp);

        spinner = findViewById(R.id.reservation_set_time_spinner);
        date_picker = (TextView) findViewById(R.id.reservation_set_date_picker);
        button = (Button) findViewById(R.id.reservation_button);

        tlb_button = (Button) findViewById(R.id.reservation_form_toolbar_button);
        tlb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationFormActivity.this, MyReservationsActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        date_picker.setOnClickListener((view) -> {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(ReservationFormActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            date_picker.setText(day + "/" + (month + 1) + "/" + year);
                        }
                    }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time_slots,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (date_picker.getText().toString().trim().matches("")) {
                    Toast.makeText(ReservationFormActivity.this,
                            "You need to enter a date!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ReservationFormActivity.this, ParkingPlacesActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("cityName", cityName);
                    intent.putExtra("date", date_picker.getText().toString());
                    intent.putExtra("time", time);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        time = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}