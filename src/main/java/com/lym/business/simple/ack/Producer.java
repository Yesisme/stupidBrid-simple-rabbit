package com.lym.business.simple.ack;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Producer {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";

        for (int i = 1; i <= 5; i++) {
            Map<String,Object> headers = new HashMap<>();
            headers.put("ack",i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .headers(headers)
                    .contentEncoding("UTF-8")
                    .build();
            String msg = "Hello Rabbitmq ack!"+i;
            channel.basicPublish(exchangeName,routingKey,true,properties,msg.getBytes());
        }
    }
}
