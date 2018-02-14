/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.dao.PersistenceException;
import com.sg.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface Service {

    /**
     * Updates the given order with the new data
     *
     * @param order the order to update
     * @param newData the new order data
     * @throws PersistenceException if there was a error talking to the database
     */
    void updateOrder(Order order, Order newData) throws PersistenceException;

    /**
     * Creates a order for the given date
     *
     * @param order the order to create
     * @param date the date to create the order
     * @throws PersistenceException if there was a error talking to the database
     */
    void createOrder(Order order, LocalDate date) throws PersistenceException;

    /**
     *
     * @param order the order to mark as deleted
     * @param date the date of the order
     * @throws PersistenceException if the given order does not exist for the
     * given date
     */
    void deleteOrder(Order order, LocalDate date) throws PersistenceException;

    /**
     * @param orderNumber the number of the order
     * @param date the date of the order
     * @return the order with the given number for the given date
     * @throws PersistenceException if the order does not exist
     */
    Order getOrder(int orderNumber, LocalDate date) throws PersistenceException;

    /**
     *
     * @param date the date to get
     * @return A list of orders for the given date or an empty list if no orders
     * exist for the date
     * @throws PersistenceException if there was a error talking to the database
     */
    List<Order> getOrders(LocalDate date) throws PersistenceException;

    /**
     * Saves all orders ie: any editing, deleting, adding, etc...
     *
     * @throws PersistenceException if there was an error saving the orders
     */
    void save() throws PersistenceException;

    /**
     *
     * @param productName the name of the product
     * @return true if the product is sold by the company
     * @throws PersistenceException if there was an error talking to the
     * database
     */
    boolean isValidProduct(String productName) throws PersistenceException;

    /**
     * @param stateName the name of the state
     * @return true if the the company sells products in the given state
     * @throws PersistenceException if there was an error talking to the
     * database
     */
    boolean isValidState(String stateName) throws PersistenceException;
}
