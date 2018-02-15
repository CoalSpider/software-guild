/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.exceptions.PersistenceException;
import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import com.sg.exceptions.DeletedOrderException;
import com.sg.exceptions.DuplicateOrderException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface Service {

    /**
     * Creates a order for the given date
     *
     * @param order the order to create
     * @param date the date to create the order
     * @throws PersistenceException if there was a error talking to the database
     */
    void createOrder(Order order, LocalDate date) throws PersistenceException, DuplicateOrderException;

    /**
     * Creates a order for today
     *
     * @param order the order to create
     * @throws PersistenceException if there was a error talking to the database
     */
    void createOrder(Order order) throws PersistenceException, DuplicateOrderException;

    /**
     *
     * @param order the order to mark as deleted
     * @param date the date of the order
     * @throws PersistenceException if the given order does not exist for the
     * given date
     */
    void deleteOrder(Order order, LocalDate date) throws PersistenceException, DuplicateOrderException;

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

    /**
     * Used for validating date. For example if you wanted valid dates to be
     * before or after today or you didnt allow dates for today. Its up to the
     * implementing class to determine what valid means
     *
     * @param date the date to check
     * @return true if the date is valid.
     */
    boolean isValidDate(LocalDate date);

    /**
     *
     * @param stateName the name of the state
     * @return the state with the given name
     * @throws PersistenceException if there was a error talking to the database
     * or the state name doesnt exist
     */
    State getState(String stateName) throws PersistenceException;

    /**
     *
     * @param productType the name of the product ie wood, carpet, vinyl, etc...
     * @return the product with the given type
     * @throws PersistenceException if there was a error talking to the database
     * or the product type doesnt exist
     */
    Product getProduct(String productType) throws PersistenceException;

    /**
     * @param date the date to check
     * @return the next order number to use
     * @throws PersistenceException if there was a issue talking to the database
     */
    int getOrderNumber(LocalDate date) throws PersistenceException;
    
    /**
     * @return a list of states that are sold
     * @throws PersistenceException if there was an error talking to the database
     */
    List<State> getStates() throws PersistenceException;
    
    /**
     * @return a list of products that are sold
     * @throws PersistenceException if there was an error talking to the database
     */
    List<Product> getProducts() throws PersistenceException;
}
