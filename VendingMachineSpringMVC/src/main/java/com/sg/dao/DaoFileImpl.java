/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.VendableItem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ben Norman
 */
@Component
public class DaoFileImpl implements Dao {

    @Value("classpath:inventory.txt")
    private Resource res;

    private Map<String, VendableItem> inventory;

    private static final String DELIMITER = "::";

    private void writeFile() throws PersistanceException {
        List<VendableItem> listOfItems = getAllItems();

        try (PrintWriter writer = new PrintWriter(res.getFilename())) {

            StringBuilder lineBuilder = new StringBuilder();

            for (VendableItem item : listOfItems) {
                lineBuilder.append(item.getNum());
                lineBuilder.append(DELIMITER);
                lineBuilder.append(item.getName());
                lineBuilder.append(DELIMITER);
                lineBuilder.append(item.getPrice());
                lineBuilder.append(DELIMITER);
                lineBuilder.append(item.getQuantity());
                lineBuilder.append("\n");
            }

            writer.println(lineBuilder.toString());
            writer.flush();

            lineBuilder.delete(0, lineBuilder.length());

        } catch (FileNotFoundException ex) {
            // should never happen as read file is always called before write
            throw new PersistanceException("Cant find items");
        }
    }

    private void readFile() throws PersistanceException {
        // if file has already been read in then return
        if (inventory != null) {
            return;
        } else {
            inventory = new TreeMap<>();
        }
  
        
        try (Scanner sc = new Scanner(res.getFile())) {
            while (sc.hasNext()) {

                String[] lineParts = sc.nextLine().split(DELIMITER);

                int num = Integer.parseInt(lineParts[0]);
                String name = lineParts[1];
                BigDecimal price = new BigDecimal(lineParts[2]);
                int quantity = Integer.parseInt(lineParts[3]);

                VendableItem item = new VendableItem(num, name, price, quantity);

                inventory.put(name, item);
            }

            if (getAllItems().isEmpty()) {
                createDefaultItemSet();
            }
        } catch(FileNotFoundException fne){
            throw new PersistanceException("could not find file " + res.getFilename());
        }catch(IOException io){
            throw new PersistanceException("io exception " + res);
        }
    }

    private void createDefaultItemSet() throws PersistanceException {
        VendableItem snickers = new VendableItem(1, "Snickers", new BigDecimal("1.25"), 10);
        VendableItem MtnDew = new VendableItem(2, "Mtn Dew", new BigDecimal("1.75"), 3);
        VendableItem cookie = new VendableItem(3, "Skittles", new BigDecimal("0.25"), 10);
        VendableItem MnMs = new VendableItem(4, "MnMs", new BigDecimal("1.00"), 1);
        VendableItem doritoes = new VendableItem(5, "Bag of Doritos", new BigDecimal("0.75"), 25);
        VendableItem solidGoldWatch = new VendableItem(6, "Solid Gold Watch", new BigDecimal("2000.00"), 0);
        inventory.put(snickers.getName(), snickers);
        inventory.put(MtnDew.getName(), MtnDew);
        inventory.put(cookie.getName(), cookie);
        inventory.put(MnMs.getName(), MnMs);
        inventory.put(doritoes.getName(), doritoes);
        inventory.put(solidGoldWatch.getName(), solidGoldWatch);
        writeFile();
    }

    @Override
    public VendableItem getItem(String name) throws PersistanceException {
        readFile();
        return inventory.get(name);
    }

    @Override
    public void setCount(String name, int newCount) throws PersistanceException {
        if (newCount < 0) {
            throw new PersistanceException("newCount must be >= 0");
        }
        getItem(name).setQuantity(newCount);
        writeFile();
    }

    @Override
    public List<VendableItem> getAllItems() throws PersistanceException {
        readFile();
        return new ArrayList<>(inventory.values());
    }

    /* 
    * ==========================================================================
    * TO BE IMPLEMENTED AS PART OF ADMIN INTERFACE
    * ==========================================================================
     */
    @Override
    public VendableItem addItem(VendableItem item) throws PersistanceException {
        readFile();
        VendableItem previousMapping = inventory.put(item.getName(), item);
        writeFile();
        return previousMapping;
    }

    @Override
    public VendableItem removeItem(String itemName) throws PersistanceException {
        readFile();
        VendableItem removed = inventory.remove(itemName);
        writeFile();
        return removed;
    }
}
