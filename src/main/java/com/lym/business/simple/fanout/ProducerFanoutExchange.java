package com.lym.business.simple.fanout;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerFanoutExchange {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = RabbitMqConfig.connectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName ="test_fanout_exchange";

        String msg = "hello RabbitMq i am is fanout";

        channel.basicPublish(exchangeName,"",null,msg.getBytes());

        channel.close();

        connection.close();

    }
}
