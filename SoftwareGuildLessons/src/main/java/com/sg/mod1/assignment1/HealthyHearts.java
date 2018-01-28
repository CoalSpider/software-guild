/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.assignment1;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class HealthyHearts {
    // Their maximum heart rate should be 220 - their age.
    // The target heart rate zone is the 50 - 85% of the maximum

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("What is your age? ");
        int age = Integer.parseInt(scanner.nextLine());
        // new line
        System.out.println("");
        int maxHeartRate = 220-age;
        int lowerHRZone = (int)(0.5*maxHeartRate);
        int higherHRZone = (int)Math.ceil((0.85*maxHeartRate));
        System.out.println("Your maximum heart rate should be " + maxHeartRate + " beats per minute");
        System.out.println("Your target HR zone is " + lowerHRZone + " - " + higherHRZone + " beats pet minute");
    }
}
