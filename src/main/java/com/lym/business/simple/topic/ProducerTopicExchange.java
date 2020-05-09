package com.lym.business.simple.topic;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerTopicExchange {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchageName = "test_topic_exchange";
        String routingKey1 ="user.lym";
        String routingKey2 ="user.lyz";
        String routingKey3 ="user.lym.lyz";
        String msg1 ="Hello RabbitMq i am is user.lym routingKey";
        String msg2 ="Hello RabbitMq i am is user.lyz routingKey";
        String msg3 ="Hello RabbitMq i am is user.lym.lyz routingKey";

        channel.basicPublish(exchageName,routingKey1,null,msg1.getBytes());
        channel.basicPublish(exchageName,routingKey2,null,msg2.getBytes());
        channel.basicPublish(exchageName,routingKey3,null,msg3.getBytes());
        channel.close();
        connection.close();
    }
}
