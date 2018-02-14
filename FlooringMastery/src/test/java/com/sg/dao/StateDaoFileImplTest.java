/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Ben Norman
 */
public class StateDaoFileImplTest {
    
    private final StateDaoFileImpl fileImpl = new StateDaoFileImpl();

    /**
     * Test of getStates method, of class StateDaoFileImpl.
     */
    @Test
    public void testGetStates(){
        try{
            assertEquals(4,fileImpl.getStates());
        }catch(PersistenceException e){
            fail(e.getMessage());
        }
    }
}
