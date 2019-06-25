package com.racegame.character.bidder.action;

public class Booster {

    private String name;
    private int effect;
    private int cost;

    public Booster(String name, int effect, int cost){
        this.name = name;
        this.effect = effect;
        this.cost = cost;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public int getEffect() {
        return effect;
    }

    public int getCost() {
        return cost;
    }
}
