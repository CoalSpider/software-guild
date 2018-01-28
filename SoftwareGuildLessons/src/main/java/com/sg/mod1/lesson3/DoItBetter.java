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
public class DoItBetter{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("How many miles can you run? ");
        int milesRun = Integer.parseInt(in.nextLine());
        System.out.println("Thats it? I can run " + (milesRun*2+1));
        
        System.out.print("How many hotdogs can you eat? ");
        int hotdogs = Integer.parseInt(in.nextLine());
        System.out.println("Thats it? I can eat " + (hotdogs*2+1));
        
        System.out.print("How many languages do you know? ");
        int languages = Integer.parseInt(in.nextLine());
        System.out.println("Thats it? I know " + (languages*2+1));
    }
}
