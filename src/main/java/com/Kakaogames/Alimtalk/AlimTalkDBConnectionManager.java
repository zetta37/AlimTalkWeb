

/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class AlimTalkDBConnectionManager{

    private static AlimTalkDBConnectionManager connectionManager;
    private static Connection conn;
    private static String url = "jdbc:mysql://10.28.162.153:3306/";

    /* DB 사용자 id/pwd는 이부분에 입력*/
    private static String id = "cuser";
    private static String pwd = "cuserpw";

    private AlimTalkDBConnectionManager() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pwd);
        } catch (ClassNotFoundException classExpt) {
            System.out.println("** FAILURE @ AlimTalkDBConnectionManager(): CLASSNOTFOUND EXCEPTION");
        }
    }

    public static AlimTalkDBConnectionManager getManager(){
        return connectionManager;
    }

    public static Connection getConnection() throws SQLException {
        if (connectionManager == null) {
            connectionManager = new AlimTalkDBConnectionManager();
        }
        return conn;
    }

    static void close() {
        try {
            connectionManager.getConnection().close();
        } catch (SQLException sqlExpt) {
            System.out.println("** FAILURE @ AlimTalkDBConnectionManager.close(): SQLException --  " + sqlExpt.getMessage());
            System.out.println("            SQLState: " + sqlExpt.getSQLState());
        }
    }

}
