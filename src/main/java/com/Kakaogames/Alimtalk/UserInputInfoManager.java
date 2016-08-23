/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

class UserInputInfoManager {

    private static ArrayList<AlimMsgData> alimMsgDataTable;
    private static UserInputInfoManager userInputInfoManager;

    private UserInputInfoManager() throws IOException {
        alimMsgDataTable = PreorderedUserListManager.getPreorderedUserListManager().getPreOrderUserList();
    }

    public static UserInputInfoManager getUserInputInfoManager() throws IOException {
        if (userInputInfoManager == null){
            userInputInfoManager = new UserInputInfoManager();
        }
        return userInputInfoManager;
    }

    public void setFixedAlimMsgInfo(FixedAlimMsgData fixedInfo) {

        for (AlimMsgData alimMsgData: alimMsgDataTable) {
            alimMsgData.setTMPL_CD(fixedInfo.getTmpl_cd());
            alimMsgData.setSMS_SND_NUM(fixedInfo.getSms_snd_num());
            alimMsgData.setREQ_DTM(fixedInfo.getReq_dtm());
            alimMsgData.setPre_order_id(fixedInfo.getPre_order_id());
            alimMsgData.setSND_MSG(replaceCouponNum(fixedInfo, alimMsgData.getCOUPON_NO()));
        }
    }

    public void putCouponInfo() throws IOException {

        XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(new File ("/Users/mf839-005/Desktop/temp/temp.xlsx")));
        XSSFSheet sheet = work.getSheetAt(0);

        for(int userNum = 0; userNum < alimMsgDataTable.size(); userNum ++){
            XSSFRow row = sheet.getRow(userNum);
            XSSFCell cell = row.getCell(0);
            alimMsgDataTable.get(userNum).setCOUPON_NO(cell.getStringCellValue());
        }
    }

    private String replaceCouponNum (FixedAlimMsgData fixedAlimMsgData, String couponNum){
        String sndMsg = fixedAlimMsgData.getSnd_msg();
        sndMsg = sndMsg.replace("#{쿠폰번호}", couponNum);
        return  sndMsg;
    }
}
