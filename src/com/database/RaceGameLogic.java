package com.database;

import com.racegame.RaceGame;
import com.racegame.RaceRanking;
import com.racegame.character.Racer;
import com.racegame.character.bidder.Bidder;
import com.vehicle.Vehicle;

import java.util.ArrayList;

public class RaceGameLogic {

    public void insertRacerWithVehicle(Racer racer){
        RacerDAO racerDAO = new RacerDAO();
        VehicleDAO vehicleDAO = new VehicleDAO();

        // save racer with null vehicle
        int racerID = racerDAO.insert(racer);
        racer.setId(racerID);

        // save vehicle
        int vehicleID = vehicleDAO.insert(racer.getRaceVehicle());
        racer.getRaceVehicle().setId(vehicleID);

        // to update racer with vehicle
        racerDAO.update(racer);
    }

    public void insertAllRacerWithVehicle(ArrayList<Racer> racers, int raceID){
        for (Racer racer: racers) {
            racer.setRaceID(raceID);
            insertRacerWithVehicle(racer);
        }
    }

    public Racer getRacer(int racerID){

        RacerDAO racerDAO = new RacerDAO();
        Racer racer = racerDAO.get(racerID);

        return racer;
    }

    public void insertBidder(Bidder bidder){
        BidderDAO bidderDAO = new BidderDAO();
        int bidderID = bidderDAO.insert(bidder);
        bidder.setId(bidderID);
    }

    public Bidder getBidderByName(String name){
        BidderDAO bidderDAO = new BidderDAO();
        Bidder bidder = bidderDAO.get(name);

        if(bidder != null) {
            Racer racer = getRacer(bidder.getRacerBetOnID());
            bidder.setRacerBetOn(racer);
        }

        return bidder;
    }

    public Bidder getBidder(int bidderID){
        BidderDAO bidderDAO = new BidderDAO();
        Bidder bidder = bidderDAO.get(bidderID);

        if(bidder != null) {
            Racer racer = getRacer(bidder.getRacerBetOnID());
            bidder.setRacerBetOn(racer);
        }

        return bidder;
    }

    public void insertAllBidder(ArrayList<Bidder> bidders, RaceGame race){
        for (Bidder bidder: bidders) {
            insertBidder(bidder);
        }

        // updates the race game with bidders
        RaceGameDAO raceGameDAO = new RaceGameDAO();
        raceGameDAO.update(race);
    }

    public void insertRaceRanking(RaceRanking raceRanking){
        RaceRankingDAO raceRankingDAO = new RaceRankingDAO();
        raceRankingDAO.insert(raceRanking);
    }

    public RaceRanking getRaceRanking(int raceID){

        RaceRankingDAO raceRankingDAO = new RaceRankingDAO();

        RaceRanking raceRanking = raceRankingDAO.get(raceID);

        Bidder player = getBidder(raceRanking.getPlayerID());
        raceRanking.setPlayer(player);

        return raceRanking;
    }

    public ArrayList<RaceRanking> getAllRaceRankingOfPlayer(int playerID){

        ArrayList<RaceRanking> raceRankings = new ArrayList<>();
        ArrayList<Integer> playerRankingIDs = new ArrayList<>();

        playerRankingIDs = getAllPlayerRankingIDs(playerID);

        for (Integer rankingID: playerRankingIDs) {
            RaceRanking raceRanking = getRaceRanking(rankingID);
            raceRankings.add(raceRanking);
        }

        return raceRankings;
    }

    public ArrayList<Integer> getAllPlayerRankingIDs(int playerID){

        ArrayList<Integer> idList = new ArrayList<>();
        RaceRankingDAO raceRankingDAO = new RaceRankingDAO();

        idList = raceRankingDAO.getAllPlayerRankingIDs(playerID);

        return idList;
    }


    public void insertRaceGame(RaceGame race){
        RaceGameDAO raceGameDAO = new RaceGameDAO();
        int raceID = raceGameDAO.insert(race);
        race.setId(raceID);
    }


    public ArrayList<Bidder> getAllBiddersForRacers(ArrayList<Racer> racers){

        BidderDAO bidderDAO = new BidderDAO();
        ArrayList<Bidder> bidders = new ArrayList<>();

        for(Racer racer: racers){
            Bidder bidder = bidderDAO.getBidderOfRacer(racer.getId());
            bidder.setRacerBetOn(racer);
            bidder.setRacerBetOnID(racer.getId());

            bidders.add(bidder);
        }

        return bidders;
    }

    public RaceGame getRaceGameOfPlayer(Bidder player){

        RaceGameDAO raceDAO = new RaceGameDAO();
        RacerDAO racerDAO = new RacerDAO();

        // retrieve race
        RaceGame race =  raceDAO.getPlayerRaceGame(player.getId());
        race.setPlayerName(player.getName());

        ArrayList<Racer> racers = racerDAO.getRacersOfRaceGame(race.getId());
        race.setRacers(racers);

        ArrayList<Bidder> bidders = getAllBiddersForRacers(racers);
        race.setBidders(bidders);

        return race;
    }


}
