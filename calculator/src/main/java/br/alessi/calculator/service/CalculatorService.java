package br.alessi.calculator.service;

import br.alessi.calculator.dto.CalculatorRequestDTO;
import br.alessi.calculator.dto.CalculatorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class CalculatorService implements ICalculatorService {

    public CalculatorResponseDTO process(Message msg) {
        CalculatorResponseDTO result = new CalculatorResponseDTO();
        try {
            BigDecimal value = calculate(getCalculatorRequestDTO(msg));
            result.setStatus(HttpStatus.OK);
            result.setResult(value);
            return result;
        } catch (Exception e) {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMsg(e.getMessage());
            result.setResult(null);
            return result;
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
