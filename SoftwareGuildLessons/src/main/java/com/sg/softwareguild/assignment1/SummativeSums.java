/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguild.assignment1;

/**
 *
 * @author Ben Norman
 */
public class SummativeSums {

    static int[] num1 = {1, 90, -33, -55, 67, -16, 28, -55, 15};
    static int[] num2 = {999, -60, -77, 14, 160, 301};
    static int[] num3 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,
        140, 150, 160, 170, 180, 190, 200, -99};

    public static void main(String[] args) {
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i : num1) {
            sum1 += i;
        }
        for (int i : num2) {
            sum2 += i;
        }
        for (int i : num3) {
            sum3 += i;
        }
        System.out.println("\t#1 Array Sum: " + sum1);
        System.out.println("\t#2 Array Sum: " + sum2);
        System.out.println("\t#3 Array Sum: " + sum3);
    }
}
