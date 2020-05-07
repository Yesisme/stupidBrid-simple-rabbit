package com.lym.business.simple.fanout;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerFanoutExchage {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqConfig.connectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchanName = "test_fanout_exchange";

        String exchangeType="fanout";

        String queueName = "test_fanout_queue";

        String routingKey="";//不设置routingkey

        channel.exchangeDeclare(exchanName,exchangeType,true,false,null);

        channel.queueDeclare(queueName,false,false,false,null);

        channel.queueBind(queueName,exchanName,"");
        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName,true,consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            log.info("消费者收到消息\t{}",msg);
        }
    }
}
