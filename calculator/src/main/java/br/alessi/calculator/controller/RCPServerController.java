package br.alessi.calculator.controller;

import br.alessi.calculator.configuration.RabbitMQConfig;
import br.alessi.calculator.dto.CalculatorResponseDTO;
import br.alessi.calculator.service.ICalculatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class RCPServerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ICalculatorService calculatorService;

    @RabbitListener(queues = RabbitMQConfig.RPC_CALC_REQ_QUEUE)
    public void calculatorListener(Message msg) throws IOException {
        MDC.put("request", msg.getMessageProperties().getHeader("Request-Id"));

        CalculatorResponseDTO result = calculatorService.process(msg);

        log.info("Msg processed: {}", result);

        Message response = MessageBuilder.withBody(getJson(result).getBytes()).build();
        CorrelationData correlationData = new CorrelationData(msg.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(RabbitMQConfig.RPC_CALC_EXCHANGE, RabbitMQConfig.RPC_CALC_RES_QUEUE, response, correlationData);
    }

    private String getJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

}
