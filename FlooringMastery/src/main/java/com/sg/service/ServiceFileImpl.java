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

    @Override
    public void createOrder(Order order, LocalDate date) throws PersistenceException, DuplicateOrderException {
        orderDao.createOrder(order, date);
    }

    @Override
    public void createOrder(Order order) throws PersistenceException, DuplicateOrderException {
        orderDao.createOrder(order, LocalDate.now());
    }

    @Override
    public void deleteOrder(Order order, LocalDate date) throws PersistenceException, DuplicateOrderException {
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

    // TODO: move to stateDao.getState() method
    @Override
    public State getState(String stateName) throws PersistenceException {
        if (isValidState(stateName)) {
            for (State s : stateDao.getStates()) {
                if (s.getName().equals(stateName)) {
                    return s;
                }
            }
        }
        throw new PersistenceException("unknown state");
    }

    // TODO: move to productDao.getProduct() method
    @Override
    public Product getProduct(String productType) throws PersistenceException {
        if (isValidProduct(productType)) {
            for (Product p : productDao.getProducts()) {
                if (p.getType().equals(productType)) {
                    return p;
                }
            }
        }
        throw new PersistenceException("unknown product");
    }

    @Override
    public int getOrderNumber(LocalDate date) throws PersistenceException {
        return orderDao.getNextOrderNumber(date);
    }

    @Override
    public List<State> getStates() throws PersistenceException {
        return stateDao.getStates();
    }

    @Override
    public List<Product> getProducts() throws PersistenceException {
        return productDao.getProducts();
    }
}
