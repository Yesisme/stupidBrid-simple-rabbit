package com.lym.business.simple.confirmListener;

import com.lym.business.simple.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//消息确认可靠性投递1
@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = RabbitMqConfig.connectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        //指定消息的投递模式,可靠性投递
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";

        String routingKey = "confirm.save";

        String msg = "Hello RabbitMq i am is Confirm!";

        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //成功进入
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                log.info("消息投递成功{}","----no ack--------");
            }

            //失败进入，deliveryTag表示唯一标识
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                log.info("消息投递失败{}","----ack--------");
            }
        });

    }
}
