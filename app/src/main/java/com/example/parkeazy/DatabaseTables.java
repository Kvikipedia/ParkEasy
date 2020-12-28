package com.example.parkeazy;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class DatabaseTables {

    public static class User {

        private int id;
        private String name;
        private String email;
        private String password;

        public User(int id, String name, String email, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public User() {
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class ParkingLot {

        private int id;
        private String name;
        private String city;
        private int spots;
        private double latitude;
        private double longitude;

        public ParkingLot(int id, String name, String city, int spots, double latitude, double longitude) {
            this.id = id;
            this.name = name;
            this.city = city;
            this.spots = spots;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public ParkingLot() {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getSpots() {
            return spots;
        }

        public void setSpots(int spots) {
            this.spots = spots;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @NonNull
        @Override
        public String toString() {
            return "ParkingLot(" +
                    "id= " + id +
                    ", name= " + name + '\'' +
                    ", city= " + city + '\'' +
                    ", spots= " + spots + '\'' +
                    ", latitude= " + latitude + '\'' +
                    ", longitude= " + longitude +
                    '}';
        }
    }

    public static class Reservation {

        private int id;
        private String email;
        private String city;
        private String parkingLot;
        private String date;
        private String time;
        private Bitmap qr;

        public Reservation(int id, String email, String city, String parkingLot, String date, String time, Bitmap qr) {
            this.id = id;
            this.email = email;
            this.city = city;
            this.parkingLot = parkingLot;
            this.date = date;
            this.time = time;
            this.qr = qr;
        }

        public Reservation() {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getParkingLot() {
            return parkingLot;
        }

        public void setParkingLot(String parkingLot) {
            this.parkingLot = parkingLot;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Bitmap getQr() {
            return qr;
        }

        public void setQr(Bitmap qr) {
            this.qr = qr;
        }
    }
}

