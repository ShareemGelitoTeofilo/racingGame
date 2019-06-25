package com.database;

import com.automobile.AutomobileFactory;
import com.racegame.character.Racer;
import com.vehicle.Vehicle;
import com.vehicle.VehicleFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RacerDAO {

    private Connection dbConnection = null;

    public int insert(Racer racer){

        int racerID = 0;
        ResultSet rs;

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "INSERT INTO racer(name, speed, race_id, vehicle_id) VALUES(?, ?, ?, ?)";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, racer.getName());
            ps.setInt(2, racer.getInitialSpeed());
            ps.setInt(3, racer.getRaceID());
            ps.setInt(4, racer.getRaceVehicle().getId());

            int rowAffected = ps.executeUpdate();

            if(rowAffected == 1){
                rs = ps.getGeneratedKeys();
                if(rs.next())
                    racerID = rs.getInt(1);

                racer.setId(racerID);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return racerID;
    }

    public Racer get(int id) {

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT racer_id, name, speed, race_id, vehicle.vehicle_id," +
                " type as vehicle_type, model as vehicle_model FROM racer " +
                "JOIN vehicle ON racer.vehicle_id = vehicle.vehicle_id WHERE racer_id = " + id;

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){

                Racer racer = extractRacer(resultSet);
                return racer;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;

    }

    private Racer extractRacer(ResultSet rs){

        try {

            Racer racer;

            int racerID = rs.getInt("racer_id");
            String name = rs.getString("name");
            int raceID = rs.getInt("race_id");
            int speed = rs.getInt("speed");

            racer = new Racer(name, null);

            racer.setId(racerID);
            racer.setRaceID(raceID);
            racer.setSpeed(speed);



            Vehicle racerVehicle;

            int vehicleID = rs.getInt("vehicle_id");
            String vehicle_model = rs.getString("vehicle_model");
            String vehicle_type = rs.getString("vehicle_type");

            racerVehicle = VehicleFactory.getAutomobile(
                    AutomobileFactory.Type.valueOf(vehicle_type), vehicle_model);
            racerVehicle.setId(vehicleID);


            racer.setRaceVehicle(racerVehicle);

            return racer;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public ArrayList<Racer> getAll() {

        ArrayList<Racer> racers = new ArrayList<>();

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT * FROM racer";

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                racers.add(extractRacer(resultSet));
            }

            return racers;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;
    }

    public ArrayList<Racer> getRacersOfRaceGame(int raceID){

        ArrayList<Racer> racers = new ArrayList<>();

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT racer_id, name, speed, race_id, vehicle.vehicle_id," +
                " type as vehicle_type, model as vehicle_model FROM racer " +
                "JOIN vehicle ON racer.vehicle_id = vehicle.vehicle_id WHERE race_id = " + raceID;

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                racers.add(extractRacer(resultSet));
            }

            return racers;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;

    }

    public boolean update(Racer racer){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "UPDATE racer SET name = ?, speed = ?, race_id = ?, vehicle_id = ? WHERE racer_id = ?";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query);
            ps.setString(1, racer.getName());
            ps.setInt(2, racer.getInitialSpeed());
            ps.setInt(3,  racer.getRaceID());
            ps.setInt(4, racer.getRaceVehicle().getId());
            ps.setInt(5, racer.getId());

            if(ps.executeUpdate() == 1)
                return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return false;
    }

    public boolean delete(int id){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "DELETE FROM racer WHERE racer_id=" + id;

        try {
            Statement s = dbConnection.createStatement();
            if(s.executeUpdate(query) == 1)
                return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return false;
    }

}