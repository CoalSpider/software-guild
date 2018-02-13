/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.Product;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface ProductDao {

    /**
     * @return a list of products sold
     */
    List<Product> getProducts();
}
