package org.example;

public class RpnElement {
    private final Token token;
    private final int priority;
    private final double value;

    public RpnElement(Token token, int priority, double value) {
        this.token = token;
        this.priority = priority;
        this.value = value;
    }

    public RpnElement(Token token, int priority) {
        this.token = token;
        this.priority = priority;
        this.value = 0;
    }

    public Token getToken() {
        return token;
    }

    public int getPriority() {
        return priority;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RpnElement{" +
                "token=" + token.getName() +
                ", priority=" + priority +
                ", value=" + value +
                '}';
    }
}
