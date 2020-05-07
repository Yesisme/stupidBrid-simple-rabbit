package com.lym.business.simple.consumer;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";

        for (int i = 0; i < 5; i++) {
            String msg = "hello rabbit consumer";
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
        }


    }
}
