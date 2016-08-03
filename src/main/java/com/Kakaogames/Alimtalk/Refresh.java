package com.Kakaogames.Alimtalk;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */
@WebServlet(name = "Refresh")


public class Refresh extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setIntHeader("Refresh", 1);
        response.setContentType("text/html");

        Calendar calendar = new GregorianCalendar();
        String am_pm;
        int hour = calendar.get(calendar.HOUR);
        int minute = calendar.get(calendar.MINUTE);
        int second = calendar.get(calendar.SECOND);

        if (calendar.get(calendar.AM_PM) == 0) am_pm = "AM";
        else am_pm = "PM";

        String CT = hour + ":" + minute + ":" + second + " " + am_pm;
        PrintWriter out = response.getWriter();
        String title = "Auto Refresh Header Setting";


        out.println(
                "<html>\n"
                + "<head><title>" + title + "</title></head>\n"
                + "<body bgcolor =\"#f00000\">\n"
                + "<h1 align=\"center\">" + title + "</h1>\n"
                + "<p>Current Time is: " + CT + "</p>\n");

    }
}
