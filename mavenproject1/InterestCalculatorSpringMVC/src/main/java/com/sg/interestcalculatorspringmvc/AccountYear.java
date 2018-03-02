/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.interestcalculatorspringmvc;

import java.math.BigDecimal;

/**
 *
 * @author Ben Norman
 */
public class AccountYear {

    private int year = 0;
    private BigDecimal startPrinciple = BigDecimal.ZERO;
    private BigDecimal intrestEarned = BigDecimal.ZERO;
    private BigDecimal endPrinciple = BigDecimal.ZERO;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getStartPrinciple() {
        return startPrinciple;
    }

    public void setStartPrinciple(BigDecimal startPrinciple) {
        this.startPrinciple = startPrinciple;
    }

    public BigDecimal getIntrestEarned() {
        return intrestEarned;
    }

    public void setIntrestEarned(BigDecimal intrestEarned) {
        this.intrestEarned = intrestEarned;
    }

    public BigDecimal getEndPrinciple() {
        return endPrinciple;
    }

    public void setEndPrinciple(BigDecimal endPrinciple) {
        this.endPrinciple = endPrinciple;
    }

}
