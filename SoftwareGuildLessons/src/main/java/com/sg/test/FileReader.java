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
public class FileReader {
   
}
class EntryPoint{
    public static void main(String[] args) {
        myMethod();
    }
    
    static void myMethod(){
        doStuff();
    }
    
    static void doStuff(){
        Thread ta = new Thread(); // read file 1
        Thread tb = new Thread(); // read file 2
        Thread tc = new Thread(); // read file 3
        ta.start();
    }
}
