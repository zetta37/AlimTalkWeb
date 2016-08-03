package com.Kakaogames.Alimtalk;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.Buffer;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */

@MultipartConfig
@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        InputStreamReader isr = new InputStreamReader(request.getInputStream());
        BufferedReader br= new BufferedReader(isr);

//        System.out.println("*******************************************");
//        System.out.println("# csvFile           ===> " + request.getParameter("csvFile"));
//        System.out.println("# tmpl_cd           ===> " + request.getParameter("temp_cd"));
//        System.out.println("# sms_snd_num       ===> " + request.getParameter("sms_snd_num"));
//        System.out.println("# req_dtm           ===> " + request.getParameter("req_dtm"));
//        System.out.println("# pre_order_id      ===> " + request.getParameter("pre_order_id"));
//        System.out.println("# snd_msg           ===> " + request.getParameter("snd_msg"));
//        System.out.println("*******************************************");


        UserInputInfo usrInput = new UserInputInfo(
                request.getParameter("temp_cd"),
                request.getParameter("sms_snd_num"),
                request.getParameter("req_dtm"),
                request.getParameter("pre_order_id"),
                request.getParameter("snd_msg"));

        ServerConnector connector = new ServerConnector(br, usrInput);
        connector.doServerConnect();

        out.println("### Complete! ### <br>");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
