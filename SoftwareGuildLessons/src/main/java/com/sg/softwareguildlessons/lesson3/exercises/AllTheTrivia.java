/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson3.exercises;

import static java.lang.System.in;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class AllTheTrivia {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String q1 = "1,024 Gigabytes is equal to one what?";
        String q2 = "In our solar system which is the only planet that rotates clockwise?";
        String q3 = "The largest volcano ever discovered in our solar system is located on which planet?";
        String q4 = "What is the most abundant element in the earth's atmosphere";
        String a1, a2, a3, a4;
        System.out.print(q1);
        a1 = in.nextLine();
        System.out.println(q2);
        a2 = in.nextLine();
        System.out.println(q3);
        a3 = in.nextLine();
        System.out.println(q4);
        a4 = in.nextLine();
        System.out.println("Wow, 1,024 Gigabytes is a " + a2);
        System.out.println("I didn't know that the largest ever volcano was discovered on " + a4);
        System.out.println("That's amazing that " + a1 + " is the most abundant element in the atmosphere..._");
        System.out.println(a3 + " is the only planet that rotates clockwise, neat!");
    }
}
