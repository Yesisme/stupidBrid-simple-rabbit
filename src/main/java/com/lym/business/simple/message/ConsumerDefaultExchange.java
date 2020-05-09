package com.lym.business.simple.message;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

//对properties消息头进行设置
@Slf4j
public class ConsumerDefaultExchange {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = "test001";

        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,consumer);

        channel.queueDeclare(queueName,false,false,false,null);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            log.info("消费者接收到消息\t{}",msg);
        }
    }
}
