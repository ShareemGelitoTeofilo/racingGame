package com.racegame;

import com.automobile.AutomobileFactory;
import com.database.RaceGameLogic;
import com.racegame.character.Racer;
import com.racegame.character.bidder.Bidder;
import com.racegame.character.bidder.action.*;
import com.racegame.savinggame.*;
import com.vehicle.Vehicle;
import com.vehicle.VehicleFactory;

import java.sql.Timestamp;
import java.util.*;

public class RaceGame {

    private Scanner console = new Scanner(System.in);

    private int id;

    private String playerName;
    private Boolean playerIsNew;

    private int trackLength;
    private int bidderRacerBudget;

    private ArrayList<Bidder> bidders;
    private ArrayList<Racer> racers;
    private ArrayList<Racer> finishers;
    private ArrayList<Racer> evicted;

    private java.sql.Timestamp date;

    public RaceGame(int trackLength, String playerName, int bidderRacerBudget){

      this.racers = new ArrayList<>();
      this.bidders = new ArrayList<>();
      this.finishers = new ArrayList<>();
      this.evicted = new ArrayList<>();

      this.trackLength = trackLength;
      this.playerName = playerName;
      this.bidderRacerBudget = bidderRacerBudget;

      this.date = new java.sql.Timestamp(new Date().getTime());

    }


    public void showMainMenu(){

        Boolean playerIsNew = identifyIfPlayerIsNew();

        if(playerIsNew){
            loadNewPlayerMenu();
        } else {
            loadExistingPlayerMenu();
        }

    }

    private void loadNewPlayerMenu(){

        System.out.println("Select:\n" +
                           "1 Start New Game\n" +
                           "2 Quit Game");


        String input = console.nextLine();
        int selection;

        if(InputChecker.checkIntInputInRange(input, 2)){
            selection = Integer.parseInt(input);

            if(selection == 1){
                startGame();
            } else {
                quitGame();
            }

        } else {
            System.out.println("Invalid input");
            loadNewPlayerMenu();
        }
    }

    private void loadExistingPlayerMenu(){

        System.out.println("Select:\n" +
                "1 Start New Game\n" +
                "2 Show Ranking History\n" +
                "3 Quit Game");


        String input = console.nextLine();
        int selection;

        if(InputChecker.checkIntInputInRange(input, 3)){
            selection = Integer.parseInt(input);

            if(selection == 1){
                startGame();
            } else if(selection == 2) {
                showRaceRankingHistory();
                showMainMenu();
            } else {
                quitGame();
            }
        } else {
            System.out.println("Invalid input");
            loadExistingPlayerMenu();
        }
    }


    private void startGame(){

        init();

        if(playerStart()) {
            startRace();
            displayRanking();
            saveGame();

            reset();
            showMainMenu();
        }
    }

    private Boolean playerStart(){

        System.out.println("Racers on your marks!\n" +
                playerName + ", you do the trigger.\n" +
                "We're all set. Pump it when your ready. (Press enter)");
        while(true){
            if(console.nextLine().length() == 0){
                for(int i = 0; i<3; i++){
                    System.out.println(".............");
                }
                System.out.println();
                return true;
            }
        }
    }

    private void startRace(){

        int pointOfRace = 0;

        while (racersStillRacing()) {

            for(Racer currentRacer: racers){
                if (currentRacer.hasFinishedTrack() || currentRacer.isEvicted()) {
                    continue;
                }
                currentRacer.accelerate(trackLength);
                checkForRacerStatusUpdates();
            }

            if (racersStillRacing()) {
                if ((pointOfRace % 4) == 0) {

                    if (pointOfRace == 0){ displayRaceStatus(); }

                    letBiddersAct();

                    if (racersStillRacing()) { displayRaceStatus(); }
                    System.out.println("---------");
                    System.out.println("---------");
                    System.out.println("---------\n");
                }
            }

            pointOfRace++;
        }

        System.out.println("GAME OVER!\n");
    }

    private void letBiddersAct(){
        for(Bidder bidder: bidders){
            if(!racersStillRacing())
                break;
            bidder.act();
            checkForRacerStatusUpdates();
        }
    }

    private void checkForRacerStatusUpdates(){

        for(Racer racer: racers){

            if(racer.isEvicted()){
                if(!evicted.contains(racer)) {
                    evicted.add(racer);
                    System.out.println("\n" + racer.getName() + " EVICTED.\n");
                }
            }

            if(racer.hasFinishedTrack()) {
                if (!finishers.contains(racer)) {
                    System.out.println("\n" + racer.getName() + " FINISHES.\n");
                    finishers.add(racer);
                }
            }
        }
    }

    private boolean racersStillRacing(){
        boolean val = true;
        if(racers.size() == (finishers.size() + evicted.size())) {
            val = false;
        }
        return val;
    }

