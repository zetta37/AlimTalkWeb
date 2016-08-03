/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

class CSVConverter {

    private File csvFile;
    private ArrayList<CSVInfo> csvTable;

    CSVConverter(BufferedReader br) {

        try {
            String str;
            String[] token;
            this.csvTable = new ArrayList<CSVInfo>();
            CSVInfo csvData;
            while(br.readLine().equals("\n")){
                br.readLine();
            }

            while ((str = br.readLine()) != null){

                csvData = new CSVInfo();
                token = str.split(",");

                for (int i=0; i<token.length; i++){
                    switch(i){
//                        case 1:
//                            csvData.setSENDER_KEY(token[0]);
//                        case 2:
//                            csvData.setCHANNEL(token[1]);
                        case 3:
                            csvData.setPHONE_NUM(token[2]);
//                        case 4:
//                            csvData.setTMPL_CD(token[3]);
//                        case 5:
//                            csvData.setSMS_SND_NUM(token[4]);
//                        case 6:
//                            csvData.setREQ_DTM(token[5]);
//                        case 7:
//                            csvData.setSMS_SND_YN(token[6]);
//                        case 8:
//                            csvData.setTRAN_STS(token[7]);
                        case 9:
                            csvData.setMEMBER_ID(token[8]);
                        case 10:
                            csvData.setCOUPON_NO(token[9]);
//                        case 11:
//                            csvData.setSND_MSG(token[10]);
                        case 12:
                            csvData.setUserid(token[11]);
//                        case 13:
//                            csvData.setPre_order_id(token[12]);
                        default:
                    }
                }

                csvTable.add(csvData);
            }

        } catch (IOException ioExpt){
            System.out.println("** Failure -- File/Directroy Not Found");
        }

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
