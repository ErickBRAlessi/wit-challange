package br.alessi.calculator.service;

import br.alessi.calculator.dto.CalculatorRequestDTO;
import br.alessi.calculator.dto.CalculatorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Slf4j
@Service
public class CalculatorService implements ICalculatorService {

    public CalculatorResponseDTO process(Message msg) {
        log.info("Msg to be processed: {}", msg);
        try {
            return CalculatorResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .result(calculate(getCalculatorRequestDTO(msg)))
                    .build();
        } catch (Exception e) {
            log.error("Error processing msg: {}", e);
            return CalculatorResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .msg(e.getMessage())
                    .result(null)
                    .build();
        }
    }


    public BigDecimal calculate(CalculatorRequestDTO calculation) throws NoSuchMethodException {
        switch (calculation.getOperator()) {
            case SUM:
                return calculation.getA().add(calculation.getB(), MathContext.DECIMAL128);
            case MINUS:
                return calculation.getA().subtract(calculation.getB(), MathContext.DECIMAL128);
            case DIVIDE:
                return calculation.getA().divide(calculation.getB(), MathContext.DECIMAL128);
            case MULTIPLY:
                return calculation.getA().multiply(calculation.getB(), MathContext.DECIMAL128);
            default:
                throw new NoSuchMethodException();
        }
    }

    private CalculatorRequestDTO getCalculatorRequestDTO(Message msg) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new String(msg.getBody()), CalculatorRequestDTO.class);
    }

}
