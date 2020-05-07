package com.lym.business.simple.limt;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
    public static void main(String[] args)throws Exception {
        ConnectionFactory factory = RabbitMqConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchageName = "test_qos_exchange";
        String queueName ="test_qos_queue";
        String routingKey ="qos.save";

        channel.exchangeDeclare(exchageName,"topic",false,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchageName,routingKey);

        //0表示消费大小限制，0是不限制，1表示一次签收1条消息，true表示是channel级别
        channel.basicQos(0,1,false);
        //设置限流这个autoAcl一定要设置false
        channel.basicConsume(queueName,false,new MyConsumer(channel));
    }
}
