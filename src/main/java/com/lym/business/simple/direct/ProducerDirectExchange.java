package com.lym.business.simple.direct;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerDirectExchange {

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = RabbitMqConfig.connectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName ="test_direct_exchange";

        String routingKey = "test.direct";

        String msg ="Hello RabbitMq,i am is directExchange!";

        //props可以对消息优先级进行设置
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
