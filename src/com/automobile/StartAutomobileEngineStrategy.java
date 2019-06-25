package com.automobile;

import com.vehicle.StartEngineStrategy;

public class StartAutomobileEngineStrategy implements StartEngineStrategy {

    public void execute(){
        System.out.println("Automobile starting engine.");
    }
}
