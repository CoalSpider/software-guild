/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lessson56;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class LuckySevens {

    private static final Random RANDOM = new Random();
    
    private final static DecimalFormatSymbols DFS = new DecimalFormatSymbols(Locale.getDefault());
    private final static String DECIMAL_SEPARATOR = DFS.getDecimalSeparator()+"";
    private final static String GROUPING_SEPARATOR = DFS.getGroupingSeparator()+"";
    private final static String MINUS_SIGN = DFS.getMinusSign()+"";
    private final static String CURRENCY_SYMBOL = DFS.getCurrencySymbol();

    /**
     * @param numDice the number of dice to roll
     * @param the number of sides on the dice
     * @return the total of the dice rolls *
     */
    private static int rollDice(int numDice, int numSidesOfDice) {
        int total = 0;
        while (numDice-- > 0) {
            total += RANDOM.nextInt(numSidesOfDice) + 1;
        }
        return total;
    }
    private static int askForBet(){
        Scanner scanner = new Scanner(System.in);
        String question = "How many dollars do you have? ";
        System.out.print(question);
        int bet;
        while(true){
            try{
                System.out.print(question);
                String betString = scanner.nextLine();
                // check for negative values
                if(betString.contains(MINUS_SIGN)){
                    System.out.println("A positive value please!!");
                    continue;
                }
                // check for decimal values
                if(betString.contains(DECIMAL_SEPARATOR)){
                    System.out.println("We only deal in whole money here. No change! ");
                    continue;
                }
                // strip grouping separator
                betString = betString.replaceAll(GROUPING_SEPARATOR, "");
                betString = betString.replaceAll(CURRENCY_SYMBOL, "");
                bet = Integer.parseInt(betString);
                break;
            }catch(NumberFormatException e){
                System.out.println("Thats... not a dollar...");
            }
        }
        return bet;
    }

    public static void main(String[] args) {
        // get the users "bet"
        int remainingMoney = askForBet();
        int maxMoney = remainingMoney;

        int numRounds = 0;
        int roundWithMaxMoney = 0;
        // now roll until there out of money
        while (remainingMoney > 0) {

            // roll two six sided dice
            int pipCount = rollDice(2, 6);

            if (pipCount == 7) {
                remainingMoney += 4;
            } else {
                remainingMoney -= 1;
            }

            if (remainingMoney > maxMoney) {
                roundWithMaxMoney = numRounds;
                maxMoney = remainingMoney;
            }

            numRounds++;
        }

        System.out.printf("You are broke after %d rounds.\n", numRounds);
        System.out.printf("You should have quit after %d when you had $%d.",roundWithMaxMoney,maxMoney);
    }
}
