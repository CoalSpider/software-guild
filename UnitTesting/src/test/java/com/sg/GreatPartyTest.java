/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

/**
 *
 * @author Ben Norman
 */
public class GreatPartyTest {

    /**
     * Test of greatParty method, of class GreatParty.
     */
    @org.junit.Test
    public void testGreatParty() {
        GreatParty instance = new GreatParty();
        assert(instance.greatParty(39, false)==false);
        assert(instance.greatParty(40, false)==true);
        assert(instance.greatParty(60, false)==true);
        assert(instance.greatParty(61, false)==false);
        
        assert(instance.greatParty(39, true)==false);
        assert(instance.greatParty(40, true)==true);
        assert(instance.greatParty(60, true)==true);
        assert(instance.greatParty(61, true)==true);
    }
    
}
