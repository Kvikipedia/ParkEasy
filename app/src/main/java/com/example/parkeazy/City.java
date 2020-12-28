package com.example.parkeazy;

public class City {

    private String name;
    private int numberOfParkingLots;
    private int imageResourceId;

    public City(String name, int numberOfParkingLots, int imageResourceId) {
        this.name = name;
        this.numberOfParkingLots = numberOfParkingLots;
        this.imageResourceId = imageResourceId;
    }

    public String getCityName() {
        return name;
    }

    public int getNumberOfParkingLots() {
        return numberOfParkingLots;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

}

