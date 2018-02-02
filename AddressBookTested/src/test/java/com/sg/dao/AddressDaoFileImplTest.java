/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.Address;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AddressDaoFileImplTest {

    AddressDaoFileImpl dao;

    public AddressDaoFileImplTest() {
        dao = new AddressDaoFileImpl();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // clear all entries
        List<Address> all = dao.getAllAddresses();
        for (Address a : all) {
            dao.removeAddress(a);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addAddress method, of class AddressDaoFileImpl.
     */
    @Test
    public void testAddAddress() {
        assertTrue(dao.getAllAddresses().isEmpty());
        dao.addAddress(getTestAddress());
        assertEquals(1, dao.getAllAddresses().size());
    }

    /**
     * Test of removeAddress method, of class AddressDaoFileImpl.
     */
    @Test
    public void testRemoveAddress() {
        assertTrue(dao.getAllAddresses().isEmpty());
        Address test = getTestAddress();
        dao.addAddress(test);
        // already tested that add works
        
        dao.removeAddress(test);
        assertTrue(dao.getAllAddresses().isEmpty());
    }

    /**
     * Test of addressCount method, of class AddressDaoFileImpl.
     */
    @Test
    public void testAddressCount() {
        assertTrue(dao.getAllAddresses().isEmpty());
        dao.addAddress(getTestAddress());
        assertEquals(1,dao.addressCount());
        dao.addAddress(new Address("A","B","C"));
        assertEquals(2,dao.addressCount());
    }

    /**
     * Test of getAllAddresses method, of class AddressDaoFileImpl.
     */
    @Test
    public void testGetAllAddresses() {
        assertTrue(dao.getAllAddresses().isEmpty());
        Address test = getTestAddress();
        Address t2 = new Address("A","B","C");
        dao.addAddress(test);
        dao.addAddress(t2);
        assertTrue(dao.getAllAddresses().size()==2);
        assertTrue(dao.getAllAddresses().contains(t2));
        assertTrue(dao.getAllAddresses().contains(test));
    }

    /**
     * Test of getAddressByLastName method, of class AddressDaoFileImpl.
     */
    @Test
    public void testGetAddressByLastName() {
        assertTrue(dao.getAllAddresses().isEmpty());
        Address test = getTestAddress();
        Address t2 = new Address("A","B","C");
        dao.addAddress(test);
        dao.addAddress(t2);
        assertTrue(dao.getAddressByLastName("TestLast").equals(test));
        assertTrue(dao.getAddressByLastName("B").equals(t2));
    }

    private Address getTestAddress() {
        Address address = new Address();
        address.setFirstName("TestFirst");
        address.setLastName("TestLast");
        address.setStreetAddress("StreetAddressTest");
        return address;
    }
}