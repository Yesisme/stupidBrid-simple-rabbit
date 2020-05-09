package com.lym.business.simple.topic;

import com.lym.business.simple.config.RabbitMqConsumerConfig;
import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerTopicExchange1 {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = RabbitMqConsumerConfig.connectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName ="test_topic_exchange";

        String exchangeType ="topic";

        String queueName ="test_topic_queue";

        String routingKey = "user.*";
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            log.info("消费者接收到消息\t{}",msg);
        }
    }
}
