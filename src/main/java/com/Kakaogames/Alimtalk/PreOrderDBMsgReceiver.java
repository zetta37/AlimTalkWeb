package com.Kakaogames.Alimtalk;

import com.rabbitmq.client.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * Created by mf839-005 on 2016. 8. 18..
 */

public class PreorderDBMsgReceiver implements ServletContextListener{

    private static final String QUEUE_NAME = "preorder";
    private static java.sql.Connection dbConn;
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    private static Connection rabbitMQConn;
    private static Channel channel;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            connectionFactory.setHost("localhost");
            rabbitMQConn = connectionFactory.newConnection();
            channel = rabbitMQConn.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);


            Consumer consumer = new DefaultConsumer(channel){
            private int queryCounter = 0;
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    try {
                        dbConn = PreorderDBConnectionManager.getConnection();

                        if (queryCounter % 100 == 0 && queryCounter != 0) {
                            System.out.println("# [PreOrder] zzzz.....");
                            Thread.sleep(30000);
                        }

                        String message = new String(body, "UTF-8");
                        dbConn.setCatalog("test");
                        java.sql.Statement statement = dbConn.createStatement();
                        statement.executeUpdate(message);
                        queryCounter++;

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(QUEUE_NAME, true, consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            PreorderDBConnectionManager.close();
            channel.close();
            rabbitMQConn.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

