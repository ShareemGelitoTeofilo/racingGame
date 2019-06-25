package com.database;

import com.automobile.AutomobileFactory;
import com.racegame.RaceRanking;
import com.racegame.character.Racer;
import com.vehicle.Vehicle;
import com.vehicle.VehicleFactory;

import java.sql.*;
import java.util.ArrayList;

public class RaceRankingDAO {

    private Connection dbConnection = null;

    public boolean insert(RaceRanking raceRanking){

        ArrayList<Racer> racers =  raceRanking.getRacers();

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "INSERT INTO race_ranking(race_id, racer_id, racer_has_finished_track, " +
                "racer_is_evicted, ranking) VALUES(?, ?, ?, ?, ?)";

        int updateCount = 0;

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query);

            ps.setInt(1, raceRanking.getRaceID());
            for(Racer racer: racers){
                ps.setInt(2, racer.getId());
                ps.setBoolean(3, racer.hasFinishedTrack());
                ps.setBoolean(4, racer.isEvicted());
                ps.setInt(5, racer.getRanking());

                int affectedRow = ps.executeUpdate();

                if(affectedRow == 1)
                    updateCount++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return (updateCount == racers.size());
    }

    public RaceRanking get(int raceID){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT " +
                "racer.racer_id, racer.name, racer.speed, " +
                "race_ranking.racer_is_evicted, race_ranking.racer_has_finished_track, race_ranking.ranking," +
                "race.track_length, race.date, vehicle.vehicle_id, vehicle.model as vehicle_model, vehicle.type as vehicle_type, " +
                "race.player_id FROM race_ranking " +
                "JOIN racer ON race_ranking.racer_id = racer.racer_id " +
                "JOIN race ON race.race_id = race_ranking.race_id " +
                "JOIN vehicle ON racer.vehicle_id = vehicle.vehicle_id WHERE race_ranking.race_id =" + raceID;

        try {

            int trackLength = 0;
            Timestamp date = null;
            int playerID = 0;

            ArrayList<Racer> racers = new ArrayList<>();

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int counter = 0;
            while(resultSet.next()){

                if(counter == 0){
                    trackLength = resultSet.getInt("track_length");
                    date = resultSet.getTimestamp("date");
                    playerID = resultSet.getInt("player_id");
                }

                String vehicle_model = resultSet.getString("vehicle_model");
                String vehicle_type = resultSet.getString("vehicle_type");

                Vehicle raceVehicle = VehicleFactory.getAutomobile(AutomobileFactory.Type.valueOf(vehicle_type), vehicle_model);

                Racer racer = new Racer(resultSet.getString("name"), raceVehicle);

                racer.setId(resultSet.getInt("racer_id"));
                racer.setRanking(resultSet.getInt("ranking"));
                racer.setAsEvicted(resultSet.getBoolean("racer_is_evicted"));
                racer.setHasFinishedTrack(resultSet.getBoolean("racer_has_finished_track"));
                racer.setSpeed(resultSet.getInt("speed"));

                racers.add(racer);

                counter++;
            }

            return new RaceRanking(raceID, racers, trackLength, date, playerID);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;
    }

    public ArrayList<Integer> getAllPlayerRankingIDs(int playerID){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT DISTINCT(rr.race_id) FROM race_ranking AS rr " +
                "JOIN race ON rr.race_id = race.race_id WHERE race.player_id = " + playerID;

        try{

            ArrayList<Integer> idList = new ArrayList<>();

            Statement st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                idList.add(rs.getInt("race_id"));
            }

            return idList;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try{
                ConnectionConfiguration.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

}
