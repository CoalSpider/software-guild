/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.assignment1;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class DogGenetics {
    public static void main(String[] args) {
        String[] dogBreeds = new String[]{
            "St. Bernard","Chihuahua","Dramatic RedNosed Asian Pug","Common Cur","King Doberman"
        };
        Scanner scanner = new Scanner(System.in);
        System.out.print("What is your dogs name?");
        String petName = scanner.nextLine();
        System.out.println("Well then, I have this highly reliable report on "+petName+" prestigious background right here.");
        System.out.println("\n" + petName + " is: \n");
        
        Random random = new Random();
        int randomTotal = 0;
        for(int i =0; i < 5; i++){
            // random between 1 and 100
            int purrcent = random.nextInt(100)+1;
            if(randomTotal + purrcent <= 100){
                randomTotal += purrcent;
            } else {
                if(randomTotal < 99)
                purrcent = 1;
                else
                    purrcent = 0;
            }
            // take care of left over precents
            if(i==4){
                purrcent = 100-randomTotal;
            }
            System.out.println(purrcent+"% "+dogBreeds[i]);
        }
        
        System.out.println("Wow, that's QUITE the dog!");
    }
}
// TODO: update with normalization