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
public class YourLifeInMovies {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your birth year:");
        int age = Integer.parseInt(sc.nextLine());
        if (age < 2005) {
            System.out.println("Pixar's 'Up' came out half a decade ago.");
        }
        if (age < 1995) {
            System.out.println("the first Harry Potter came out over 15 years ago.");
        }
        if (age < 1985) {
            System.out.println(" Space Jam came out not last decade, but the one before THAT.");
        }
        if (age < 1975) {
            System.out.println("the original Jurassic Park release is closer to the lunar landing, than today.");
        }
        if (age < 1965) {
            System.out.println("MASH has been around for almost half a century!");
        }
    }
}
