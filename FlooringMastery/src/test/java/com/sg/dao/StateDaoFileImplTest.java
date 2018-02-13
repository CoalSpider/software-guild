/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.State;
import java.util.List;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Ben Norman
 */
public class StateDaoFileImplTest {
    
    private StateDaoFileImpl fileImpl = new StateDaoFileImpl();

    /**
     * Test of getStates method, of class StateDaoFileImpl.
     */
    @Test
    public void testGetStates(){
        try{
            List<State> states = fileImpl.getStates();
            assert(states.size()==4);
        }catch(PersistenceException e){
            fail(e.getMessage());
        }
    }
}
