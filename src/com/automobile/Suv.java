package com.automobile;

public class Suv extends Automobile {

    public Suv(String modelNo, String brand, String color, String model){
        super(modelNo, brand, color, model);
    }

    public Suv(String model){
        super();
        setSuv(model);
    }

    public void setSuv(String model){

        String modelNo = "";
        String brand = "";
        String color = "";

        switch(model)
        {
            case "MODEL1":
                modelNo = "MODEL1";
                brand = "Toyota";
                color = "Red";
                break;
            case "MODEL2":
                modelNo = "MODEL2";
                brand = "Nissan";
                color = "Black";
                break;
            case "MODEL3":
                modelNo = "MODEL3";
                brand = "Ford";
                color = "White";
                break;
            case "MODEL4":
                modelNo = "MODEL4";
                brand = "Honda";
                color = "Navy Blue";
                break;
        }

        this.setModelNo(modelNo);
        this.setBrand(brand);
        this.setColor(color);
        this.setModel(model);
    }

    @Override
    public void honk() {
        System.out.println("Suv honk");
    }

}
