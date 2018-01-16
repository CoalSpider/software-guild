/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson3.exercises;

/**
 *
 * @author Ben Norman
 */
public class ComentingCode {

    public static void main(String[] args) {

        // Comments are written to explain code in an easily
        // understandable way
        // Basically for single lines 
        // anything after // is considered a comment
        System.out.println("Normal code is compiled and runs ...");
        System.out.println("Comments however ... ");// do not execute!

        // Comments can be on their own line
        System.out.println("..."); // or they can share like this

        // However if you put the // BEFORE a line of code
        // System.out.println("Then it is considered a commnent");
        // System.out.println("and won't execute!");
        /*
        
           This is a multi-lined comment!
           Named because, well, it spans SO many lines!
        
         */
    }

    // you can also use a hack when autoformatting is not being nice
    // the autoformat will not move lines that end in a comment it will hoever split the comment if needed
    // now that I think of it this may only be for eclipse with mostly default autoformatting
    public static void someReallyLongMethodName(int arg1, int arg2, int arg3, int arg4) {//

    }
}
