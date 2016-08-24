package com.Kakaogames.Alimtalk;

/**
 * Created by mf839-005 on 2016. 8. 3..
 */
public class FixedAlimMsgData {
    private String tmpl_cd;
    private String sms_snd_num;
    private String req_dtm;
    private String game_id;
    private String snd_msg;
    private String reg_dtm;
    private String genre_id;

    public FixedAlimMsgData(String reg_dtm,  String game_id, String genre_id, String tmpl_cd, String sms_snd_num, String req_dtm, String snd_msg){
        this.reg_dtm = reg_dtm;
        this.genre_id = genre_id;
        this.tmpl_cd = tmpl_cd;
        this.sms_snd_num = sms_snd_num;
        this.req_dtm = req_dtm;
        this.game_id = game_id;
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
    public String getGame_id(){
        return game_id;
    }
    public String getSnd_msg(){
        return snd_msg;
    }
    public String getReg_dtm() {
        return reg_dtm;
    }
    public String getGenre_id() {
        return genre_id;
    }
}
