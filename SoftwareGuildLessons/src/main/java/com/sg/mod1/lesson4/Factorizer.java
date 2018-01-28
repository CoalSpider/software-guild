/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lesson4;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class Factorizer {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number to factor: ");
        int input = Integer.parseInt(sc.nextLine());
        System.out.println("The factors of " + input + " are:");
        int factorTotal = 0;
        int factorCount = 1;
        System.out.println(1);
        for (int i = 2; i <= Math.abs(input/2); i++) {
            // check if its a factor
            if (input % i == 0) {
                System.out.println(i);
                factorTotal += i;
                factorCount++;
            }
        }
        
        System.out.println("Number of factors for " + input + " = " + factorCount);
        if(factorTotal == input){
            System.out.println(input + " is perfect");
        }
        if(factorCount == 1)
            System.out.println(input + " is prime");
    }
}
