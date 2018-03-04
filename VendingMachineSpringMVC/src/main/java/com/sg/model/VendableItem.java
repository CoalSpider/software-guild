/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.model;

import java.math.BigDecimal;

/**
 *
 * @author Ben Norman
 */
public class VendableItem {
    private int num;
    private String name;
    private BigDecimal price;
    private int quantity;
    
    public VendableItem(){
        this.num = -1;
        this.name = "";
        this.price = BigDecimal.ZERO;
        this.quantity = 0;
    }
    
    public VendableItem(int num, String name, BigDecimal price, int quantity) {
        this.num = num;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
