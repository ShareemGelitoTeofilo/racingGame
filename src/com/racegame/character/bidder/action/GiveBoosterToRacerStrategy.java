package com.racegame.character.bidder.action;

import com.racegame.InputChecker;
import com.racegame.character.Racer;
import com.racegame.character.bidder.Bidder;

import java.util.ArrayList;
import java.util.Scanner;

public class GiveBoosterToRacerStrategy implements BidderActionStrategy {

    private ArrayList<Booster> boosters;
    private Booster selectedBooster;
    private Racer racerToBoost;
    private Bidder bidder;

    public GiveBoosterToRacerStrategy(ArrayList<Booster> boosters){
        this.boosters = boosters;
    }

    @Override
    public void execute() {

        setRacerToBoost(bidder.getRacerBetOn());
        setSelectedBooster(boosters.get(promptBidderForBooster()));

        racerToBoost.increaseSpeed(selectedBooster.getEffect());
        bidder.setRaceBudget(bidder.getRaceBudget() - selectedBooster.getCost());

        displayResult();
    }

    @Override
    public void displayResult(){
        System.out.println(bidder.getName() + " gave booster (" + selectedBooster.getName() + ") to racer " + racerToBoost.getName());
        System.out.println(racerToBoost.getName() + "'s speed increased from "
                + (racerToBoost.getSpeed() - selectedBooster.getEffect()) + " to " + racerToBoost.getSpeed());
        System.out.println();
    }

    private void displayAllBoosters(){
        for(int i = 0; i < boosters.size(); i++){
            System.out.println((i + 1) + " --- " + boosters.get(i).getName());
        }
    }

    private int promptBidderForBooster(){

        Scanner console = new Scanner(System.in);

        int boosterIndex;

        if(bidder.isPlayer()){

            System.out.println("Select booster: ");
            displayAllBoosters();

            String input = console.next();

            if(!InputChecker.checkIntInputInRange(input, boosters.size())){
                System.out.println("Invalid input\n");
                boosterIndex = promptBidderForBooster();
            } else {
                boosterIndex = Integer.parseInt(input) - 1;
            }

        } else {
            // auto-generated
            boosterIndex = ((int) (Math.random() * boosters.size()));
        }

        return boosterIndex;

    }

    // GETTERS
    public String getDetails(){
        return "Give booster to your racer";
    }

    public ArrayList<Booster> getBoosters() {
        return boosters;
    }

    // SETTERS
    private void setSelectedBooster(Booster selectedBooster) {
        this.selectedBooster = selectedBooster;
    }

    @Override
    public void setActingBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public void setRacerToBoost(Racer racer) {
        this.racerToBoost = racer;
    }

}
