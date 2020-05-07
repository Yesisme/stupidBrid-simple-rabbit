package com.lym.business.simple.config;

import com.rabbitmq.client.ConnectionFactory;

//@Configuration
//@Component
public class RabbitMqConfig {

    //工厂
   // @Bean
    public static ConnectionFactory connectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.96.128");
        factory.setPort(5672);
        factory.setUsername("lym");
        factory.setPassword("123456");
        //是否支持自动重连
        factory.setAutomaticRecoveryEnabled(true);
        //3000秒执行一次
        factory.setNetworkRecoveryInterval(3000);
        factory.setVirtualHost("/vhost_lym");
        return factory;
    }

}
