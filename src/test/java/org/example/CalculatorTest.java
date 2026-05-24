package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Adding two positive numbers returns their sum")
    void addTwoPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("Subtracting two numbers returns the difference")
    void subtractTwoNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    @DisplayName("Multiplying two numbers returns the product")
    void multiplyTwoNumbers() {
    assertEquals(6, calculator.multiply(2, 3));
    }
    @Test
    @DisplayName("Dividing two numbers returns the quotient")
    void divideTwoNumbers() {
    assertEquals(2.5, calculator.divide(5, 2));
    }
    @Test
    @DisplayName("Dividing by zero throws IllegalArgumentException")
    void divideByZeroThrowsException() {
    assertThrows(IllegalArgumentException.class,
    ()-> calculator.divide(5, 0));
    }

    @Test
    @DisplayName("Raising a number to a power returns the correct result")
    void powerReturnsCorrectResult() {
    assertEquals(8.0, calculator.power(2, 3));
    }
}
