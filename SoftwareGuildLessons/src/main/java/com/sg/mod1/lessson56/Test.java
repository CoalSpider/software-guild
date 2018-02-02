/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lessson56;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println("Days till friday = " + DaysOfWeek.getDaysTillFriday(input));
    }
}

enum DaysOfWeek {
    MONDAY(4), TUESDAY(3), WEDNESDAY(2), THURSDAY(1), FRIDAY(0), SATURDAY(6), SUNDAY(5);
    int daysTillFriday;

    DaysOfWeek(int daysTillFriday) {
        this.daysTillFriday = daysTillFriday;
    }

    static int getDaysTillFriday(String input) {
        try {
            return DaysOfWeek.valueOf(input.toUpperCase()).daysTillFriday;
        } catch (IllegalArgumentException e) {
            return Integer.MAX_VALUE;
        }
    }
}
