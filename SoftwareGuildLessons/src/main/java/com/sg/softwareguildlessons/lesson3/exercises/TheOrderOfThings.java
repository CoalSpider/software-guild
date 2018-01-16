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
public class TheOrderOfThings {
    public static void main(String[] args) {
        double number;
        String opinion, size, age, shape, color, origin, material, purpose;
        String noun;
        // accepted order number-opinion-size-age-shape-color-origin-material-purpose noun.

        number = 5.0;
        opinion = "AWESOME";
        size = "teensy-weensy";
        age = "new";
        shape = "oblong";
        color = "BRIGHT yellow";
        origin = "AlphaCentaurian";
        material = "platinum";
        purpose = "good";

        noun = "dragons";

        // Using the + with strings, doesn't add it concatenates! (sticks them together)
        System.out.println(number +" "+ opinion +" "+ size +" "+ age +" "+ shape +" "+ color
                +" "+ origin +" "+ material +" "+ purpose +" "+ noun);
        // coula just added a space to the end of the variables
        // order2 is better
        System.out.println(number +" "+ color +" "+opinion+" "+ size +" "+ age +" "+ shape +" "
                +" "+ origin +" "+ material +" "+ purpose +" "+ noun);
    }
}
