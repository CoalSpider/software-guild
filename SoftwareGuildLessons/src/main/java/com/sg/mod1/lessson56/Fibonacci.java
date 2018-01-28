/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lessson56;

import java.util.Random;

/**
 *
 * @author Ben Norman
 */
public class Fibonacci {

    public static void main(String[] args) {
        //    getFibonacciSeq(0,1,10);
        //    getFibonacciSeqIter(0,1,10);
        Random random = new Random(1993);
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandomInRangeInclusive(random, -110, -100));
        }
        //   System.out.println(getRandomInRangeInclusive(random, -100, 110));
        //  System.out.println(getRandomInRangeInclusive(random, -110, 100));

    }

    // 0 1 1 2 3 5 8 12 20
    private static void getFibonacciSeq(int i, int j, int counter) {
        System.out.println(i);
        if (counter >= 0) {
            getFibonacciSeq(j, i + j, --counter);
        }
    }

    private static void getFibonacciSeqIter(int i, int j, int counter) {
        for (; counter >= 0; counter--) {
            System.out.println(j);
            int temp = j;
            j = i + j;
            i = temp;
        }
    }

    private static int getRandomInRangeInclusive(Random random, int min, int max) {
        if (max <= min) {
            throw new IllegalArgumentException("max must be > min");
        }

        return random.nextInt(max + 1 - min) + min;
    }
}
