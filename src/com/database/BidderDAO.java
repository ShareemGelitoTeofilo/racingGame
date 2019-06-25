package com.database;

import com.racegame.character.Racer;
import com.racegame.character.bidder.Bidder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// Todo create a facade for DAOs to seperate responsibilities

public class BidderDAO {

    private Connection dbConnection = null;

    public int insert(Bidder bidder){

        int bidderID = 0;

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "INSERT INTO bidder(name, is_player, racer_bet_on_id) VALUES(?, ?, ?)";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bidder.getName());
            ps.setString(2, Integer.toString( bidder.isPlayer() ? 1 : 0 ));
            ps.setString(3, Integer.toString(bidder.getRacerBetOn().getId()));

            int affectedRow = ps.executeUpdate();

            if(affectedRow == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                    bidderID = rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return bidderID;
    }

    public Bidder get(String bidderName){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT * FROM bidder WHERE name=\"" + bidderName + "\"";

        try {

            RacerDAO racerDAO = new RacerDAO();

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                Bidder bidder = extractBidder(resultSet);
                return bidder;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;
    }

    public Bidder get(int id) {

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT * FROM bidder WHERE bidder_id=" + id;

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                Bidder bidder = extractBidder(resultSet);
                return bidder;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;

    }

    public Bidder getBidderOfRacer(int racerID){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT * FROM bidder WHERE racer_bet_on_id=" + racerID;

        try {

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                Bidder bidder = extractBidder(resultSet);
                return bidder;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;
    }

    public Bidder extractBidder(ResultSet rs) {

        try {

            String name = rs.getString("name");
            Boolean is_player = rs.getBoolean("is_player");
            int racerBetOnID = rs.getInt("racer_bet_on_id");

            Bidder bidder = new Bidder(name, null, 0, null);
            bidder.setIsPlayer(is_player);
            bidder.setId(rs.getInt("bidder_id"));
            bidder.setRacerBetOnID(racerBetOnID);

            return bidder;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public ArrayList<Bidder> getAll() {

        ArrayList<Bidder> bidders = new ArrayList<>();

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "SELECT * FROM bidder";

        try {

            RacerDAO racerDAO = new RacerDAO();

            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                String name = resultSet.getString("name");
                Boolean is_player = resultSet.getBoolean("is_player");
                Racer racerBetOn = racerDAO.get(resultSet.getInt("racer_bet_on_id"));

                Bidder bidder = new Bidder(name, racerBetOn, 0, null);
                bidder.setIsPlayer(is_player);

                bidders.add(bidder);
            }

            return bidders;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionConfiguration.closeConnection();
        }

        return null;
    }

    public boolean update(int id, Object[] params){

        dbConnection = ConnectionConfiguration.getSqlConnection();

        String query = "UPDATE bidder SET name = ?, is_player = ?, racer_bet_on_id = ? WHERE bidder_id = ?";

        try {

            PreparedStatement ps = dbConnection.prepareStatement(query);
            ps.setString(1, (String) params[0]);
            ps.setString(2, Integer.toString((Boolean) params[1] ? 1 : 0));
            ps.setString(3, Integer.toString((int) params[2]));
            ps.setString(4, Integer.toString(id));

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

        String query = "DELETE FROM bidder WHERE bidder_id=" + id;

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

