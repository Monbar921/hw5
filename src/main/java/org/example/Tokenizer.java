package org.example;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    public static final String NUMBER = "NUMBER";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String SIN = "sin";
    public static final String COS = "cos";
    public static final String SQR = "sqr";
    public static final String POW = "pow";
    public static final String LEFT_PAR = "(";
    public static final String RIGHT_PAR = ")";
    public static final String PLAIN_SEPARATOR = " ";
    public static final String FUNCTION_SEPARATOR = ",";

    public static RpnElement getRpnElement(String element) {
        RpnElement rpnElement;

        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(element);
        if (m.matches()) {
            rpnElement = new RpnElement(Token.NUMBER, 0, Double.parseDouble(m.group()));
        } else {
            switch (element) {
                case PLUS:
                    rpnElement = new RpnElement(Token.PLUS, 1);
                    break;
                case MINUS:
                    rpnElement = new RpnElement(Token.MINUS, 1);
                    break;
                case MULTIPLY:
                    rpnElement = new RpnElement(Token.MULTIPLY, 2);
                    break;
                case DIVIDE:
                    rpnElement = new RpnElement(Token.DIVIDE, 2);
                    break;
                case POW:
                    rpnElement = new RpnElement(Token.POW, 3);
                    break;
                case COS:
                    rpnElement = new RpnElement(Token.COS, 4);
                    break;
                case SIN:
                    rpnElement = new RpnElement(Token.SIN, 4);
                    break;
                case SQR:
                    rpnElement = new RpnElement(Token.SQR, 3);
                    break;
                case LEFT_PAR:
                    rpnElement = new RpnElement(Token.LEFT_PAR, -1);
                    break;
                case RIGHT_PAR:
                    rpnElement = new RpnElement(Token.RIGHT_PAR, -1);
                    break;
                case FUNCTION_SEPARATOR:
                    rpnElement = new RpnElement(Token.FUNCTION_SEPARATOR, 3);
                    break;
                default:
                    rpnElement = null;
                    break;
            }
        }

        return rpnElement;
    }

    public static Deque<RpnElement> tokenize(String expression) {
        Deque<RpnElement> tokens = new ArrayDeque<>();

        if (expression != null) {
            for (int i = 0; i < expression.length(); ++i) {
                RpnElement tempToken = null;
                int[] endPositions = new int[4];

                if (isPlainTokenSeparator(expression.charAt(i))) {
                    continue;
                }

                endPositions[0] = expression.indexOf(Token.LEFT_PAR.getName(), i);
                endPositions[1] = expression.indexOf(Token.RIGHT_PAR.getName(), i);
                endPositions[2] = expression.indexOf(Token.PLAIN_SEPARATOR.getName(), i);
                endPositions[3] = expression.indexOf(Token.FUNCTION_SEPARATOR.getName(), i);

                sortArr(endPositions, expression);

                if (!isTokenSeparator(expression.charAt(i))) {
                    String element = expression.substring(i, endPositions[0]);
                    tempToken = getRpnElement(String.valueOf(element));
                    tokens.push(tempToken);
                }

                char endChar = expression.charAt(endPositions[0] == expression.length() ? endPositions[0] - 1 : endPositions[0]);
                if (isTokenSeparator(endChar)) {
                    tempToken = getRpnElement(String.valueOf(endChar));
                    tokens.push(tempToken);
                }

                if (tempToken == null) {
                    tokens = null;
                    break;
                }

                i = endPositions[0];
            }
        }
        return tokens;
    }

    private static void sortArr(int[] arr, String expression) {
        int least = -1;

        Arrays.sort(arr);

        for (int x : arr) {
            if (x >= 0) {
                least = x;
                break;
            }
        }

        if (least == -1) {
            least = expression.length();
        }

        arr[0] = least;
    }

    private static boolean isTokenSeparator(char curToken) {
        return curToken == Token.RIGHT_PAR.getName().charAt(0)
                || curToken == Token.LEFT_PAR.getName().charAt(0)
                || curToken == Token.FUNCTION_SEPARATOR.getName().charAt(0);
    }

    private static boolean isPlainTokenSeparator(char curToken) {
        return curToken == Token.PLAIN_SEPARATOR.getName().charAt(0);
    }
}