    private void displayRaceStatus(){

        System.out.println("CURRENT RACE STATUS: \n");

        Collections.sort(racers, Racer.distanceTraveledComparator);

        int count = 1;
        for(Racer racer: racers){
            if(racer.isEvicted() || racer.hasFinishedTrack())
                continue;
            System.out.println(count + "    " + racer.getName() + "(" + racer.getSpeed() + "km/h) ");
            System.out.println("Distance traveled: " + racer.getDistanceTraveled() + "\n");
            count++;
        }

        System.out.println("\n");
    }

    private void setRacerRankings(){

        for(int i = 0; i < finishers.size(); i++){
            finishers.get(i).setRanking(i+1);
        }

        for(int j = 0; j < evicted.size(); j++){
            evicted.get(j).setRanking(j+1);
        }

    }

    private void displayRanking(){

        setRacerRankings();

        System.out.println("TODAY'S RANKING:");

        int finisherCounter = 1;
        for(Racer finisher: finishers){
            System.out.println((finisherCounter++) +
                    "   " + finisher.getName());
        }

        System.out.println();

        int evictedCounter = 1;
        for(Racer evicted: evicted){
            if (evictedCounter == 1)
                System.out.println("Evicted:");

            System.out.println((evictedCounter++) +
                    "   " + evicted.getName());
        }
        System.out.println();
    }

    private void reset(){
        bidders.clear();
        racers.clear();
        finishers.clear();
        evicted.clear();
    }

    private void quitGame(){
        System.exit(0);
    }

    private void showRaceRankingHistory(){

        RaceGameLogic raceGameLogic = new RaceGameLogic();
        Bidder player = raceGameLogic.getBidderByName(playerName);

        ArrayList<RaceRanking> raceRankings = raceGameLogic.getAllRaceRankingOfPlayer(player.getId());
        displayListOfRankHistory(raceRankings);

        int rankingHistoryIndex = promptToSelectRankingHistory(raceRankings.size());
        raceRankings.get(rankingHistoryIndex).display();

    }

    private void displayListOfRankHistory(ArrayList<RaceRanking> raceRankings){
        System.out.println("List of race rankings: ");
        for(int i = 0; i < raceRankings.size(); i++){
            System.out.println((i + 1) + "   " + raceRankings.get(i).getDate().toString());
        }
    }

    private int promptToSelectRankingHistory(int range){

        Scanner console = new Scanner(System.in);
        System.out.println("Please enter a number to view: ");

        String input  = console.next();

        if(InputChecker.checkIntInputInRange(input, range)){
            return (Integer.parseInt(input)) - 1;
        } else {
            System.out.println("Invalid input");
            promptToSelectRankingHistory(range);
        }

        return 0;
    }


    private void init() {

        playerIsNew = identifyIfPlayerIsNew();

        if(playerIsNew){
            initForNewPlayer();
        } else {
            initForExistingPlayer();
        }

    }

    private void initForNewPlayer() {

        int noOfBidders = promptPlayerForNumberOfBidders();

        ArrayList<Racer> generatedRacers =  generateRacersWithVehicles(noOfBidders);
        racers.addAll(generatedRacers);

        initializeRacersSpeed();

        ArrayList<Bidder> generatedBidders = generateBiddersWithActions(noOfBidders, generateBidderActions(racers));
        bidders.addAll(generatedBidders);


        displayListOfCompetingRacers();
        int playerChosenRacerIndex = promptPlayerPickARacer();

        assignBiddersWithRacers(playerChosenRacerIndex);

    }

    private void initForExistingPlayer() {

        RaceGameLogic raceGameLogic = new RaceGameLogic();

        Bidder player = raceGameLogic.getBidderByName(playerName);
        RaceGame loadGame = raceGameLogic.getRaceGameOfPlayer(player);

        setRacers(loadGame.getRacers());
        setBidders(loadGame.getBidders());

        for(Bidder bidder: bidders){
            bidder.setBidderActions(generateBidderActions(racers));
        }

    }

    private Boolean identifyIfPlayerIsNew() {

        RaceGameLogic raceGameLogic = new RaceGameLogic();

        // check if player is existing in database;
        Bidder player = raceGameLogic.getBidderByName(playerName);

        return player == null;

    }

    private void saveGame(){

        SaveGameStrategy saveGameStrategy;

        if(playerIsNew){
            saveGameStrategy = new SaveGameForNewPlayer(this);
        } else {
            saveGameStrategy = new SaveGameForExistingPlayer(this);
        }

        saveGameStrategy.saveGame();

    }

    private int promptPlayerForNumberOfBidders() {

        int noOfBidders;

        System.out.println("How many bidders are present today? Including you " + playerName + " :)\n");
        String input = console.next();

        if(!InputChecker.checkIntInputValid(input)){
            System.out.println("Invalid input");
            noOfBidders = promptPlayerForNumberOfBidders();
        } else {
            noOfBidders = Integer.parseInt(input);
        }

        return noOfBidders;

    }

