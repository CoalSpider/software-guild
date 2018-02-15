/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.view;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class UserIOConsoleImpl implements UserIO {

    public final Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float readFloat(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        print(prompt);
        try {
            int input = Integer.parseInt(sc.nextLine());
            return (input >= min && input <= max) ? input : -1;
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    @Override
    public long readLong(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        print(prompt);
        try {
            return new BigDecimal(sc.nextLine());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

}
