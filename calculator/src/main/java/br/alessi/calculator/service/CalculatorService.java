package br.alessi.calculator.service;

import br.alessi.calculator.dto.CalculatorRequestDTO;
import br.alessi.calculator.dto.CalculatorResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class CalculatorService implements ICalculatorService {

    public CalculatorResponseDTO calculate(CalculatorRequestDTO calculation) {
        switch (calculation.getOperator()) {
            case SUM:
                return CalculatorResponseDTO.builder().result(calculation.getA().add(calculation.getB(), MathContext.DECIMAL128)).build();
            case MINUS:
                return CalculatorResponseDTO.builder().result(calculation.getA().subtract(calculation.getB(), MathContext.DECIMAL128)).build();
            case DIVIDE:
                return CalculatorResponseDTO.builder().result(calculation.getA().divide(calculation.getB(), MathContext.DECIMAL128)).build();
            case MULTIPLY:
                return CalculatorResponseDTO.builder().result(calculation.getA().multiply(calculation.getB(), MathContext.DECIMAL128)).build();
            default:
                return CalculatorResponseDTO.builder().result(BigDecimal.ZERO).build();
        }
    }


}
