package org.example;

public enum Token {
    NUMBER(Tokenizer.NUMBER),
    PLUS(Tokenizer.PLUS),
    MINUS(Tokenizer.MINUS),
    MULTIPLY(Tokenizer.MULTIPLY),
    DIVIDE(Tokenizer.DIVIDE),
    SIN(Tokenizer.SIN),
    COS(Tokenizer.COS),
    SQR(Tokenizer.SQR),
    POW(Tokenizer.POW),
    LEFT_PAR(Tokenizer.LEFT_PAR),
    RIGHT_PAR(Tokenizer.RIGHT_PAR),
    PLAIN_SEPARATOR(Tokenizer.PLAIN_SEPARATOR),
    FUNCTION_SEPARATOR(Tokenizer.FUNCTION_SEPARATOR);

    private final String name;

    Token(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
