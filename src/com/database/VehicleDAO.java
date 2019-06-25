package com.database;

import com.automobile.AutomobileFactory;
import com.vehicle.Vehicle;
import com.vehicle.VehicleFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class VehicleDAO {

    private Connection dbConnection = null;

    public int insert(Vehicle vehicle){

        int vehicleID = 0;

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "INSERT INTO vehicle VALUES(null, ?, ?)";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vehicle.getClass().getSimpleName().toUpperCase());
            ps.setString(2, vehicle.getModel().toUpperCase());


            int rowAffected = ps.executeUpdate();
            if(rowAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next())
                    vehicleID = rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return vehicleID;
    }

    public Vehicle get(int id) {

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT * FROM vehicle WHERE vehicle_id=" + id;

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                String model = resultSet.getString("model");
                AutomobileFactory.Type type = AutomobileFactory.Type.valueOf(resultSet.getString("type"));

                Vehicle vehicle = VehicleFactory.getAutomobile(type, model);
                vehicle.setId(id);

                return vehicle;
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
            ConnectionConfiguration.closeConnection();
        }


        return null;

    }

    public ArrayList<Vehicle> getAll() {

        ArrayList<Vehicle> vehicles = new ArrayList<>();

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT * FROM vehicle";

        try{

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){

                String model = resultSet.getString("model");
                AutomobileFactory.Type type = AutomobileFactory.Type.valueOf(resultSet.getString("type"));

                Vehicle vehicle = VehicleFactory.getAutomobile(type, model);
                vehicle.setId(resultSet.getInt("vehicle_id"));

                vehicles.add(vehicle);
            }

            return vehicles;


        } catch (Exception ex) {

        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;
    }

    public boolean update(int id, String[] params){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "UPDATE vehicle SET type = ?, model = ? WHERE vehicle_id = ?";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query);
            ps.setString(1, params[0].toUpperCase());
            ps.setString(2, params[1].toUpperCase());
            ps.setString(3, Integer.toString(id));

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

        String query = "DELETE FROM vehicle WHERE vehicle_id=" + id;

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
