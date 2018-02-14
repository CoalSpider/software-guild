/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Ben Norman
 */
public class FlooringUtil {
    public static final MathContext MONEY_CONTEXT = new MathContext(2,RoundingMode.HALF_DOWN);
    
    public static final BigDecimal DIVISOR = new BigDecimal(100);
    public static BigDecimal percentToDecimal(BigDecimal percent){
        return percent.divide(DIVISOR);
    }
}
