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
public class WithoutOperators {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /*   System.out.println("MULTIPLICATION");
        System.out.println("ENTER A NUMBER");
        String num1 = scanner.nextLine();
        int a = Integer.parseInt(num1);
        System.out.println("ENTER ANOTHER NUMBER");
        String num2 = scanner.nextLine();
        int b = Integer.parseInt(num2);
        int result = 0;
        for(int i = 0; i < b; i++){
            result = result + a;
        }
        System.out.println("result="+result);*/

        System.out.println("DIVISION");
        System.out.println("ENTER A NUMBER");
        String num3 = scanner.nextLine();
        int d = Integer.parseInt(num3);
        System.out.println("ENTER DIVISOR");
        String num4 = scanner.nextLine();
        int c = Integer.parseInt(num4);
        int result2 = 0;
        for (int i = Math.abs(d); i >= Math.abs(c); i -= Math.abs(c)) {
            result2++;
        }
        if (d * c < 0) {
            result2 = -result2;
        }
        // clever --> result *= (d*c < 0) ? -1 : 1;
        System.out.println(d + " / " + c + " = " + result2);
        /*
        System.out.println("REMAINDER");
        System.out.println("ENTER A NUMBER");
        String num3 = scanner.nextLine();
        int d = Integer.parseInt(num3);
        System.out.println("ENTER DIVISOR");
        String num4 = scanner.nextLine();
        int c = Integer.parseInt(num4);
        int i = Math.abs(d);
        for (; i >= Math.abs(c); i -= Math.abs(c)) {
            // do nothing
        }
        if(c*d < 0) i = -i
        System.out.println(d + " % " + c + " = " + i);
        System.out.println(d % c);
       int one = scanner.nextInt();
       int two  = scanner.nextInt();
        System.out.println(one+" , " + two);*/

    }
}
