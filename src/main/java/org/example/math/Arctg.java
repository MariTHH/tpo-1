package org.example.math;

public class Arctg {
    public static double arctg(double x, int terms) {
        if (Math.abs(x) > 1) {
            throw new IllegalArgumentException("only valid for |x| ≤ 1");
        }
        double result = 0.0;
        for (int n = 0; n < terms; n++) {
            double term = calculateTerm(x, n);
            result += term;
        }
        return result;
    }

    private static double calculateTerm(double x, int n) {
        return Math.pow(-1, n) * Math.pow(x, 2 * n + 1) / (2 * n + 1);
    }
}
