package com.example.parkeazy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class reservationAdapter extends RecyclerView.Adapter<reservationAdapter.ViewHolder> {

    Context context;
    ArrayList<DatabaseTables.Reservation> reservations;
    String email;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView parkingQrImgView;
        TextView parkingLotName, parkingLotDate, parkingLotTime;
        Button navigation, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parkingLotName = itemView.findViewById(R.id.reservations_parking_name);
            parkingLotDate = itemView.findViewById(R.id.reservations_parking_date);
            parkingLotTime = itemView.findViewById(R.id.reservations_parking_time);
            parkingQrImgView = itemView.findViewById(R.id.reservations_parking_qr);
            delete = itemView.findViewById(R.id.reservations_button_delete);
            navigation = itemView.findViewById(R.id.reservations_button_navigation);
        }
    }

    public reservationAdapter(Context context, ArrayList<DatabaseTables.Reservation> reservations, String email){
        this.context = context;
        this.reservations = reservations;
        this.email = email;
    }

    @NonNull
    @Override
    public reservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reservation_single_item, parent, false);
        reservationAdapter.ViewHolder viewHolder = new reservationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull reservationAdapter.ViewHolder holder, int position) {
        holder.parkingLotName.setText(reservations.get(position).getParkingLot());
        holder.parkingLotDate.setText(reservations.get(position).getDate());
        holder.parkingLotTime.setText(reservations.get(position).getTime());
        holder.parkingQrImgView.setImageBitmap(reservations.get(position).getQr());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.deleteReservation(reservations.get(position));
                databaseHelper.close();
                reservations.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                Uri navigationIntentUri = Uri.parse("google.navigation:q=" +
                        databaseHelper.getParkingLot(reservations.get(position).getParkingLot()).getLatitude() +
                        "," + databaseHelper.getParkingLot(reservations.get(position).getParkingLot()).getLongitude());
                databaseHelper.close();
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }
}
