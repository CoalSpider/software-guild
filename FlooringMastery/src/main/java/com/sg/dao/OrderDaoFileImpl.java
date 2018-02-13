/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author Ben Norman
 */
public class OrderDaoFileImpl implements OrderDao {
    // all the orders that exist
    private final Map<LocalDate, List<Order>> allOrders = new TreeMap<>();
    // keeps track of the number of orders for a given date
    private final Map<LocalDate, Integer> maxOrderNumbers = new TreeMap<>();
    
    private void writeData() throws PersistenceException{
        
    }
    
    private void readData() throws PersistenceException{
        
    }

    @Override
    public void createOrder(Order order, LocalDate date) throws DuplicateOrderException {
        // if the order already exists throw
        if (allOrders.containsKey(date)) {
            if (allOrders.get(date).contains(order)) {
                throw new DuplicateOrderException("order already exists");
            }
        }
        // set the order number
        int orderNumber = getNextOrderNumber(date);
        order.setOrderNumber(orderNumber);

        // add out order
        allOrders.putIfAbsent(date, new ArrayList<>());
        allOrders.get(date).add(order);
    }

    @Override
    public void deleteOrder(Order order, LocalDate date) throws PersistenceException {
        if (allOrders.containsKey(date)) {
            if (allOrders.get(date).contains(order)) {
                allOrders.get(date).remove(order);
            } else {
                throw new PersistenceException("order does not exist for given date");
            }
        }
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws PersistenceException {
        if (allOrders.containsKey(date)) {
            List<Order> orders = allOrders
                    .get(date)
                    .stream()
                    .filter((order) -> order.getOrderNumber() == orderNumber)
                    .collect(Collectors.toList());
            if (orders.isEmpty() == false) {
                return orders.get(0);
            }
        }
        throw new PersistenceException("order does not exist for given date");
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        return allOrders.getOrDefault(date, new ArrayList<>());
    }

    @Override
    public int getNextOrderNumber(LocalDate date) {
        int orderNumber = maxOrderNumbers.getOrDefault(date, 0);
        maxOrderNumbers.put(date, orderNumber + 1);
        return orderNumber;
    }

}
