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
import java.util.concurrent.TimeoutException;

/**
 * Created by mf839-005 on 2016. 8. 1..
 */

@MultipartConfig(location="/Users/mf839-005/Desktop/temp")
@WebServlet(name = "UploadServlet")
public class AlimtalkServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");

        // InputStream으로부터 Excel File 추출
        Part prt = request.getPart("couponFile");
        prt.write("temp.xlsx");

        // 사용자 입력 text 추출
        FixedAlimMsgData fixedInfo = new FixedAlimMsgData(
                request.getParameter("reg_dtm"),
                request.getParameter("game_id"),
                request.getParameter("genre_id"),
                request.getParameter("temp_cd"),
                request.getParameter("sms_snd_num"),
                request.getParameter("req_dtm"),
                request.getParameter("snd_msg"));

        PreorderedUserListManager preOrderListManager = PreorderedUserListManager.getPreorderedUserListManager();

        preOrderListManager.setReg_dtm(fixedInfo.getReg_dtm());
        preOrderListManager.setGame_id(fixedInfo.getGame_id());
        preOrderListManager.setGenre_id(fixedInfo.getGenre_id());


        try {
            SQLQueryMsgProducer SQLQueryMsgProducer = new SQLQueryMsgProducer();
            PhoneNumberLoader pnloader = new PhoneNumberLoader();

            // 회원정보DB API연동 및 데이터 처리
            preOrderListManager.setPreorderUserList(pnloader.addPhoneNumberToList(preOrderListManager.getPreorderUserList()));

            // View 처리
            request.setAttribute("totalPreorder", pnloader.getTotalPreOrder());
            request.setAttribute("unidentified", pnloader.getPhoneNumUnidentified());
            request.setAttribute("withdraw", pnloader.getWithdrawUser());
            request.setAttribute("removed", pnloader.getWithdrawUser()+pnloader.getPhoneNumUnidentified());
            request.setAttribute("success", pnloader.getTotalPreOrder()-(pnloader.getWithdrawUser()+pnloader.getPhoneNumUnidentified()));

            UserInputInfoManager userInputInfoManager = UserInputInfoManager.getUserInputInfoManager();
            userInputInfoManager.putCouponInfo();
            userInputInfoManager.setFixedAlimMsgInfo(fixedInfo);

            // Query 생성 후 MQ로 발송
            SQLQueryMsgProducer.sendQueryMsg(
                    AlimtalkDBConnectionManager.getManager(),
                    preOrderListManager.getPreorderUserList());
            SQLQueryMsgProducer.sendQueryMsg(
                    PreorderDBConnectionManager.getManager(),
                    preOrderListManager.getPreorderUserList());

            getServletContext().getRequestDispatcher("/WEB-INF/AlimResult.jsp").forward(request, response);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
