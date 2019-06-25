package com.racegame.character;

import com.vehicle.Vehicle;

import java.util.Comparator;

public class Racer {

    private int id;

    private String name;
    private int raceID;
    private int initialSpeed;
    private int speed;
    private int distanceTraveled;
    private Boolean hasFinishedTrack = false;
    private Boolean isEvicted = false;
    private int ranking;
    private Vehicle raceVehicle;


    public Racer(String name, Vehicle raceVehicle){
        this.name = name;
        this.raceVehicle = raceVehicle;
    }

    public void initializeSpeed(int trackLength){
        speed = (int) (Math.random() * (trackLength / 4)) + 1;
        setInitialSpeed(speed);
    }

    public void accelerate(int trackLength){

        distanceTraveled += speed;

        if(distanceTraveled >= trackLength){
            hasFinishedTrack = true;
        }
    }

    public Boolean hasFinishedTrack(){
        return hasFinishedTrack;
    }

    public void increaseSpeed(int amount){
        speed += amount;
    }

    public void decreaseSpeed(int amount){
        speed -= amount;
        if(speed <= 0){
            setAsEvicted(true);
        }
    }

    // SETTERS
    public void setAsEvicted(Boolean evicted) {
        isEvicted = evicted;
    }

    public void setRaceVehicle(Vehicle raceVehicle) {
        this.raceVehicle = raceVehicle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setInitialSpeed(int initialSpeed) {
        this.initialSpeed = initialSpeed;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setHasFinishedTrack(Boolean hasFinishedTrack) {
        this.hasFinishedTrack = hasFinishedTrack;
    }

    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getInitialSpeed() {
        return initialSpeed;
    }

    public Vehicle getRaceVehicle() {
        return raceVehicle;
    }

    public Boolean isEvicted() {
        return isEvicted;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getId() {
        return id;
    }

    public int getRanking() {
        return ranking;
    }

    public int getRaceID() {
        return raceID;
    }

    public static Comparator<Racer> distanceTraveledComparator = new Comparator<Racer>(){
        @Override
        public int compare(Racer o1, Racer o2) {
            return o2.distanceTraveled - o1.distanceTraveled;
        }
    };

    public static Comparator<Racer> rankingComparator = new Comparator<Racer>() {
        @Override
        public int compare(Racer o1, Racer o2) {

            if(o1.hasFinishedTrack() && o2.hasFinishedTrack()){

                return o1.getRanking() - o2.getRanking();

            } else if(o1.isEvicted() && o2.isEvicted()){

                return o1.getRanking() - o2.getRanking();

            } else if(o1.hasFinishedTrack() && o2.isEvicted()){

                return -1;

            } else if(o1.isEvicted() && o2.hasFinishedTrack()){

                return 1;
            }

            return 0;
        }
    };



}
