package br.alessi.rest.service;

import br.alessi.rest.configuration.RabbitMQConfig;
import br.alessi.rest.dto.CalculatorRequestMQDTO;
import br.alessi.rest.dto.CalculatorResponseDTO;
import br.alessi.rest.enums.Operation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@Service
public class CalculatorService implements ICalculatorService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public BigDecimal calculate(Operation operation, BigDecimal a, BigDecimal b) {
        try {
            switch (operation) {
                case SUM:
                    return sum(a, b);
                case MINUS:
                    return minus(a, b);
                case MULTIPLY:
                    return multiply(a, b);
                case DIVIDE:
                    return divide(a, b);
                default:
                    throw new ArithmeticException("Operation not supported");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public BigDecimal sum(BigDecimal a, BigDecimal b) throws JsonProcessingException {
        String result = send(getJson(Operation.SUM, a, b));
        return getCalculatorResponseDTO(result);
    }


    @Override
    public BigDecimal minus(BigDecimal a, BigDecimal b) throws JsonProcessingException {
        String result = send(getJson(Operation.MINUS, a, b));
        return getCalculatorResponseDTO(result);
    }

    @Override
    public BigDecimal multiply(BigDecimal a, BigDecimal b) throws JsonProcessingException {
        String result = send(getJson(Operation.MULTIPLY, a, b));
        return getCalculatorResponseDTO(result);
    }

    @Override
    public BigDecimal divide(BigDecimal a, BigDecimal b) throws JsonProcessingException {
        String result = send(getJson(Operation.DIVIDE, a, b));
        return getCalculatorResponseDTO(result);
    }

    private String send(String message) {
        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();

        log.info("Rest will send the following msg：{}", newMessage);

        Message result = rabbitTemplate.sendAndReceive(RabbitMQConfig.RPC_CALC_EXCHANGE, RabbitMQConfig.RPC_CALC_REQ_QUEUE, newMessage);

        String response = "";
        if (result != null) {
            //  correlationId
            String correlationId = newMessage.getMessageProperties().getCorrelationId();
            log.info("correlationId:{}", correlationId);

            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();

            //  server  id
            String msgId = (String) headers.get("spring_returned_message_correlation");

            if (msgId.equals(correlationId)) {
                response = new String(result.getBody());
                log.info("client receive：{}", response);
            }
        }
        return response;
    }

    private BigDecimal getCalculatorResponseDTO(String result) throws JsonProcessingException {
        log.info("Calculator succefully return: {}", result);
        ObjectMapper mapper = new ObjectMapper();
        CalculatorResponseDTO responseDTO = mapper.readValue(result, CalculatorResponseDTO.class);
        return responseDTO.getResult();
    }

    private String getJson(Operation operator, BigDecimal a, BigDecimal b) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(CalculatorRequestMQDTO
                .builder()
                .a(a)
                .operator(operator)
                .b(b)
                .build());
    }

}
