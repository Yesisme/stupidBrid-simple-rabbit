package com.lym.business.simple.quickstart;

import com.lym.business.simple.config.RabbitMqProcuderConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//@Service
public class Procuder {

   /* @Autowired
    private ConnectionFactory factory;*/

   // public void sendMsg() throws Exception{

  //  }

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = RabbitMqProcuderConfig.connectionFactory();

        //新建连接
        Connection connection = factory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //发送消息
        try {
            for (int i = 0; i <5 ; i++) {
                String msg = "Hello RabbitMQ";
                channel.basicPublish("","test001",null,msg.getBytes());
            }
        } finally {
            channel.close();
            connection.close();
        }

    }
}
