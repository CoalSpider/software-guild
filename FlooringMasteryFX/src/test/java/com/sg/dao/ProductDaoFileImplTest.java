/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.exceptions.PersistenceException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben Norman
 */
public class ProductDaoFileImplTest {

    private final ProductDaoFileImpl impl = new ProductDaoFileImpl();

    /**
     * Test of getProducts method, of class ProductDaoFileImpl.
     */
    @Test
    public void testGetProducts() {
        try {
            assertEquals(4, impl.getProducts().size());
        } catch (PersistenceException e) {
            fail();
        }
    }

}
