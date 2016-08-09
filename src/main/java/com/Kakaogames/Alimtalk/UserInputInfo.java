package com.Kakaogames.Alimtalk;

/**
 * Created by mf839-005 on 2016. 8. 3..
 */
public class UserInputInfo {
    private String tmpl_cd;
    private String sms_snd_num;
    private String req_dtm;
    private String pre_order_id;
    private String snd_msg;

    public UserInputInfo(String tmpl_cd, String sms_snd_num, String req_dtm, String pre_order_id, String snd_msg){
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

//    public void setTmpl_cd(String str) {
//        tmpl_cd = str;
//    }
//    public void setSms_snd_num(String str){
//        sms_snd_num = str;
//    }
//    public void setReq_dtm(String str){ req_dtm = str; }
//    public void setPre_order_id(String str){
//        pre_order_id = str;
//    }
//    public void setSnd_msg(String str){
//        snd_msg = str;
//    }
}
