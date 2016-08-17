package com.Kakaogames.Alimtalk;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mf839-005 on 2016. 8. 12..
 */
public class PhoneNumberLoader {

    public PhoneNumberLoader()  {
    }

    public ArrayList<AlimMessageDataFormat> addPhoneNumberToList(ArrayList<AlimMessageDataFormat> preorderList) throws ParserConfigurationException {

        ArrayList<AlimMessageDataFormat> phoneNumMatchedList = new ArrayList<AlimMessageDataFormat>();

        while(preorderList.size() > 0){
            String userID = preorderList.get(0).getUserid();
            String pNum = getPhoneNumber(userID);
            if(pNum.equals("")){
                preorderList.remove(0);
                continue;
            }
            AlimMessageDataFormat alimMsg = preorderList.remove(0);
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
//            String userID = "BeN42";
//            String xml = String.format("http://dev.rest.member.game.daum.net/services/13111/users/%s", userID);
            String xml = String.format("http://dev.rest.member.game.daum.net/services/13111/users/%s", userID);

            org.w3c.dom.Document document = builder.parse(xml);

            NodeList nodelist = document.getElementsByTagName("cphone");
            Node textNode = nodelist.item(0).getChildNodes().item(0);
            result = textNode.getNodeValue();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(NullPointerException e){
            return null;
        }
        return result;
    }
}
