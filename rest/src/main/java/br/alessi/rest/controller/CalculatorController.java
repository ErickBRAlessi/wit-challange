package br.alessi.rest.controller;


import br.alessi.rest.dto.CalculatorResponseDTO;
import br.alessi.rest.enums.Operation;
import br.alessi.rest.service.ICalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CalculatorController {

    @Autowired
    ICalculatorService calculatorService;

    @GetMapping("/{operation}")
    public ResponseEntity<CalculatorResponseDTO> sum(@PathVariable String operation,
                                                     @RequestParam BigDecimal a,
                                                     @RequestParam BigDecimal b) {
        return ResponseEntity.ok(CalculatorResponseDTO
                .builder()
                .result(calculatorService.calculate(Operation.valueOf(operation.toUpperCase()), a, b))
                .build());
    }

}
