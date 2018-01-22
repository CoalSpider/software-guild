/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguild.assignment1;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class RockPaperScissors {
    // user choice
    private static final int ROCK = 1;
    private static final int PAPER = 2;
    private static final int SCISSORS = 3;
    // round results
    private static final int WIN = 1;
    private static final int LOSS = -1;
    private static final int TIE = 0;
    // max and min rounds the user can play
    private static final int MIN_ROUNDS = 1;
    private static final int MAX_ROUNDS = 10;

    private static Scanner scanner = new Scanner(System.in);

    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Lets play Rock Paper Scissors!");
        while (true) {
            System.out.println("How many rounds do you want to play? (min " + MIN_ROUNDS + ", max " + MAX_ROUNDS + ")");
            // assuming valid int input
            int numRounds = Integer.parseInt(scanner.nextLine());
            if (numRounds >= MIN_ROUNDS && numRounds <= MAX_ROUNDS) {
                runGame(numRounds);
            } else {
                System.err.println("invalid number of rounds, round input must be between " + MIN_ROUNDS + " and " + MAX_ROUNDS);
                break;
            }
            System.out.println("Do you want to play agian? (y / n)");
            // asuumming valid input
            String input = scanner.nextLine();
            if ("y".equals(input)) {
                // continue;
            } else {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private static void runGame(int numRounds) {
        int wins = 0;
        int losses = 0;
        int ties = 0;
        for (int i = 0; i < numRounds; i++) {
            switch (playRound()) {
                case WIN:
                    System.out.println("Player Wins!");
                    wins++;
                    break;
                case LOSS:
                    System.out.println("Computer Wins!");
                    losses++;
                    break;
                case TIE:
                    System.out.println("Its a tie!");
                    ties++;
                    break;
            }
        }
        System.out.println("Thats the last round lets tally the results...");
        System.out.print("The overall winner is... ");
        if (wins > losses) {
            System.out.println("Player!!!");
        } else if (losses > wins) {
            System.out.println("Computer");
        } else {
            System.out.println("Unbelievable!!! Its a tie!!!");
        }
        System.out.println("Results: ");
        System.out.printf("Wins: %d \t Losses: %d \t Ties: %d\n", wins, losses, ties);
    }

    /**
     * @return 0 if tie, 1 if userWins, -1 if computerWins*
     */
    private static int playRound() {
        System.out.println("Choose your weapon!");
        System.out.println("1) ROCK \t 2) PAPER \t 3) SCISSORS");
        // assuming valid integer input
        int userChoice = Integer.parseInt(scanner.nextLine());
        int computerChoice = random.nextInt(3) + 1;
        System.out.println(getChoice(userChoice) + " vs " + getChoice(computerChoice));

        // computer choice cannot be invalid, assuming valid user input
        switch (userChoice) {
            case ROCK:
                switch (computerChoice) {
                    case PAPER:
                        return WIN;
                    case SCISSORS:
                        return LOSS;
                    default:
                        return TIE;
                }
            case PAPER:
                switch (computerChoice) {
                    case ROCK:
                        return WIN;
                    case SCISSORS:
                        return LOSS;
                    default:
                        return TIE;
                }
            case SCISSORS:
                switch (computerChoice) {
                    case ROCK:
                        return LOSS;
                    case PAPER:
                        return WIN;
                    default:
                        return TIE;
                }
            default:
                throw new RuntimeException("unknown choice"); // TODO: erro handling to prevent this from happening

        }
    }

    /**
     * mapping between user choices and associated strings*
     */
    private static String getChoice(int i) {
        switch (i) {
            case 1:
                return "Rock";
            case 2:
                return "Paper";
            case 3:
                return "Scissors";
            default:
                return null;
        }
    }
}