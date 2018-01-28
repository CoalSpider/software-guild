/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.addressbook.ui;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class UserIOConsoleImpl implements UserIO {

    private final Scanner scanner;

    public UserIOConsoleImpl() {
        scanner = new Scanner(System.in);
    }

    public UserIOConsoleImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * calls System.out.println(message)*
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        return Double.parseDouble(scanner.nextLine());
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        print(prompt);
        while (true) {
            double result = Double.parseDouble(scanner.nextLine());
            if (result >= min && result <= max) {
                return result;
            } else {
                print("Invalid double, double must be between " + min + " and " + max);
            }
        }
    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        return Float.parseFloat(scanner.nextLine());
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        print(prompt);
        while (true) {
            float result = Float.parseFloat(scanner.nextLine());
            if (result >= min && result <= max) {
                return result;
            } else {
                print("Invalid float, float must be between " + min + " and " + max);
            }
        }
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        print(prompt);
        while (true) {
            int result = -1;
            try {
                result = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // do nothing print normal error message   
            }
            if (result >= min && result <= max) {
                return result;
            } else {
                print("Invalid integer, integer must be between " + min + " and " + max);
            }
        }
    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        return Long.parseLong(scanner.nextLine());
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        print(prompt);
        while (true) {
            long result = Long.parseLong(scanner.nextLine());
            if (result >= min && result <= max) {
                return result;
            } else {
                print("Invalid long, long must be between " + min + " and " + max);
            }
        }
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

}
