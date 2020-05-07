package com.lym.business.simple.dlx;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName ="test_dlx_exchange";
        String routingKey ="dlx.save";

        String msg="hello rabbit dlx!";
        //消息10秒过期，测试是否会发送到私信队列上
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .contentEncoding("UTF-8")
                .deliveryMode(2)
                .expiration("10000")
                .build();
        channel.basicPublish(exchangeName,routingKey,properties,msg.getBytes());
    }
}
