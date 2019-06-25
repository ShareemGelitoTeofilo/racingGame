package com.database;

import com.racegame.RaceGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RaceGameDAO {

    private Connection dbConnection;

    public int insert(RaceGame race){

        int raceID = 0;

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "INSERT INTO race(player_id, track_length, bidder_race_budget, date) VALUES(?, ?, ?, ?)";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, race.getPlayer().getId());
            ps.setInt(2, race.getTrackLength());
            ps.setInt(3, race.getBidderRacerBudget());
            ps.setTimestamp(4, race.getDate());

            int rowAffected = ps.executeUpdate();

            if(rowAffected == 1){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                    raceID = rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return raceID;
    }

    public boolean update(RaceGame race){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "UPDATE race set player_id = ?, track_length = ?, bidder_race_budget = ?, date = ? where race_id = ?";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query);

            ps.setInt(1, race.getPlayer().getId());
            ps.setInt(2, race.getTrackLength());
            ps.setInt(3, race.getBidderRacerBudget());
            ps.setTimestamp(4, race.getDate());
            ps.setInt(5, race.getId());

            int rowAffected = ps.executeUpdate();

            if(rowAffected == 1)
                return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return false;
    }

    public RaceGame get(int id) {

        dbConnection = ConnectionConfiguration.getSqlConnection();
        String query = "SELECT * FROM race WHERE race_id=" + id;

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                int track_length = resultSet.getInt("track_length");
                int bidder_race_budget = resultSet.getInt("bidder_race_budget");

                RaceGame raceGame = new RaceGame(track_length, null, bidder_race_budget);
                raceGame.setId(id);

                return raceGame;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;

    }

    public RaceGame getPlayerRaceGame(int playerID){

        dbConnection = ConnectionConfiguration.getSqlConnection();
        String query = "SELECT * FROM race WHERE player_id=" + playerID;

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                int raceID = resultSet.getInt("race_id");
                int track_length = resultSet.getInt("track_length");
                int bidder_race_budget = resultSet.getInt("bidder_race_budget");

                RaceGame raceGame = new RaceGame(track_length, null, bidder_race_budget);
                raceGame.setId(raceID);

                return raceGame;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;

    }

}
