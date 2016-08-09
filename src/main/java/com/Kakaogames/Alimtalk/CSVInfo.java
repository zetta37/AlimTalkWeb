package com.Kakaogames.Alimtalk;

/**
 * Created by mf839-005 on 2016. 7. 27..
 */

class CSVInfo {

    // 고정 값
    private final String SENDER_KEY = "8f67c73164660c66fa89eb70ccc2826b0de8f802";
    private final String CHANNEL = "A";
    private final String SMS_SND_YN = "Y";
    private final String TRAN_STS = "1";

    private String PHONE_NUM;
    private String TMPL_CD;
    private String SMS_SND_NUM;
    private String REQ_DTM;
    private String MEMBER_ID;
    private String COUPON_NO;
    private String SND_MSG;
    private String userid;
    private String pre_order_id;

    CSVInfo() {
    }

    public String getCOUPON_NO() {
        return COUPON_NO;
    }

    //Setter
    void setPHONE_NUM(String PHONE_NUM) {
        this.PHONE_NUM = PHONE_NUM;
    }
    void setTMPL_CD(String TMPL_CD) {
        this.TMPL_CD = TMPL_CD;
    }
    void setSMS_SND_NUM(String SMS_SND_NUM) {
        this.SMS_SND_NUM = SMS_SND_NUM;
    }
    void setREQ_DTM(String REQ_DTM) {
        this.REQ_DTM = REQ_DTM;
    }
    void setMEMBER_ID(String MEMBER_ID) {
        this.MEMBER_ID = MEMBER_ID;
    }
    void setCOUPON_NO(String COUPON_NO) {
        this.COUPON_NO = COUPON_NO;
    }
    void setSND_MSG(String SND_MSG) {
        this.SND_MSG = SND_MSG;
    }
    void setUserid(String userid) {
        this.userid = userid;
    }
    void setPre_order_id(String pre_order_id) {
        this.pre_order_id = pre_order_id;
    }


    /*
     아래 부분은 알림톡/사전알림 서버 내 테이블들 별로 다른 Column 형식을 맞추기 위해 Query string을 생성해 주는 파트
     */
    String alimTalkColumnFormat() {
        return ("SENDER_KEY, CHANNEL, PHONE_NUM, TMPL_CD, SMS_SND_NUM, REQ_DTM, SMS_SND_YN, TRAN_STS, MEMBER_ID, COUPON_NO, SND_MSG");
    }

    String alimTalkDataFormat() {
        return ("'" + SENDER_KEY + "', '" + CHANNEL + "', '" + PHONE_NUM + "', '" + TMPL_CD
                + "', '" + SMS_SND_NUM + "', '" + REQ_DTM + "', '" + SMS_SND_YN + "', '"
                + TRAN_STS + "', '" + MEMBER_ID + "', '" + COUPON_NO + "', '" + SND_MSG + "'");
    }

    String preOrderColumnFormat() {
        return ("pre_order_id, userid, coupon, memberid");
    }

    String preOrderDataFormat() {
        return ("'" + pre_order_id + "', '" + userid + "', '" + COUPON_NO + "', '" + MEMBER_ID+"'");
    }
}
