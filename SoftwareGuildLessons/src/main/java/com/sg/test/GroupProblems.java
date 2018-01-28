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
public class GroupProblems {
    public static void main(String[] args) {
        GroupProblems gp = new GroupProblems();
        int[] result = gp.countDigits("abc1230913xouhgox321");
        for(int i = 0; i < result.length; i++){
            System.out.println(result[i]);
        }
    }
    
    int[] countDigits(String input){
        int[] result = new int[10];
        if(input==null){
            return result;
        }
        for(int i = 0; i< input.length(); i++){
            switch(input.substring(i, i+1)){
                case "0": result[0] = result[0]+1; break;
                case "1": result[1] = result[1]+1; break;
                case "2": result[2] = result[2]+1; break;
                case "3": result[3] = result[3]+1; break;
                case "4": result[4] = result[4]+1; break;
                case "5": result[5] = result[5]+1; break;
                case "6": result[6] = result[6]+1; break;
                case "7": result[7] = result[7]+1; break;
                case "8": result[8] = result[8]+1; break;
                case "9": result[9] = result[9]+1; break;
                default: // do nothing
            }
        }
        return result;
    }
    
    String[] stringByTwo(String input){
        int len = (input.length()+1)/2;
        String[] result = new String[len];
        int resultIndex = 0;
        for(int i = 0; i < input.length(); i+=2){
        }
        return result;
    }
}
