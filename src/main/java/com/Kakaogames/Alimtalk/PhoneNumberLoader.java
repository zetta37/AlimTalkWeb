package com.Kakaogames.Alimtalk;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mf839-005 on 2016. 8. 12..
 */
//public class PhoneNumberLoader {
//    private static final String QUEUE_NAME = "UserInfoQueue";
//
//    public PhoneNumberLoader()  {
//    }
//
//    public ArrayList<AlimMsgData> addPhoneNumberToList(ArrayList<AlimMsgData> preorderList) throws ParserConfigurationException {
//
//        ArrayList<AlimMsgData> phoneNumMatchedList = new ArrayList<AlimMsgData>();
//
//        while(preorderList.size() > 0){
//            String userID = preorderList.get(0).getUserid();
//            String pNum = getPhoneNumber(userID);
//            if(pNum.equals("")){
//                preorderList.remove(0);
//                continue;
//            }
//            AlimMsgData alimMsg = preorderList.remove(0);
//            alimMsg.setPHONE_NUM(pNum);
//            phoneNumMatchedList.add(alimMsg);
//
//        }
//
//        return phoneNumMatchedList;
//    }
//
//    public String getPhoneNumber(String userID) throws ParserConfigurationException, IOException, TimeoutException {
//
//
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        String message = String.format("http://dev.rest.member.game.daum.net/services/13111/users/%s", userID);
//        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//
//        String result="";
//
//        try {
//
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
////            String userID = "BeN42";
////            String xml = String.format("http://dev.rest.member.game.daum.net/services/13111/users/%s", userID);
//            String xml = String.format("http://dev.rest.member.game.daum.net/services/13111/users/%s", userID);
//
//            org.w3c.dom.Document document = documentBuilder.parse(xml);
//
//            NodeList nodelist = document.getElementsByTagName("cphone");
//            Node textNode = nodelist.item(0).getChildNodes().item(0);
//            result = textNode.getNodeValue();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch(NullPointerException e){
//            return null;
//        }
//        return result;
//    }
//
//    private void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties){
//
//    }
//}


public class PhoneNumberLoader {

    private int totalPreOrder = 0;
    private int withdrawUser = 0;
    private int phoneNumUnidentified = 0;

    public PhoneNumberLoader()  {
    }

    public ArrayList<AlimMsgData> addPhoneNumberToList(ArrayList<AlimMsgData> alimMsgDataList) throws ParserConfigurationException {

        ArrayList<AlimMsgData> phoneNumMatchedList = new ArrayList<AlimMsgData>();
        totalPreOrder = alimMsgDataList.size();

        while(alimMsgDataList.size() > 0){
            String userID = alimMsgDataList.get(0).getUserid();
            String pNum = getPhoneNumber(userID);
            if(pNum==null||pNum.equals("")){
                alimMsgDataList.remove(0);
                continue;
            }
            AlimMsgData alimMsg = alimMsgDataList.remove(0);
            alimMsg.setPHONE_NUM(pNum);
            phoneNumMatchedList.add(alimMsg);
        }

        return phoneNumMatchedList;
    }

    public String getPhoneNumber(String userID) throws ParserConfigurationException {

        String result="";

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String xml = String.format("http://dev.rest.member.game.daum.net/services/13111/users/%s", userID);

            org.w3c.dom.Document document = builder.parse(xml);
            NodeList nodelist = document.getElementsByTagName("cphone");
            Node textNode = nodelist.item(0).getChildNodes().item(0);
            result = textNode.getNodeValue();

        } catch(SAXParseException e){
            withdrawUser++;
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(NullPointerException e){
            phoneNumUnidentified++;
            return null;
        }

        return "01090257060";           // 테스트용 코드
//        return result;                // 실제 사용 코드
    }

    public int getPhoneNumUnidentified() {
        return phoneNumUnidentified;
    }

    public int getTotalPreOrder() {
        return totalPreOrder;
    }

    public int getWithdrawUser() {
        return withdrawUser;
    }
}