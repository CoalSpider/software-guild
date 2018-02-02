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
public class AlgorithmStuff {
    public double[] someMethod(double a, double b, double c){
        double[] arr = new double[2];
        if(a < 0){
           arr[0] = b + c;
        }else if(b < 0){
            arr[0] = a + c;
        }else if(c < 0){
            arr[0] = a + b;
        }
         
        
         return arr;
    }
    
    public static void main(String[] args) {
        AlgorithmStuff as = new AlgorithmStuff();
        double[] a = as.someMethod(1, -4, 3);
        System.out.println(a[0] + " " + a[1]);
    }
}
