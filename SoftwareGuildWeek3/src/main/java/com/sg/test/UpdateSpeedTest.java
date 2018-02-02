/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Ben Norman
 */
public class UpdateSpeedTest {

    public static void main(String[] args) {
        List<MyObject> stuff = new ArrayList<>();
        for(int i = 0; i < 10_000; i++){
            stuff.add(new MyObject());
        }
        long time = (long) ((1e9)*5);
        long now = System.nanoTime();
        int count = 0;
        while(System.nanoTime()-now < time) {
            for (MyObject m : stuff) {
                m.update();
            }
            count++;
        }
        for(MyObject m : stuff){
            m.clear();
        }
        System.out.println("felops 1st = " + count);
        System.gc(); // ask to run
        now = System.nanoTime();
        count = 0;
        while(System.nanoTime()-now < time) {
            stuff.forEach((m) ->{
                m.update();
            });
            count++;
        }
        stuff.forEach((m) -> {
            m.clear();
        });
        System.out.println("Lamdas 2nd = " + count);
        System.gc(); // ask to run
        Consumer<MyObject> update = m -> m.update();
        Consumer<MyObject> clear = m -> m.clear();
        now = System.nanoTime();
        count = 0;
        while(System.nanoTime()-now < time) {
            stuff.spliterator().forEachRemaining(update);
            count++;
        }
        stuff.spliterator().forEachRemaining(clear);
        System.out.println("predic 3nd = " + count);
        now = System.nanoTime();
        count = 0;
        while(System.nanoTime()-now < time) {
            for (MyObject m : stuff) {
                m.update();
            }
            count++;
        }
        for(MyObject m : stuff){
            m.clear();
        }
        System.out.println("felops 1st = " + count);
        System.gc(); // ask to run
        now = System.nanoTime();
        count = 0;
        while(System.nanoTime()-now < time) {
            stuff.spliterator().forEachRemaining((m) ->{
                m.update();
            });
            count++;
        }
        stuff.spliterator().forEachRemaining((m) -> {
            m.clear();
        });
        System.out.println("Lamdas 2nd = " + count);
        System.gc(); // ask to run
        now = System.nanoTime();
        count = 0;
        while(System.nanoTime()-now < time) {
            stuff.spliterator().forEachRemaining(update);
            count++;
        }
        stuff.spliterator().forEachRemaining(clear);
        System.out.println("predic 3nd = " + count);
        
        stuff.stream().map((s) -> s.i!=0);
    }
}

class MyObject {

    int i = 0;

    void update() {
        i++;
    }
    
    void clear(){
        i = 0;
    }
}
