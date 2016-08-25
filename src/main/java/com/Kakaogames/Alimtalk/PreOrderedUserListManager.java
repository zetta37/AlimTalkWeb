package com.Kakaogames.Alimtalk;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by mf839-005 on 2016. 8. 12..
 */
public class PreorderedUserListManager {

//    private final Object lock = new Object();
    private static PreorderedUserListManager preorderedUserListManager;
    private static ArrayList<AlimMsgData> preorderUserList;
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

    public synchronized ArrayList<AlimMsgData> getPreorderUserList(){
            System.out.println("lock getPreOrder");
            if (preorderUserList == null) {
                preorderUserList = buildList();
            }
            System.out.println("unlock getPreOrder");
            return preorderUserList;
    }

    public synchronized void setPreorderUserList(ArrayList<AlimMsgData> modifiedlist){
            System.out.println("lock setPreOrder");
            preorderUserList = modifiedlist;
            System.out.println("unlock setPreOrder");
    }

    private synchronized ArrayList<AlimMsgData> buildList(){

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
//        }
    }

    public void setGame_id(String game_id) {
            this.game_id = game_id;
    }

    public void setGenre_id(String genre_id) {
            this.genre_id = genre_id;
    }

}