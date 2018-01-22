/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson4;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class TriviaNight {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("ITS TRIVIA NIGHT");
        System.out.println("FIRST QUESTION!");
        String q1, q2, q3;
        String q1a, q1b, q1c, q1d;
        String q2a, q2b, q2c, q2d;
        String q3a, q3b, q3c, q3d;
        int a1, a2, a3;
        int sa1, sa2, sa3;
        String yourAnswer = "YOUR ANSWER: ";
        String input = null;

        q1 = "What is the answer?";
        q1a = "1) One";
        q1b = "2) Two";
        q1c = "3) Three";
        q1d = "4) Four";
        a1 = 4;
        System.out.printf("%s\n%s\t%s \n%s\t%s\n\n%s", q1, q1a, q1b, q1c, q1d, yourAnswer);
        input = sc.nextLine();
        sa1 = Integer.parseInt(input);
        System.out.println("");

        q2 = "What is the answer?";
        q2a = "1) One";
        q2b = "2) Two";
        q2c = "3) Three";
        q2d = "4) Four";
        a2 = 3;
        System.out.printf("%s\n%s\t%s \n%s\t%s\n\n%s", q2, q2a, q2b, q2c, q2d, yourAnswer);
        input = sc.nextLine();
        sa2 = Integer.parseInt(input);
        System.out.println("");

        q3 = "What is the answer?";
        q3a = "1) One";
        q3b = "2) Two";
        q3c = "3) Three";
        q3d = "4) Four";
        a3 = 2;
        System.out.printf("%s\n%s\t%s \n%s\t%s\n\n%s", q3, q3a, q3b, q3c, q3d, yourAnswer);
        input = sc.nextLine();
        sa3 = Integer.parseInt(input);
        System.out.println("");

        int correctCount = 0;
        correctCount += (a1 == sa1) ? 1 : 0;
        correctCount += (a2 == sa2) ? 1 : 0;
        correctCount += (a3 == sa3) ? 1 : 0;
        
        System.out.println("You got: " + correctCount + " correct");
    }
}
