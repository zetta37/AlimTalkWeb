

/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class PreorderDBConnectionManager {

    private static PreorderDBConnectionManager connectionManager;
    private static Connection conn;
    private static String url = "jdbc:mysql://10.30.143.54:3306/";

    /* DB 사용자 id/pwd는 이부분에 입력*/
    private static String id = "gametest";
    private static String pwd = "gametestpw";

    private PreorderDBConnectionManager() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pwd);
        } catch (ClassNotFoundException classExpt) {
            System.out.println("** FAILURE @ AlimtalkDBConnectionManager(): CLASSNOTFOUND EXCEPTION");
        }
    }

    public static PreorderDBConnectionManager getManager(){
        return connectionManager;
    }

    public static Connection getConnection() throws SQLException {
        if (connectionManager == null) {
            connectionManager = new PreorderDBConnectionManager();
        }
        return conn;
    }

    static void close() {
        try {
            connectionManager.getConnection().close();
        } catch (SQLException sqlExpt) {
            System.out.println("** FAILURE @ AlimtalkDBConnectionManager.close(): SQLException --  " + sqlExpt.getMessage());
            System.out.println("            SQLState: " + sqlExpt.getSQLState());
        }
    }
}
