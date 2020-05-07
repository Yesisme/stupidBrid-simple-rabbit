package com.lym.business.simple.quickstart;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

//走默认的交换机，生产者没有绑定queue(队列)，默认的交换机会根据routking名字取找队列，没有找到则会删除
public class Consumer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = RabbitMqConfig.connectionFactory();

        //新建连接
        Connection connection = factory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //声明一个队列
        String queueName = "test001";

        channel.queueDeclare("test001",true,false,false,null);

        //创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //设置channle
        channel.basicConsume(queueName,true,queueingConsumer);

        //获取消息
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端接收到消息\t"+msg);
        }
    }
}
