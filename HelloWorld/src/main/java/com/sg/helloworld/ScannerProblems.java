/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.helloworld;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class ScannerProblems {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String inputA = scanner.nextLine();
        System.out.println("You entered " + inputA);
        
        int inputIntA = scanner.nextInt();
        System.out.println("You entered " + inputIntA);
         // consume carrige return that was not consumed by scanner.nextInt()
        scanner.nextLine();
        
        inputA = scanner.nextLine();
        System.out.println("You entered " + inputA);
    }
}
