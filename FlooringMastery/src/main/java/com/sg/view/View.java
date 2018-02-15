/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.view;

import com.sg.controller.Command;
import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Ben Norman
 */
public class View {

    private final UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public void displayMainMenu() {
        io.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
        io.println("* <<Flooring Program>>");
        io.println("* 1. Display Orders");
        io.println("* 2. Add an Order");
        io.println("* 3. Edit an Order");
        io.println("* 4. Remmove an Order");
        io.println("* 5. Save Current Work");
        io.println("* 6. Quit");
        io.println("*");
        io.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
    }

    public void displayOrder(Order order) {
        io.println("#" + order.getOrderNumber() + "\tName: " + order.getCustomerName() + "\tArea " + order.getAreaInSquareFeet().toPlainString() + " ft^2");

        io.println("State: " + order.getState().getName() + "\tTax:" + order.getState().getTaxRate());

        io.print("Type \tMaterial Cost \tLabor Cost");
        io.println(order.getProduct().getType() + "\t" + order.getProduct().getCostPerSquareFoot() + "\t" + order.getProduct().getLaborCostPerSquareFoot());

        io.print("Material Cost \tLabor Cost \tTax \tTotal");
        io.println(order.getMaterialCost() + "\t" + order.getLaborCost() + "\t" + order.getTax() + "\t" + order.getTotal());
    }

    public void displayOrders(List<Order> orders) {
        for (Order order : orders) {
            displayOrder(order);
        }
    }

    public void displayPersistenceException(String message) {
        io.println(message);
    }

    public void displayDuplicateOrderMsg() {
        io.println("Order already exists");
    }

    public void displayInvalidProductMsg() {
        io.println("product is not sold or does not exist");
    }

    public void displayInvalidStateMsg() {
        io.println("state does not exist or products are not sold there");
    }

    public void displayInvalidDateMsg() {
        io.println("Invalid date. Date must be before or on today");
    }

    public void displayGoodbyMessage() {
        io.println("Goodbye");
    }

    public void displayNoOrdersForDate() {
        io.println("No orders for given date");
    }

    public void displayErrorMsg(String errorMsg) {
        io.println(errorMsg);
    }

    public Command askForMenuChoice() {
        do {
            Command c = Command.commandFromInt(io.readInt("Enter Choice: ", 1, 6));
            if (c.equals(Command.UNKNOWN)) {
                io.println("Unknown Command");
            } else {
                return c;
            }
        } while (true);
    }

    public LocalDate askForDate() {
        do {
            try {
                return LocalDate.parse(io.readString("Enter Date MM-dd-yyyy: "), DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            } catch (DateTimeParseException e) {
                io.println("Invalid date");
            }
        } while (true);
    }

    public String askForName() {
        return io.readString("Enter Name: ");
    }

    public String askForState() {
        return io.readString("Enter State: ");
    }

    public String askForProduct() {
        return io.readString("Enter Product: ");
    }

    public BigDecimal askForAreaInSquareFeet() {
        do {
            BigDecimal bd = io.readBigDecimal("Enter an area in ft^2: ");
            if (bd == null) {
                io.println("Invalid area");
            } else if (BigDecimal.ZERO.compareTo(bd) >= 0) {
                io.println("Area must be > 0");
            } else {
                return bd;
            }
        } while (true);
    }

    public boolean askToConfirmOrder() {
        return "y".equalsIgnoreCase(io.readString("Create Order? (y/n): "));
    }

    public int askForOrderNumber() {
        do {
            int input = io.readInt("Enter Order Number: ", 1, Integer.MAX_VALUE);
            if (input != -1) {
                return input;
            } else {
                io.println("Invalid order number");
            }
        } while (true);
    }

    public boolean askToConfirmDelete() {
        return "y".equalsIgnoreCase(io.readString("Really? Delete Order? (y/n) :"));
    }

    public void editOrder(Order order, List<Product> validProducts, List<State> validStates) {
        editName(order);
        editState(order, validStates);
        editProduct(order, validProducts);
        editArea(order);
    }

    private void editName(Order order) {
        String customerName = io.readString("Enter new name (" + order.getCustomerName() + "): ");
        if (customerName.isEmpty() == false) {
            order.setCustomerName(customerName);
        }
    }

    private void editState(Order order, List<State> validStates) {
        do {
            String stateName = io.readString("Enter state (" + order.getState().getName() + "): ");
            // if user skipped then break
            if (stateName.isEmpty()) {
                break;
            }
            // check for matching states
            List<State> matchingState
                    = validStates
                            .stream()
                            .filter((state) -> state.getName().equals(stateName))
                            .collect(Collectors.toList());
            // if there was no match display error
            if (matchingState.isEmpty()) {
                displayInvalidStateMsg();
            } else {
                // otherwise we edit and break
                order.setState(matchingState.get(0));
                break;
            }
        } while (true);
    }

    private void editProduct(Order order, List<Product> validProducts) {
        do {
            String productName = io.readString("Enter product (" + order.getProduct().getType() + "): ");
            // if user skipped then break
            if (productName.isEmpty()) {
                break;
            }
            // check for matching product
            List<Product> matchingProduct
                    = validProducts
                            .stream()
                            .filter((product) -> product.getType().equals(productName))
                            .collect(Collectors.toList());
            // if there was no match display error
            if (matchingProduct.isEmpty()) {
                displayInvalidProductMsg();
            } else {
                // otherwise we edit and break
                order.setProduct(matchingProduct.get(0));
                break;
            }
        } while (true);
    }

    private void editArea(Order order) {
        do {
            String newArea = io.readString("Enter new area (" + order.getAreaInSquareFeet().toPlainString() + "): ");
            // if user skipped then break
            if (newArea.isEmpty()) {
                break;
            }
            // check for valid big decimal
            try {
                BigDecimal bd = new BigDecimal(newArea).setScale(2,RoundingMode.HALF_UP);
                // if were positive then edit else show error
                if (bd.compareTo(BigDecimal.ZERO) > 0) {
                    order.setAreaInSquareFeet(bd);
                    break;
                } else {
                    displayErrorMsg("Area must be > 0");
                }
            } catch (NumberFormatException e) {
                displayErrorMsg("Invalid area");
            }
        } while (true);
    }
}
