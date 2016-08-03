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

    CSVConverter(BufferedReader br, UserInputInfo usrInputInfo) {

        try {
            String str;
            String[] token;
            this.csvTable = new ArrayList<CSVInfo>();
            CSVInfo csvData;
            while(br.readLine().equals("Content-Type: text/csv")){
            }
            br.readLine();

            while ((str = br.readLine()) != null){

                csvData = new CSVInfo();
                token = str.split(",");

                for (int i=0; i<token.length; i++){
                    switch(i){
                        case 1:
                            csvData.setPHONE_NUM(token[0]);
                        case 2:
                            csvData.setMEMBER_ID(token[1]);
                        case 3:
                            csvData.setCOUPON_NO(token[2]);
                        case 4:
                            csvData.setUserid(token[3]);
                        default:
                    }
                }

                csvData.setTMPL_CD(usrInputInfo.getTmpl_cd());
                csvData.setSMS_SND_NUM(usrInputInfo.getSms_snd_num());
                csvData.setREQ_DTM(usrInputInfo.getReq_dtm());
                csvData.setPre_order_id(usrInputInfo.getPre_order_id());
                csvData.setSND_MSG(replaceCouponNum(usrInputInfo, csvData));

                csvTable.add(csvData);
            }

        } catch (IOException ioExpt){
            System.out.println("** Failure -- File/Directroy Not Found");
        }

    }

    String replaceCouponNum (UserInputInfo usrInputInfo, CSVInfo csvData){
        String sndMsg = usrInputInfo.getSnd_msg();
        sndMsg.replace("#{쿠폰번호}", csvData.getCOUPON_NO());
        return  sndMsg;
    }

    void queryProcessor(java.sql.Statement statement, String dbName) {

        String columns;
        String data;

        //알림톡 INSERT
        if(dbName.equals("alimTalkServer")) {

            try {
                columns = csvTable.get(0).alimTalkColumnFormat();
                statement.addBatch("use test");
                for (int i = 1; i<csvTable.size(); i++){
                    data = csvTable.get(i).alimTalkDataFormat();
                    statement.addBatch("insert into Test_Alim1 (" + columns + ") values (" + data + ")");
                }
                //statement.executeBatch();
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
                //statement.executeBatch();
                statement.clearBatch();
            } catch (SQLException sqlExpt) {
                System.out.println("** FAILURE: SQLException --  " + sqlExpt.getMessage());
                System.out.println("            SQLState: " + sqlExpt.getSQLState());
            }

        } else {
            System.out.println("    ERROR: Unsupported Query Processing");
        }
    }
}
