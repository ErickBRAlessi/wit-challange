package br.alessi.calculator.service;

import br.alessi.calculator.dto.CalculatorRequestDTO;
import br.alessi.calculator.dto.CalculatorResponseDTO;

public interface ICalculatorService {

    public CalculatorResponseDTO calculate(CalculatorRequestDTO calculation);
}
