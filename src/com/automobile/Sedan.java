package com.automobile;

public class Sedan extends Automobile {

    public Sedan(String modelNo, String brand, String color, String model){
        super(modelNo, brand, color, model);
    }

    public Sedan(String model){
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
                brand = "Porsche";
                color = "Gold";
                break;
            case "MODEL2":
                modelNo = "MODEL2";
                brand = "Mercedes-Benz";
                color = "Silver Gray";
                break;
            case "MODEL3":
                modelNo = "MODEL3";
                brand = "BMW";
                color = "Purple";
                break;
            case "MODEL4":
                modelNo = "MODEL4";
                brand = "Bugatti";
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
        System.out.println("Sedan honk");
    }


}
