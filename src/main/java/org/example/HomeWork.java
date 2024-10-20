package org.example;

import java.util.Deque;
import java.util.InputMismatchException;
import java.util.Stack;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать метод, который по входной строке будет вычислять математические выражения.
     * <br/>
     * Операции: +, -, *, / <br/>
     * Функции: sin, cos, sqr, pow <br/>
     * Разделители аргументов в функции: , <br/>
     * Поддержка скобок () для описания аргументов и для группировки операций <br/>
     * Пробел - разделитель токенов, пример валидной строки: "1 + 2 * ( 3 - 4 )" с результатом -1.0 <br/>
     * <br/>
     * sqr(x) = x^2 <br/>
     * pow(x,y) = x^y
     */
    double calculate(String expr) {
        Deque<RpnElement> tokens = Tokenizer.tokenize(expr);
        Stack<RpnElement> numbers = new Stack<>();
        Stack<RpnElement> operands = new Stack<>();

        while (tokens != null && tokens.size() != 0) {
            RpnElement element = tokens.pollLast();
            if (element.getPriority() == 0) {
                numbers.push(element);
            } else if (operands.empty()) {
                operands.push(element);
            } else {
                if (element.getPriority() > 0) {
                    handleOperands(element, numbers, operands);
                } else {
                    handleParenthesis(element, numbers, operands);
                }
            }
        }

        while (operands.size() != 0) {
            RpnElement operand = operands.pop();
            handleArithmetic(operand, numbers);
        }

        return numbers.pop().getValue();
    }

    private void handleOperands(RpnElement element, Stack<RpnElement> numbers, Stack<RpnElement> operands) {
        RpnElement previousOperand = operands.peek();
        boolean isComma = false;

        while (element.getPriority() <= previousOperand.getPriority() && previousOperand.getPriority() > 0) {
            boolean tempCommaRes = isCommaOperand(previousOperand);
            if (isComma && tempCommaRes) {
                throw new InputMismatchException("There is extra comma token");
            } else if (tempCommaRes) {
                isComma = tempCommaRes;
                previousOperand = operands.pop();
                continue;
            }

            handleArithmetic(previousOperand, numbers);
            operands.pop();
            if (operands.size() > 0) {
                previousOperand = operands.peek();
            } else{
                break;
            }
        }

        operands.push(element);
    }

    private void handleParenthesis(RpnElement element, Stack<RpnElement> numbers, Stack<RpnElement> operands) {
        boolean isComma = false;
        if (element.getToken() == Token.LEFT_PAR) {
            operands.push(element);
        } else {
            RpnElement previousOperand = operands.pop();

            while (!operands.empty() && previousOperand.getToken() != Token.LEFT_PAR) {
                boolean tempCommaRes = isCommaOperand(previousOperand);
                if (isComma && tempCommaRes) {
                    break;
                } else if (tempCommaRes) {
                    isComma = tempCommaRes;
                    previousOperand = operands.pop();
                    continue;

                }

                if (previousOperand.getToken() == Token.POW && !isComma) {
                    continue;
                }

                handleArithmetic(previousOperand, numbers);
                previousOperand = operands.pop();
            }
        }
    }

    private boolean isCommaOperand(RpnElement operand) {
        return operand.getToken() == Token.FUNCTION_SEPARATOR;
    }

    private void handleArithmetic(RpnElement operand, Stack<RpnElement> numbers) {
        if (((operand.getPriority() <= 2 || operand.getToken() == Token.POW) && numbers.size() >= 2)
                || (operand.getPriority() >= 4 || operand.getToken() == Token.SQR) && numbers.size() >= 1) {
            double calcRes = doArithmetic(operand, numbers);
            numbers.push(new RpnElement(Token.NUMBER, 0, calcRes));
        }
    }

    private double doArithmetic(RpnElement operand, Stack<RpnElement> numbers) {
        double number1 = numbers.pop().getValue();

        double res = 0;

        if (operand.getToken() == Token.PLUS) {
            double number2 = numbers.pop().getValue();
            res = number2 + number1;
        } else if (operand.getToken() == Token.MINUS) {
            double number2 = numbers.pop().getValue();
            res = number2 - number1;
        } else if (operand.getToken() == Token.MULTIPLY) {
            double number2 = numbers.pop().getValue();
            res = number2 * number1;
        } else if (operand.getToken() == Token.DIVIDE) {
            double number2 = numbers.pop().getValue();
            res = number2 / number1;
        } else if (operand.getToken() == Token.COS) {
            res = Math.cos(number1);
        } else if (operand.getToken() == Token.SIN) {
            res = Math.sin(number1);
        } else if (operand.getToken() == Token.SQR) {
            res = Math.pow(number1, 2);
        } else if (operand.getToken() == Token.POW) {
            double number2 = numbers.pop().getValue();
            res = Math.pow(number2, number1);
        }

        return res;
    }

}
