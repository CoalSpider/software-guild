/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class ProductDaoFileImpl implements ProductDao {

    private static final String FILE_NAME = "products";
    private static final String DELIMITER = ",";

    private List<Product> products;

    private void readProducts() throws PersistenceException {
        if (products != null) {
            return;
        }
        products = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_NAME))) {
            // skip header line
            sc.nextLine();
            while(sc.hasNext()){
                String[] tokens = sc.nextLine().split(DELIMITER);
                products.add(new Product(tokens[0],new BigDecimal(tokens[1]),new BigDecimal(tokens[2])));
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceException("could not find product file " + e.getMessage());
        } catch(IndexOutOfBoundsException | NumberFormatException e){
            throw new PersistenceException("error reading product " + e.getMessage());
        }
    }

    @Override
    public List<Product> getProducts() throws PersistenceException {
        readProducts();
        // defensive copy
        return new ArrayList<>(products);
    }

}
