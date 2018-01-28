/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson12;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ben Norman this may seem like alot of code but most of it can be reused... so.... yeah....
 */
public class LuckySevensRefactor {
    public static void main(String[] args) {
        DiceGame diceGame = new DiceGame();
        Dealer diceDealer = new Dealer();
        diceGame.start(diceDealer);
    }
}

class DiceGame {
    private final Random random = new Random();
    private final Dice dice = new Dice(new Die[]{new D6(random), new D6(random)});
    
    private int numRounds;
    private int roundWithMaxMoney;
    
    public void start(Dealer dealer){
        dealer.askForBet();
        do{
            playRound(dealer);
        }while(dealer.getAccountBalance() > 0);
        dealer.printGoodbyMessage(this);
    }
    
    /** @return gain/loss of round 
     ex: return -1 if you loose $1 on a loss
     return 4 if you gain $4 on a win**/
    private void playRound(Dealer dealer){
        int roll = dice.roll();
        if(roll==7){
            dealer.addToAccount(4);
            if(dealer.getAccountLimit() > dealer.getAccountBalance()-4){
                roundWithMaxMoney = numRounds; // current round
            }
        } else {
            dealer.removeFromAccount(1);
        }
        numRounds++;
    }
    
    public int getNumRounds(){
        return numRounds;
    }
    
    public int getRoundWithMaxMoney(){
        return this.roundWithMaxMoney;
    }
}

// manages player amount / account details
class Dealer {
    private final static DecimalFormatSymbols DFS = new DecimalFormatSymbols(Locale.getDefault());
    private final static String DECIMAL_SEPARATOR = DFS.getDecimalSeparator() + "";
    private final static String GROUPING_SEPARATOR = DFS.getGroupingSeparator() + "";
    private final static String MINUS_SIGN = DFS.getMinusSign() + "";
    private final static String CURRENCY_SYMBOL = DFS.getCurrencySymbol();

    private Account playerAccount;

    public void askForBet() {
        Scanner scanner = new Scanner(System.in);
        String question = "How many dollars do you have? ";
        System.out.print(question);
        int bet;
        while (true) {
            try {
                System.out.print(question);
                String betString = scanner.nextLine();
                // check for negative values
                if (betString.contains(MINUS_SIGN)) {
                    System.out.println("A positive value please!!");
                    continue;
                }
                // check for decimal values
                if (betString.contains(DECIMAL_SEPARATOR)) {
                    System.out.println("We only deal in whole money here. No change! ");
                    continue;
                }
                // strip grouping separator
                betString = betString.replaceAll(GROUPING_SEPARATOR, "");
                betString = betString.replaceAll(CURRENCY_SYMBOL, "");
                bet = Integer.parseInt(betString);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Thats... not a dollar...");
            }
        }
        openAccount(bet);
    }
    
    public void printGoodbyMessage(DiceGame game){
        System.out.printf("You are broke after %d rounds.\n", game.getNumRounds());
        System.out.printf("You should have quit after %d when you had $%d.", game.getRoundWithMaxMoney(), playerAccount.getAccountLimit());
    }
    
    public int getAccountBalance(){
        return playerAccount.getBalance();
    }
    
    public int getAccountLimit(){
        return playerAccount.getAccountLimit();
    }
    
    public void addToAccount(int amount){
        playerAccount.deposit(amount);
    }
    
    public void removeFromAccount(int amount){
        playerAccount.withdraw(amount);
    }

    private void openAccount(int initialBalance) {
        playerAccount = new Account(initialBalance);
    }
}

class Dice implements Roller {

    private final Die[] dice;

    public Dice(Die[] dice) {
        this.dice = dice;
    }

    @Override
    public int roll() {
        int total = 0;
        for (Die d : dice) {
            total += d.roll();
        }
        return total;
    }
}

interface Roller {

    public int roll();
}

abstract class Die implements Roller {

    private final int numSides;
    private final Random rand;

    public Die(int numSides, Random rand) {
        this.numSides = numSides;
        this.rand = rand;
    }

    public int roll() {
        return rand.nextInt(numSides) + 1;
    }
}

// singular?
class D6 extends Die {

    public D6(Random rand) {
        super(6, rand);
    }
}

class Account {

    // current balance
    private int balance;
    // max money that can go into account
    private int accountLimit;

    public Account(int initialBalance) {
        accountLimit = initialBalance;
        balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public int getAccountLimit() {
        return accountLimit;
    }

    public void deposit(int amount) {
        balance += amount;
        if (balance > accountLimit) {
            upLimit();
        }
    }

    public void withdraw(int amount) {
        if (balance <= 0) {
            throw new RuntimeException("cant widthdraw out of money");
        }
        balance -= amount;
    }

    public void upLimit() {
        accountLimit = balance;
    }

}
