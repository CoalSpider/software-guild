/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson12;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class App {

    private final Scanner scanner = new Scanner(System.in);
    private String operator;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        while (true) {
            askForOperator();
            if ("exit".equalsIgnoreCase(operator)) {
                System.out.println("Thanks for calculating! Bye");
                break;
            } else {
                SimpleCalculator.calculate(operator, askForOperands());
                System.out.println("result of " + SimpleCalculator.getCurrentResultAsString() + " is " + SimpleCalculator.getCurrentResult().toPlainString());
            }
        }
    }

    private void askForOperator() {
        System.out.println("Enter a operator:\n + addition\n - subtraction\n * multiplication\n / division\n exit exits the program");
        do {
            operator = scanner.nextLine();
            if (isValidOperator()) {
                break;
            } else {
                System.out.println("Please choose a valid operator");
            }
        } while (true);
    }

    private boolean isValidOperator() {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equalsIgnoreCase("exit");
    }

    // todo move to calculator
    private List<BigDecimal> askForOperands() {
        System.out.println("Enter at least two numbers");
        List<BigDecimal> operands = new ArrayList<>();

        // scanner nextBigDeicmal blocks cant use
//        while (true) {
//            while (scanner.hasNextBigDecimal()) { // blocks here because reasons
//                operands.add(new BigDecimal(scanner.next()));
//            } 
//            if(operands.size() < 2){
//                System.out.println("Need at least 2 numbers found " + operands.size());
//            } else {
//                break;
//            }
//        }

        // assuming valid inputs spaced by strings
        while (true) {
            String[] bigDecimals = scanner.nextLine().split(" ");
            for (String s : bigDecimals) {
                operands.add(new BigDecimal(s));
            }
            if(operands.size() < 2){
                System.out.println("Need at least 2 numbers found " + operands.size());
            } else {
                break;
            }
        }
        return operands;
    }

}
