package com.lym.business.simple.message;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class ProducerDefaultExchange {

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = RabbitMqConfig.connectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String queueName ="test001";

        Map<String,Object> headers = new HashMap<>();
        headers.put("my1","lyz");
        headers.put("my2","lym");

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)//2表示默认持久化 消息队列宕机也可以用
                .contentEncoding("UTF-8")
                .expiration("10000")//消费者没有接收到消息自动过期删除
                .headers(headers)
                .build();

        String msg = "Hello,i am properties!";
        channel.basicPublish("","test001",properties,msg.getBytes());

        channel.close();
        connection.close();
    }
}
