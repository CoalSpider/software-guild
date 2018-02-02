/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

/**
 *
 * @author Ben Norman
 */
public class Tests {

    public static void main(String[] args) {
        TimeSinceEpoch.printEpoch();
    }
}

class TimeSinceEpoch {

    static void printEpoch() {
        System.out.println(System.nanoTime());
        System.out.println("vs max");
        System.out.println(Long.MAX_VALUE);
        LocalDateTime ldt = LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0);
        System.out.println(ldt.toEpochSecond(ZoneOffset.UTC));

//        for (int i = 1970; i <= 2018; i++) {
//            long epochA = LocalDateTime.of(i, Month.JANUARY, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
//            long epochB = LocalDateTime.of(i - 1, Month.JANUARY, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
//            System.out.println("delta=" + (epochA - epochB));
//        }

        // every 4 we add 31622400
        // instead of 31536000
        long i = 0;
        long four = 31622400l+31536000l*3;
        i = Long.MAX_VALUE/four * 4;
        System.out.println("i="+i);
        System.out.println("292_271_023_044 years or " + (i/13_700_000_000l) + " universes");
    }
}
