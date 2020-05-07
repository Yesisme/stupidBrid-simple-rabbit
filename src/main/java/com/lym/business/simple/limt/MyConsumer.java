package com.lym.business.simple.limt;

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
        this.channel =channel;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {

        log.info("consumerTag:\t{}",consumerTag);
        log.info("envelope:\t{}",envelope);
        log.info("properties:\t{}",properties);
        log.info("body:\t{}",new String(body));

        //getDeliveryTag表示获取到消费消息的id号,false表示不批量签收
        //会主动回送给broken，表示已经处理玩，可以给我下一条消息了
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
