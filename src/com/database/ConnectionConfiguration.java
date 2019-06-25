package com.database;

import java.sql.Connection;
import java.sql.DriverManager;

final public class ConnectionConfiguration {

    private static Connection sqlConnection;

    private ConnectionConfiguration(){}

    public static Connection getSqlConnection(){

        if(sqlConnection == null || connectionIsClosed()){
            try{
                sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/race_game", "root", "123");
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return sqlConnection;
    }

    public static void closeConnection(){

        try{
            sqlConnection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Boolean connectionIsClosed(){

        try {
             if(sqlConnection.isClosed())
                 return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


}
