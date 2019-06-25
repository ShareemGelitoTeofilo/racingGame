package com.racegame.character.bidder.action;

import com.racegame.InputChecker;
import com.racegame.character.Racer;
import com.racegame.character.bidder.Bidder;

import java.util.ArrayList;
import java.util.Scanner;

public class PutObstacleToTargetStrategy implements BidderActionStrategy {

    Scanner console = new Scanner(System.in);

    private ArrayList<Obstacle> obstacles;
    private Obstacle selectedObstacle;

    private ArrayList<Racer> targets;
    private Racer target;

    private Bidder bidder;

    public PutObstacleToTargetStrategy(ArrayList<Obstacle> obstacles, ArrayList<Racer> targets){
        this.obstacles = obstacles;
        this.targets = targets;
    }

    @Override
    public void execute() {

        setTargetAndObstacle();

        target.decreaseSpeed(selectedObstacle.getEffect());
        bidder.setRaceBudget(bidder.getRaceBudget() - selectedObstacle.getCost());

        displayResult();
    }

    @Override
    public void displayResult() {

        System.out.println(bidder.getName() + " gave obstacle (" + selectedObstacle.getName() + ") to racer " + target.getName());
        System.out.println(target.getName() + "'s speed decreased from "
                + (target.getSpeed() + selectedObstacle.getEffect()) + " to " + target.getSpeed());
        System.out.println();
    }

    private void setTargetAndObstacle(){

        int targetIndex = promptPlayerForTarget();
        int obstacleIndex = promptPlayerForObstacle();

        setTarget(targets.get(targetIndex));
        setSelectedObstacle(obstacles.get(obstacleIndex));

    }

    private void displayAllAvailableTargets(){
        for (int i = 0; i < targets.size(); i++) {
            Racer selectionTarget = targets.get(i);
            if (!(bidder.getRacerBetOn().getName().equals(selectionTarget.getName()) ||
                    selectionTarget.hasFinishedTrack() || selectionTarget.isEvicted())) {
                System.out.println(i + 1 + " --- " + selectionTarget.getName());
            }
        }
    }

    private void displayAllObstacles(){
        for (int i = 0; i < obstacles.size(); i++) {
            System.out.println(i + 1 + " --- " + obstacles.get(i).getName());
        }
    }

    private int promptPlayerForTarget(){

        int targetIndex;

        if(bidder.isPlayer()) {

            Scanner console = new Scanner(System.in);

            System.out.println("Choose target: ");
            displayAllAvailableTargets();

            String targetIndexInput = console.next();

            if(!InputChecker.checkIntInputInRange(targetIndexInput, targets.size())){
                System.out.println("Invalid input");
                targetIndex = promptPlayerForTarget();
            } else {
                targetIndex = Integer.parseInt(targetIndexInput) - 1;
            }

        } else {

            int selectionTargetIndex;
            while(true){
                selectionTargetIndex = ((int) (Math.random() * targets.size()));
                Racer selectionTarget = targets.get(selectionTargetIndex);
                if (!(selectionTarget.equals(bidder.getRacerBetOn()) ||
                        selectionTarget.hasFinishedTrack()|| selectionTarget.isEvicted())) {
                    targetIndex = selectionTargetIndex;
                    break;
                }
            }
        }

        return targetIndex;

    }

    private int promptPlayerForObstacle(){

        int obstacleIndex;

        if(bidder.isPlayer()) {
            System.out.println("Select obstacle: ");
            displayAllObstacles();

            String input = console.next();

            if (!InputChecker.checkIntInputInRange(input, obstacles.size())) {
                System.out.println("Invalid input");
                obstacleIndex = promptPlayerForObstacle();
            } else {
                obstacleIndex = Integer.parseInt(input) - 1;
            }

        } else {
            obstacleIndex = (int) (Math.random() * obstacles.size());
        }

        return obstacleIndex;

    }


    // GETTERS
    public String getDetails(){
        return "Put obstacle to a racer";
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    // SETTERS
    @Override
    public void setActingBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public void setSelectedObstacle(Obstacle selectedObstacle) {
        this.selectedObstacle = selectedObstacle;
    }

    public void setTarget(Racer target) {
        this.target = target;
    }


}
