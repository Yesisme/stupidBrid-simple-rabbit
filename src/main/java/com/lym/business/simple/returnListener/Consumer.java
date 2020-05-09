package com.lym.business.simple.returnListener;

import com.lym.business.simple.config.RabbitMqConsumerConfig;
import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer {
    public static void main(String[] args) throws Exception{


        ConnectionFactory factory = RabbitMqConsumerConfig.connectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchageName ="test_return_exchange";
        String routingKey ="return.#";
        String queueName="test_return_queue";

        channel.exchangeDeclare(exchageName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchageName,routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            log.info("returnlisten:消费端收到消息\t{}",msg);
        }
    }
}
