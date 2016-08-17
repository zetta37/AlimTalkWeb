

/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class AlimTalkDBConnectionManager extends MySqlDBConnectionManager {

    private static String dbName = "AlimTalkDB";
    private static String url = "jdbc:mysql://10.28.162.153:3306/alimtalk";
    private static String id = "cuser";
    private static String pwd = "cuserpw";

    private AlimTalkDBConnectionManager() {
    }

    public static Connection getConnection() throws SQLException{
        if (getManager() == null) {
            setManager(url, id, pwd, dbName);
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classExpt) {
            System.out.println("** FAILURE @ AlimTalkDBConnectionManager(): CLASSNOTFOUND EXCEPTION");
        }

        return DriverManager.getConnection(
                MySqlDBConnectionManager.getManager().getUrl(),
                MySqlDBConnectionManager.getManager().getId(),
                MySqlDBConnectionManager.getManager().getPwd());
    }
}
