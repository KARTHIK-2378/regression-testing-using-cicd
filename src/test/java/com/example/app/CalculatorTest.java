package com.example.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    @Test
    void add_shouldReturnSum() {
        Calculator c = new Calculator();
        assertEquals(8, c.add(3, 4)); // Intentionally wrong to test pipeline blocking
    }

    @Test
    void subtract_shouldReturnDifference() {
        Calculator c = new Calculator();
        assertEquals(1, c.subtract(5, 4));
    }
}
