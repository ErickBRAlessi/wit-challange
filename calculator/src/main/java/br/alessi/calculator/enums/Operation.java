package br.alessi.calculator.enums;

public enum Operation {
    SUM("sum"), MINUS("minus"), MULTIPLY("multiply"), DIVIDE("divide");

    private String operationName;

    Operation(String operation) {
        this.operationName = operation;
    }
}
