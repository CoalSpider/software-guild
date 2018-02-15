/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.exceptions.DuplicateOrderException;
import com.sg.exceptions.PersistenceException;
import com.sg.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface OrderDao {

    /**
     * Create an order with the specified date
     *
     * @param order the order to create
     * @param date the date of the order
     * @throws DuplicateOrderException if the order already exists
     * @throws PersistenceException if some error occured with reading the data
     */
    void createOrder(Order order, LocalDate date) throws PersistenceException, DuplicateOrderException;

    /**
     * Mark a order as deleted by settings its total field to BigDecimal.ZERO.
     * Once marked as deleted an order should not be editable
     *
     * @param order the order to mark as deleted
     * @param data the date of the order
     * @throws PersistenceException if the order was not found
     */
    void deleteOrder(Order order, LocalDate date) throws PersistenceException, DuplicateOrderException;

    /**
     * Gets an order with the specified order number from the given date
     *
     * @param orderNumber the number of the order
     * @param date the date of the order
     * @return the order with the specified order number;
     * @throws PersistenceException if the order was not found
     */
    Order getOrder(int orderNumber, LocalDate date) throws PersistenceException;

    /**
     * @param date the date of the given orders
     * @return all orders from the given date. If there are no orders for the
     * given date an empty list is returned
     * @throws PersistenceException if some error occured with reading the data
     */
    List<Order> getOrders(LocalDate date) throws PersistenceException;

    /**
     * @param date the date to check
     * @return an unused (positive) order number for the given date. This would
     * be 0 for the first order of a new day
     * @throws PersistenceException if some error occured with reading the data
     */
    int getNextOrderNumber(LocalDate date) throws PersistenceException;

    /**
     * Saves order data
     *
     * @throws PersistenceException if something went wrong with saving the data
     */
    void saveData() throws PersistenceException;
}
