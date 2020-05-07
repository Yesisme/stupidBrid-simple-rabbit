package com.lym.business.simple.dlx;

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
        this.channel = channel;
    }

    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException
    {
        log.info("consumer:\t{}",consumerTag);
        log.info("envelope:\t{}",envelope);
        log.info("properties:\t{}",properties);
        log.info("body:\t{}",new String(body));
    }



}
