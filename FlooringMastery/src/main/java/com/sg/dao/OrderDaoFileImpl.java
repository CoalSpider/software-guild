/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.exceptions.DuplicateOrderException;
import com.sg.exceptions.PersistenceException;
import com.sg.dto.Order;
import com.sg.dto.Product;
import com.sg.dto.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Ben Norman
 */
public class OrderDaoFileImpl implements OrderDao {

    private static final String FOLDER_NAME = "orders";
    private static final String FILE_EXTENSION = ".txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMddyyyy");
    private static final String DELIMITER = ",";
    private static final String ORDER_FILE_HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";

    // all the orders that exist
    private final Map<LocalDate, List<Order>> allOrders = new TreeMap<>();

    private void writeData() throws PersistenceException {
        StringBuilder fileNameBuilder = new StringBuilder();
        for (LocalDate date : allOrders.keySet()) {
            // clear file builder
            fileNameBuilder.delete(0, fileNameBuilder.length());
            // now build the file name
            fileNameBuilder.append("Order_");
            fileNameBuilder.append(date.format(DATE_FORMAT));
            fileNameBuilder.append(FILE_EXTENSION); // extension
            File file = new File(FOLDER_NAME, fileNameBuilder.toString());
            // if the file doesnt exist
            if (file.exists() == false) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    throw new PersistenceException("could not create file for " + date);
                }
            }
            writeFileForDate(file, date);
        }
    }

    // helper method for writeData()
    private void writeFileForDate(File f, LocalDate date) throws PersistenceException {
        try (PrintWriter writer = new PrintWriter(f)) {
            // write header line
            writer.println(ORDER_FILE_HEADER);
            List<Order> orders = allOrders.get(date);
            StringBuilder orderBuilder = new StringBuilder();
            for (Order order : orders) {
                // clear order builder
                orderBuilder.delete(0, orderBuilder.length());
                // builder new order
                String orderString = orderToFileString(order, orderBuilder);
                // write to file
                writer.println(orderString);
                // force immediate write
                writer.flush();
            }
        } catch (FileNotFoundException ex) {
            throw new PersistenceException("could not save orders for " + f);
        }
    }

    private String orderToFileString(Order order, StringBuilder orderBuilder) {
        //OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
        orderBuilder.append(order.getOrderNumber());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getCustomerName());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getState().getName());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getState().getTaxRate().toPlainString());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getProduct().getType());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getAreaInSquareFeet().toPlainString());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getProduct().getCostPerSquareFoot().toPlainString());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getProduct().getLaborCostPerSquareFoot().toPlainString());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getMaterialCost().toPlainString());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getLaborCost().toPlainString());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.getTax().toPlainString());
        orderBuilder.append(DELIMITER);
        orderBuilder.append(order.isDeleted() ? "0" : order.getTotal().toPlainString());
        return orderBuilder.toString();
    }

    /**
     * This method will load ALL order names into memory
     *
     * @return null if the order file was not found with the given date
     * @throws PersistenceException if there was a error finding the order or if
     * more than one file found matching the date
     */
    private File getOrderFile(LocalDate date) throws PersistenceException {
        String dateAsString = date.format(DATE_FORMAT);
        try (Stream<Path> fileStream = Files.walk(Paths.get(FOLDER_NAME))) {
            List<File> files = fileStream.map(Path::toFile).filter((file) -> file.getName().contains(dateAsString)).collect(Collectors.toList());
            if (files.isEmpty()) {
                return null;
            }
            if (files.size() > 1) {
                throw new PersistenceException("duplicate order file found");
            }
            return files.get(0);
        } catch (IOException ex) {
            throw new PersistenceException("could not find orders");
        }
    }

    /**
     * Literally do nothing if the file is null
     */
    private void readOrderFileIfNotNull(File f) throws PersistenceException {
        if (f != null) {
            readOrderFile(f);
        }
    }

    /**
     * This method assumes a valid file (non null) with a valid file name
     */
    private void readOrderFile(File f) throws PersistenceException {
        LocalDate date;
        try {
            // strip file extension and extract the date out of the filename
            date = LocalDate.parse(f.getName().replace(FILE_EXTENSION, "").split("_")[1], DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new PersistenceException("could not read file");
        }
        // add date to map
        allOrders.putIfAbsent(date, new ArrayList<>());
        try (Scanner sc = new Scanner(f)) {
            // confirm the file is not empty
            if (sc.hasNext() == false) {
                throw new PersistenceException("error reading order file " + f.getName());
            }
            // skip the header line
            sc.nextLine();
            while (sc.hasNext()) {
                String line = sc.nextLine();
                allOrders.get(date).add(orderFromString(line));
            }
        } catch (FileNotFoundException ex) {
            throw new PersistenceException("could not find file " + f.getName());
        } catch (NumberFormatException nfe) {
            System.out.println("NFE = " + nfe);
            throw new PersistenceException("error reading order in file " + f.getName());
        }
    }

    private Order orderFromString(String line) throws NumberFormatException {
        //OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total
        String[] tokens = line.split(DELIMITER);

        int orderNumber = Integer.parseInt(tokens[0]);
        String customerName = tokens[1];
        String stateName = tokens[2];
        BigDecimal stateTaxRate = new BigDecimal(tokens[3]);
        String productType = tokens[4];
        BigDecimal area = new BigDecimal(tokens[5]);
        BigDecimal costPerSqrFt = new BigDecimal(tokens[6]);
        BigDecimal laborCostPerSqrFt = new BigDecimal(tokens[7]);
        BigDecimal materialCost = new BigDecimal(tokens[8]);
        BigDecimal laborCost = new BigDecimal(tokens[9]);
        BigDecimal tax = new BigDecimal(tokens[10]);
        BigDecimal total = tokens[11].equals("0") ? BigDecimal.ZERO : new BigDecimal(tokens[11]);

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setCustomerName(customerName);
        order.setState(new State(stateName, stateTaxRate));
        order.setProduct(new Product(productType, costPerSqrFt, laborCostPerSqrFt));
        order.setAreaInSquareFeet(area);
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);

        return order;
    }

    @Override
    public void createOrder(Order order, LocalDate date) throws DuplicateOrderException, PersistenceException {
        readOrderFileIfNotNull(getOrderFile(date));
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
        readOrderFileIfNotNull(getOrderFile(date));
        if (allOrders.containsKey(date)) {
            if (allOrders.get(date).contains(order)) {
                order.setTotal(BigDecimal.ZERO);
            } else {
                throw new PersistenceException("order does not exist for given date");
            }
        } else {
            throw new PersistenceException("no orders for the given date");
        }
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws PersistenceException {
        readOrderFileIfNotNull(getOrderFile(date));
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
    public List<Order> getOrders(LocalDate date) throws PersistenceException {
        readOrderFileIfNotNull(getOrderFile(date));
        return allOrders.getOrDefault(date, new ArrayList<>());
    }

    @Override
    public int getNextOrderNumber(LocalDate date) throws PersistenceException {
        readOrderFileIfNotNull(getOrderFile(date));
        return allOrders.getOrDefault(date, new ArrayList<>())
                .stream()
                .map(Order::getOrderNumber)
                .reduce(0, (orderNumA, orderNumB) -> (orderNumA > orderNumB) ? orderNumA : orderNumB) + 1;
    }

    @Override
    public void saveData() throws PersistenceException {
        writeData();
    }

}
