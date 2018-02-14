/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import com.sg.exceptions.DuplicateOrderException;
import com.sg.exceptions.PersistenceException;
import com.sg.service.Service;
import com.sg.view.View;
import java.time.LocalDate;

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
                displayErrorMsg(ex.getMessage());
            } catch (DuplicateOrderException ex) {
                displayDuplicateOrderMsg();
            }
        } while (true);
    }

    private void displayMainMenu() {
        view.displayMainMenu();
    }

    private void displayErrorMsg(String errorMsg) {
        view.displayErrorMsg(errorMsg);
    }

    private void displayDuplicateOrderMsg() {
        view.displayDuplicateOrderMsg();
    }

    /**
     * should return a valid command ie: not an unknown command
     */
    private Command getMenuChoice() {
        return view.getMenuChoice();
    }

    private void displayOrders() throws PersistenceException {
        view.displayOrders(service.getOrders(getDate()));
    }

    private LocalDate getDate() {
        do {
            LocalDate date = view.askForDate();
            if (service.isValidDate(date)) {
                return date;
            } else {
                view.displayInvalidDateMsg();
            }
        } while (true);
    }

    private void addOrder() throws PersistenceException, DuplicateOrderException {
        Order order = new Order();
        order.setCustomerName(view.askForName());
        order.setState(getState());
        order.setProduct(getProduct());
        order.setAreaInSquareFeet(view.askForAreaInSquareFeet());

        view.displayOrder(order);

        if (view.askToConfirmOrder()) {
            service.createOrder(order);
        }
    }

    // view get state if empty return origonal state
    private State getState() throws PersistenceException {
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
        do {
            String productName = view.askForProduct();
            if (service.isValidProduct(productName)) {
                return service.getProduct(productName);
            } else {
                view.displayInvalidProductMsg();
            }
        } while (true);
    }

    private void editOrder() throws PersistenceException{
        LocalDate date = view.askForDate();
        int orderNumber = view.askForOrderNumber();
        Order order = service.getOrder(orderNumber, date);
        view.editOrder(order);
    }

    private void removeOrder() throws PersistenceException {
        LocalDate date = view.askForDate();
        int orderNumber = view.askForOrderNumber();
        Order order = service.getOrder(orderNumber, date);
        boolean confirmDelete = view.askToConfirmDelete();
        if (confirmDelete) {
            service.deleteOrder(order, date);
        }
    }

    // TODO: do not call in training mode
    private void save() throws PersistenceException {
        service.save();
    }

    private void quit() {
        view.displayGoodbyMessage();
        System.exit(0);
    }
}
