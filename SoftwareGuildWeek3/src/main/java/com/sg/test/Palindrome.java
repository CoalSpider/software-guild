/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

/**
 *
 * @author Ben Norman
 */
public class Palindrome {

    public static void main(String[] args) {
        Palindrome p = new Palindrome();

        System.out.println("\ndupe string test\n");
        System.out.println("noon = " + p.isPalidrome("noon"));
        System.out.println("anna = " + p.isPalidrome("anna"));
        System.out.println("tacocat = " + p.isPalidrome("tacocat"));
        System.out.println("Ben = " + p.isPalidrome("Ben"));

        System.out.println("\nchar test\n");
        System.out.println("noon = " + p.isPalidrome2("noon"));
        System.out.println("anna = " + p.isPalidrome2("anna"));
        System.out.println("tacocat = " + p.isPalidrome2("tacocat"));
        System.out.println("Ben = " + p.isPalidrome2("Ben"));

        System.out.println("\nspace test\n");
        System.out.println("noon = " + p.isPalidrome3("no on"));
        System.out.println("anna = " + p.isPalidrome3("  anna"));
        System.out.println("tacocat = " + p.isPalidrome3("ta co cat    "));
        System.out.println("Ben = " + p.isPalidrome3("Be n"));

        System.out.println("\npunctuation test\n");
        System.out.println("noon = " + p.isPalidrome4("no,on"));
        System.out.println("anna = " + p.isPalidrome4(".'anna"));
        System.out.println("tacocat = " + p.isPalidrome4("!@#$%ta co cat@#$%"));
        System.out.println("Ben = " + p.isPalidrome4("B,e.n!"));
    }

    /**
     * checks if string is palidrome ignoring case*
     */
    boolean isPalidrome(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if string is palidrome ignoring case*
     */
    public boolean isPalidrome2(String s) {
        int low = 0;
        int high = s.length() - 1;
        while (low < high) {
            if (Character.toLowerCase(s.charAt(low)) != Character.toLowerCase(s.charAt(high))) {
                return false;
            }
            low++;
            high--;
        }
        return true;
    }

    /**
     * checks if string is palidrome ignoring case and spaces using
     * {@link Character#isSpace(char)}
     */
    public boolean isPalidrome3(String s) {
        int low = 0;
        int high = s.length() - 1;
        while (low < high) {
            while (Character.isSpace(s.charAt(low))) {
                low++;
            }
            while (Character.isSpace(s.charAt(high))) {
                high--;
            }
            if (Character.toLowerCase(s.charAt(low)) != Character.toLowerCase(s.charAt(high))) {
                return false;
            }
            low++;
            high--;
        }
        return true;
    }

    /**
     * checks if string is palidrome ignoring case and spaces using
     * {@link Character#isSpace(char)} and punctuation using...
     */
    public boolean isPalidrome4(String s) {
        int low = 0;
        int high = s.length() - 1;
        while (low < high) {
            while (Character.isLetterOrDigit(s.charAt(low)) == false) {
                low++;
            }
            while (Character.isLetterOrDigit(s.charAt(high)) == false) {
                high--;
            }
            if (Character.toLowerCase(s.charAt(low)) != Character.toLowerCase(s.charAt(high))) {
                return false;
            }
            low++;
            high--;
        }
        return true;
    }
}
