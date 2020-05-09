package com.lym.business.simple.confirmListener;

import com.lym.business.simple.config.RabbitMqConsumerConfig;
import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqConsumerConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName ="test_confirm_exchange";
        String routingkey="confirm.#";
        String queueName ="test_confirm_queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingkey);

        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,consumer);

        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            log.info("消费者端收到消息\t{}",msg);

        }
    }
}
