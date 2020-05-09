package com.lym.business.simple.limt;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";

        for (int i = 0; i < 5; i++) {
            String msg = "hello rabbit qos";
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
        }


    }
}
