package com.racegame.savinggame;

import com.racegame.RaceGame;

public class SaveGameForExistingPlayer extends SaveGameStrategy {

    public SaveGameForExistingPlayer(RaceGame raceGame){
        super(raceGame);
    }

    @Override
    public final void saveGame() {
        super.saveRace();
        super.saveRaceRanking();
    }
}
