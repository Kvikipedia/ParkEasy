package com.example.parkeazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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


public class cityAdapter extends RecyclerView.Adapter<cityAdapter.ViewHolder> {

    Context context;
    ArrayList<City> cities;
    String email;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cityImgView;
        TextView cityName;
        TextView cityParkingLot;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cityImgView = itemView.findViewById(R.id.city_image);
            cityName = itemView.findViewById(R.id.city_name);
            cityParkingLot = itemView.findViewById(R.id.city_parkings);
            button = itemView.findViewById(R.id.city_button);
        }
    }

    public cityAdapter(Context context, ArrayList<City> cities, String email){
        this.context = context;
        this.cities = cities;
        this.email = email;
    }

    @NonNull
    @Override
    public cityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.city_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull cityAdapter.ViewHolder holder, int position) {
        holder.cityImgView.setImageResource(cities.get(position).getImageResourceId());
        holder.cityName.setText(cities.get(position).getCityName());
        holder.cityParkingLot.setText("Parking lots: " + cities.get(position).getNumberOfParkingLots());
        holder.button.setText(R.string.reservation);

        holder.button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                int threshold = databaseHelper.getNumberOfReservation(email);
                databaseHelper.close();
                if (threshold >= 3) {
                    Toast.makeText(context, "Maximum 3 reservations allowed!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, ReservationFormActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("cityName", cities.get(position).getCityName());
                    intent.putExtra("cityParkingLots", cities.get(position).getNumberOfParkingLots());
                    intent.putExtra("cityImage", cities.get(position).getImageResourceId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
