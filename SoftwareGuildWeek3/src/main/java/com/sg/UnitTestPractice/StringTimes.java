/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.UnitTestPractice;

/**
 *
 * @author Ben Norman
 */
public class StringTimes {
    public static void main(String[] args) {
        StringTimes st = new StringTimes();
        String s = st.stringTimes(null, 4);
        System.out.println("s="+s);
        System.out.println(null+"");
    }
    // Given a String and a non-negative int n, return a larger String 
    // that is n copies of the original String. 
    //
    // stringTimes("Hi", 2) -> "HiHi"
    // stringTimes("Hi", 3) -> "HiHiHi"
    // stringTimes("Hi", 1) -> "Hi"
    public String stringTimes(String str, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        
        String result = "";
        for(int i = 0; i < n; i++){
            result += str;
        }
        return result;
    }

}
