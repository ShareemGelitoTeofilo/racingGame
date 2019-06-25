package com.vehicle;

import com.automobile.AutomobileFactory;

final public class VehicleFactory {

    public static Vehicle getAutomobile(AutomobileFactory.Type type, String model){
        Vehicle automobile = new AutomobileFactory(type, model).createVehicle();
        return automobile;
    }
}
