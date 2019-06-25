package com;
import java.util.Scanner;


// original race game simulator

public class TestDrive {

    public static void greetPlayer(){
        System.out.println("Welcome to INSANITY COURSE!\n" +
                "A perfect place to seek thrilling \n" +
                "and insane race courses in the world. \n" +
                "Enjoy! Buckle your seat belts!\n\n");
    }

    public static String promptPlayerName(){
        Scanner console = new Scanner(System.in);
        System.out.println("May I know your name player?");
        return console.nextLine();
    }

    public static void main(String[] args) {

       /* RaceGame game;
        String playerName;

        int bidderRaceBudget = 50;
        int trackLength = 100;

        greetPlayer();

        playerName = promptPlayerName();

        System.out.println();


        // new game
        game = new RaceGame(trackLength, playerName, bidderRaceBudget);
        game.startRace();*/


       /*RaceGameLogic raceGameLogic = new RaceGameLogic();

       Racer racer = new Racer("shareem", null);

        Vehicle raceVehicle = VehicleFactory.getAutomobile(AutomobileFactory.Type.SEDAN, "MODEL2");
        racer.setRaceVehicle(raceVehicle);

        raceGameLogic.insertRacerWithVehicle(racer);*/

        // load history

        /*RaceGameLogic raceGameLogic = new RaceGameLogic();

        Bidder player = raceGameLogic.getBidderByName("Shareem");
        RaceGame raceGame = raceGameLogic.getRaceGameOfPlayer(player);

        System.out.println("Racers for race game with ID: " + raceGame.getId());

        for(Racer racer: raceGame.getRacers()){
            System.out.println(racer.getName());
        }*/


    }
}
