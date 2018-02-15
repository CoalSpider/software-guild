/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.view;

import java.math.BigDecimal;

/**
 *
 * @author Ben Norman
 */
public interface UserIO {

    /**
     * @param message the message to print
     */
    void print(String message);

    /**
     * @param message the message to print on a new line
     */
    void println(String message);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    /**
     *
     * @param prompt the message to print
     * @return the int input or -1 for invalid input
     */
    int readInt(String prompt);

    /**
     *
     * @param prompt the message to print
     * @return -1 for invalid and out of range input. Otherwise returns the int
     * the user inputed
     */
    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    /**
     *
     * @param prompt message to print
     * @return a valid big decimal or null if there was an issue converting the
     * input to a big decimal
     */
    BigDecimal readBigDecimal(String prompt);

    /**
     * @param prompt the message to print
     * @return the user inputF
     */
    String readString(String prompt);
}
