package com.Kakaogames.Alimtalk;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by mf839-005 on 2016. 8. 12..
 */
public class PreorderedUserListManager {

    private static PreorderedUserListManager preorderedUserListManager;
    private static ArrayList<AlimMsgData> alimMsgDataTable;
    private String reg_dtm;
    private String game_id;
    private String genre_id;

    private PreorderedUserListManager(){
    }

    public static PreorderedUserListManager getPreorderedUserListManager(){
        if(preorderedUserListManager == null){
            preorderedUserListManager = new PreorderedUserListManager();
        }
        return preorderedUserListManager;
    }

    public ArrayList<AlimMsgData> getPreOrderUserList(){
        if (alimMsgDataTable == null){
            alimMsgDataTable = buildList();
        }
        return alimMsgDataTable;
    }

    public void setPreOrderList(ArrayList<AlimMsgData> modifiedlist){
        alimMsgDataTable = modifiedlist;
    }


    private ArrayList<AlimMsgData> buildList(){

        ArrayList<AlimMsgData> resultList = new ArrayList<AlimMsgData>();

        try {
            java.sql.Connection conn = PreorderDBConnectionManager.getConnection();
            conn.setCatalog("GAME");
            PreparedStatement statement = conn.prepareStatement("select memberid, userid\n" +
                    "from (\n" +
                    "(select memberid, userid, reg_dttm, genre_id from pre_order_reserve_game where game_id = ?)\n" +
                    "UNION\n" +
                    "(select memberid, userid, reg_dttm, genre_id from pre_order_genre_reserve_game where genre_id = ? and reg_dttm < ?)\n) temp\n" +
                    "group by userid\n" +
                    "order by memberid");
            statement.setString(1, game_id);
            statement.setString(2, genre_id);
            statement.setString(3, reg_dtm);
//            String query = "select memberid, userid\n" +
//                    "from (\n" +
//                    "(select memberid, userid, reg_dttm, genre_id from pre_order_reserve_game where game_id = "+game_id+")\n" +
//                    "UNION\n" +
//                    "(select memberid, userid, reg_dttm, genre_id from pre_order_genre_reserve_game where genre_id = "+ genre_id +
//                    " and reg_dttm < \""+reg_dtm+"\")\n) temp\n" +
//                    "group by userid\n" +
//                    "order by memberid";

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                AlimMsgData alimMsgData = new AlimMsgData();
                alimMsgData.setMEMBER_ID(resultSet.getString("memberid"));
                alimMsgData.setUserid(resultSet.getString("userid"));
                resultList.add(alimMsgData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void setReg_dtm(String reg_dtm) {
        this.reg_dtm = reg_dtm;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public void setGenre_id(String genre_id) {
        this.genre_id = genre_id;
    }
}