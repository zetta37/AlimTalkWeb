

/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnector {

    CSVConverter cvt;
    ConnectionInfo preOrderServer;
    ConnectionInfo alimTalkServer;

    public ServerConnector(BufferedReader br){

//        Scanner userInput = new Scanner(System.in);
//        System.out.println( "\n   =========================================" +
//                            "\n            AlimTalk Module v.1.0.0         "+
//                            "\n\n    KakaoGamesCorp                        "+
//                            "\n    PC Platform Dev. Team                 "+
//                            "\n   =========================================");
//
//        System.out.println();
//        System.out.print("Enter \"사전알림신청내역\" Server Connection Info (SYNTAX: url id pw)\n    -> ");
//        String input = userInput.nextLine();
//        String[] serverInfo = input.split(" ");
//
//        final ConnectionInfo preOrderServer
//                  = new ConnectionInfo("jdbc:mysql://"+serverInfo[0], serverInfo[1], serverInfo[2]);
//
//        System.out.print("Enter \"알림톡\" Server Connection Info (SYNTAX: url id pw)\n    -> ");
//        input = userInput.nextLine();
//        serverInfo = input.split(" ");
//
//        final ConnectionInfo alimTalkServer
//                 = new ConnectionInfo("jdbc:mysql://"+serverInfo[0], serverInfo[1], serverInfo[2]);
//        System.out.println("### Alimtalk Server...");

        alimTalkServer
                = new ConnectionInfo("jdbc:mysql://10.28.162.153:3306/test", "cuser", "cuserpw");
//        System.out.println("### Preorder Server...");
        preOrderServer
                = new ConnectionInfo("jdbc:mysql://10.30.143.54:3306/test", "gametest", "gametestpw");
//        System.out.println("### DONE...!!");

        this.cvt = new CSVConverter(br);

    }

    public void doServerConnect(){

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
            cvt.queryProcessor(state, "preOrderServer");
            System.out.println("# COMPLETE: 사전알림신청내역 Server Query INSERT");

            // 알림톡 연결 및 쿼리 INSERT
            conn = DriverManager.getConnection(
                    alimTalkServer.getIP(),
                    alimTalkServer.getID(),
                    alimTalkServer.getPwd());
            System.out.println("# CONNECTED: 알림톡 Server");
            state = conn.createStatement();
            cvt.queryProcessor(state, "alimTalkServer");
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
