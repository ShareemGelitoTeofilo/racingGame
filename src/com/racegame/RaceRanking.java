package com.racegame;

import com.racegame.character.Racer;
import com.racegame.character.bidder.Bidder;
import com.vehicle.Vehicle;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;


public class RaceRanking {

    private int race_id;
    private ArrayList<Racer> racers;
    private int track_length;
    private Timestamp date;
    private int playerID;
    private Bidder player;

    public RaceRanking(int race_id, ArrayList<Racer> racers, int track_length, Timestamp date, int playerID){
        this.race_id = race_id;
        this.racers = racers;
        this.track_length = track_length;
        this.date = date;
        this.playerID = playerID;
    }

    public void display(){

        System.out.println("Ranking History of " + date.toString());
        System.out.println("Track length: " + track_length + " km.\n\n");

        int evictedCounter = 1;
        int finisherCounter = 1;

        Collections.sort(racers, Racer.rankingComparator);

        for(Racer racer: racers){

            if(racer.hasFinishedTrack()){
                if(finisherCounter++ == 1) System.out.println("Finishers:");
                displayRacer(racer);
            }

            if (racer.isEvicted()) {
                if(evictedCounter++ == 1)
                    System.out.println("Evicted:");
                displayRacer(racer);
            }
        }

    }

    private void displayRacer(Racer racer){
        System.out.print(racer.getRanking() +
                "   " + racer.getName() + " " + "(" + racer.getSpeed() + "kph)");
        if(player.getRacerBetOn().getId() == racer.getId())
            System.out.print(" (Player's racer)");

        System.out.println();

        Vehicle raceVehicle = racer.getRaceVehicle();

        System.out.println("    " + "Race Vehicle: " + raceVehicle.getModel() +
                " " + raceVehicle.getColor() + " " + raceVehicle.getBrand());
        System.out.println();
    }


    public int getRaceID() {
        return race_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public ArrayList<Racer> getRacers() {
        return racers;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayer(Bidder player) {
        this.player = player;
    }
}
