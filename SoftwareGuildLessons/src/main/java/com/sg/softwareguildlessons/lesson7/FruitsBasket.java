/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson7;

/**
 *
 * @author Ben Norman
 */
public class FruitsBasket {

    public static void main(String[] args) {
        String[] fruit = {"Orange", "Apple", "Orange", "Apple", "Orange", "Apple", "Orange", "Apple", "Orange", "Orange", "Orange", "Apple", "Orange", "Orange", "Apple", "Orange", "Orange", "Apple", "Apple", "Orange", "Apple", "Apple", "Orange", "Orange", "Apple", "Apple", "Apple", "Apple", "Orange", "Orange", "Apple", "Apple", "Orange", "Orange", "Orange", "Orange", "Apple", "Apple", "Apple", "Apple", "Orange", "Orange", "Apple", "Orange", "Orange", "Apple", "Orange", "Orange", "Apple", "Apple", "Orange", "Orange", "Apple", "Orange", "Apple", "Orange", "Apple", "Orange", "Apple", "Orange", "Orange"};

        // Fruit sorting code goes here!
        int numOranges = 0;
        int numApples = 0;
        for (String s : fruit) {
            if (s.equals("Orange")) {
                numOranges++;
            }
            if (s.equals("Apple")) {
                numApples++;
            }
        }

        String[] oranges = new String[numOranges];
        String[] apples = new String[numApples];

        int orangeIndex = 0;
        int appleIndex = 0;
        for (String s : fruit) {
            // if(s.equals("Orange")){ // needs to check for null
            if ("Orange".equals(s)) {
                // assign orange to the index the increment the index
                oranges[orangeIndex++] = s; // || = "Orange" || == fruit[i];
            }
            //  if(s.equals("Apple")){ // needs to check for null
            if ("Apple".equals(s)) {
                apples[appleIndex++] = s; // || = "Apple" || == fruit[i]; 
            }
        }
        System.out.println("Total number of fruit in basket = " + fruit.length);
        System.out.println("num oranges = " + oranges.length);
        System.out.println("num apples = " + apples.length);
    }
}
