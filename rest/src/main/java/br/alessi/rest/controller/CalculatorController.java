package br.alessi.rest.controller;


import br.alessi.rest.dto.CalculatorResponseDTO;
import br.alessi.rest.enums.Operation;
import br.alessi.rest.service.ICalculatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
public class CalculatorController {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Autowired
    ICalculatorService calculatorService;

    @GetMapping("/{operation}")
    public ResponseEntity<CalculatorResponseDTO> sum(@PathVariable String operation,
                                                     @RequestParam BigDecimal a,
                                                     @RequestParam BigDecimal b) throws JsonProcessingException {
        return ResponseEntity.ok(CalculatorResponseDTO
                .builder()
                .result(calculatorService.calculate(Operation.valueOf(operation.toUpperCase()), a, b))
                .build());
    }

}

