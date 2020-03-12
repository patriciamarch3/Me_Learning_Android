package com.example.fragmentsrecyclerviewchallenge;

public class Car {
    private String make;
    private String model;
    private String ownerName;
    private String ownerNumber;

    public Car(String make, String model, String ownerName, String ownerNumber) {
        this.make = make;
        this.model = model;
        this.ownerName = ownerName;
        this.ownerNumber = ownerNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        this.ownerNumber = ownerNumber;
    }
}
