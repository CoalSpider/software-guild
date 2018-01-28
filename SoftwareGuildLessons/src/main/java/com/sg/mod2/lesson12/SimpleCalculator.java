/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson12;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public class SimpleCalculator {

    private static List<BigDecimal> currentOperators;
    private static BigDecimal currentResult;
    private static String currentResultAsString;
    private static MathContext mc = new MathContext(25, RoundingMode.HALF_UP);

    public static void calculate(String operator, List<BigDecimal> operators) {
        currentOperators = operators;
        StringBuilder resultString = new StringBuilder();
        switch (operator) {
            case "+":
                currentResult = addAll(resultString);
                break;
            case "-":
                currentResult = subAll(resultString);
                break;
            case "*":
                currentResult = multAll(resultString);
                break;
            case "/":
                currentResult = divideAll(resultString);
                break;
            default:
                System.err.println("unknown operator");
                currentResult = new BigDecimal(0);
                break;
        }
        currentResultAsString = resultString.toString();
    }

    private static BigDecimal addAll(StringBuilder builder) {
        BigDecimal result = currentOperators.get(0);
        for (int i = 1; i < currentOperators.size(); i++) {
            BigDecimal bd = currentOperators.get(i);
            result = result.add(bd, mc);
            resultsAppend(bd, builder);
        }
        result.stripTrailingZeros();
        return result;
    }

    private static BigDecimal subAll(StringBuilder builder) {
        BigDecimal result = currentOperators.get(0);
        for (int i = 1; i < currentOperators.size(); i++) {
            BigDecimal bd = currentOperators.get(i);
            result = result.subtract(bd, mc);
            resultsAppend(bd, builder);
        }
        result.stripTrailingZeros();
        return result;
    }

    private static BigDecimal multAll(StringBuilder builder) {
        BigDecimal result = currentOperators.get(0);
        for (int i = 1; i < currentOperators.size(); i++) {
            BigDecimal bd = currentOperators.get(i);
            result = result.multiply(bd, mc);
            resultsAppend(bd, builder);
        }
        result.stripTrailingZeros();
        return result;
    }

    private static BigDecimal divideAll(StringBuilder builder) {
        BigDecimal result = currentOperators.get(0);
        for (int i = 1; i < currentOperators.size(); i++) {
            BigDecimal bd = currentOperators.get(i);
            result = result.divide(bd, mc);
            resultsAppend(bd, builder);
        }
        result.stripTrailingZeros();
        return result;
    }

    public static BigDecimal getCurrentResult() {
        return currentResult;
    }

    public static String getCurrentResultAsString() {
        return currentResultAsString;
    }

    private static void resultsAppend(BigDecimal bd, StringBuilder builder) {
        builder.append(bd.toPlainString());
        builder.append(" ");
    }
}
