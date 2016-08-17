package com.Kakaogames.Alimtalk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mf839-005 on 2016. 8. 10..
 */
public class InsertQueryProcessor {

    public InsertQueryProcessor() {

    }

    java.sql.Statement createInsertQuery(MySqlDBConnectionManager connectionManager, ArrayList<AlimMessageDataFormat> alimTalkMessageInfoTable) {

        String columns;
        String values;
        Connection conn = null;
        java.sql.Statement statement = null;
//
        //알림톡 INSERT
        if (connectionManager.getDBName().equals("AlimTalkDB")) {

            try {
                conn = connectionManager.getConnection();
                statement = conn.createStatement();
                columns = AlimMessageDataFormat.ALIMTALK_COLUMN;
//                statement.addBatch("use test");
                statement.addBatch("use alimtalk");
                for (int i = 0; i < alimTalkMessageInfoTable.size(); i++) {
                    values = alimTalkMessageInfoTable.get(i).formAlimTalkDBTableData();
//                    statement.addBatch("insert into Test_Alim1 (" + columns + ") values (" + values + ")");
                    statement.addBatch("insert into MZSENDTRAN (" + columns + ") values (" + values + ")");
                }

            } catch (SQLException sqlExpt) {
                System.out.println("** FAILURE @ createQuery(AlimTalkDB): SQLException --  " + sqlExpt.getMessage());
                System.out.println("            SQLState: " + sqlExpt.getSQLState());
            }

        }

        //사전알림신청내역 INSERT
        else if (connectionManager.getDBName().equals("PreOrderDB")) {

            try {
                conn = connectionManager.getConnection();
                statement = conn.createStatement();
                columns = AlimMessageDataFormat.PRE_ORDER_COLUMN;
                statement.addBatch("use test");
                for (int i = 0; i<alimTalkMessageInfoTable.size(); i++){
                    values = alimTalkMessageInfoTable.get(i).formPreOrderDBTableData();
                    statement.addBatch("insert into Test_PreOrder (" + columns + ") values (" + values + ")");
                }

            } catch (SQLException sqlExpt) {
                System.out.println("** FAILURE: SQLException --  " + sqlExpt.getMessage());
                System.out.println("            SQLState: " + sqlExpt.getSQLState());
            }

        }

        // DB 이름이 정확히 매칭되지 않을경우
        else {
            System.out.println("    ERROR: Unsupported Query Processing");
        }

        return statement;
    }

    public void processInsertQuery(java.sql.Statement statement) {

        try {
            statement.executeBatch();
            statement.clearBatch();
        } catch (SQLException sqlExpt) {
            System.out.println("** FAILURE @ processQuery(): SQLException --  " + sqlExpt.getMessage());
            System.out.println("            SQLState: " + sqlExpt.getSQLState());
        }
    }

}
