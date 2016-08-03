package com.Kakaogames.Alimtalk;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import javax.servlet.*;
import java.util.*;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */
@WebServlet(name = "LogFilter")
public class LogFilter implements Filter {
    public void  init(FilterConfig config)
            throws ServletException{
        // Get init parameter
        String testParam = config.getInitParameter("test-param");

        //Print the init parameter
        System.out.println("Test Param: " + testParam);
    }
    public void  doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws java.io.IOException, ServletException {


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the IP address of client machine.
        String ipAddress = request.getRemoteAddr();


        // Log the IP address and current timestamp.
        out.println("** IP "+ ipAddress + ", Time "
                + new Date().toString() + "<br>");

        // Pass request back down the filter chain
        chain.doFilter(request,response);
    }
    public void destroy( ){
      /* Called before the Filter instance is removed
      from service by the web container*/
    }

    public boolean isLoggable(LogRecord record) {
        return true;
    }
}
