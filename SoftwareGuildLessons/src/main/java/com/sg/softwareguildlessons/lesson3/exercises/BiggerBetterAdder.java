/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson3.exercises;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class BiggerBetterAdder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number");
        int a = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Enter another number");
        int b = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Enter another number");
        int c = Integer.parseInt(scanner.nextLine());
        
        System.out.println("num1=" + a + " num2=" + b + " num3=" + c);
        int d = a + b + c;
        System.out.println("sum=" + d);
        System.out.println(d);
    }
}
