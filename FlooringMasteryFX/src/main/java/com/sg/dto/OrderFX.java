/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dto;

import java.math.BigDecimal;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ben Norman
 */
public class OrderFX {

    private final IntegerProperty orderNumber = new SimpleIntegerProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> areaFtSqrd = new SimpleObjectProperty<>();
    private final ObjectProperty<Product> product = new SimpleObjectProperty<>();
    private final ObjectProperty<State> state = new SimpleObjectProperty<>();

    public int getOrderNumber() {
        return orderNumber.get();
    }

    public void setOrderNumber(int value) {
        orderNumber.set(value);
    }

    public IntegerProperty orderNumberProperty() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String value) {
        customerName.set(value);
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public BigDecimal getAreaFtSqrd() {
        return areaFtSqrd.get();
    }

    public void setAreaFtSqrd(BigDecimal value) {
        areaFtSqrd.set(value);
    }

    public ObjectProperty areaFtSqrdProperty() {
        return areaFtSqrd;
    }

    public Product getProduct() {
        return product.get();
    }

    public void setProduct(Product value) {
        product.set(value);
    }

    public ObjectProperty productProperty() {
        return product;
    }

    public State getState() {
        return state.get();
    }

    public void setState(State value) {
        state.set(value);
    }

    public ObjectProperty stateProperty() {
        return state;
    }
}
