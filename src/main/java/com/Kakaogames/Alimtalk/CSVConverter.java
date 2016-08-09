/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

class CSVConverter {

    private ArrayList<CSVInfo> csvTable;

    // CSV Converter: CSVInfo라는 형식에 각각의 데이터 내용을 담아 하나의 csvTable이라는 ArrayList에 보관.
    // ### csv파일은 순서에 맞게 유저전화번호/memberID/쿠폰번호/userID 순으로 저장되어있어야 함. ###
    CSVConverter(BufferedReader br, UserInputInfo usrInputInfo) {

        try {
            String str;
            String[] token;
            this.csvTable = new ArrayList<CSVInfo>();
            CSVInfo csvData;

            while ((str = br.readLine()) != null){

                csvData = new CSVInfo();
                token = str.split(",");

                for (int i=0; i<token.length; i++){
                    switch(i){
                        case 1:     // 유저 전화번호
                            csvData.setPHONE_NUM(token[0]);
                        case 2:     // 유저의 memberid
                            csvData.setMEMBER_ID(token[1]);
                        case 3:     // 유저에게 부여되는 쿠폰번호
                            csvData.setCOUPON_NO(token[2]);
                        case 4:     //
                            csvData.setUserid(token[3]);
                        default:
                    }
                }

                csvData.setTMPL_CD(usrInputInfo.getTmpl_cd());
                csvData.setSMS_SND_NUM(usrInputInfo.getSms_snd_num());
                csvData.setREQ_DTM(usrInputInfo.getReq_dtm());
                csvData.setPre_order_id(usrInputInfo.getPre_order_id());
                csvData.setSND_MSG(replaceCouponNum(usrInputInfo, csvData.getCOUPON_NO()));
                csvTable.add(csvData);
            }

        } catch (IOException ioExpt){
            System.out.println("** Failure -- File/Directroy Not Found");
        }
    }

    // 쿠폰번호 치환 메소드
    String replaceCouponNum (UserInputInfo usrInputInfo, String couponNum){
        String sndMsg = usrInputInfo.getSnd_msg();
        sndMsg = sndMsg.replace("#{쿠폰번호}", couponNum);
        return  sndMsg;
    }

    // 쿼리문 입력 및 데이터베이스 INSERT: 각각의 테이블에 담긴 CSVInfo의 내용들을 하나로 모아서 batch로 넘겨주고 처리
    void processQuery(java.sql.Statement statement, String dbName) {

        String columns;
        String data;

        //알림톡 INSERT
        if(dbName.equals("alimTalkServer")) {

            try {
                columns = csvTable.get(0).alimTalkColumnFormat();
                statement.addBatch("use test");
//                statement.addBatch("use alimtalk");
                for (int i = 1; i<csvTable.size(); i++){
                    data = csvTable.get(i).alimTalkDataFormat();
                    statement.addBatch("insert into Test_Alim1 (" + columns + ") values (" + data + ")");
                }
                statement.executeBatch();
                statement.clearBatch();

            } catch (SQLException sqlExpt) {
                System.out.println("** FAILURE: SQLException --  " + sqlExpt.getMessage());
                System.out.println("            SQLState: " + sqlExpt.getSQLState());
            }

        //사전알림신청내역 INSERT
        } else if (dbName.equals("preOrderServer")) {

            try {
                columns = csvTable.get(0).preOrderColumnFormat();
                statement.addBatch("use test");
                for (int i = 1; i<csvTable.size(); i++){
                    data = csvTable.get(i).preOrderDataFormat();
                    statement.addBatch("insert into Test_PreOrder (" + columns + ") values (" + data + ")");
                }
                statement.executeBatch();
                statement.clearBatch();

            } catch (SQLException sqlExpt) {
                System.out.println("** FAILURE: SQLException --  " + sqlExpt.getMessage());
                System.out.println("            SQLState: " + sqlExpt.getSQLState());
            }

        // DB 이름이 정확히 매칭되지 않을경우
        } else {
            System.out.println("    ERROR: Unsupported Query Processing");
        }
    }
}
