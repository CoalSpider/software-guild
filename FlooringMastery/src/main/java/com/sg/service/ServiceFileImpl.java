/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.service;

import com.sg.dao.OrderDao;
import com.sg.exceptions.PersistenceException;
import com.sg.dao.ProductDao;
import com.sg.dao.StateDao;
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
public class ServiceFileImpl implements Service {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final StateDao stateDao;

    public ServiceFileImpl(OrderDao orderDao, ProductDao productDao, StateDao stateDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.stateDao = stateDao;
    }

    // TODO: make sure user entered data always has 2 decimal places
    @Override
    public void updateOrder(Order order, Order newData) throws PersistenceException, DeletedOrderException {
        if(order.isDeleted()){
            throw new DeletedOrderException("cannt update a deleted order");
        }
        order.setCustomerName(newData.getCustomerName());
        order.setProduct(newData.getProduct());
        order.setState(newData.getState());
        order.setAreaInSquareFeet(newData.getAreaInSquareFeet());
    }

    @Override
    public void createOrder(Order order, LocalDate date) throws PersistenceException, DuplicateOrderException {
        orderDao.createOrder(order, date);
    }

    @Override
    public void createOrder(Order order) throws PersistenceException, DuplicateOrderException {
        orderDao.createOrder(order, LocalDate.now());
    }

    @Override
    public void deleteOrder(Order order, LocalDate date) throws PersistenceException {
        orderDao.deleteOrder(order, date);
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws PersistenceException {
        return orderDao.getOrder(orderNumber, date);
    }

    @Override
    public List<Order> getOrders(LocalDate date) throws PersistenceException {
        return orderDao.getOrders(date);
    }

    //TODO: do not call in training mode
    @Override
    public void save() throws PersistenceException {
        orderDao.saveData();
    }

    @Override
    public boolean isValidProduct(String productName) throws PersistenceException {
        return productDao
                .getProducts()
                .stream()
                .map(Product::getType)
                .anyMatch((name) -> name.equals(productName));
    }

    @Override
    public boolean isValidState(String stateName) throws PersistenceException {
        return stateDao
                .getStates()
                .stream()
                .map(State::getName)
                .anyMatch((name) -> name.equals(stateName));
    }

    /**
     * @param date the date to check
     * @return true if the given date IS NOT AFTER LocalDate.now()
     */
    @Override
    public boolean isValidDate(LocalDate date) {
        return date.isAfter(LocalDate.now()) == false;
    }
}
