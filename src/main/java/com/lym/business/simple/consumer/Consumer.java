package com.lym.business.simple.consumer;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
    public static void main(String[] args)throws Exception {
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchageName = "test_consumer_exchange";
        String queueName ="test_consumer_queue";
        String routingKey ="consumer.save";

        channel.exchangeDeclare(exchageName,"topic",false,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchageName,routingKey);

        channel.basicConsume(queueName,true,new MyConsumer(channel));
    }
}
