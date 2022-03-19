package br.alessi.calculator.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String RPC_CALC_REQ_QUEUE = "calc_req_queue";
    public static final String RPC_CALC_RES_QUEUE = "calc_res_queue";
    public static final String RPC_CALC_EXCHANGE = "calc_exchange";

    @Bean
    Queue msgQueue() {
        return new Queue(RPC_CALC_REQ_QUEUE);
    }


    @Bean
    Queue replyQueue() {
        return new Queue(RPC_CALC_RES_QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RPC_CALC_EXCHANGE);
    }


    @Bean
    Binding msgBinding() {
        return BindingBuilder.bind(msgQueue()).to(exchange()).with(RPC_CALC_REQ_QUEUE);
    }


    @Bean
    Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(exchange()).with(RPC_CALC_RES_QUEUE);
    }
}
