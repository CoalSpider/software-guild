/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.view;

import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class UserIOConsoleImpl implements UserIO {

    private final Scanner scanner;

    public UserIOConsoleImpl(){
        scanner = new Scanner(System.in);
    }
    
    public UserIOConsoleImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    /** calls System.out.println(message)**/
    @Override
    public void print(String message) {
        System.out.println(message);
    }
    
    /** used to choose commands, returns -1 if input is not a integer**/
    @Override
    public int readInt(String prompt) {
        print(prompt);
        try{
            return Integer.parseInt(scanner.nextLine());
        }catch(NumberFormatException nfe){
            // do nothing
        }
        return -1;
    }

    /** used to choose commands in a range. returns -1 if input is non a integer or out of range */
    @Override
    public int readInt(String prompt, int min, int max) {
        print(prompt);
        try{
            int num = Integer.parseInt(scanner.nextLine());
            if(num >= min && num <= max){
                return num;
            }
        }catch(NumberFormatException nfe){
            // do nothing
        }
        return -1;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    
    // the following methods are not supported by choice case I dont use them
    
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
    public long readLong(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
