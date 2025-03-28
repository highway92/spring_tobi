package com.springbook.tobi;

import com.springbook.tobi.learningtest.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcSumTest {
    Calculator calc;
    String filePath;
    @BeforeEach
    public void setUp() {
        this.calc = new Calculator();
        this.filePath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void testCalcSum() throws IOException {
        Integer sum = calc.calcSum(filePath);
        assertEquals(sum, 10);
    }

    @Test
    public void multipleSum() throws IOException {
        Integer sum = calc.multiple(filePath);
        assertEquals(sum, 24);
    }

    @Test
    public void concatTest() throws IOException {
        String result = calc.concat(filePath);
        assertEquals(result, "a1234");
    }
}
