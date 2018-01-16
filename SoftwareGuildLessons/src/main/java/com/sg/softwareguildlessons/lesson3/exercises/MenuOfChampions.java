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
public class MenuOfChampions {
    public static void main(String[] args){
          String art = "_,.-'~'-.,__,.-'~'-.,__,.-'~'-.,__,.-'~'-.,__,.-'~'-.,_";
          String restrauntName = "GET OUT";
          String item1 = "Slice of Pizza";
          String item2 = "1 Hardboiled Egg";
          String item3 = "Star matter (heavy)";
          float itemPrice1 = 3;
          float itemPrice2 = 1;
          float itemPrice3 = 1000;
          
          System.out.println(art);
          System.out.println("\t WELCOME TO RESTAURANT " + restrauntName);
          System.out.println("\t\t Today's Menu Is...");
          System.out.println(art+"\n");
          System.out.printf("\t%s \t\t$ %.2f\n", item1,itemPrice1);
          System.out.printf("\t%s \t$ %.2f\n", item2,itemPrice2);
          System.out.printf("\t%s \t$ %.2f\n", item3,itemPrice3);
    }
}
