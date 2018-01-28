/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lesson4;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class BirthStones {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter birth month (as number)");
        int birthMonth = Integer.parseInt(sc.nextLine());
        String monthName = null;
        String birthStone = null;
        if (birthMonth == 1) {
            monthName = "January";
            birthStone = "Garnet";
        } else if (birthMonth == 2) {
            monthName = "February";
            birthStone = "Amethyst";
        } else if (birthMonth == 3) {
            monthName = "March";
            birthStone = "Aquamarine";
        } else if (birthMonth == 4) {
            monthName = "April";
            birthStone = "Diamond";
        } else if (birthMonth == 5) {
            monthName = "May";
            birthStone = "Emerald";
        } else if (birthMonth == 6) {
            monthName = "June";
            birthStone = "Pearl";
        } else if (birthMonth == 7) {
            monthName = "July";
            birthStone = "Ruby";
        } else if (birthMonth == 8) {
            monthName = "August";
            birthStone = "Periodot";
        } else if (birthMonth == 9) {
            monthName = "September";
            birthStone = "Sapphire";
        } else if (birthMonth == 10) {
            monthName = "October";
            birthStone = "Opal";
        } else if (birthMonth == 11) {
            monthName = "November";
            birthStone = "Topaz";
        } else if (birthMonth == 12) {
            monthName = "December";
            birthStone = "Turquioze";
        } else {
            System.err.println("unknown month");
        }
        if(monthName!=null&&birthStone!=null)
        System.out.printf("%s's birthstone is %s\n",monthName,birthStone);
    }
}
