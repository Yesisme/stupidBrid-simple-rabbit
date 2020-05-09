package com.lym.business.simple.returnListener;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName="test_return_exchange";
        String routingKey ="return1.save";
        String routingkeyError = "test.error";

        //如果消息不可达，设置mandator=true会进入此方法
        //设置false不会进入此方法
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode,
                                     String replyText,
                                     String exchange,
                                     String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {

                log.info("replyCode:\t{}",replyCode);
                log.info("replyText:\t{}",replyText);
                log.info("exchange:\t{}",exchange);
                log.info("routingKey:\t{}",routingKey);
                log.info("properties:\t{}",properties);
                log.info("body:\t{}",new String(body));

            }
        });

        String msg = "hello return listener!";
        //设置mandator=true会进入addReturnListener方法
        channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
        //channel.basicPublish(exchangeName,routingkeyError,false,null,msg.getBytes());
    }
}
