/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dto;

import static com.sg.common.MoneyUtil.MONEY_ROUND;
import static com.sg.common.MoneyUtil.SCALE;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Ben Norman
 */
public class Order {

    private static final String CANCELED = "[CANCELED]";

    // generated by dao, will be unqiue
    private int orderNumber;
    private String customerName;
    private BigDecimal areaInSquareFeet;
    // calculated data
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    // the state the order was placed in/for
    private State state;
    // the product ordered
    private Product product;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (this.customerName!=null && this.customerName.contains(CANCELED)) {
            // do nothing
        } else {
            this.customerName = customerName;
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        nullCalculated();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        nullCalculated();
    }

    public BigDecimal getAreaInSquareFeet() {
        return areaInSquareFeet;
    }

    public void setAreaInSquareFeet(BigDecimal areaInSquareFeet) {
        this.areaInSquareFeet = areaInSquareFeet.setScale(SCALE, MONEY_ROUND);
        nullCalculated();
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost.setScale(SCALE, MONEY_ROUND);
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost.setScale(SCALE, MONEY_ROUND);
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax.setScale(SCALE, MONEY_ROUND);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getMaterialCost() {
        if (materialCost == null) {
            // make sure scale is 2
            setMaterialCost(product.getCostPerSquareFoot()
                    .multiply(areaInSquareFeet));
        }
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        if (laborCost == null) {
            // make sure scale is 2
            setLaborCost(product.getLaborCostPerSquareFoot()
                    .multiply(areaInSquareFeet));
        }
        return laborCost;
    }

    public BigDecimal getTax() {
        if (tax == null) {
            // tax rates are stored as a percent we need to convert them
            BigDecimal taxRate = state.getTaxRate().movePointLeft(2);
            // make sure scale is 2
            setTax(getMaterialCost()
                    .add(getLaborCost())
                    .multiply(taxRate));
        }
        return tax;
    }

    public BigDecimal getTotal() {
        if (total == null) {
            // make sure scale is 2
            setTotal(getMaterialCost()
                    .add(getLaborCost())
                    .add(getTax()));
        }
        return total;
    }

    public boolean isDeleted() {
        return getCustomerName().contains(CANCELED);
    }

    public void setDeleted() {
        setCustomerName(CANCELED + getCustomerName());
    }

    // when the user edits data we want to null out calculated fields so they are recalculated next time we call the Fgetters
    private void nullCalculated() {
        materialCost = null;
        laborCost = null;
        tax = null;
        total = null;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.orderNumber;
        hash = 47 * hash + Objects.hashCode(this.customerName);
        hash = 47 * hash + Objects.hashCode(this.areaInSquareFeet);
        hash = 47 * hash + Objects.hashCode(this.materialCost);
        hash = 47 * hash + Objects.hashCode(this.laborCost);
        hash = 47 * hash + Objects.hashCode(this.tax);
        hash = 47 * hash + Objects.hashCode(this.total);
        hash = 47 * hash + Objects.hashCode(this.state);
        hash = 47 * hash + Objects.hashCode(this.product);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        // checks plain string to check equality properly between exponential and non exponential forms
        if (!Objects.equals(this.areaInSquareFeet.toPlainString(), other.areaInSquareFeet.toPlainString())) {
            return false;
        }
        // checks plain string to check equality properly between exponential and non exponential forms
        if (!Objects.equals(this.materialCost.toPlainString(), other.materialCost.toPlainString())) {
            return false;
        }
        // checks plain string to check equality properly between exponential and non exponential forms
        if (!Objects.equals(this.laborCost.toPlainString(), other.laborCost.toPlainString())) {
            return false;
        }
        // checks plain string to check equality properly between exponential and non exponential forms
        if (!Objects.equals(this.tax.toPlainString(), other.tax.toPlainString())) {
            return false;
        }
        // checks plain string to check equality properly between exponential and non exponential forms
        if (!Objects.equals(this.total.toPlainString(), other.total.toPlainString())) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderNumber="
                + orderNumber
                + ", customerName="
                + customerName
                + ", areaInSquareFeet="
                + areaInSquareFeet.toPlainString()
                + ", materialCost="
                + materialCost.toPlainString()
                + ", laborCost="
                + laborCost.toPlainString()
                + ", tax="
                + tax.toPlainString()
                + ", total="
                + total.toPlainString()
                + ", state="
                + state
                + ", product="
                + product
                + '}';
    }

}
