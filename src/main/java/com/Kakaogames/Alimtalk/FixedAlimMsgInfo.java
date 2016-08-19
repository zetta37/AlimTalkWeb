package com.Kakaogames.Alimtalk;

/**
 * Created by mf839-005 on 2016. 8. 3..
 */
public class FixedAlimMsgInfo {
    private String tmpl_cd;
    private String sms_snd_num;
    private String req_dtm;
    private String pre_order_id;
    private String snd_msg;

    public FixedAlimMsgInfo(String tmpl_cd, String sms_snd_num, String req_dtm, String pre_order_id, String snd_msg){
        this.tmpl_cd = tmpl_cd;
        this.sms_snd_num = sms_snd_num;
        this.req_dtm = req_dtm;
        this.pre_order_id = pre_order_id;
        this.snd_msg = snd_msg;
    }

    public String getTmpl_cd() {
        return tmpl_cd;
    }
    public String getSms_snd_num(){
        return sms_snd_num;
    }
    public String getReq_dtm(){
        return req_dtm;
    }
    public String getPre_order_id(){
        return pre_order_id;
    }
    public String getSnd_msg(){
        return snd_msg;
    }
}
