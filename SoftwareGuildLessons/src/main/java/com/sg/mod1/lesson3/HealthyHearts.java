/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lesson3;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class HealthyHearts {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("What is your age?");
        int age = Integer.parseInt(in.nextLine());
        int maxHeartRate = 220 - age;
        int minZone = (int)(0.5*maxHeartRate);
        int maxZone = (int)(0.85*maxHeartRate);
        System.out.println("Your maximum heart rate is " + maxHeartRate);
        System.out.println("Your target heart rate zone is " + minZone + " to " + maxZone + " beats per minute");
    }
}
