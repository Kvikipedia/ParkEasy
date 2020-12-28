package com.example.parkeazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class parkingAdapter extends RecyclerView.Adapter<parkingAdapter.ViewHolder> {

    Context context;
    ArrayList<DatabaseTables.ParkingLot> parkingLots;
    String email, city_name, date, time;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView parkingLotImage;
        TextView parkingLotName;
        TextView parkingLotFreeSpots;
        TextView parkingLotOccupiedSpots;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parkingLotImage = itemView.findViewById(R.id.parking_image);
            parkingLotName = itemView.findViewById(R.id.parking_name);
            parkingLotFreeSpots = itemView.findViewById(R.id.parking_freeSpots);
            parkingLotOccupiedSpots = itemView.findViewById(R.id.parking_occupiedSpots);
            button = itemView.findViewById(R.id.parking_button);
        }
    }

    public parkingAdapter(Context context, ArrayList<DatabaseTables.ParkingLot> parkingLots,
                          String email, String city_name, String date, String time) {
        this.context = context;
        this.parkingLots = parkingLots;
        this.email = email;
        this.city_name = city_name;
        this.date = date;
        this.time = time;
    }

    @NonNull
    @Override
    public parkingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.parking_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull parkingAdapter.ViewHolder holder, int position) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        int freeSpots = parkingLots.get(position).getSpots() -
                databaseHelper.getNumberOfReservation(city_name, parkingLots.get(position).getName(),
                        date, time);
        int occupiedSpots = databaseHelper.getNumberOfReservation(city_name,
                parkingLots.get(position).getName(), date, time);
        databaseHelper.close();
        holder.parkingLotImage.setImageResource(R.drawable.parking_symbol);
        holder.parkingLotName.setText(parkingLots.get(position).getName());
        holder.parkingLotFreeSpots.setText("" + freeSpots);
        holder.parkingLotOccupiedSpots.setText("" + occupiedSpots);
        holder.button.setText(R.string.reservation);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int threshold = databaseHelper.getNumberOfReservation(email);
                databaseHelper.close();
                if (threshold >= 3) {
                    Toast.makeText(context, "Maximum 3 reservations allowed!", Toast.LENGTH_SHORT).show();
                } else {
                    if (freeSpots > 0) {
                        String data = email + city_name + parkingLots.get(position).getName() + date + time;
                        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 20);
                        Bitmap bitmap = qrgEncoder.getBitmap();
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        databaseHelper.addReservation(new DatabaseTables.Reservation(-1, email, city_name,
                                parkingLots.get(position).getName(), date, time, bitmap));
                        databaseHelper.close();
                        Intent intent = new Intent(context, ReservationConfirmActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("city", city_name);
                        intent.putExtra("parkingLot", parkingLots.get(position).getName());
                        intent.putExtra("date", date);
                        intent.putExtra("time", time);
                        intent.putExtra("latitude", parkingLots.get(position).getLatitude());
                        intent.putExtra("longitutde", parkingLots.get(position).getLongitude());
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "No free spots at the moment!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingLots.size();
    }

}
