

/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnector {

    CSVConverter cvt;
    ConnectionInfo preOrderServer;
    ConnectionInfo alimTalkServer;

    public ServerConnector(BufferedReader br, UserInputInfo userInputInfo){

        alimTalkServer
                = new ConnectionInfo("jdbc:mysql://10.28.162.153:3306/test", "cuser", "cuserpw");

        preOrderServer
                = new ConnectionInfo("jdbc:mysql://10.30.143.54:3306/test", "gametest", "gametestpw");

        this.cvt = new CSVConverter(br, userInputInfo);
    }

    public void connectServer(){

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;

            // 사전알림신청내역 연결 및 쿼리 INSERT
            conn = DriverManager.getConnection(
                    preOrderServer.getIP(),
                    preOrderServer.getID(),
                    preOrderServer.getPwd());

            System.out.println("# CONNECTED: 사전알림신청내역 Server");

            java.sql.Statement state = conn.createStatement();
            cvt.processQuery(state, "preOrderServer");
            System.out.println("# COMPLETE: 사전알림신청내역 Server Query INSERT");

            // 알림톡 연결 및 쿼리 INSERT
            conn = DriverManager.getConnection(
                    alimTalkServer.getIP(),
                    alimTalkServer.getID(),
                    alimTalkServer.getPwd());

            System.out.println("# CONNECTED: 알림톡 Server");

            state = conn.createStatement();
            cvt.processQuery(state, "alimTalkServer");
            System.out.println("# COMPLETE: 사전알림신청내역 Server Query INSERT");

            System.out.println("\nTerminating Process... Bye");

        } catch (SQLException sqlExpt) {
            System.out.println("** FAILURE: SQLException --  " + sqlExpt.getMessage());
            System.out.println("            SQLState: " + sqlExpt.getSQLState());
        }

        catch (ClassNotFoundException classExpt){
            System.out.println("** FAILURE: CLASSNOTFOUND EXCEPTION");
        }
    }
}
