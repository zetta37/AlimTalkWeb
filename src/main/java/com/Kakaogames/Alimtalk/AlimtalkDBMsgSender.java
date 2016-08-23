package com.Kakaogames.Alimtalk;

import com.rabbitmq.client.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * Created by mf839-005 on 2016. 8. 17..
 */

public class AlimtalkDBMsgSender implements ServletContextListener {

    private static final String QUEUE_NAME = "alimtalk";
    private static java.sql.Connection conn;
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    private static Channel channel;
    private static Connection connection = null;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("AlimThread INIT");

        try {
            connectionFactory.setHost("localhost");
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);


            Consumer consumer = new DefaultConsumer(channel){
                private int queryCounter = 0;
                @Override
                public void handleDelivery (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    try {

                        conn=AlimTalkDBConnectionManager.getConnection();

                        if(queryCounter % 100 == 0&& queryCounter != 0) {
                            conn.close();
                            System.out.println("# [Alimtalk] zzzz.....");
                            Thread.sleep(30000);
                        }

                        String message = new String(body, "UTF-8");
                        java.sql.Statement statement = null;

                            conn.setCatalog("test");
                            statement = conn.createStatement();
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
            conn.close();
            channel.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DESTROYED");
    }
}
