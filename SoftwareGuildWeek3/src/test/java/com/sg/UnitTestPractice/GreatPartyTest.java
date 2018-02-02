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
public class GreatPartyTest {
    GreatParty gp = new GreatParty();
    /**
     * Test of greatParty method, of class GreatParty.
     */
    @Test
    public void weekendTest(){
        assertFalse(gp.greatParty(39, true));
        assertTrue(gp.greatParty(40, true));
        assertTrue(gp.greatParty(60, true));
        assertTrue(gp.greatParty(61, true));
    }
    
    @Test
    public void weekdayTest(){
        assertFalse(gp.greatParty(39, false));
        assertTrue(gp.greatParty(40, false));
        assertTrue(gp.greatParty(60, false));
        assertFalse(gp.greatParty(61, false));
    }
    
}