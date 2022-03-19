package br.alessi.rest.enums;

public enum Operation {
    SUM("sum"), MINUS("minus"), MULTIPLY("multiply"), DIVIDE("divide");

    private String operationName;

    Operation(String operation) {
        this.operationName = operation;
    }

    public String getOperation() {
        return this.operationName;
    }

}
