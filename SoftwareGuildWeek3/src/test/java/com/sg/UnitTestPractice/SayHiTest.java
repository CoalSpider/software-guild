/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.UnitTestPractice;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben Norman
 */
public class SayHiTest {
    
    SayHi sh = new SayHi();

    /**
     * Test of sayHi method, of class SayHi.
     */
    @Test
    public void testSayHi() {
        assertEquals("Hello name!",sh.sayHi("name"));
        assertEquals("Hello null!",sh.sayHi(null));
        assertEquals("Hello !",sh.sayHi(""));
    }
    
}
