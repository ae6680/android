package com.shinav.mathapp.calculator;

public class OnNumpadOperationClickedEvent {

    public static final int OPERATION_INSERT = 0;
    public static final int OPERATION_BACKSPACE = 1;
    public static final int OPERATION_REMOVE_ALL = 2;

    private final int operation;
    private final String value;

    public OnNumpadOperationClickedEvent(int operation, String value) {
        this.operation = operation;
        this.value = value;
    }

    public int getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }
}
