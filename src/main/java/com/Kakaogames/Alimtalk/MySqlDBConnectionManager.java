package com.Kakaogames.Alimtalk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mf839-005 on 2016. 8. 11..
 */
class MySqlDBConnectionManager {

    private static MySqlDBConnectionManager mySqlDBConnectionManager;

    private String url;
    private String id;
    private String pwd;
    private String dbName;

    protected MySqlDBConnectionManager() {
    }

    public static MySqlDBConnectionManager getManager(){
        return mySqlDBConnectionManager;
    }

    public static void setManager(String url, String id, String pwd, String dbName){
        mySqlDBConnectionManager = new MySqlDBConnectionManager();
        mySqlDBConnectionManager.setUrl(url);
        mySqlDBConnectionManager.setId(id);
        mySqlDBConnectionManager.setPwd(pwd);
        mySqlDBConnectionManager.setDBName(dbName);

    }

    public static Connection getConnection() throws SQLException {

        if (mySqlDBConnectionManager == null) {
            mySqlDBConnectionManager = new MySqlDBConnectionManager();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classExpt) {
            System.out.println("** FAILURE @ AlimTalkDBConnectionManager(): CLASSNOTFOUND EXCEPTION");
        }

        return DriverManager.getConnection(
                mySqlDBConnectionManager.url,
                mySqlDBConnectionManager.id,
                mySqlDBConnectionManager.pwd);

    }

    static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                mySqlDBConnectionManager = null;
            }
        } catch (SQLException sqlExpt) {
            System.out.println("** FAILURE @ AlimTalkDBConnectionManager.close(): SQLException --  " + sqlExpt.getMessage());
            System.out.println("            SQLState: " + sqlExpt.getSQLState());
        }
    }

    private void setUrl(String url) {
        this.url = url;
    }
    private void setId(String id) {
        this.id = id;
    }
    private void setPwd(String pwd) {
        this.pwd = pwd;
    }
    private void setDBName(String dbName){
        this.dbName = dbName;
    }
    String getUrl(){
        return this.url;
    }
    String getId() {
        return id;
    }
    String getPwd() {
        return pwd;
    }
    public String getDBName() { return dbName; }
}