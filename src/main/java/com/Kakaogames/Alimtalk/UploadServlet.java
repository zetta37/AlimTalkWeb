package com.Kakaogames.Alimtalk;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */

@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        BufferedReader br = request.getReader();
        Enumeration<String> enu = request.getAttributeNames();

        while(enu.hasMoreElements()){
            String s = enu.nextElement();
            System.out.println("# "+s+"   ===> " + request.getAttribute(s));
        }
        System.out.println("****************");
//        String s;
//        while((s = br.readLine()) != null){
//            System.out.println(s);
//        }
        ServerConnector connector = new ServerConnector(br);
//        connector.doServerConnect();

        out.println("### Complete! ### <br>");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
