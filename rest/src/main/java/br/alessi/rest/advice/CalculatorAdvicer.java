package br.alessi.rest.advice;

import br.alessi.rest.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Slf4j
@ControllerAdvice(basePackages = "br.alessi.rest.controller")
public class CalculatorAdvicer {


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArithmeticException.class)
    public ErrorDTO invalidInput(
            Exception exception) {
        log.error("", exception);
        return ErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDTO invalidOperation(
            Exception exception) {
        log.error("", exception);
        return ErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("The allowed method are sum, minus, divide and multiply. " + exception.getMessage())
                .timestamp(new Date())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDTO unexpectedError(
            Exception exception) {
        log.error("", exception);
        return ErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();
    }
}






