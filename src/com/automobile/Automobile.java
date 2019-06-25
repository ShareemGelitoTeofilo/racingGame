package com.automobile;

import com.vehicle.Vehicle;

public abstract class Automobile extends Vehicle {


    public Automobile(){
        super(new StartAutomobileEngineStrategy(), new TravelOnLandStrategy());
    }

    public Automobile(String modelNo, String brand, String color, String model){
        super(modelNo, brand, color, new StartAutomobileEngineStrategy(), new TravelOnLandStrategy(), model);
    }

    @Override
    public void honk(){
        System.out.println("Automobile honk.");
    }
}
