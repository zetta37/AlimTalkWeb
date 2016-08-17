/**
 * Created by mf839-005 on 2016. 7. 26..
 */

package com.Kakaogames.Alimtalk;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

class UserInputInfoManager {

    private ArrayList<AlimMessageDataFormat> alimTalkMessageInfoTable;


    UserInputInfoManager(UserInputInfo fixedValue) throws IOException {

        alimTalkMessageInfoTable = insertCouponInfo();
        setFixedAlimTalkMessageInfo(fixedValue);

    }


    private void setFixedAlimTalkMessageInfo(UserInputInfo fixedValue) {

        for (AlimMessageDataFormat info: alimTalkMessageInfoTable) {
            info.setTMPL_CD(fixedValue.getTmpl_cd());
            info.setSMS_SND_NUM(fixedValue.getSms_snd_num());
            info.setREQ_DTM(fixedValue.getReq_dtm());
            info.setPre_order_id(fixedValue.getPre_order_id());
            info.setSND_MSG(replaceCouponNum(fixedValue, info.getCOUPON_NO()));
        }
    }

    public ArrayList<AlimMessageDataFormat> insertCouponInfo() throws IOException {

        ArrayList<AlimMessageDataFormat> alimTable = PreOrderListGenerator.getPreOrderListGenerator().getPreOrderList();
        XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(new File ("/Users/mf839-005/Desktop/temp/temp.xlsx")));
        XSSFSheet sheet = work.getSheetAt(0);

        for(int alimNum = 0; alimNum < alimTable.size(); alimNum ++){
            XSSFRow row = sheet.getRow(alimNum);
            XSSFCell cell = row.getCell(0);
            alimTable.get(alimNum).setCOUPON_NO(cell.getStringCellValue());
        }


        return alimTable;
    }


    // 쿠폰번호 치환 메소드
    private String replaceCouponNum (UserInputInfo usrInputInfo, String couponNum){
        String sndMsg = usrInputInfo.getSnd_msg();
        sndMsg = sndMsg.replace("#{쿠폰번호}", couponNum);
        return  sndMsg;
    }


    public ArrayList<AlimMessageDataFormat> getAlimTalkMessageInfoTable(){
        return alimTalkMessageInfoTable;
    }
}