    private ArrayList<Racer> generateRacersWithVehicles(int noOfBidders){

        ArrayList<Racer> generatedRacersWithVehicles = new ArrayList<>();

        for(int i = 0; i < noOfBidders; i++) {
            Vehicle racingVehicle = generateVehicle();
            Racer racer = new Racer("Racer" + i, racingVehicle);

            generatedRacersWithVehicles.add(racer);
        }

        return generatedRacersWithVehicles;
    }
    private Vehicle generateVehicle() {

        Vehicle generatedVehicle;

        AutomobileFactory.Type[] types = AutomobileFactory.Type.values();
        int randType = (int) (Math.random() * types.length);
        AutomobileFactory.Type type = types[randType];

        AutomobileFactory.Model[] models = AutomobileFactory.Model.values();
        int randModel = (int) (Math.random() * models.length);
        String model = models[randModel].toString();

        generatedVehicle = VehicleFactory.getAutomobile(type, model);

        return generatedVehicle;
    }

    private ArrayList<Bidder> generateBiddersWithActions(int noOfBidders, ArrayList<BidderActionStrategy> actions){

        ArrayList<Bidder> generateBidders = new ArrayList<>();

        for(int i = 0; i < noOfBidders; i++){
            if(i == 0){
                generateBidders.add(new Bidder(
                        playerName, null, bidderRacerBudget, actions).setIsPlayer(true));
            } else {
                generateBidders.add(new Bidder(
                        "Bidder" + i, null, bidderRacerBudget, actions));
            }
        }

        return generateBidders;
    }
    private ArrayList<BidderActionStrategy> generateBidderActions(ArrayList<Racer> racers){

        ArrayList<Booster> boosters = new ArrayList<>();
        Booster vitamin = new Booster("Vitamin", 3, 25);
        Booster turbo = new Booster("Turbo", 5, 35);

        boosters.add(vitamin);
        boosters.add(turbo);

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        Obstacle nails = new Obstacle("Nails", 6, 20);
        Obstacle bananaPeel = new Obstacle("Banana peel", 5, 35);

        obstacles.add(nails);
        obstacles.add(bananaPeel);

        ArrayList<BidderActionStrategy> actions = new ArrayList<>();

        GiveBoosterToRacerStrategy action1 = new GiveBoosterToRacerStrategy(boosters);
        PutObstacleToTargetStrategy action2 = new PutObstacleToTargetStrategy(obstacles, racers);

        actions.add(action1);
        actions.add(action2);

        return actions;

    }

    private void assignBiddersWithRacers(int playerChosenRacerIndex){

        ArrayList<Racer> racersForBots = (ArrayList<Racer>) racers.clone();
        racersForBots.remove(playerChosenRacerIndex);

        for (Bidder bidder: bidders) {
            if (bidder.isPlayer()) {
                bidder.setRacerBetOn(racers.get(playerChosenRacerIndex));
            } else {
                bidder.setRacerBetOn(racersForBots.get(0));
                racersForBots.remove(0);
            }
        }

    }

    private void displayListOfCompetingRacers() {

        System.out.println("Here's our list of racers today:\n");
        int counter = 1;
        for(Racer racer: racers){
            System.out.println(counter + "    " + racer.getName());
            Vehicle raceVehicle = racer.getRaceVehicle();

            System.out.println("Race car: "
                    + raceVehicle.getColor() + " "
                    + raceVehicle.getBrand() + " "
                    + "(" + raceVehicle.getModelNo() + ")");
            System.out.println();

            counter++;
        }

    }

    private int promptPlayerPickARacer() {

        int playerChosenRacerIndex;

        System.out.println(playerName + ", pick your racer: ");
        String input = console.next();
        console.nextLine();

        if(!InputChecker.checkIntInputInRange(input, racers.size())){
            System.out.println("Invalid input");
            playerChosenRacerIndex = promptPlayerPickARacer();
        } else {
            playerChosenRacerIndex = Integer.parseInt(input) - 1;
            System.out.println("You chose " + racers.get(playerChosenRacerIndex).getName() + ". Good choice!\n\n");
        }

        return playerChosenRacerIndex;
    }

    private void initializeRacersSpeed(){
        for(Racer racer: racers)
            racer.initializeSpeed(trackLength);
    }



    // GETTERS
    public ArrayList<Bidder> getBidders() {
        return bidders;
    }

    public Bidder getPlayer(){

        for(Bidder bidder: bidders){
            if(bidder.isPlayer()){
                return bidder;
            }
        }

        return null;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public int getBidderRacerBudget() {
        return bidderRacerBudget;
    }

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public ArrayList<Racer> getRacers() {
        return racers;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setBidders(ArrayList<Bidder> bidders) {
        this.bidders = bidders;
    }

    public void setRacers(ArrayList<Racer> racers) {
        this.racers = racers;
    }
}

