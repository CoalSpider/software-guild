/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import com.sg.exceptions.AlreadyDeletedException;
import com.sg.exceptions.PersistenceException;
import com.sg.service.Service;
import com.sg.view.View;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public class Controller {

    private final View view;
    private final Service service;

    public Controller(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        do {
            try {
                displayMainMenu();
                switch (getMenuChoice()) {
                    case DISPLAY:
                        displayOrders();
                        break;
                    case ADD:
                        addOrder();
                        break;
                    case EDIT:
                        editOrder();
                        break;
                    case REMOVE:
                        removeOrder();
                        break;
                    case SAVE:
                        save();
                        break;
                    case QUIT:
                        quit(); // calls System.exit
                        break;
                }
            } catch (PersistenceException ex) {
                view.displayErrorMsg(ex.getMessage());
            } catch (AlreadyDeletedException ex) {
                view.displayAlreadyDeletedOrderMsg();
            }
        } while (true);
    }

    private void displayMainMenu() {
        view.displayMainMenu();
    }

    /**
     * always returns a valid command *
     */
    private Command getMenuChoice() {
        return view.askForMenuChoice();
    }

    private LocalDate getDate() throws PersistenceException {
        do {
            LocalDate date = view.askForDate();
            if (service.isValidDate(date)) {
                return date;
            } else {
                view.displayDateAfterTodayMsg();
            }
        } while (true);
    }

    private State getState() throws PersistenceException {
        view.displayStates(service.getStates());
        do {
            String stateName = view.askForState();
            if (service.isValidState(stateName)) {
                return service.getState(stateName);
            } else {
                view.displayInvalidStateMsg();
            }
        } while (true);
    }

    private Product getProduct() throws PersistenceException {
        view.displayProducts(service.getProducts());
        do {
            String productName = view.askForProduct();
            if (service.isValidProduct(productName)) {
                return service.getProduct(productName);
            } else {
                view.displayInvalidProductMsg();
            }
        } while (true);
    }

    /* 
     * ================================================================
     *                  DISPLAY ORDERS COMMAND
     * ================================================================
     */
    private void displayOrders() throws PersistenceException {
        LocalDate date = getDate();
        List<Order> orders = service.getOrders(date);
        if (orders.isEmpty()) {
            view.displayNoOrdersForDate();
        } else {
            view.displayOrders(orders);
        }
    }

    /* 
     * ================================================================
     *                  ADD ORDER COMMAND
     * ================================================================
     */
    private void addOrder() throws PersistenceException {
        Order order = new Order();
        order.setOrderNumber(service.getOrderNumber(LocalDate.now()));
        order.setCustomerName(view.askForName());
        order.setState(getState());
        order.setProduct(getProduct());
        order.setAreaInSquareFeet(view.askForAreaInSquareFeet());

        view.displayOrder(order);

        if (view.askToConfirmOrder()) {
            service.createOrder(order);
        }
    }

    /* 
     * ================================================================
     *                  EDIT ORDER COMMAND
     * ================================================================
     */
    private void editOrder() throws PersistenceException {
        LocalDate date = getDate();
        if (service.getOrders(date).isEmpty()) {
            view.displayNoOrdersForDate();
        } else {
            view.displayOrders(service.getOrders(date));
            int orderNumber = view.askForOrderNumber();
            Order order = service.getOrder(orderNumber, date);
            if (order.isDeleted()) {
                view.displayErrorMsg("Cannot edit canceled orders");
            } else {
                view.editOrder(order, service.getProducts(), service.getStates());
                view.displayOrder(order);
            }
        }
    }

    /* 
     * ================================================================
     *                  REMOVE ORDER COMMAND
     * ================================================================
     */
    private void removeOrder() throws PersistenceException, AlreadyDeletedException {
        LocalDate date = getDate();
        if (service.getOrders(date).isEmpty()) {
            view.displayNoOrdersForDate();
        } else {
            view.displayOrders(service.getOrders(date));
            int orderNumber = view.askForOrderNumber();
            Order order = service.getOrder(orderNumber, date);
            view.displayOrder(order);
            boolean confirmDelete = view.askToConfirmDelete();
            if (confirmDelete) {
                service.deleteOrder(order, date);
            }
        }
    }

    /* 
     * ================================================================
     *                  SAVE COMMAND
     * ================================================================
     */
    private void save() throws PersistenceException {
        service.save();
    }

    /* 
     * ================================================================
     *                  QUIT COMMAND
     * ================================================================
     */
    public void quit() {
        view.displayGoodbyMessage();
        System.exit(0);
    }
}
