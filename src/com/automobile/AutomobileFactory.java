package com.automobile;

import com.vehicle.Vehicle;
import com.vehicle.VehicleAbstractFactory;

public class AutomobileFactory implements VehicleAbstractFactory {

    private Type type;
    private String model;

    public AutomobileFactory(Type type, String model){
        this.type = type;
        this.model = model;
    }

    @Override
    public Vehicle createVehicle() {

        Vehicle automobile = null;

        if(type == Type.SUV){
            automobile = new Suv(model);
        } else if(type == Type.SEDAN){
            automobile = new Sedan(model);
        } else if(type == Type.TANK){
            automobile = new Tank(model);
        }

        return automobile;
    }

    public enum Type {
        SUV, SEDAN, TANK
    }

    public enum Model {
        MODEL1, MODEL2, MODEL3, MODEL4
    }


}