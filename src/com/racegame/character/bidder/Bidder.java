package com.racegame.character.bidder;

import com.racegame.InputChecker;
import com.racegame.character.Racer;
import com.racegame.character.bidder.action.BidderActionStrategy;

import java.util.ArrayList;
import java.util.Scanner;

public class Bidder {

    private int id;

    private String name;

    private Racer racerBetOn;
    private int racerBetOnID;
    private int raceBudget;
    private boolean isPlayer;

    private ArrayList<BidderActionStrategy> bidderActions;

    public Bidder(String name, Racer racerBetOn, int raceBudget, ArrayList<BidderActionStrategy> bidderActions){
        this.racerBetOn = racerBetOn;
        this.raceBudget = raceBudget;
        this.name = name;

        this.bidderActions = bidderActions;
    }

    public void act(){
        int actionIndex = askBidderForAction();

        BidderActionStrategy selectedAction = bidderActions.get(actionIndex);

        selectedAction.setActingBidder(this);
        selectedAction.execute();
    }

    private void displayAllActions(){
        for (int i = 0; i < bidderActions.size(); i++) {
            System.out.println((i + 1) + " " + bidderActions.get(i).getDetails());
        }
    }

    private int askBidderForAction() {

        int actionIndex;

        if(this.isPlayer) {
            Scanner console = new Scanner(System.in);

            System.out.println(name + ", please select an action:");
            displayAllActions();

            String input = console.next();

            if(!InputChecker.checkIntInputInRange(input, bidderActions.size())){
                System.out.println("Invalid input");
                actionIndex = askBidderForAction();
            } else {
                actionIndex = Integer.parseInt(input) - 1;
            }

        } else {
            if (this.getRacerBetOn().isEvicted() || this.getRacerBetOn().hasFinishedTrack()) {
                actionIndex = 1;
            } else {
                actionIndex = (int) (Math.random() * bidderActions.size());
            }
        }

        return actionIndex;
    }

    // SETTERS
    public void setRacerBetOn(Racer racerBetOn) {
        this.racerBetOn = racerBetOn;
    }

    public Bidder setIsPlayer(boolean value){
        isPlayer = value;
        return this;
    }

    public void setRaceBudget(int raceBudget) {
        this.raceBudget = raceBudget;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRacerBetOnID(int racerBetOnID) {
        this.racerBetOnID = racerBetOnID;
    }

    public void setBidderActions(ArrayList<BidderActionStrategy> bidderActions) {
        this.bidderActions = bidderActions;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public Racer getRacerBetOn() {
        return racerBetOn;
    }

    public int getRaceBudget() {
        return raceBudget;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public int getId() {
        return id;
    }

    public int getRacerBetOnID() {
        return racerBetOnID;
    }
}
