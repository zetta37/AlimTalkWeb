

/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class PreOrderMySqlDBConnectionManager extends MySqlDBConnectionManager {

    private static String dbName = "PreOrderDB";
    private static String url = "jdbc:mysql://10.30.143.54:3306/test";
    private static String id = "gametest";
    private static String pwd = "gametestpw";

    private PreOrderMySqlDBConnectionManager(){
    }

    public static Connection getConnection() throws SQLException{
        if (getManager() == null) {
            setManager(url, id, pwd, dbName);
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classExpt) {
            System.out.println("** FAILURE @ PreOrderDBConnectionManager(): CLASSNOTFOUND EXCEPTION");
        }

        return DriverManager.getConnection(
                MySqlDBConnectionManager.getManager().getUrl(),
                MySqlDBConnectionManager.getManager().getId(),
                MySqlDBConnectionManager.getManager().getPwd());

    }
}
