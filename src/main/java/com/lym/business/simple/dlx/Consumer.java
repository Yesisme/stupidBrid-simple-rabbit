package com.lym.business.simple.dlx;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Consumer {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_dlx_exchange";
        String routingKey ="dlx.save";
        String queueName = "test_dlx_queue";

        Map<String,Object> arguments = new HashMap<String,Object>();

        arguments.put("x-dead-letter-exchange","dlx-exchange");

        channel.exchangeDeclare(exchangeName,"topic",false,false,null);
        //死信队列绑定
        channel.queueDeclare(queueName,false,false,false,arguments);
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.exchangeDeclare("dlx-exchange","topic",false,false,null);
        channel.queueDeclare("dlx_queue",false,false,false,null);
        channel.queueBind("dlx_queue","dlx-exchange","#");

        channel.basicConsume(queueName,true,new MyConsumer(channel));
    }
}
