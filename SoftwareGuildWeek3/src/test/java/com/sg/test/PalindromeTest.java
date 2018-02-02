/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ben Norman
 */
public class PalindromeTest {
    
    public PalindromeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of isPalidrome method, of class Palidrome.
     */
    @Test
    public void testIsPalidrome() {
        
    }

    /**
     * Test of isPalidrome2 method, of class Palidrome.
     */
    @Test
    public void testIsPalidrome2() {
        
    }

    /**
     * Test of isPalidrome3 method, of class Palidrome.
     */
    @Test
    public void testIsPalidrome3() {
        
    }

    /**
     * Test of isPalidrome4 method, of class Palidrome.
     */
    @Test
    public void testIsPalidrome4() {
        Palindrome p = new Palindrome();
        assertTrue(p.isPalidrome("tacocat"));
        assertFalse(p.isPalidrome("Ben"));
        
        assertTrue(p.isPalidrome(""));
        assertFalse(p.isPalidrome(null));
    }
    
}
