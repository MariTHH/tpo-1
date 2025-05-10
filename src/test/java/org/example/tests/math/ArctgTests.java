package org.example.tests.math;

import org.example.tests.extensions.TestPrivateMethod;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.example.math.Arctg.arctg;
import static org.junit.jupiter.api.Assertions.*;

public class ArctgTests {

    @Test
    void TestZero() {
        assertEquals(0.0, arctg(0, 10), 1e-6);
    }

    @Test
    void TestOne() {
        assertEquals(Math.PI / 4, arctg(1, 300000), 1e-6);
    }

    @Test
    void TestMinusOne() {
        assertEquals(-Math.PI / 4, arctg(-1, 300000), 1e-6);
    }

    @Test
    void TestHalf() {
        assertEquals(Math.atan(0.5), arctg(0.5, 20), 1e-6);
    }

    @Test
    void testMinusHalf() {
        assertEquals(Math.atan(-0.5), arctg(-0.5, 20), 1e-6);
    }

    @Test
    void testNearOne() {
        assertEquals(Math.atan(0.999), arctg(0.999, 3000), 1e-6);
    }

    @Test
    void testNearMinusOne() {
        assertEquals(Math.atan(-0.999), arctg(-0.999, 3000), 1e-6);
    }

    @Test
    void testDivergenceOutsideRadius() {
        assertThrows(IllegalArgumentException.class, () -> arctg(1.1, 10));
    }



    @Test
    void testMaxDouble() {
        assertThrows(IllegalArgumentException.class, () -> arctg(Double.MAX_VALUE, 10));
    }

    @Test
    void testMinDouble() {
        assertEquals(Math.atan(Double.MIN_VALUE), arctg(Double.MIN_VALUE, 10), 1e-6);
    }

    @Test
    void testPositiveInfinity() {
        assertThrows(IllegalArgumentException.class, () -> arctg(Double.POSITIVE_INFINITY, 10));
    }

    @Test
    void testNegativeInfinity() {
        assertThrows(IllegalArgumentException.class, () -> arctg(Double.NEGATIVE_INFINITY, 10));
    }

    @Test
    void testNaN() {
        assertTrue(Double.isNaN(arctg(Double.NaN, 10)));
    }


    @Test
    @TestPrivateMethod(
            className = "org.example.math.Arctg",
            methodName = "calculateTerm",
            paramTypes = {double.class, int.class}
    )
    void testCalculateTerm() throws Exception {
        Class<?> clazz = Class.forName("org.example.math.Arctg");
        Method method = clazz.getDeclaredMethod("calculateTerm", double.class, int.class);
        method.setAccessible(true);

        double result = (double) method.invoke(null, 0.5, 2);
        double expected = Math.pow(-1, 2) * Math.pow(0.5, 5) / 5;

        assertEquals(expected, result, 1e-6);
    }
}