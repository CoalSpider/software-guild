/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson12;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class IntrestCalculatorRefactor {
    public static void main(String[] args) {
        IntrestCalculator.start();
    }
}

class IntrestCalculator{
    private static Scanner scanner = new Scanner(System.in);

    private static BigDecimal principle;
    private static BigDecimal anualIntrestRate;
    private static BigDecimal quarterlyIntrestRate;
    private static int yearsToCompound;
    
    static void start(){
        System.out.println("WELCOME TO THE INTREST CALCULATOR");
        getInput();
    }

    static void getInput() {
        System.out.print("Enter Principle: ");
        while (true) {
            // todo: erro handling msg
            principle = new BigDecimal(scanner.nextLine());
            break;
        }
        while(true){
            // todo error handling and msg
            anualIntrestRate = new BigDecimal(scanner.nextLine());
            break;
        }
        quarterlyIntrestRate = anualIntrestRate.divide(new BigDecimal(4));
        while(true){
            // todo error handling msg
            yearsToCompound = Integer.parseInt(scanner.nextLine());
        }
    }
}
