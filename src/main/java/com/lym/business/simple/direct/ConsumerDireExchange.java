package com.lym.business.simple.direct;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerDireExchange {

    public static void main(String[] args) throws Exception{
        //创建工厂
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();

        //创建连接
        Connection connection = factory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //创建exchangeName
        String exchangeName = "test_direct_exchange";

        //交换机类型
        String exchageType="direct";

        String queueName = "test_direct_queue";

        String routingKey = "test.direct";

        //声明一个交换机，可持久化，不能自动删除，当前Exchange是否用于RabbitMq内部使用，扩展参数为null
        channel.exchangeDeclare(exchangeName,exchageType,true,false,false,null);
        //durable是否持久化消息,队列名称，不可持久化，队列不是只能被当前连接占有，没有使用使不被删除，扩展参数为null
        channel.queueDeclare(queueName,false,false,false,null);
        //建立一个绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);

        //是否自动签收
        channel.basicConsume(queueName,true,consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            String msg = new String(delivery.getBody());
            System.out.println("消费者接收到消息\t"+msg);
        }


    }
}
