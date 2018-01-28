/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lesson3;

import static java.lang.System.in;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class PassingTheTuringTest {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("what is your name? ");
        String name = in.nextLine();
        
        System.out.print("Hi, " + name+"! My name is AI98123.\n\nWhat's your favorite color? "); 
        String favoriteColor = in.nextLine();
        
        System.out.println("Huh, " + favoriteColor+"? Mine's Electric Lime.\n");
        
        System.out.println("I really like limes. They're my favorite food, too.");
        System.out.print("What's YOUR favorite food, "+name+"?");
        String favoriteFood  = in.nextLine();
        
        if(favoriteFood.toLowerCase().equals("cake")){
            System.out.println("Really? " + favoriteFood + "? ITS A LIE!!!!");
            throw new RuntimeException("The cake is a lie");
        } else {
            System.out.println("Really? " + favoriteFood + "? That's wild!\n");
        }
        
        System.out.print("Speaking of favorites, whats your favorite number? ");
        int favoriteNumber = Integer.parseInt(in.nextLine());
        System.out.println();
        
        System.out.println(favoriteNumber + " is a cool number. Mine's -7");
        System.out.println("Did you know " + favoriteNumber + " * -7 is " + (favoriteNumber*-7) + "? That's a cool number too!\n");
        

        System.out.println("Well, thanks for talking to me, " + name);
    }
}
