package com.automobile;

import com.vehicle.CombatVehicle;

public class Tank extends Automobile implements CombatVehicle {

    public Tank(String modelNo, String brand, String color, String model){
        super(modelNo, brand, color, model);
    }

    public Tank(String model){
        super();
        setTank(model);
    }

    public void setTank(String model){

        String modelNo = "";
        String brand = "";
        String color = "";

        switch(model)
        {
            case "MODEL1":
                modelNo = "MODEL1";
                brand = "Black Panther";
                color = "Navy Green";
                break;
            case "MODEL2":
                modelNo = "MODEL2";
                brand = "Leopard";
                color = "Brown & Black";
                break;
            case "MODEL3":
                modelNo = "MODEL3";
                brand = "Armata";
                color = "Green";
                break;
            case "MODEL4":
                modelNo = "MODEL4";
                brand = "Merkava";
                color = "Camouflage";
                break;
        }

        this.setModelNo(modelNo);
        this.setBrand(brand);
        this.setColor(color);
        this.setModel(model);
    }

    @Override
    public void honk() {
        System.out.println("Tank honk");
    }

    @Override
    public void aim() {
        System.out.println("Tank targeting enemy.");
    }

    @Override
    public void reload() {
        System.out.println("Tank reloading weapon.");
    }

    @Override
    public void fire() {
        System.out.println("Tank firing at enemy.");
    }

}
