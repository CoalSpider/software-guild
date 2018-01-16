/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson3;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class Adder2 {

    /**
     * keeps asking for a valid int from the given scanner until one is provided
     * prints a error method otherwise*
     */
    static int getIntFromConsole(Scanner scanner) {
        while (true) {
            try {
                int result = Integer.parseInt(scanner.nextLine());
                return result;
            } catch (Exception e) {
                System.out.println("not a valid number");
            }
        }
    }

    public static void main(String[] args) {
        int sum = 0;
        int operand1 = 0;
        int operand2 = 0;

        Scanner myScanner = new Scanner(System.in);

        System.out.println("Enter a non decimal number: ");
        // loop until user enters a valid number
        operand1 = Adder2.getIntFromConsole(myScanner);

        System.out.println("Enter another non decimal number: ");
        // loop until user enteres a valid number
        operand2 = Adder2.getIntFromConsole(myScanner);

        // assign the sum of operand1 and operand2 to sum
        sum = operand1 + operand2;

        // print the sum to the console
        System.out.println("The sum of the numbers is: " + sum);
    }
}
