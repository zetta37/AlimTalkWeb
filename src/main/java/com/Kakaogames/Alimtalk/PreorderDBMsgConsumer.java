package com.Kakaogames.Alimtalk;

import com.rabbitmq.client.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import static java.lang.Integer.parseInt;

/**
 * Created by mf839-005 on 2016. 8. 18..
 */

public class PreorderDBMsgConsumer implements ServletContextListener{

    private static final String QUEUE_NAME = "preorder";
    private static java.sql.Connection dbConn;
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    private static Connection rabbitMQConn;
    private static Channel channel;
    private static int msgCount = 0;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            System.out.println(" # Preorder Consumer Initialized");
            connectionFactory.setHost("localhost");
            rabbitMQConn = connectionFactory.newConnection();
            channel = rabbitMQConn.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, true, null);      // Durable = true, exclusive = false, auto-delete = true, other properties = null


            Consumer consumer = new DefaultConsumer(channel){
            private int queryCounter = 0;
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        dbConn = PreorderDBConnectionManager.getConnection();

                        if (queryCounter % 500 == 0 && queryCounter != 0) {
                            System.out.println("# [PreOrder] Paused");
                            Thread.sleep(30000);
                        }

                        String message = new String(body, "UTF-8");
                        if(msgCount == 0){
                            msgCount = parseInt(message);
                        } else {
                            dbConn.setCatalog("GAME");
                            java.sql.Statement statement = dbConn.createStatement();
                            statement.executeUpdate(message);
                            queryCounter++;
                            if(queryCounter == msgCount){
                                statement.executeUpdate("update pre_order_game set mail = \"Y\" where pre_order_game.ID = 24");
                            }
                        }

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
            System.out.println("  ### CONN CLOSING ###");
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

