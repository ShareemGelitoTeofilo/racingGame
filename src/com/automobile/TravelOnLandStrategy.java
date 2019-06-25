package com.automobile;

import com.vehicle.TravelStrategy;

public class TravelOnLandStrategy implements TravelStrategy {

    public void execute(){
        System.out.println("Traveling on land with wheels.");
    }
}
