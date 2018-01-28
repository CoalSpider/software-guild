/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson34;

import com.sg.mod2.lesson12.SimpleCalculator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class AppIO {
    private static UserIO io;
    private String operator;
    
    AppIO(){
        io = new UserIOImpl(new Scanner(System.in));
    }

    public static void main(String[] args) {
        AppIO app = new AppIO();
        app.run();
    }

    private void run() {
        while (true) {
            askForOperator();
            if ("exit".equalsIgnoreCase(operator)) {
                io.print("Thanks for calculating! Bye\n");
                break;
            } else {
                SimpleCalculator.getCurrentResult();
                SimpleCalculator.calculate(operator, askForOperands());
                io.print("result of " + SimpleCalculator.getCurrentResultAsString() + " is " + SimpleCalculator.getCurrentResult().toPlainString()+ " \n");
            }
        }
    }

    private void askForOperator() {
       io.print("Enter a operator:\n + addition\n - subtraction\n * multiplication\n / division\n exit exits the program\n");
        do {
            operator = io.readString("");
            if (isValidOperator()) {
                break;
            } else {
                io.print("Please choose a valid operator\n");
            }
        } while (true);
    }

    private boolean isValidOperator() {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equalsIgnoreCase("exit");
    }

    // todo move to calculator
    private List<BigDecimal> askForOperands() {
       io.print("Enter at least two numbers\n");
        List<BigDecimal> operands = new ArrayList<>();

        while (true) {
            String[] bigDecimals = io.readString("").split(" ");
            for (String s : bigDecimals) {
                operands.add(new BigDecimal(s));
            }
            if (operands.size() < 2) {
                io.print("Need at least 2 numbers found\n" + operands.size());
            } else {
                break;
            }
        }
        return operands;
    }

}