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
public class MiniMadLibs {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String noun, adjective, noun2, number, adjective2, plural1, plural2, plural3, verbPresTense, verbPastTense;
        System.out.println("LETS PLAY MAD LIBS");
        System.out.print("I need a noun: ");
        noun = in.nextLine();
        System.out.print("Now a adj: ");
        adjective = in.nextLine();
        System.out.print("Another noun: ");
        noun2 = in.nextLine();
        System.out.print("And a number: ");
        number = in.nextLine();
        System.out.print("Another adj: ");
        adjective2 = in.nextLine();
        System.out.print("A plural noun: ");
        plural1 = in.nextLine();
        System.out.print("Another one: ");
        plural2 = in.nextLine();
        System.out.print("One more: ");
        plural3 = in.nextLine();
        System.out.print("a verb (present tense): ");
        verbPresTense = in.nextLine();
        System.out.print("Same verb (past tense): ");
        verbPastTense = in.nextLine();
        System.out.printf("%s: the %s frontier. These are the voyages of the starship %s. Its %s-year mission: to explore strange %s %s, to seek out %s %s and %s %s, to boldly %s where no one has %s before.",
         // 1 2 4 3 5 6 5 7 5 8 9 10
                noun,adjective,noun2,number,adjective2,plural1,adjective2, plural2,adjective2, plural3,verbPresTense,verbPastTense);

    }
}
