package br.alessi.calculator.service;

import br.alessi.calculator.dto.CalculatorResponseDTO;
import org.springframework.amqp.core.Message;

public interface ICalculatorService {

    public CalculatorResponseDTO process(Message msg);

}
