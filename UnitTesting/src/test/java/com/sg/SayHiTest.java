/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import org.junit.Test;

/**
 *
 * @author Ben Norman
 */
public class SayHiTest {
    

    /**
     * Test of sayHi method, of class SayHi.
     */
    @Test
    public void testSayHi() {
        SayHi instance = new SayHi();
        assert(instance.sayHi("Hi").equals("Hello Hi!"));
        assert(instance.sayHi("").equals("Hello !"));
        assert(instance.sayHi(null).equals("Hello null!"));
    }
    
}
