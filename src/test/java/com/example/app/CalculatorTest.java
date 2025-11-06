package com.example.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Test
    void add_shouldReturnSum() {
        Calculator c = new Calculator();
        assertEquals(7, c.add(3, 4));
    }

    @Test
    void subtract_shouldReturnDifference() {
        Calculator c = new Calculator();
        assertEquals(1, c.subtract(5, 4));
    }
}
