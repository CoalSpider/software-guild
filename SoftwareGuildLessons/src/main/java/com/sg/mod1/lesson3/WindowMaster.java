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
public class WindowMaster {

    /**
     * keeps asking for a valid float from the given scanner until one is
     * provided prints a error method otherwise*
     */
    static float getFloatFromConsole(Scanner scanner) {
        while (true) {
            try {
                float result = Float.parseFloat(scanner.nextLine());
                return result;
            } catch (Exception e) {
                System.out.println("not a valid number");
            }
        }
    }

    public static void main(String[] args) {
        // declare variables for height and width
        float height;
        float width;

        // declare other variables
        float areaOfWindow;
        float cost;
        float perimeterOfWindow;

        // declare and initial the Scanner
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Please enter window height:");
        // loop until the user enteres a valid height
        height = WindowMaster.getFloatFromConsole(myScanner);

        System.out.println("Please enter window width:");
        // loop until the user enters a valid width
        width = WindowMaster.getFloatFromConsole(myScanner);

        //calculate the area of the window
        areaOfWindow = height * width;

        // calculate the perimeter of the window
        perimeterOfWindow = 2 * (height + width);

        // calculate the total cost - use a hard coded value for
        // material cost
        cost = ((3.50f * areaOfWindow) + (2.25f * perimeterOfWindow));

        System.out.println("Window height = " + height);
        System.out.println("Window width = " + width);
        System.out.println("Window area = " + areaOfWindow);
        System.out.println("Window perimeter = " + perimeterOfWindow);
        System.out.println("Total Cost =  " + cost);
    }
}
