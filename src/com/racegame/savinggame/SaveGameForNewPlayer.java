package com.racegame.savinggame;

import com.racegame.RaceGame;

public class SaveGameForNewPlayer extends SaveGameStrategy {

    public SaveGameForNewPlayer(RaceGame raceGame){
        super(raceGame);
    }

    @Override
    public final void saveGame() {
        super.saveRace();
        super.saveRacersAndVehicles();
        super.saveBidders();
        super.saveRaceRanking();
    }

}
