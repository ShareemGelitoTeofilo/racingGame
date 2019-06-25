package com.vehicle;


public abstract class Vehicle {

    private int id;

    private StartEngineStrategy startEngine;
    private TravelStrategy travel;

    private String modelNo;
    private String brand;
    private String color;

    private String model;
    private  int racerId;

    public Vehicle(StartEngineStrategy startEngine, TravelStrategy travel){
        this.startEngine = startEngine;
        this.travel = travel;
    }

    public Vehicle(String modelNo, String brand, String color,
                   StartEngineStrategy startEngine, TravelStrategy travel, String model){
        this.modelNo = modelNo;
        this.brand = brand;
        this.color = color;
        this.startEngine = startEngine;
        this.travel = travel;
        this.model = model;
    }

    public abstract void honk();

    public void travel(){
        travel.execute();
    }

    public void startEngine(){
        startEngine.execute();
    }

    public void turnLightsOn(){
        System.out.println("Vehicle turning lights ON.");
    }

    public void turnLightsOff(){
        System.out.println("Vehicle turning lights OFF.");
    }

    // SETTERS
    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setId(int id) {
        this.id = id;
    }

    // GETTERS
    public String getModelNo() {
        return modelNo;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getModel() {
        return model;
    }

    public int getId() {
        return id;
    }
}
