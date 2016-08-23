package com.Kakaogames.Alimtalk;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by mf839-005 on 2016. 8. 10..
 */
public class SQLQueryMsgSender {

    private final static String ALIMTALKDB_QUEUE = "alimtalk";
    private final static String PREORDERDB_QUEUE = "preorder";

    public SQLQueryMsgSender() {
    }

    void sendQueryMsg(AlimtalkDBConnectionManager connectionManager, ArrayList<AlimMsgData> alimTalkMessageInfoTable) throws IOException, TimeoutException {

        String columns= AlimMsgData.ALIMTALK_COLUMN;
        String values;
        String message;

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(ALIMTALKDB_QUEUE, false, false, false, null);

        for (int i = 0; i < alimTalkMessageInfoTable.size(); i++) {
            values = alimTalkMessageInfoTable.get(i).makeMZSENDTRANFormat();
            message = "insert into MZSENDTRAN (" + columns + ") values (" + values + ")";
            channel.basicPublish("", ALIMTALKDB_QUEUE, null, message.getBytes());
        }
        channel.close();
        connection.close();
    }

    void sendQueryMsg(PreorderDBConnectionManager connectionManager, ArrayList<AlimMsgData> alimMsgInfoTable) throws IOException, TimeoutException {

        String columns= AlimMsgData.PRE_ORDER_COLUMN;
        String values;
        String message;

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(PREORDERDB_QUEUE, false, false, false, null);

        for (int i = 0; i < alimMsgInfoTable.size(); i++) {
            values = alimMsgInfoTable.get(i).makePreOrderCouponFormat();
            message = "insert into Test_PreOrder (" + columns + ") values (" + values + ")";
            channel.basicPublish("", PREORDERDB_QUEUE, null, message.getBytes());
        }
        channel.close();
        connection.close();
    }
}