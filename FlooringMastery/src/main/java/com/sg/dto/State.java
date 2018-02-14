/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Ben Norman
 */
public class State {
    private final String name;
    private final BigDecimal taxRate;

    /**
     * @param name the state name
     * @param taxRate the tax rate of the state as a percent
     */
    public State(String name, BigDecimal taxRate) {
        this.name = name;
        this.taxRate = taxRate;
    }

    public String getName() {
        return name;
    }

    /** @return the tax rate as a percent*/
    public BigDecimal getTaxRate() {
        return taxRate;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.taxRate);
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
        final State other = (State) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.taxRate.toPlainString(), other.taxRate.toPlainString())) {
            return false;
        }
        return true;
    }
    
}
