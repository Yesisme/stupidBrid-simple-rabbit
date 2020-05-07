package com.lym.business.simple.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MyConsumer extends DefaultConsumer {


    private Channel channel;
    public MyConsumer(Channel channel) {
        super(channel);
        this.channel=channel;
    }

    public void handleDelivery(String consumerTag,
                              Envelope envelope,
                              AMQP.BasicProperties properties,
                              byte[] body)
            throws IOException
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if((Integer) properties.getHeaders().get("ack")==0){
            //requeue:表示重回队列生产端会重新投递，false表示不会回队列
            channel.basicNack(envelope.getDeliveryTag(),false,true);
        }else{
            channel.basicAck(envelope.getDeliveryTag(),false);
        }
        log.info("消费端收到消息\t{}",new String(body));
    }
}
