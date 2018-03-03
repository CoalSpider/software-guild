/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.VendableItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ben Norman
 */
@Component
public class DaoFileImpl implements Dao {
    
    private Map<String, VendableItem> inventory;
    
    private final String FILE_NAME = "inventory.txt";
    private static final String DELIMITER = "::";
    
    private void writeFile() throws PersistanceException {
        List<VendableItem> listOfItems = getAllItems();
        
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            
            StringBuilder lineBuilder = new StringBuilder();
            
            for (VendableItem item : listOfItems) {
                lineBuilder.append(item.getName());
                lineBuilder.append(DELIMITER);
                lineBuilder.append(item.getPrice());
                lineBuilder.append(DELIMITER);
                lineBuilder.append(item.getCount());
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

        // create the file if it doesnt exist
        File file = new File(FILE_NAME);
        if (file.exists() == false) {
            // create in default directory
            try {
                file.createNewFile();
                createDefaultItemSet();
            } catch (IOException io) {
                throw new PersistanceException("Cant find machine");
            }
        }
        
        try (Scanner sc = new Scanner(file)) {
            
            while (sc.hasNext()) {
                
                String[] lineParts = sc.nextLine().split(DELIMITER);
                
                String name = lineParts[0];
                BigDecimal price = new BigDecimal(lineParts[1]);
                int quantity = Integer.parseInt(lineParts[2]);
                
                VendableItem item = new VendableItem(name, price, quantity);
                
                inventory.put(name, item);
            }
        
        } catch (IOException io) {
            // should never happen
            throw new PersistanceException("Cant find items");
        } catch (NumberFormatException nfe) {
            throw new PersistanceException("Error getting item");
        }
        
        if(getAllItems().isEmpty()){
            createDefaultItemSet();
        }
    }
    
    private void createDefaultItemSet() throws PersistanceException{
        VendableItem snickers = new VendableItem("Snickers",new BigDecimal("1.25"),10);
        VendableItem MtnDew = new VendableItem("Mtn Dew",new BigDecimal("1.75"),3);
        VendableItem cookie = new VendableItem("Snickers",new BigDecimal("0.25"),10);
        VendableItem MnMs = new VendableItem("MnMs",new BigDecimal("1.00"),1);
        VendableItem doritoes = new VendableItem("Bag of Doritos",new BigDecimal("0.75"),25);
        VendableItem solidGoldWatch = new VendableItem("Solid Gold Watch",new BigDecimal("2000.00"),0);
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
        if(newCount < 0){
            throw new PersistanceException("newCount must be >= 0");
        }
        getItem(name).setCount(newCount);
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
