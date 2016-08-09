package com.Kakaogames.Alimtalk;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */

@MultipartConfig
@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // InputStream으로부터 File 추출
        Part prt = request.getPart("csvFile");
        InputStream is = prt.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br= new BufferedReader(isr);

        // 사용자 입력 text 추출
        UserInputInfo usrInput = new UserInputInfo(
                request.getParameter("temp_cd"),
                request.getParameter("sms_snd_num"),
                request.getParameter("req_dtm"),
                request.getParameter("pre_order_id"),
                request.getParameter("snd_msg"));

        // 서버연결 및 데이터 파싱
        ServerConnector connector = new ServerConnector(br, usrInput);
        connector.connectServer();

        out.println("### Complete! ### <br>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
