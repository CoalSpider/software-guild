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
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestWordMin;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sg.view.ConsoleColors.*;

/**
 *
 * @author Ben Norman
 */
public class View {

    private final UserIO io;
    private static final String CANCELED = "CANCELED";
    private static final String ACTIVE = "ACTIVE";

    public View(UserIO io) {
        this.io = io;
    }

    public void displayMainMenu() {
        io.println(WHITE + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * " + RESET);
        io.println(WHITE + "* <<Flooring Program>>");
        io.println(WHITE + "*" + PURPLE + " 1. Display Orders");
        io.println(WHITE + "*" + BLUE + " 2. Add an Order");
        io.println(WHITE + "*" + CYAN + " 3. Edit an Order");
        io.println(WHITE + "*" + GREEN + " 4. Remmove an Order");
        io.println(WHITE + "*" + YELLOW + " 5. Save Current Work");
        io.println(WHITE + "*" + RED + " 6. Quit");
        io.println(WHITE + "*");
        io.println(WHITE + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
    }

    public void displayOrder(Order order) {
        List<Order> tmp = new ArrayList<>();
        tmp.add(order);
        displayOrders(tmp);
    }

    private void displayOrder(AsciiTable orderTable, Order order) {
        State s = order.getState();
        Product p = order.getProduct();
        String customerName = order.isDeleted() ? order.getCustomerName().replaceAll("\\[" + CANCELED + "\\]", "") : order.getCustomerName();
        customerName = customerName.replaceAll(" ", "_");
        orderTable.addRow(
                order.getOrderNumber(),
                customerName,
                order.getAreaInSquareFeet().toPlainString(),
                s.getName(),
                s.getTaxRate() + "%",
                p.getType(),
                "$" + p.getCostPerSquareFoot().toPlainString(),
                "$" + p.getLaborCostPerSquareFoot().toPlainString(),
                "$" + order.getMaterialCost().toEngineeringString(),
                "$" + order.getLaborCost().toPlainString(),
                "$" + order.getTax().toPlainString(),
                // mark as red for deleted
                "$" + order.getTotal().toPlainString(),
                order.isDeleted() ? "[" + CANCELED + "]" : "[" + ACTIVE + "]  ");
        orderTable.addRule();
    }

    public void displayOrders(List<Order> orders) {
        AsciiTable orderTable = new AsciiTable();
        orderTable.addRule();
        orderTable.addRow(
                "#",
                "Name",
                "Area ft^2",
                "State",
                "Tax Rate",
                "Type",
                "Material Cost",
                "Labor Cost",
                "Material Total",
                "Labor Total",
                "Tax",
                "Total",
                "Status");
        orderTable.addRule();
        for (Order order : orders) {
            displayOrder(orderTable, order);
        }
        orderTable.getRenderer().setCWC(new CWC_LongestWordMin(4));
        orderTable.setTextAlignment(TextAlignment.RIGHT);
        orderTable.getContext().setGrid(A7_Grids.minusBarPlus());
        String table = orderTable.render();
        table = table.replaceAll("\\$", YELLOW+"\\$"+RESET);
        table = table.replaceAll(CANCELED, RED+CANCELED+RESET+"");
        table = table.replaceAll(ACTIVE, GREEN+ACTIVE+RESET+"");
        table = table.replaceAll("\\+", WHITE+"\\+");
        table = table.replaceAll("_", " ");
        io.println(table);
    }

    public void displayPersistenceException(String message) {
        io.println(message);
    }

    public void displayAlreadyDeletedOrderMsg() {
        io.println("Order already deleted");
        promptContinue();
    }

    public void displayInvalidProductMsg() {
        io.println("product is not sold or does not exist");
    }

    public void displayInvalidStateMsg() {
        io.println("state does not exist or products are not sold there");
    }

    public void displayDateAfterTodayMsg() {
        io.println("Invalid date. Date must be before or on today");
    }

    public void displayInvalidDateMsg() {
        io.println("Invalid date. Date must be in form MM-dd-yyyy.");
    }

    public void displayGoodbyMessage() {
        io.println("Goodbye");
    }

    public void displayNoOrdersForDate() {
        io.println("No orders for given date");
    }

    public void promptContinue() {
        io.readString("Hit Enter To Continue...");
    }

    public void displayErrorMsg(String errorMsg) {
        io.println(errorMsg);
        promptContinue();
    }

    public void displayStates(List<State> states) {
        io.print("States: ");
        states.forEach((state) -> io.print(state.getName() + ", "));
        io.println("");
    }

    public void displayProducts(List<Product> products) {
        io.print("Products: ");
        products.forEach((product) -> io.print(product.getType() + ", "));
        io.println("");
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

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public LocalDate askForDate() {
        do {
            try {
                String dateString = io.readString("Enter Date MM-dd-yyyy: ");
                dateString = fixFormattingForSingleDigits(dateString);
                LocalDate date = LocalDate.parse(dateString, dateTimeFormat);
                if (date.isAfter(LocalDate.now())) {
                    this.displayDateAfterTodayMsg();
                } else {
                    return date;
                }
            } catch (DateTimeParseException e) {
                displayInvalidDateMsg();
            }
        } while (true);
    }

    private String fixFormattingForSingleDigits(String dateString) {
        StringBuilder newDateString = new StringBuilder();
        String[] dateParts = dateString.split("-");
        if (dateParts.length >= 1) {
            String s = dateParts[0];
            newDateString.append((s.length() == 1) ? 0 + s : s);
            newDateString.append("-");
        }
        if (dateParts.length >= 2) {
            String s = dateParts[1];
            newDateString.append((s.length() == 1) ? 0 + s : s);
            newDateString.append("-");
        }
        if (dateParts.length >= 3) {
            newDateString.append(dateParts[2]);
        }
        return newDateString.toString();
    }

    public String askForName() {
        String name = "";
        while ((name = io.readString("Enter Name: ")).isEmpty()) {
            io.println("name cannot be blank");
        }
        return name;
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
            if (input == -1) {
                io.println("Invalid order number");
            } else {
                return input;
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
        displayStates(validStates);
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
        displayProducts(validProducts);
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
                BigDecimal bd = new BigDecimal(newArea).setScale(2, RoundingMode.HALF_UP);
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
