package com.racegame.application;

import com.racegame.RaceGame;
import java.util.Scanner;

class GameSimulator {

    private void greetPlayer(){
        System.out.println("Welcome to INSANITY COURSE!\n" +
                        "A perfect place to seek thrilling \n" +
                        "and insane race courses in the world. \n" +
                        "Enjoy! Buckle your seat belts!\n\n");
    }

    private String promptPlayerName(){
        Scanner console = new Scanner(System.in);
        System.out.println("May I know your name player?");
        return console.nextLine();
    }

    public static void main(String[] args) {

        GameSimulator gameSimulator = new GameSimulator();

        String playerName;
        int bidderRaceBudget = 50;
        int trackLength = 100;

        gameSimulator.greetPlayer();

        playerName = gameSimulator.promptPlayerName();
        System.out.println();

        RaceGame raceGame = new RaceGame(trackLength, playerName, bidderRaceBudget);
        raceGame.showMainMenu();

    }
}
