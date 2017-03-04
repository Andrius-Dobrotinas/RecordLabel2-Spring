package com.andrewd.recordlabel.common.components;

import org.junit.*;

public class BatchCountCalculatorTests {

    @Test
    public void TestCase1() {
        int result = BatchCountCalculator.calc(2, 2);
        Assert.assertEquals(1, result);
    }

    @Test
    public void TestCase2() {
        int result = BatchCountCalculator.calc(3, 2);
        Assert.assertEquals(2, result);
    }

    @Test
    public void TestCase3() {
        int result = BatchCountCalculator.calc(1, 2);
        Assert.assertEquals(1, result);
    }

    @Test
    public void TestCase4() {
        int result = BatchCountCalculator.calc(0, 2);
        Assert.assertEquals(1, result);
    }
}
