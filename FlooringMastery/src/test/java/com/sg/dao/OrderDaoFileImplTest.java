/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.exceptions.AlreadyDeletedException;
import com.sg.exceptions.PersistenceException;
import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben Norman
 */
public class OrderDaoFileImplTest {

    private final LocalDate orderDate = LocalDate.of(9999, Month.JANUARY, 1);
    private final Order order = new Order();
    private final Product product = new Product("wood", new BigDecimal("1.00"), new BigDecimal("2.00"));
    private final State state = new State("MN", new BigDecimal("7.12"));
    private final int orderNumber = 1;

    @Before
    public void setUp() {
        order.setOrderNumber(orderNumber);
        order.setCustomerName("TestMan");
        order.setAreaInSquareFeet(new BigDecimal("100.00"));
        // service layer will prevent invalid product types and invalid states
        order.setProduct(product);
        order.setState(state);
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

    /**
     * Test of createOrder method, of class OrderDaoFileImpl.
     */
    @Test
    public void testCreateOrder() {
        OrderDaoFileImpl fileImpl = new OrderDaoFileImpl();

        try {
            // confrim file is empty
            assertEquals(1, fileImpl.getNextOrderNumber(orderDate));
            // confim creation didnt break
            fileImpl.createOrder(order, orderDate);
            // confirm that something was added
            assertEquals(1, fileImpl.getOrders(orderDate).size());
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        try {
            // confim we added what we think we added
            Order ord = fileImpl.getOrder(1, orderDate);
            assertEquals(order, ord);
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        // confirm that no exception is thrown for adding the order order number to a different date
        try {
            fileImpl.createOrder(order, LocalDate.of(9998, 1, 1));
        } catch (PersistenceException e) {
            fail(e.getMessage());
        }
        try {
            //confirm something was added to 9998 and not 9999
            assertEquals(1, fileImpl.getOrders(LocalDate.of(9998, 1, 1)).size());
            assertEquals(1, fileImpl.getOrders(orderDate).size());
            // confirm that they are the same object but different days
            assertEquals(fileImpl.getOrder(orderNumber, LocalDate.of(9998, 1, 1)), fileImpl.getOrder(orderNumber, orderDate));
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        // comfirm deleted order stays deleted
        try {
            Order deletedOrder = fileImpl.getOrder(2, orderDate.minusYears(9));
            assertTrue(deletedOrder.isDeleted());
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of deleteOrder method, of class OrderDaoFileImpl.
     */
    @Test
    public void testDeleteOrder() {
        OrderDaoFileImpl fileImpl = new OrderDaoFileImpl();

        try {
            // confim creation didnt break
            fileImpl.createOrder(order, orderDate);
            // cofirm deletion didnt break;
            fileImpl.deleteOrder(order, orderDate);
            // confirm we marked deletion but did not remove from list
            assertEquals(1, fileImpl.getOrders(orderDate).size());
            assertTrue(fileImpl.getOrder(1, orderDate).isDeleted());
        } catch (PersistenceException | AlreadyDeletedException ex) {
            fail(ex.getMessage());
        }

        // confirm trying to delete  the same order twice fails
        try {
            fileImpl.deleteOrder(order, orderDate.minusYears(1));
            fail("did not catch error");
        } catch (PersistenceException e) {
            // yay 
        } catch (AlreadyDeletedException ex) {
            fail(ex.getMessage());
        }

        try {
            fileImpl.deleteOrder(order, orderDate);
            fail("did no catch duplicate");
        } catch (AlreadyDeletedException ex) {
            // yay
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getOrder method, of class OrderDaoFileImpl.
     */
    @Test
    public void testGetOrder() {
        OrderDaoFileImpl fileImpl = new OrderDaoFileImpl();

        try {
            // confim creation didnt break
            fileImpl.createOrder(order, orderDate);
            // cofirm getting didnt break;
            Order gottenOrder = fileImpl.getOrder(orderNumber, orderDate);
            // confirm we got what we think we got
            assertEquals(gottenOrder, order);
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        // confirm trying to get a order number that doesnt exist throws
        try {
            fileImpl.getOrder(2, orderDate);
            fail("did not catch error");
        } catch (PersistenceException e) {
            // yay 
        }

        // confirm that trying to get a order from a date that doesnt have any orders throws
        try {
            fileImpl.getOrder(orderNumber, LocalDate.of(orderDate.getYear(), orderDate.getMonth(), orderDate.getDayOfMonth() + 1));
            fail("did not catch error");
        } catch (PersistenceException e) {
            // yay 
        }
    }

    /**
     * Test of getOrders method, of class OrderDaoFileImpl.
     */
    @Test
    public void testGetOrders() {
        OrderDaoFileImpl fileImpl = new OrderDaoFileImpl();

        try {
            // confim creation didnt break
            fileImpl.createOrder(order, orderDate);
            // cofirm getting all from date didnt break;
            List<Order> orders = fileImpl.getOrders(orderDate);
            // confirm we got what we think we got (same hash code to)
            assertEquals(1, orders.size());

            assertEquals(4, fileImpl.getOrders(orderDate.minusYears(9)).size());
            assertEquals(4, fileImpl.getOrders(orderDate.minusYears(9)).size());
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        // confirm that a date with no orders returns a empty list
        try {
            List<Order> orders = fileImpl.getOrders(LocalDate.of(orderDate.getYear(), orderDate.getMonth(), orderDate.getDayOfMonth() + 1));
            assertEquals(0, orders.size());
        } catch (PersistenceException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test of getNextOrderNumber method, of class OrderDaoFileImpl.
     */
    @Test
    public void testGetNextOrderNumber() {
        OrderDaoFileImpl fileImpl = new OrderDaoFileImpl();
        // confirm next order number equals 1
        try {
            assertEquals(1, fileImpl.getNextOrderNumber(orderDate));
            // confirm its still zero because theres nothing there
            assertEquals(1, fileImpl.getNextOrderNumber(orderDate));
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        // confrim next order equals 2
        try {
            fileImpl.createOrder(order, orderDate);
            assertEquals(2, fileImpl.getNextOrderNumber(orderDate));
            // confirm its still 2
            assertEquals(2, fileImpl.getNextOrderNumber(orderDate));
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }

        // confirm that next order equals 5
        try {
            assertEquals(5, fileImpl.getNextOrderNumber(orderDate.minusYears(9)));
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of saveData method, of class OrderDaoFileImpl.
     *
     */
    @Test
    public void testSaveData() {
        OrderDaoFileImpl fileImpl = new OrderDaoFileImpl();

        Order order2 = new Order();
        order2.setCustomerName("TestManB");
        order2.setAreaInSquareFeet(new BigDecimal(1 * 10));
        // service layer will prevent invalid product types and invalid states
        order2.setProduct(new Product("candy", new BigDecimal("0.01"), new BigDecimal("9")));
        order2.setState(new State("MN", new BigDecimal("1.2")));
        // force calculation
        order2.getTotal();
        System.out.println("order2 = " + order2.getTotal().toPlainString());

        Order order3 = new Order();
        order3.setCustomerName("TestManB");
        order3.setAreaInSquareFeet(new BigDecimal(4 * 3));
        // service layer will prevent invalid product types and invalid states
        order3.setProduct(new Product("solid gold", new BigDecimal("1231"), new BigDecimal("500")));
        order3.setState(new State("BD", new BigDecimal("15")));
        // force calculation
        order3.getTotal();

        try {
            // create order 1
            fileImpl.createOrder(order, orderDate);
            // create order 2
            fileImpl.createOrder(order2, orderDate);
            // create order 3
            fileImpl.createOrder(order3, orderDate);
            // delete the second order
            fileImpl.deleteOrder(order2, orderDate);

            // confirm writing goes without a hitch
            fileImpl.saveData();
        } catch (PersistenceException | AlreadyDeletedException ex) {
            fail(ex.getMessage());
        }

        // now we "exit" the program and read the data in
        fileImpl = new OrderDaoFileImpl();
        // confrim we got what we wanted
        try {
            Order a = fileImpl.getOrder(1, orderDate);
            assert (order.equals(a));

            Order b = fileImpl.getOrder(2, orderDate);
            System.out.println("2="+order2);
            System.out.println("b="+b);
            assertEquals(order2,b);

            Order c = fileImpl.getOrder(3, orderDate);
            assert (order3.equals(c));
        } catch (PersistenceException ex) {
            fail(ex.getMessage());
        }
    }
}
