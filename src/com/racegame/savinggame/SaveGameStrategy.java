package com.racegame.savinggame;

import com.database.RaceGameLogic;
import com.racegame.RaceGame;
import com.racegame.RaceRanking;

public abstract class SaveGameStrategy {

    RaceGame raceGame;
    RaceGameLogic raceGameLogic;

    public SaveGameStrategy(RaceGame raceGame){
        this.raceGame = raceGame;
        this.raceGameLogic = new RaceGameLogic();
    }

    public abstract void saveGame();


    public void saveBidders(){
        raceGameLogic.insertAllBidder(raceGame.getBidders(), raceGame);
    }

    public void saveRacersAndVehicles(){
        raceGameLogic.insertAllRacerWithVehicle(raceGame.getRacers(), raceGame.getId());
    }

    public void saveRace(){
        raceGameLogic.insertRaceGame(raceGame);
    }

    public void saveRaceRanking(){
        RaceRanking raceRanking = new RaceRanking(raceGame.getId(), raceGame.getRacers(),
                raceGame.getTrackLength(), raceGame.getDate(), raceGame.getPlayer().getId());
        raceGameLogic.insertRaceRanking(raceRanking);
    }


}
