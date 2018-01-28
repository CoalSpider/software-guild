/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class Test {

    // cant edit console lines
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }
        }
    }
}

// Singleton pattern example
class TheOneRing {

    private static TheOneRing instance;

    private TheOneRing() {
        // One Ring to Rule Them All
    }

    public static TheOneRing getInstance() {
        if (instance == null) {
            instance = new TheOneRing();
        }
        return instance;
    }
}

class MapStuff {

    public static void main(String[] args) {
        Map<Integer, Object> map = new HashMap<>();
        // prefill
        for (int i = 0; i < 1000; i++) {
            Object previousMapping = map.put(i, new Object());
            if (previousMapping != null) {
                map.put(i, previousMapping);
            }
        }
    }
}

class LabeledBreak {

    public static void main(String[] args) {
        outerLoop:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                break outerLoop;
            }
        }
    }
}
