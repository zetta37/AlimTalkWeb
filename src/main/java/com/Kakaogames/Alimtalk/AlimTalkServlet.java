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
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */

@MultipartConfig(location="/Users/mf839-005/Desktop/temp")
@WebServlet(name = "AlimTalkServlet")
public class AlimTalkServlet extends HttpServlet {

    public static PrintWriter out;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        out = response.getWriter();
//        out.println("<html>\n" +
//                "<head>\n" +
//                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\n" +
//                "<title>Result</title>\n" +
//                "</head>\n" +
//                "<body bgcolor=\"#ffff00\">");

        // InputStream으로부터 Excel File 추출
        Part prt = request.getPart("csvFile");
        prt.write("temp.xlsx");

        // 사용자 입력 text 추출
        FixedAlimMsgData fixedInfo = new FixedAlimMsgData(
                request.getParameter("temp_cd"),
                request.getParameter("sms_snd_num"),
                request.getParameter("req_dtm"),
                request.getParameter("pre_order_id"),
                request.getParameter("snd_msg"));

        PreOrderedUserListManager preOrderListManager = PreOrderedUserListManager.getPreOrderedUserListManager();

        preOrderListManager.setReg_dtm(request.getParameter("reg_dtm"));
        preOrderListManager.setGame_id(request.getParameter("game_id"));
        preOrderListManager.setGenre_id(request.getParameter("genre_id"));

        try {
            SQLQueryMsgSender SQLQueryMsgSender = new SQLQueryMsgSender();
            PhoneNumberLoader pnloader = new PhoneNumberLoader();

            preOrderListManager.setPreOrderList(pnloader.addPhoneNumberToList(preOrderListManager.getPreOrderUserList()));
            request.setAttribute("totalPreorder", pnloader.getTotalPreOrder());
            request.setAttribute("unidentified", pnloader.getPhoneNumUnidentified());
            request.setAttribute("withdraw", pnloader.getWithdrawUser());
            request.setAttribute("removed", pnloader.getWithdrawUser()+pnloader.getPhoneNumUnidentified());
            request.setAttribute("success", pnloader.getTotalPreOrder()-(pnloader.getWithdrawUser()+pnloader.getPhoneNumUnidentified()));
            getServletContext().getRequestDispatcher("/WEB-INF/AlimResult.jsp").forward(request, response);

            UserInputInfoManager userInputInfoManager = UserInputInfoManager.getUserInputInfoManager();
            userInputInfoManager.putCouponInfo();
            userInputInfoManager.setFixedAlimMsgInfo(fixedInfo);

            // Query 생성 후 MQ로 발송
            SQLQueryMsgSender.sendQueryMsg(
                    AlimTalkDBConnectionManager.getManager(),
                    preOrderListManager.getPreOrderUserList());
            SQLQueryMsgSender.sendQueryMsg(
                    PreOrderDBConnectionManager.getManager(),
                    preOrderListManager.getPreOrderUserList());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
