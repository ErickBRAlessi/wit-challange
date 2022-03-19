package br.alessi.calculator.service;

import br.alessi.calculator.dto.CalculatorRequestDTO;
import br.alessi.calculator.dto.CalculatorResponseDTO;
import org.springframework.amqp.core.Message;

import java.math.BigDecimal;

public interface ICalculatorService {

    public CalculatorResponseDTO process(Message msg);

    public BigDecimal calculate(CalculatorRequestDTO calculation) throws NoSuchMethodException;
}
