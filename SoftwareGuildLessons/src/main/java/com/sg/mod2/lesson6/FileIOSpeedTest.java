/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class FileIOSpeedTest {
    public static void main(String[] args) {
        Random random = new Random(1993);
        // first create a file with 10_000 lines if it doesnt already exist
        File file = new File("LargeTestFile.txt");
        
        // makes a roughly 9mb file
        if(file.exists()==false){
            try {
                PrintWriter pw = new PrintWriter("LargeTestFile.txt");
                StringBuilder result = new StringBuilder();
                for(int i = 0; i < 10_000; i++){
                    result.delete(0, result.length());
                    for(int j = 0; j < 1_000; j++){
                        // randomizes 1/2 character range
                        result.append((char)random.nextInt(Character.MAX_VALUE));
                    }
                    pw.println(result.toString());
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File not found " + ex.getMessage());
            }
        }
        
        final int iterations = 100_000;
        long start = System.nanoTime();
        for(int i = 0; i < iterations; i++){
            try {
                Scanner scanner = new Scanner(file);
                String currLine = scanner.nextLine();
            } catch (FileNotFoundException ex) {
                System.out.println("file not found " + ex.getMessage());
                System.exit(0);
            }
        }
        long result = System.nanoTime() - start;
        System.out.println("plain scanner ns = " +result);
        System.out.println("plain scanner ms = " +result/1e-6);
        System.out.println("plain scanner sec = " + result/1e-9);
        
        long start2 = System.nanoTime();
        for(int i = 0; i < iterations; i++){
            try {
                Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
                String currLine = scanner.nextLine();
            } catch (FileNotFoundException ex) {
                System.out.println("file not found " + ex.getMessage());
                System.exit(0);
            }
        }
        long result2 = System.nanoTime() - start2;
        System.out.println("buffered scanner ns = " +result2);
        System.out.println("buffered scanner ms = " +result2/1e-6);
        System.out.println("buffered scanner sec = " + result2/1e-9);
        
        
        long start3 = System.nanoTime();
        for(int i = 0; i < iterations; i++){
            try {
                Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
                String currLine = scanner.nextLine();
            } catch (FileNotFoundException ex) {
                System.out.println("file not found " + ex.getMessage());
                System.exit(0);
            }
        }
        long result3 = System.nanoTime() - start3;
        System.out.println("b scanner ns = " +result3);
        System.out.println("b scanner ms = " +result3/1e-6);
        System.out.println("b scanner sec = " + result3/1e-9);
        
        long start4 = System.nanoTime();
        for(int i = 0; i < iterations; i++){
            try {
                Scanner scanner = new Scanner(file);
                String currLine = scanner.nextLine();
            } catch (FileNotFoundException ex) {
                System.out.println("file not found " + ex.getMessage());
                System.exit(0);
            }
        }
        long result4 = System.nanoTime() - start4;
        System.out.println("p scanner ns = " +result4);
        System.out.println("p scanner ms = " +result4/1e-6);
        System.out.println("p scanner sec = " + result4/1e-9);
   }
//    plain scanner ns = 4813194131
//plain scanner ms = 4.813194131E15
//plain scanner sec = 4.8131941309999995E18
//buffered scanner ns = 5949777349
//buffered scanner ms = 5.949777349E15
//buffered scanner sec = 5.9497773489999995E18
//b scanner ns = 5552862330
//b scanner ms = 5.55286233E15
//b scanner sec = 5.55286233E18
//p scanner ns = 4335027965
//p scanner ms = 4.335027965E15
//p scanner sec = 4.3350279649999995E18
    
    // probably the extra object creation in the buffered method
    // test was the equivlent of reading 100_000 9mb files or 900 gb
}
