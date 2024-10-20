package org.example;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void checkPlus() {
        String expression = "(1 + 1)";
        double res = homeWork.calculate(expression);
        assertEquals(2, res, 0.0001);
    }

    @Test
    void checkMinus() {
        String expression = "(1 + 1) - 3 - 4";
        double res = homeWork.calculate(expression);
        assertEquals(-5, res, 0.0001);
    }

    @Test
    void checkMultiply() {
        String expression = "(1 + 1) - 3 * 4";
        double res = homeWork.calculate(expression);
        assertEquals(-10, res, 0.0001);
    }

    @Test
    void checkDivide() {
        String expression = "(10 + 1) / 3 - 4";
        double res = homeWork.calculate(expression);
        assertEquals(-0.33333, res, 0.0001);
    }

    @Test
    void checkPow() {
        String expression = "(10 + 1) * pow(2,3) - 4 + 1";
        double res = homeWork.calculate(expression);
        assertEquals(85, res, 0.0001);
    }

    @Test
    void checkSqr() {
        String expression = "(10 + 1) * pow(2,3) / sqr(2) + 1";
        double res = homeWork.calculate(expression);
        assertEquals(23, res, 0.0001);
    }

    @Test
    void checkCos() {
        String expression = "(10 + 1) * cos(0) + 1";
        double res = homeWork.calculate(expression);
        assertEquals(12, res, 0.0001);
    }

    @Test
    void checkSin() {
        String expression = "(10 + 1) * sin(0) + 1";
        double res = homeWork.calculate(expression);
        assertEquals(1, res, 0.0001);
    }

}