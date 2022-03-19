package br.alessi.calculator.dto;

import br.alessi.calculator.enums.Operation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorRequestDTO {

    private Operation operator;

    private BigDecimal a;

    private BigDecimal b;

}
