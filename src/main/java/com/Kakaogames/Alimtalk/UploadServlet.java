package com.Kakaogames.Alimtalk;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */

@MultipartConfig(location="/Users/mf839-005/Desktop/temp")
@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // InputStream으로부터 File 추출
        Part prt = request.getPart("csvFile");
        prt.write("temp.xlsx");

        // 사용자 입력 text 추출
        UserInputInfo usrInput = new UserInputInfo(
                request.getParameter("temp_cd"),
                request.getParameter("sms_snd_num"),
                request.getParameter("req_dtm"),
                request.getParameter("pre_order_id"),
                request.getParameter("snd_msg"));

        UserInputInfoManager userInputInfoManager = new UserInputInfoManager(usrInput);
        InsertQueryProcessor iqp = new InsertQueryProcessor();

        try {
            PreOrderMySqlDBConnectionManager.getConnection();

            PhoneNumberLoader pnloader = new PhoneNumberLoader();
            PreOrderListGenerator.getPreOrderListGenerator().setPreOrderList(
                    pnloader.addPhoneNumberToList(PreOrderListGenerator.getPreOrderListGenerator().getPreOrderList()));


            PreOrderMySqlDBConnectionManager.close(PreOrderMySqlDBConnectionManager.getConnection());


            // 알림톡DB연결 -> 쿼리제작 -> 쿼리Run -> 연결해제
            AlimTalkDBConnectionManager.getConnection();
            Statement insertStatement = iqp.createInsertQuery(
                    AlimTalkDBConnectionManager.getManager(),
                    PreOrderListGenerator.getPreOrderListGenerator().getPreOrderList());
            iqp.processInsertQuery(insertStatement);
            AlimTalkDBConnectionManager.close(AlimTalkDBConnectionManager.getConnection());

            // 사전알림DB연결 -> 쿼리제작 -> 쿼리Run -> 연결해제
            PreOrderMySqlDBConnectionManager.getConnection();
            insertStatement = iqp.createInsertQuery(
                    PreOrderMySqlDBConnectionManager.getManager(),
                    PreOrderListGenerator.getPreOrderListGenerator().getPreOrderList());
            iqp.processInsertQuery(insertStatement);
            PreOrderMySqlDBConnectionManager.close(PreOrderMySqlDBConnectionManager.getConnection());

        }catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        out.println("### Complete! ### <br>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
