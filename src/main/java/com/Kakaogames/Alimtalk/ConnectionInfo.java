package com.Kakaogames.Alimtalk;

/**
 * Created by mf839-005 on 2016. 7. 26..
 */
class ConnectionInfo {

    private String ipAddr;
    private String usrID;
    private String usrPwd;

    ConnectionInfo(String ip, String id, String pwd) {
        this.ipAddr = ip;
        this.usrID = id;
        this.usrPwd = pwd;
    }

    String getIP() { return ipAddr; }
    String getID() { return usrID; }
    String getPwd() { return usrPwd; }
}
