/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.dao.OrderDaoFileImpl;
import com.sg.dao.ProductDaoFileImpl;
import com.sg.dao.StateDaoFileImpl;
import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import com.sg.exceptions.DeletedOrderException;
import com.sg.exceptions.DuplicateOrderException;
import com.sg.exceptions.PersistenceException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ServiceFileImplTest {

    private ServiceFileImpl service;
    private final StateDaoFileImpl states = new StateDaoFileImpl();
    private final ProductDaoFileImpl products = new ProductDaoFileImpl();
    private final Order expected = new Order();
    private final LocalDate testDate = LocalDate.of(9999, 01, 01);

    public ServiceFileImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // reset OrderDaoFileImpl between tests
        service = new ServiceFileImpl(new OrderDaoFileImpl(), products, states);

        expected.setOrderNumber(1);
        expected.setCustomerName("TestManA");
        expected.setAreaInSquareFeet(new BigDecimal("100.00"));
        expected.setState(new State("OH", new BigDecimal("6.25")));
        expected.setProduct(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
        // force calculations by calling getTotal()
        expected.getTotal();
    }

    @After
    public void tearDown() {
        File f = new File("orders", "Order_01019999.txt");
        f.delete();
        try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            PrintWriter writer = new PrintWriter(f);
            // put the header back
            writer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
            writer.flush();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void testCreateOrder_Order_LocalDate() {
        try {
            // test creation
            service.createOrder(expected, testDate);
            // test that we added what we think we added
            Order got = service.getOrder(1, testDate);
            assertEquals(expected, got);
        } catch (PersistenceException | DuplicateOrderException ex) {
            fail(ex.getMessage());
        }

        try {
            // confrim duplicate order exception thrown
            service.createOrder(expected, testDate);
            fail("did not catch dupe exception");
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        } catch (DuplicateOrderException ex) {
            // pass
        }
    }

    @Test
    public void testCreateOrder_Order() {
        // DO NOT TEST will mangle todays data becasue we have no separation between test and production data
        // This method calls the version with the localDate anyways so as long as that works were fine
    }

    @Test
    public void testDeleteOrder() {
        try {
            // add1
            service.createOrder(expected, testDate);
            // delete
            service.deleteOrder(expected, testDate);
            // confirm deleted
            assertEquals(BigDecimal.ZERO, service.getOrder(1, testDate).getTotal());
        } catch (PersistenceException | DuplicateOrderException ex) {
            fail(ex.getMessage());
        }

        try {
            // confirm deleting order that doesnt exist for given date throws
            service.deleteOrder(expected, LocalDate.of(9998, 01, 01));
            fail("did not catch exception");
        } catch (PersistenceException ex) {
            // pass
        }
    }

    @Test
    public void testGetOrder() {
        // first order of file Order_01019990.txt == expected
        // 1,TestManA,OH,6.25,Wood,100.00,5.15,4.75,515.00,475.00,61.88,1051.88

        try {
            assertEquals(expected, service.getOrder(1, LocalDate.of(9990, 01, 01)));
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        // make sure we throw an error for invalid number but valid date
        try {
            // zero is the header row
            service.getOrder(0, LocalDate.of(9990, 01, 01));
            fail("did not catch error");
        } catch (PersistenceException ex) {
            // pass
        }

        // make sure we throw an error for a valid number but invalid date
        try {
            service.getOrder(1, LocalDate.of(9993, 01, 01));
            fail("did not catch error");
        } catch (PersistenceException ex) {
            // pass
        }
    }

    @Test
    public void testGetOrders() {
        try {
            // test file Order_01019990.txt should have 4 orders in it
            assertEquals(4, service.getOrders(LocalDate.of(9990, 01, 01)).size());
            // test file Order_01010000.txt should have 0 orders in it
            assertEquals(0, service.getOrders(testDate).size());
            // confirm that a date that doesnt exist blank list
            assertEquals(0, service.getOrders(LocalDate.of(9993, 01, 01)).size());
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testSave() {
        Order order2 = new Order();
        order2.setCustomerName("TestManB");
        order2.setAreaInSquareFeet(new BigDecimal(1 * 10));
        // service layer will prevent invalid product types and invalid states
        order2.setProduct(new Product("candy", new BigDecimal("0.01"), new BigDecimal("9")));
        order2.setState(new State("MN", new BigDecimal("1.2")));

        Order order3 = new Order();
        order3.setCustomerName("TestManB");
        order3.setAreaInSquareFeet(new BigDecimal(4 * 3));
        // service layer will prevent invalid product types and invalid states
        order3.setProduct(new Product("solid gold", new BigDecimal("1231"), new BigDecimal("500")));
        order3.setState(new State("BD", new BigDecimal("15")));

        try {
            // create order 1
            service.createOrder(expected, testDate);
            // create order 2
            service.createOrder(order2, testDate);
            // create order 3
            service.createOrder(order3, testDate);
            // delete the second order
            service.deleteOrder(order2, testDate);

            // confirm writing goes without a hitch
            service.save();
        } catch (PersistenceException | DuplicateOrderException ex) {
            fail(ex.getMessage());
        }

        // now we "exit" the program and read the data in
        service = new ServiceFileImpl(new OrderDaoFileImpl(), products, states);
        // confrim we got what we wanted
        try {
            Order a = service.getOrder(1, testDate);
            assert (expected.equals(a));

            Order b = service.getOrder(2, testDate);
            assert (order2.equals(b));

            Order c = service.getOrder(3, testDate);
            assert (order3.equals(c));
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of isValidProduct method, of class ServiceFileImpl.
     */
    @Test
    public void testIsValidProduct() {
        try {
            assertTrue(service.isValidProduct("Carpet"));
            assertTrue(service.isValidProduct("Laminate"));
            assertTrue(service.isValidProduct("Tile"));
            assertTrue(service.isValidProduct("Wood"));

            assertFalse(service.isValidProduct("Candy")); // doesnt exist
            assertFalse(service.isValidProduct("Woood")); // extra char in valid name
            assertFalse(service.isValidProduct("")); // empty
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of isValidState method, of class ServiceFileImpl.
     */
    @Test
    public void testIsValidState() {
        try {
            assertTrue(service.isValidState("OH"));
            assertTrue(service.isValidState("PA"));
            assertTrue(service.isValidState("MI"));
            assertTrue(service.isValidState("IN"));

            assertFalse(service.isValidState("MN")); // no sold here 
            assertFalse(service.isValidState("Ohio")); // full name invalid 
            assertFalse(service.isValidState("")); // empty 
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of isValidDate method, of class ServiceFileImpl.
     */
    @Test
    public void testIsValidDate() {
        // test now is valid
        assertTrue(service.isValidDate(LocalDate.now()));
        // test before taday is valid
        assertTrue(service.isValidDate(LocalDate.now().minusDays(1)));
        // test after today is invalid
        assertFalse(service.isValidDate(LocalDate.now().plusDays(1)));
    }

    @Test
    public void testGetState() {
        try {
            // should all find the state fails if the product was not found
            service.getState("OH");
            service.getState("PA");
            service.getState("MI");
            service.getState("IN");
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        try {
            service.getState("MN"); // no sold here 
            fail("did not catch error");
        } catch (PersistenceException ex) {
            // pass
        }

        try {
            service.getState("Ohio"); // full name invalid 
            fail("did not catch error");
        } catch (PersistenceException ex) {
            //pass
        }
        try {
            service.getState(""); // empty 
            fail("did not catch error");
        } catch (PersistenceException ex) {
            // pass
        }
    }

    @Test
    public void testGetProduct() {
        try {
            // should find all products fails if it cant
            service.getProduct("Carpet");
            service.getProduct("Laminate");
            service.getProduct("Tile");
            service.getProduct("Wood");
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        try {
            service.getProduct("Candy"); // doesnt exist
            fail("did not catch error");
        } catch (PersistenceException ex) {
            // pass
        }
        try {
            service.getProduct("Woood"); // extra char in valid name
            fail("did not catch error");
        } catch (PersistenceException ex) {
            // pass
        }
        try {
            service.getProduct(""); // empty
            fail("did not catch error");
        } catch (PersistenceException ex) {
            // pass
        }
    }
}
