/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

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
     */
    void createOrder(Order order, LocalDate date) throws DuplicateOrderException;

    /**
     * Delete an order from the specified date
     *
     * @param order the order to delete
     * @param data the date of the order
     * @throws PersistenceException if the order was not found
     */
    void deleteOrder(Order order, LocalDate date) throws PersistenceException;

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
     */
    List<Order> getOrders(LocalDate date);

    /**
     * @param date the date to check
     * @return an unused (positive) order number for the given date. This would
     * be 0 for the first order of a new day
     */
    int getNextOrderNumber(LocalDate date) throws PersistenceException;
}
