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
public class GuessMe {
public static void main(String[] args) {
        int pickedNumber = 10;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick a number");
        int userNum = Integer.parseInt(scanner.nextLine());
        if(userNum == pickedNumber)
            System.out.println("Wow, nice guess! That was it!");
        else if(userNum < pickedNumber)
            System.out.println("Ha, nice try - too low! I chose " + pickedNumber);
        else
            System.out.println("Too bad, way to high. I chose " + pickedNumber);
    }
}
