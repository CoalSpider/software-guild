/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson3.exercises;

/**
 *
 * @author Ben Norman
 */
public class InABucket {

    public static void main(String[] args) {

        // You can declare all KINDS of variables.
        String walrus;
        double piesEaten;
        float weightOfTeacupPig = 100.0f;
        int grainsOfSand = 42;

        // But declaring them just sets up the space for data
        // to use the variable, you have to put data IN it first!
        walrus = "Sir Leroy Jenkins III";
        piesEaten = 42.1;

        System.out.println("Meet my pet Walrus, " + walrus);
        System.out.print("He was a bit hungry today, and ate this many pies : ");
        System.out.println(piesEaten);
        
        System.out.println("The weight of a teacup pig is " + weightOfTeacupPig);
        System.out.println("In the known universe there are " + grainsOfSand + " grains of sand");
    }
}
