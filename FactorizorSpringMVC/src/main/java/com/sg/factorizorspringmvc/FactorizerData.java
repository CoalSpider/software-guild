/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.factorizorspringmvc;

import java.util.List;

/**
 *
 * @author Ben Norman
 */
public class FactorizerData {
    private int numberToFactor;
    private List<Integer> factors;
    private boolean isPrime;
    private boolean isPerfect;

    public FactorizerData(int numberToFactor, List<Integer> factorList, boolean isPrime, boolean isPerfect) {
        this.numberToFactor = numberToFactor;
        this.factors = factorList;
        this.isPrime = isPrime;
        this.isPerfect = isPerfect;
    }

    public int getNumberToFactor() {
        return numberToFactor;
    }

    public void setNumberToFactor(int numberToFactor) {
        this.numberToFactor = numberToFactor;
    }

    public List<Integer> getFactors() {
        return factors;
    }

    public void setFactors(List<Integer> factors) {
        this.factors = factors;
    }

    public boolean isIsPrime() {
        return isPrime;
    }

    public void setIsPrime(boolean isPrime) {
        this.isPrime = isPrime;
    }

    public boolean isIsPerfect() {
        return isPerfect;
    }

    public void setIsPerfect(boolean isPerfect) {
        this.isPerfect = isPerfect;
    }
    
    
}
