package br.alessi.calculator.controller;

import br.alessi.calculator.configuration.RabbitMQConfig;
import br.alessi.calculator.dto.CalculatorRequestDTO;
import br.alessi.calculator.dto.CalculatorResponseDTO;
import br.alessi.calculator.service.ICalculatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RCPServerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ICalculatorService calculatorService;

    @RabbitListener(queues = RabbitMQConfig.RPC_CALC_REQ_QUEUE)
    public void process(Message msg) throws IOException {
        CalculatorResponseDTO result = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            CalculatorRequestDTO calculation = mapper.readValue(new String(msg.getBody()), CalculatorRequestDTO.class);
            log.info("Calculator: {}", calculation);
            result = calculatorService.calculate(calculation);
        } catch (Exception e) {
            result = null;
        }
        Message response = MessageBuilder.withBody(getJson(result).getBytes()).build();

        CorrelationData correlationData = new CorrelationData(msg.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(RabbitMQConfig.RPC_CALC_EXCHANGE, RabbitMQConfig.RPC_CALC_RES_QUEUE, response, correlationData);
    }

    private String getJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return "";
    }

}
