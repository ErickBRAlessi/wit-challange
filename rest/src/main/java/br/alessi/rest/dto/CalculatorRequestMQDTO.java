package br.alessi.rest.dto;

import br.alessi.rest.enums.Operation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorRequestMQDTO {

    private Operation operator;

    private BigDecimal a;

    private BigDecimal b;

}
