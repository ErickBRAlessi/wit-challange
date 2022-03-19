package br.alessi.rest.service;

import br.alessi.rest.enums.Operation;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public interface ICalculatorService {

    public BigDecimal sum(BigDecimal a, BigDecimal b) throws JsonProcessingException;

    public BigDecimal minus(BigDecimal a, BigDecimal b) throws JsonProcessingException;

    public BigDecimal multiply(BigDecimal a, BigDecimal b) throws JsonProcessingException;

    public BigDecimal divide(BigDecimal a, BigDecimal b) throws JsonProcessingException;

    public BigDecimal calculate(Operation operation, BigDecimal a, BigDecimal b) throws JsonProcessingException;
}
